package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-18
 */
public class AppAppright {
    private Integer apprightId;

    private String entityId;

    private short controlType;

    private short objectType;

    private String objectId;

    public Integer getApprightId() {
        return apprightId;
    }

    public void setApprightId(Integer apprightId) {
        this.apprightId = apprightId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId == null ? null : entityId.trim();
    }

    public short getControlType() {
        return controlType;
    }

    public void setControlType(short controlType) {
        this.controlType = controlType;
    }

    public short getObjectType() {
        return objectType;
    }

    public void setObjectType(short objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }
}