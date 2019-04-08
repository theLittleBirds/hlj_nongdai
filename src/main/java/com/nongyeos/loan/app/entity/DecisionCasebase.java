package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-05-08
 */
public class DecisionCasebase {
    private Integer csbaseId;

    private String caseId;

    private Byte type;

    private String baseId;

    public Integer getCsbaseId() {
        return csbaseId;
    }

    public void setCsbaseId(Integer csbaseId) {
        this.csbaseId = csbaseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId == null ? null : baseId.trim();
    }
}