package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-07-10
 */
public class BusFollowData {
    private String id;

    /**
     * 客户id
     */
    private String memberOperateId;

    /**
     * 跟进数据项id
     */
    private String followItemId;

    /**
     * 数据值
     */
    private String itemValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMemberOperateId() {
        return memberOperateId;
    }

    public void setMemberOperateId(String memberOperateId) {
        this.memberOperateId = memberOperateId == null ? null : memberOperateId.trim();
    }

    public String getFollowItemId() {
        return followItemId;
    }

    public void setFollowItemId(String followItemId) {
        this.followItemId = followItemId == null ? null : followItemId.trim();
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue == null ? null : itemValue.trim();
    }
}