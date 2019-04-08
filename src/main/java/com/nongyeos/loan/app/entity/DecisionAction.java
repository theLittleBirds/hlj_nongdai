package com.nongyeos.loan.app.entity;

import java.io.Serializable;

public class DecisionAction implements Serializable {
    
    private String actionId;

    private String appId;

    private Short category;

    private String name;

    private String fromItemId;

    private Short operator;

    private String optValue;

    private String toItemId;

    private String toNodeId;

    private String checkItemIds;
    
    private Short isAllNull;

    private Integer seqno;

    private static final long serialVersionUID = 1L;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId == null ? null : actionId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Short getCategory() {
        return category;
    }

    public void setCategory(Short category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFromItemId() {
        return fromItemId;
    }

    public void setFromItemId(String fromItemId) {
        this.fromItemId = fromItemId == null ? null : fromItemId.trim();
    }

    public Short getOperator() {
        return operator;
    }

    public void setOperator(Short operator) {
        this.operator = operator;
    }

    public String getOptValue() {
        return optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue == null ? null : optValue.trim();
    }

    public String getToItemId() {
        return toItemId;
    }

    public void setToItemId(String toItemId) {
        this.toItemId = toItemId == null ? null : toItemId.trim();
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public void setToNodeId(String toNodeId) {
        this.toNodeId = toNodeId == null ? null : toNodeId.trim();
    }

    public String getCheckItemIds() {
        return checkItemIds;
    }

    public void setCheckItemIds(String checkItemIds) {
        this.checkItemIds = checkItemIds == null ? null : checkItemIds.trim();
    }

    public Short getIsAllNull() {
    	return isAllNull;
    }
    
    public void setIsAllNull(Short isAllNull) {
    	this.isAllNull = isAllNull;
    }
    
    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

}