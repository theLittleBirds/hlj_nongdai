package com.nongyeos.loan.app.entity;

import java.io.Serializable;

public class FlowData implements Serializable {
    private Integer dataId;

    private String nodeId;

    private Short objectType;

    private Short controlType;

    private String objectId;

    private static final long serialVersionUID = 1L;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Short getObjectType() {
        return objectType;
    }

    public void setObjectType(Short objectType) {
        this.objectType = objectType;
    }

    public Short getControlType() {
        return controlType;
    }

    public void setControlType(Short controlType) {
        this.controlType = controlType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }
}