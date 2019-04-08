package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-24
 */
public class FleFlowright {
    private Integer flowrightId;

    private String flowId;

    private short controlType;

    private short objectType;

    private String objectCode;

    public Integer getFlowrightId() {
        return flowrightId;
    }

    public void setFlowrightId(Integer flowrightId) {
        this.flowrightId = flowrightId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
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

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode == null ? null : objectCode.trim();
    }
}