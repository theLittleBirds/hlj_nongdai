package com.nongyeos.loan.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.nongyeos.loan.admin.entity.BusMemberOperate;

public class CompletingDegree {
	/**
	 * 根据已填数据计算出资料填写完整度
	 * @param busMemberOperate
	 * @return
	 */
	public static BusMemberOperate caculate(BusMemberOperate busMemberOperate){
		int cropParams = 0;
		int livestockParams = 0;
		int NOParams = 0;
		BigDecimal completing = new BigDecimal("0.0");
		String name = busMemberOperate.getName();
		if(StringUtils.isNotEmpty(name)) 
			completing = completing.add(new BigDecimal("10.0"));
		Integer age = busMemberOperate.getAge();
		if(age!=null)
			completing = completing.add(new BigDecimal("10.0"));
		String address = busMemberOperate.getAddress();
		if(StringUtils.isNotEmpty(address))
			completing = completing.add(new BigDecimal("10.0"));
		String operateAddress = busMemberOperate.getOperateAddress();
		if(StringUtils.isNotEmpty(operateAddress))
			completing = completing.add(new BigDecimal("10.0"));
		String phone = busMemberOperate.getPhone();
		if(StringUtils.isNotEmpty(phone))
			completing = completing.add(new BigDecimal("10.0"));
		String operateType = busMemberOperate.getOperateType();
		if(StringUtils.isNotEmpty(operateType))
			completing = completing.add(new BigDecimal("10.0"));
		if(StringUtils.isNotEmpty(operateType)&&(operateType.contains("1")||operateType.contains("2"))){
			//种植参数
			String cropType = busMemberOperate.getCropType();
			if(StringUtils.isNotEmpty(cropType))
				cropParams++;
			String cropYears = busMemberOperate.getCropYears();
			if(StringUtils.isNotEmpty(cropYears))
				cropParams++;
			String cropScale = busMemberOperate.getCropScale();
			if(StringUtils.isNotEmpty(cropScale))
				cropParams++;
			String cropExpectedValue = busMemberOperate.getCropExpectedValue();
			if(StringUtils.isNotEmpty(cropExpectedValue))
				cropParams++;
			String cropPeriod = busMemberOperate.getCropPeriod();
			if(StringUtils.isNotEmpty(cropPeriod))
				cropParams++;
			String cropInvestment = busMemberOperate.getCropInvestment();
			if(StringUtils.isNotEmpty(cropInvestment))
				cropParams++;
			String cropMainNeeds = busMemberOperate.getCropMainNeeds();
			if(StringUtils.isNotEmpty(cropMainNeeds))
				cropParams++;
		}
		if(StringUtils.isNotEmpty(operateType)&&operateType.contains("3")){
			//养殖参数
			String livestockType = busMemberOperate.getLivestockType();
			if(StringUtils.isNotEmpty(livestockType))
				livestockParams++;
			String livestockScale = busMemberOperate.getLivestockScale();
			if(StringUtils.isNotEmpty(livestockScale))
				livestockParams++;
			String livestockYears = busMemberOperate.getLivestockYears();
			if(StringUtils.isNotEmpty(livestockYears))
				livestockParams++;
			String livestockExpectedValue = busMemberOperate.getLivestockExpectedValue();
			if(StringUtils.isNotEmpty(livestockExpectedValue))
				livestockParams++;
			Integer livestockSiteType = busMemberOperate.getLivestockSiteType();
			if(livestockSiteType!=null)
				livestockParams++;
			String livestockPeriod = busMemberOperate.getLivestockPeriod();
			if(StringUtils.isNotEmpty(livestockPeriod))
				livestockParams++;
			String livestockMainNeeds = busMemberOperate.getLivestockMainNeeds();
			if(StringUtils.isNotEmpty(livestockMainNeeds))
				livestockParams++;
		}
		
		if(StringUtils.isNotEmpty(operateType)&&(operateType.contains("4")||operateType.contains("5"))){
			//经商参数
			String nongsellOtherType = busMemberOperate.getNongsellOtherType();
			if(StringUtils.isNotEmpty(nongsellOtherType))
				NOParams++;
			String workNoRemark = busMemberOperate.getWorkNoRemark();
			if(StringUtils.isNotEmpty(workNoRemark))
				NOParams++;
			String otherNoRemark = busMemberOperate.getOtherNoRemark();
			if(StringUtils.isNotEmpty(otherNoRemark))
				NOParams++;
			String noYears = busMemberOperate.getNoYears();
			if(StringUtils.isNotEmpty(noYears))
				NOParams++;
			String noIncome = busMemberOperate.getNoIncome();
			if(StringUtils.isNotEmpty(noIncome))
				NOParams++;
			Integer noSite = busMemberOperate.getNoSite();
			if(noSite!=null)
				NOParams++;
			String noMainNeeds = busMemberOperate.getNoMainNeeds();
			if(StringUtils.isNotEmpty(noMainNeeds))
				NOParams++;
		}
		
		BigDecimal crop = new BigDecimal(cropParams);
		BigDecimal livestock = new BigDecimal(livestockParams);
		BigDecimal NO = new BigDecimal(NOParams);
		crop = crop.divide(new BigDecimal("7"),3,RoundingMode.HALF_UP).multiply(new BigDecimal(40)).setScale(0,RoundingMode.HALF_UP);
		livestock = livestock.divide(new BigDecimal("7"),3,RoundingMode.HALF_UP).multiply(new BigDecimal(40)).setScale(0,RoundingMode.HALF_UP);
		NO = NO.divide(new BigDecimal("7"),3,RoundingMode.HALF_UP).multiply(new BigDecimal(40)).setScale(0,RoundingMode.HALF_UP);
		BigDecimal max1 = crop.max(livestock);
		BigDecimal max = max1.max(NO);
		completing = max.add(completing);
		int comp = completing.intValue();
		busMemberOperate.setCompletingDegree(comp+"%");
		return busMemberOperate;
	}
	
}
