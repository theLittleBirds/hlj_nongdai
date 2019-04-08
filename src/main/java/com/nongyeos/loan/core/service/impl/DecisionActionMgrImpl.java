package com.nongyeos.loan.core.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.app.mapper.AppItemMapper;
import com.nongyeos.loan.app.mapper.DecisionActionMapper;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.model.ResMsg;
import com.nongyeos.loan.core.service.DecisionActionMgr;

@Service("actionMgrService")
public class DecisionActionMgrImpl implements DecisionActionMgr{
	
	@Autowired
	private DecisionActionMapper actionMapper;
	@Autowired
	private AppItemMapper itemMapper;
	@Autowired
	private ItemMgrImpl itemMgrService;

	@Override
	public boolean doAction(BusinessObject bo, String actionId) throws Exception{
		// TODO Auto-generated method stub
		int category;
		boolean result = false;
		try{
			if(actionId != null && !actionId.equals("")){
				DecisionAction beanAction = actionMapper.selectByPrimaryKey(actionId);
				if(beanAction != null){
					category = beanAction.getCategory();
					String fromItemId = beanAction.getFromItemId();
					short operator = 0;
					if (beanAction.getOperator() != null) {
						operator = beanAction.getOperator();
					}
					String optValue = beanAction.getOptValue();
					String toItemId = beanAction.getToItemId();
					String toNodeid = beanAction.getToNodeId();
					String checkItemIds = beanAction.getCheckItemIds();
					short isAllNull = 0;
					if (beanAction.getIsAllNull() != null) {
						isAllNull = beanAction.getIsAllNull();
					}
					switch(category){
						//改变数据项
					    case 1: result = itemChange(bo,fromItemId,operator,optValue,toItemId); break;
					    //改变流程流向
						case 2: result = direction(bo, toNodeid);break;
						//判断是否为空
						case 3: result = isNull(bo, checkItemIds, isAllNull);break;
							
						default: return false;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("策略执行失败");
		}
		return result;
	}
	//数据项改变方法
	public boolean itemChange(BusinessObject bo, String fromItemId, short operator, String optValue, String toItemId) throws Exception{
		int type;
		boolean result = false;
		try{
        	if(fromItemId != null && !fromItemId.equals("")){
        		AppItem beanItem = itemMapper.selectByPrimaryKey(fromItemId);
        		if(beanItem != null){
        		   type = beanItem.getDataType();
        		   String cname = beanItem.getCname();
        		   switch(type){
						//文本
					    case 1: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
					    //出生日期
						case 2: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//年龄
						case 3: result = intChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//多值文本
					    case 4: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
					    //手机号
						case 5: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//电话号码
						case 6: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//传真号码
					    case 7: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
					    //身份证号码
						case 8: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//邮政编码
						case 9: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//电子邮箱
					    case 10: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
					    //金额
						case 11: result = decimalChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//实数
						case 12: result = doubleChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//日期/时间
						case 13: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
						//日期
					    case 14: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
					    //时间
						case 15: result = stringChange(bo,fromItemId,operator,optValue,toItemId,cname); break;
							
						default: return false;
				   }
        		}
        	}
        }catch(Exception e){
        	e.printStackTrace();
        	throw new Exception (fromItemId+"数据项改变失败");
        }
		return result;
	}
	
	@SuppressWarnings("unchecked")
	//int类型的数据项改变方法
	public boolean intChange(BusinessObject bo, String fromItemId, short operator, String optValue, String toItemId, String cname) throws Exception{
		try{
			if(fromItemId != null && !fromItemId.equals("")){
				Object oldValue3 = itemMgrService.getObjectValue(bo, fromItemId);
				if(oldValue3 == null || oldValue3.equals("")){
					oldValue3 = 0;
				}
				int oldValue = (int)oldValue3;
				int optValue2 = Integer.parseInt(optValue);
				int newValue = 0;
				switch(operator){
					//加
				    case 1: newValue = oldValue + optValue2; break;
				    //减
					case 2: newValue = oldValue - optValue2; break;
					//乘
					case 3: newValue = oldValue * optValue2; break;
					//除
				    case 4: newValue = oldValue / optValue2; break;
				    //等
					case 5: newValue = optValue2; break;
					
					default: return false;
			    }
				itemMgrService.setObjectValue(bo, toItemId, newValue);
				ResMsg poll = new ResMsg();
				Map<String, Object> map = poll.getData();
				map.put("msg", "数据项" + cname + "由" + oldValue + "改为" + newValue);
				poll.setCode(6000);
				poll.setData(map);
				bo.getMsgQueue().add(poll);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (fromItemId+"数据项改变失败");
		}
		return true;
	}
	
	//double类型的数据项改变方法
	public boolean doubleChange(BusinessObject bo, String fromItemId, short operator, String optValue, String toItemId, String cname) throws Exception{
		try{
			if(fromItemId != null && !fromItemId.equals("")){
				Object oldValue3 = itemMgrService.getObjectValue(bo, fromItemId);
				if(oldValue3 == null || oldValue3.equals("")){
					oldValue3 = 0;
				}
				double oldValue = (double)oldValue3;
				double optValue2 = Integer.parseInt(optValue);
				double newValue = 0;
				switch(operator){
					//加
				    case 1: newValue = oldValue + optValue2; break;
				    //减
					case 2: newValue = oldValue - optValue2; break;
					//乘
					case 3: newValue = oldValue * optValue2; break;
					//除
				    case 4: newValue = oldValue / optValue2; break;
				    //等
					case 5: newValue = optValue2; break;
					
					default: return false;
			    }
				itemMgrService.setObjectValue(bo, toItemId, newValue);
				ResMsg poll = new ResMsg();
				Map<String, Object> map = poll.getData();
				map.put("msg", "数据项" + cname + "由" + oldValue + "改为" + newValue);
				poll.setCode(6000);
				poll.setData(map);
				bo.getMsgQueue().add(poll);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (fromItemId+"数据项改变失败");
		}
	    return true;
	}
	
	//decimal类型的数据项改变方法
	public boolean decimalChange(BusinessObject bo, String fromItemId, short operator, String optValue, String toItemId, String cname) throws Exception{
		try{
			if(fromItemId != null && !fromItemId.equals("")){
				Object oldValue3 = itemMgrService.getObjectValue(bo, fromItemId);
				if(oldValue3 == null || oldValue3.equals("")){
					oldValue3 = 0;
				}
				BigDecimal oldValue = getBigDecimal(oldValue3);
				BigDecimal optValue2 = new BigDecimal(optValue);  
				BigDecimal newValue;
				switch(operator){
					//加
				    case 1: newValue = oldValue.add(optValue2); break;
				    //减
					case 2: newValue = oldValue.subtract(optValue2); break;
					//乘
					case 3: newValue = oldValue.multiply(optValue2); break;
					//除
				    case 4: newValue = oldValue.divide(optValue2); break;
				    //等
					case 5: newValue = optValue2; break;
					
					default: return false;
			    }
				itemMgrService.setObjectValue(bo, toItemId, newValue);
				ResMsg poll = new ResMsg();
				Map<String, Object> map = poll.getData();
				map.put("msg", "数据项" + cname + "由" + oldValue + "改为" + newValue);
				poll.setCode(6000);
				poll.setData(map);
				bo.getMsgQueue().add(poll);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (fromItemId+"数据项改变失败");
		}
		return true;
	}
	
	//String类型的数据项改变方法
	public boolean stringChange(BusinessObject bo, String fromItemId, short operator, String optValue, String toItemId, String cname) throws Exception{
		try{
			if(fromItemId != null && !fromItemId.equals("")){
				String oldValue = (String) itemMgrService.getObjectValue(bo, fromItemId);
				String newValue;
				switch(operator){
					//追加
				    case 1: newValue = oldValue + optValue; break;
				    //替换
					case 2: newValue = optValue; break;
					
					default: return false;
			    }
			    itemMgrService.setObjectValue(bo, toItemId, newValue);
				ResMsg poll = new ResMsg();
				Map<String, Object> map = poll.getData();
				map.put("msg", "数据项" + cname + "由" + oldValue + "改为" + newValue);
				poll.setCode(6000);
				poll.setData(map);
				bo.getMsgQueue().add(poll);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (fromItemId+"数据项改变失败");
		}
		return true;
	}
	
	//流向改变方法
	public boolean direction(BusinessObject bo, String toNodeid)throws Exception{
		try{
			if(toNodeid != null && !toNodeid.equals("")){
				ResMsg poll = new ResMsg();
				Map<String, Object> map = poll.getData();
				map.put("msg", toNodeid);
				poll.setCode(7000);
				poll.setData(map);
				bo.getMsgQueue().add(poll);
				return true;
			}else{
				throw new Exception (toNodeid+"流向改变失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (toNodeid+"流向改变失败");
		}

	}
	
	//判断是否为空
	public boolean isNull(BusinessObject bo, String checkItemIds, short isAllNull) throws Exception{
		boolean whether = false;
		ResMsg poll = new ResMsg();
		String msg = "";
		Map<String, Object> map = poll.getData();
		try{
			if(checkItemIds != null && !checkItemIds.equals("")){
				String[] itemIds = checkItemIds.split(",");
				for(String itemId : itemIds){
					Object obj = itemMgrService.getObjectValue(bo, itemId);
					if(obj == null || obj.equals("")){
						AppItem beanItem = itemMapper.selectByPrimaryKey(itemId);
						String itemName = beanItem.getCname();
						msg += itemName + "为空；";
						map.put("msg",itemName + "为空");
						whether = true;
						//把itemName放进bo里面
						if(isAllNull == 2){
							poll.setCode(4000);
							poll.setMsg(msg);
							poll.setData(map);
							bo.getMsgQueue().add(poll);
							return whether;
						}
					}
				}
				poll.setCode(4000);
				poll.setMsg(msg);
				poll.setData(map);
				bo.getMsgQueue().add(poll);
				return whether;
			}else{
				return whether;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception (checkItemIds+"数据项判空失败");
		}
	}
	
	public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

}
