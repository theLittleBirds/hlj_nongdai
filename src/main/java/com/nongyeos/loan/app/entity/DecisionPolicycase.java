package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-05-08
 */
public class DecisionPolicycase {
    private String caseId;

    private String appId;

    private String decisionCategoryId;

    private String miaoshu;

    private Integer seqno;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
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