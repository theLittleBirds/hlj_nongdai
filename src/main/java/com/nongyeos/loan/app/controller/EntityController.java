package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppAppright;
import com.nongyeos.loan.app.entity.AppEntity;
import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.service.IAppItemService;
import com.nongyeos.loan.app.service.IApprightService;
import com.nongyeos.loan.app.service.IEntityService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/entity")
public class EntityController {

	@Resource
	private IEntityService entityService;
	@Resource
	private ISysSenoService sysSenoService;
	@Resource
	private IApprightService apprightService;
	@Resource
	private IAppItemService itemService;

	/**
	 * 实体--分页全查
	 */
	@RequestMapping(value = "selectAll", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<AppEntity> partnerPage(String appId) {
		PageBeanUtil<AppEntity> pi = new PageBeanUtil<AppEntity>();
		try {
			List<AppEntity> list = entityService.selectAll(appId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	/**
	 * 实体--添加和修改
	 * 
	 * @param appEntity
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppEntity appEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (appEntity.getEntityId() == null || appEntity.getEntityId().equals("")) {
				appEntity.setEntityId(sysSenoService.getNextString(Constants.TABLE_NAME_ENTITY, 10, "ET", 1));
				if(appEntity.getParentId() != null && !appEntity.getParentId().equals("")){
					AppEntity beanAppEntity = entityService.getAppEntityByEntityId(appEntity.getParentId());
				    if(beanAppEntity != null && beanAppEntity.getParentIds() != null && !beanAppEntity.getParentIds().equals("")){
				    	appEntity.setParentIds(beanAppEntity.getParentIds()+";"+beanAppEntity.getEntityId());
				    }else{
				    	appEntity.setParentIds(beanAppEntity.getEntityId());
				    }
				}
				entityService.addEntity(appEntity);
				map.put("msg", "添加成功");
			} else {
				if(appEntity.getParentId() != null && !appEntity.getParentId().equals("")){
					AppEntity beanAppEntity = entityService.getAppEntityByEntityId(appEntity.getParentId());
					 if(beanAppEntity != null && beanAppEntity.getParentIds() != null && !beanAppEntity.getParentIds().equals("")){
					     appEntity.setParentIds(beanAppEntity.getParentIds()+";"+beanAppEntity.getEntityId());
					 }else{
					     appEntity.setParentIds(beanAppEntity.getEntityId());
					 }
				}
				entityService.updateEntity(appEntity);
				map.put("msg", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 实体--单个删除和批量删除
	 * 
	 * @param currIds
	 * @return
	 */
	@RequestMapping("/delEntity")
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] enitys = currIds.split(",");
				for (String enityId : enitys) {
					entityService.deleteEntity(enityId);
				}
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/saveRole")
	@ResponseBody
	public Map<String, Object> saveRole(String roleCodes,String entityId)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(entityId != null && !entityId.equals("")){
			apprightService.deleteByEntityId(entityId);
			if(roleCodes != null && !roleCodes.equals("")){
				AppAppright beanAppAppright = new AppAppright();
				String[] roleIds = roleCodes.split(",");
				for(String roleId : roleIds){
					beanAppAppright.setEntityId(entityId);
					beanAppAppright.setObjectId(roleId);
					beanAppAppright.setControlType((short)1);
					beanAppAppright.setObjectType((short)1);
					apprightService.insertRole(beanAppAppright);
				}
			}
			map.put("msg", "保存成功");
		}else{
			map.put("msg", "保存失败");
		}
		return map;
	}
	
	@RequestMapping("/getMainEntityDS")
	@ResponseBody
	public String selectMainEntity(String appId){
		String strJson = "[";
		List<AppEntity> list= entityService.getMainEntity(appId);
		if(list != null && list.size() > 0){
			AppEntity beanAppEntity = null;
			for(int i = 0;i <list.size();i++){
				beanAppEntity = list.get(i);
				if(beanAppEntity != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanAppEntity.getNameCn() + "', parameterValue:'" + beanAppEntity.getEntityId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	//应用id--》实体--》数据项
	@RequestMapping(value = "getItem1DS", method = RequestMethod.POST)
	@ResponseBody
	public String getScvarDS(String appId)throws Exception{
			String strJson = "[";
			AppEntity beanEntity=null;
			AppItem beanItem=null;
			int num=0;
			List<AppEntity> list1=entityService.selectAll(appId);
			if(list1 != null && list1.size() > 0){
				for(int i=0;i<list1.size();i++){
					beanEntity=list1.get(i);
					if(num>0){
						strJson = strJson + ", ";
					}
					strJson = strJson + "{parameterName:'" + beanEntity.getNameCn() + "', parameterValue:'" + beanEntity.getEntityId() + "'}";
					List<AppItem> list2=itemService.selectAllByEntityId(beanEntity.getEntityId());
					if(list2 != null && list2.size() > 0){
						for(int j=0;j<list2.size();j++){
							beanItem=list2.get(j);
							strJson = strJson + ", ";
							strJson = strJson + "{parameterName:'" + "　　"+beanItem.getCname() + "', parameterValue:'" + beanItem.getItemId() + "'}";
						}
					}
					num++;
				}
			}
			strJson = strJson + "]";
			return strJson;
		}
}
