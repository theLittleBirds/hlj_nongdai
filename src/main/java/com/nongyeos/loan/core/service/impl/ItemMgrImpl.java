package com.nongyeos.loan.core.service.impl;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.nongyeos.loan.admin.entity.BusApplyPieceExt;
import com.nongyeos.loan.admin.mapper.BusApplyPieceExtMapper;
import com.nongyeos.loan.app.entity.AppEntity;
import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.mapper.AppEntityMapper;
import com.nongyeos.loan.app.mapper.AppItemMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.ItemMgr;

@Service("itemMgrService")
public class ItemMgrImpl implements ItemMgr {

	@Autowired
	private AppItemMapper itemMapper;
	@Autowired
	private AppEntityMapper entityMapper;
	@Autowired
	private ItemMgrImpl itemMgr;
	@Autowired
	private SqlSession session;
	@Autowired
	private BusApplyPieceExtMapper applyPieceExtMapper;
	@Autowired
	private DecisionCaseMgrImpl CaseMgrImpl;
	private Constants  constants;
	
	@Override
	public Object getObjectValue(BusinessObject bo, String itemId) throws Exception {
		Object result = null;
		String mainId = "";
		String appId=bo.getCurApp().getAppId();
		String entityId=null;
		AppItem item=null;
		List<AppEntity> entityList=null;
		AppEntity appEntity=null;
		Object bean = bo.getBean();
		try {
			//获得数据项,数据实体
			if(itemId != null && !itemId.equals("") && appId != null && !appId.equals("")){
				item=itemMapper.selectByPrimaryKey(itemId);
				if(item!=null){
					entityId=item.getEntityId();
				}
				entityList=entityMapper.selectAll(appId);
			}
			//确定数据项对应实体
			if(entityId != null && !entityId.equals("") && entityList != null && entityList.size() > 0){
				for(int i=0;i<entityList.size();i++){
					if(entityList.get(i).getEntityId().equals(entityId)){
						appEntity=entityList.get(i);
						break;
					}
				}
			}
			String fieldName=item.getFieldName();
			if(fieldName == null || fieldName.equals("")){
				throw new Exception(item.getCname() + "数据项字段名称fieldName为空");
			}else{
				//主实体
				if(appEntity.getType()==constants.MAIN_ENTITYTYPE){
					Field field = Class.forName(bean.getClass().getName()).getDeclaredField(fieldName);
					if(field != null){
						field.setAccessible(true);
						result=field.get(bean);
					}
				}else if(appEntity.getType()==constants.SON_ENTITYTYPE){//子实体
					String parentItemId=appEntity.getParentItemId();
					if(parentItemId == null || parentItemId.equals("")){
						throw new Exception("子实体parentItemId为空");
					}else{
						AppItem item1=itemMapper.selectByPrimaryKey(parentItemId);
						Field field = Class.forName(bean.getClass().getName()).getDeclaredField(item1.getFieldName());
						if(field != null){
							field.setAccessible(true);
							mainId=(String) field.get(bean);//主实体id
						}
						if(item.getDesignType()==constants.APPITEM_BASE){//基本型
							//根据实体确定className,根据className得到实现类,传参并执行实现类里面的方法。
							String className=itemMgr.getClassName(appEntity);
							Class beanImpl=Class.forName(className);
							Method method=beanImpl.getMethod("getSonEntryByParentItemId",String.class,SqlSession.class);
							List<Object> listBeanSon= (List<Object>) method.invoke(beanImpl.newInstance(),mainId,session);
							if(listBeanSon != null){
								if(listBeanSon.size() == 1){
									Field field1 = Class.forName(listBeanSon.get(0).getClass().getName()).getDeclaredField(fieldName);
									if(field1!=null){
										field1.setAccessible(true);
										result=field1.get(listBeanSon.get(0));
									}
								}else if(listBeanSon.size() > 1){
									for(int i=0;i<listBeanSon.size();i++){
										Object beanSon=listBeanSon.get(i);
										String caseId=item.getPcId();
										BusinessObject co=new BusinessObject(bo.getEntry(), beanSon, bo.getCurApp());
										if(CaseMgrImpl.logicExpress2(co, caseId)){
											Field field2 = Class.forName(beanSon.getClass().getName()).getDeclaredField(fieldName);
											field2.setAccessible(true);
											result=field2.get(beanSon);
											return result;
										}
									}
								}
							}
						}else if(item.getDesignType()==constants.APPITEM_EXCUTION){//扩展型
							BusApplyPieceExt busApplyPieceExt = null;
							busApplyPieceExt = itemMgr.getByItemIdAndIntoPieceId(mainId, itemId);
							if(busApplyPieceExt != null){
								result = busApplyPieceExt.getItemValue();	
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception (item.getCname() + "数据项取值失败");
		}
		return result;
	}
	
	
	//不管item是啥，直接取值
	@Override
	public Object getObjectItemValue(BusinessObject co, String itemId) throws Exception{
		Object result = null;
		AppItem item = null;
		try{
			Object bean = co.getBean();
			item=itemMapper.selectByPrimaryKey(itemId);
			if(item != null){
				String fieldName=item.getFieldName();
				if(fieldName == null || fieldName.equals("")){
					throw new Exception(item.getCname() + "数据项字段名fieldName为空");
				}else{
					Field field = Class.forName(bean.getClass().getName()).getDeclaredField(fieldName);
					field.setAccessible(true);
					result=field.get(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (item.getCname() + "数据项取值失败");
		}
		return result;
	}
	//更新一个值
	@Override
	public void setObjectValue(BusinessObject bo, String itemId, Object newValue) throws Exception {
		AppItem item = null;
		String entityId = null;
		AppEntity entity = null;
		String fieldName = null;
		String mainId = "";
		Object bean = bo.getBean();
		try{
			Class cla = Class.forName(bean.getClass().getName());
			if(itemId != null && !itemId.equals("")){
				item=itemMapper.selectByPrimaryKey(itemId);//获得item
				if(item!=null){
					entityId=item.getEntityId();
					entity=entityMapper.selectByPrimaryKey(entityId);//获得entity
					fieldName=item.getFieldName();
					if(fieldName == null || fieldName.equals("")){
						throw new Exception("数据项字段名fieldName为空");
					}else{
						if(entity.getType()==constants.MAIN_ENTITYTYPE){//主实体
							Field field = cla.getDeclaredField(fieldName);
							if(field != null){
								field.setAccessible(true);
								field.set(bean, newValue);//bean-瞬时态数据
								String className=itemMgr.getClassName(entity);
								Class beanImpl=Class.forName(className);
								Method method0=beanImpl.getMethod("updateBean",Object.class,SqlSession.class);
								method0.invoke(beanImpl.newInstance(),bean,session);//持久态数据
							}
						}else if(entity.getType()==constants.SON_ENTITYTYPE){//子实体
							String parentItemId=entity.getParentItemId();
							if(parentItemId == null || parentItemId.equals("")){
								throw new Exception("子实体parentItemId为空");
							}else{
								AppItem item1=itemMapper.selectByPrimaryKey(parentItemId);
								Field field = Class.forName(bean.getClass().getName()).getDeclaredField(item1.getFieldName());
								if(field != null){
									field.setAccessible(true);
									mainId=(String) field.get(bean);//主实体id
								}
								if(item.getDesignType()==constants.APPITEM_BASE){//基本型
									//根据实体确定className,根据className得到实现类,传参并执行实现类里面的方法。
									String className=itemMgr.getClassName(entity);
									Class beanImpl=Class.forName(className);
									Method method=beanImpl.getMethod("getSonEntryByParentItemId",String.class,SqlSession.class);
									List<Object> listBeanSon= (List<Object>) method.invoke(beanImpl.newInstance(),mainId,session);
									if(listBeanSon != null){
										if(listBeanSon.size() == 1){
											Field field1 = Class.forName(listBeanSon.get(0).getClass().getName()).getDeclaredField(fieldName);
											if(field1!=null){
												field1.setAccessible(true);
												field1.set(listBeanSon.get(0), newValue);//bean-瞬时态数据
												Method method1=beanImpl.getMethod("updateBean",Object.class,SqlSession.class);
												method1.invoke(beanImpl.newInstance(),listBeanSon.get(0),session);
											}
										}else if(listBeanSon.size() > 1){
											for(int i=0;i<listBeanSon.size();i++){
												Object beanSon=listBeanSon.get(i);
												String caseId=item.getPcId();
												bo.setBean(beanSon);
												if(CaseMgrImpl.logicExpress2(bo, caseId)){
													Field field2 = Class.forName(beanSon.getClass().getName()).getDeclaredField(fieldName);
													field2.setAccessible(true);
													field2.set(beanSon, newValue);//bean-瞬时态数据
													Method method2=beanImpl.getMethod("updateBean",Object.class,SqlSession.class);
													method2.invoke(beanImpl.newInstance(),beanSon,session);
												}
											}
										}
									}
								}else if(item.getDesignType()==constants.APPITEM_EXCUTION){//扩展型
									BusApplyPieceExt busApplyPieceExt = null;
									busApplyPieceExt = itemMgr.getByItemIdAndIntoPieceId(mainId, itemId);
									busApplyPieceExt.setItemValue((String) newValue);
									applyPieceExtMapper.updateByPrimaryKeySelective(busApplyPieceExt);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("数据项取值失败");
		}
	}
	
		//根据传入的实体获取实体对应的serviceImpl类
		private  String getClassName(AppEntity appEntity) throws Exception{
			String serviceImplName="";
			String beanClassName=appEntity.getClass().getName();
			try {
				if(beanClassName == null || beanClassName.equals("")){
					throw new Exception("fieldName为空");
				}else{
					String[] serviceName=beanClassName.split("\\.");
					serviceName[serviceName.length-3]="admin";
					serviceName[serviceName.length-2]="service.impl";
					serviceName[serviceName.length-1]=appEntity.getClassName();
					for(int i=0;i<serviceName.length;i++){
						if(i>0)
							serviceImplName+=".";
						serviceImplName+=serviceName[i];
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception ("实体对象获取失败");
			}
			return serviceImplName;
		}
		
	//根据itemId和进件表id在扩展数据表中寻找数据
	private BusApplyPieceExt getByItemIdAndIntoPieceId(String intoPieceId,String itemId) throws Exception{
		BusApplyPieceExt busApplyPieceExt=null;
		try{
			if(itemId!=null && !itemId.equals("") && intoPieceId!=null && !intoPieceId.equals("")){
				busApplyPieceExt=applyPieceExtMapper.getByItemIdAndIntoPieceId(intoPieceId,itemId);
			}else{
				throw new Exception("intoPieceId或者itemId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("数据项取值失败");
		}
		return busApplyPieceExt;
	}
}
