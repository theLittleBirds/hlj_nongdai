package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-01
 */
public class AppAppgroup {
    private String groupId;

    private String finsId;

    private String parentGroupId;

    private String parentGroupIds;

    private String name;

    private Integer seqno;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getFinsId() {
        return finsId;
    }

    public void setFinsId(String finsId) {
        this.finsId = finsId == null ? null : finsId.trim();
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId == null ? null : parentGroupId.trim();
    }

    public String getParentGroupIds() {
        return parentGroupIds;
    }

    public void setParentGroupIds(String parentGroupIds) {
        this.parentGroupIds = parentGroupIds == null ? null : parentGroupIds.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}