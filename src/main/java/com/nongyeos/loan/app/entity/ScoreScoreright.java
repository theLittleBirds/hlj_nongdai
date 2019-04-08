package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-25
 */
public class ScoreScoreright {
    private Integer scrightId;

    private String scoreId;

    private short controlType;

    private short objectType;

    private String objectId;

    public Integer getScrightId() {
        return scrightId;
    }

    public void setScrightId(Integer scrightId) {
        this.scrightId = scrightId;
    }

    public String getscoreId() {
        return scoreId;
    }

    public void setscoreId(String scoreId) {
        this.scoreId = scoreId == null ? null : scoreId.trim();
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