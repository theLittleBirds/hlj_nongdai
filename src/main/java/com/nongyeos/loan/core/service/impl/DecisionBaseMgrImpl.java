package com.nongyeos.loan.core.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.entity.DecisionPolicybase;
import com.nongyeos.loan.app.mapper.AppItemMapper;
import com.nongyeos.loan.app.mapper.DecisionPolicybaseMapper;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.DecisionBaseMgr;
import com.nongyeos.loan.core.service.ItemMgr;

@Service("baseMgrService")
public class DecisionBaseMgrImpl implements DecisionBaseMgr {
	
	@Autowired
	private DecisionPolicybaseMapper baseMapper;
	@Autowired
	private AppItemMapper itemMapper;
	@Autowired
	private ItemMgr itemMgrService;

	@Override
	public boolean logicExpress(BusinessObject bo, String baseId, short mode) throws Exception{
		try {
			DecisionPolicybase base = baseMapper.selectByPrimaryKey(baseId);
			boolean logicExpress = logicExpress(bo, base, mode);
			return logicExpress;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("基本条件错误");
		}
	}

	@Override
	public boolean logicExpress(BusinessObject bo, DecisionPolicybase base, short mode) throws Exception {
		boolean flag = false;
		try {
			String itemId = base.getItemId();
			AppItem item = itemMapper.selectByPrimaryKey(itemId);
			Short type = item.getDataType();
			if (type == 3 || type == 11 || type == 12) {
				flag = intValue(bo, base, mode);
			}else if (type == 2 || type == 13 || type == 14 || type == 15) {
				flag = dateValue(bo, base, mode);
			}else {
				flag = stringValue(bo, base, mode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("基本条件错误");
		}
		return flag;
	}

	//日期类型操作
	private boolean dateValue(BusinessObject bo, DecisionPolicybase base, short mode) throws Exception {
		Object obj = null;
		
		boolean flag = false;
		String itemId = base.getItemId();
		String lowerValue = base.getLowerValue();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(mode == 1){
				obj = itemMgrService.getObjectValue(bo, itemId);
			}else if(mode == 2){
				obj = itemMgrService.getObjectItemValue(bo, itemId);
			}
			if (base.getLowerOperator().equals("isnull")) {
				flag = (obj=="" || obj==null);
			}else {
				if (obj=="" || obj==null) {
//					throw new Exception("数据项为空");
					flag = false;
				}else {
					Date lowDate = sdf.parse(lowerValue);
					Date date = (Date) obj;
					if (base.getLowerOperator().equals("=")) {
						flag = date.equals(lowDate);
					}else {
						String upperValue = base.getUpperValue();
						Date upDate = sdf.parse(upperValue);
						if (base.getLowerOperator().equals(">")) {
							if (base.getUpperOperator().equals("<")) {
								flag = date.before(upDate) && date.after(lowDate);
							}else {
								flag = (date.before(upDate) || date.equals(upDate)) && date.after(lowDate);
							}
						}else if (base.getLowerOperator().equals(">=")) {
							if (base.getUpperOperator().equals("<")) {
								flag = date.before(upDate) && (date.after(lowDate) || date.equals(lowDate));
							}else {
								flag = (date.before(upDate) || date.equals(upDate)) && (date.after(lowDate) || date.equals(lowDate));
							}
						}else {
							throw new Exception("操作符不符合规定");
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new Exception("基本条件错误");
		}
		return flag;
	}

	//字符类型操作
	private boolean stringValue(BusinessObject bo, DecisionPolicybase base, short mode) throws Exception {
		Object obj = null;
		
		String itemId = base.getItemId();
		String lowerValue = base.getLowerValue();
		boolean flag = false;
		try {
			if(mode == 1){
				obj = itemMgrService.getObjectValue(bo, itemId);
			}else if(mode == 2){
				obj = itemMgrService.getObjectItemValue(bo, itemId);
			}
			if (base.getLowerOperator().equals("isnull")) {
				flag = (obj=="" || obj==null);
			}else {
				if (obj=="" || obj==null) {
//					throw new Exception("数据项为空");
					flag = false;
				}else {
					String stringValue = obj.toString();
					if (base.getLowerOperator().equals("==")) {
						flag = lowerValue.equals(stringValue);
					}else if (base.getLowerOperator().equals("include")) {
						flag = (lowerValue.contains(stringValue));
					}else if (base.getLowerOperator().equals("ismember")) {
						flag = (stringValue.contains(lowerValue));
					}else {
						throw new Exception("操作符不符合规定");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("基本条件错误");
		}
		return flag;
	}

	// 数值类型操作
	private boolean intValue(BusinessObject bo, DecisionPolicybase base, short mode) throws Exception {
		Object obj = null;
		
		String itemId = base.getItemId();
		String lowerValue = base.getLowerValue();
		String upperValue = base.getUpperValue();
		String lowerOperator = base.getLowerOperator();
		String upperOperator = base.getUpperOperator();
		boolean flag = false;
		try {
			if(mode == 1) {
				obj = itemMgrService.getObjectValue(bo, itemId);
			}else if(mode == 2) {
				obj = itemMgrService.getObjectItemValue(bo, itemId);
			}
			if (base.getLowerOperator().equals("isnull")) {
				flag = (obj=="" || obj==null);
			}else {
				if (obj=="" || obj==null) {
//					throw new Exception("数据项为空");
					flag = false;
				}else {
					double doubleValue = Double.parseDouble(obj.toString());
					if(lowerOperator != null && !lowerOperator.equals("")){
						if(upperValue == null || upperValue.equals("")){
							if(lowerOperator.equals("=")){
								flag = doubleValue == Double.parseDouble(lowerValue);
							}
							if(lowerOperator.equals(">")){
								flag = doubleValue > Double.parseDouble(lowerValue);
							}
							if(lowerOperator.equals(">=")){
								flag = doubleValue >= Double.parseDouble(lowerValue);
							}
						}else{
							double low = Double.parseDouble(lowerValue);
							double up = Double.parseDouble(upperValue);
							if(lowerOperator.equals(">") && upperOperator.equals("<")){
								flag = (doubleValue > low) && (doubleValue < up);
							}
							if(lowerOperator.equals(">") && upperOperator.equals("<=")){
								flag = (doubleValue > low) && (doubleValue <= up);
							}
							if(lowerOperator.equals(">=") && upperOperator.equals("<")){
								flag = (doubleValue >= low) && (doubleValue < up);
							}
							if(lowerOperator.equals(">=") && upperOperator.equals("<=")){
								flag = (doubleValue >= low) && (doubleValue <= up);
							}
						}
					}else{
						throw new Exception("数据错误，下限值为空");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("基本条件错误");
		}
		return flag;
	}

}
