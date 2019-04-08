package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-05-08
 */
public class DecisionPolicybase {
    private String baseId;

    private String appId;

    private String decisionCategoryId;

    private String itemId;

    private String lowerValue;

    private String lowerOperator;

    private String upperValue;

    private String upperOperator;

    private String miaoshu;

    private Integer seqno;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId == null ? null : baseId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getDecisionCategoryId() {
        return decisionCategoryId;
    }

    public void setDecisionCategoryId(String decisionCategoryId) {
        this.decisionCategoryId = decisionCategoryId == null ? null : decisionCategoryId.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(String lowerValue) {
        this.lowerValue = lowerValue == null ? null : lowerValue.trim();
    }

    public String getLowerOperator() {
        return lowerOperator;
    }

    public void setLowerOperator(String lowerOperator) {
        this.lowerOperator = lowerOperator == null ? null : lowerOperator.trim();
    }

    public String getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(String upperValue) {
        this.upperValue = upperValue == null ? null : upperValue.trim();
    }

    public String getUpperOperator() {
        return upperOperator;
    }

    public void setUpperOperator(String upperOperator) {
        this.upperOperator = upperOperator == null ? null : upperOperator.trim();
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu == null ? null : miaoshu.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}