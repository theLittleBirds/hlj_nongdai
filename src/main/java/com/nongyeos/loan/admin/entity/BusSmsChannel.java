package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-03-06
 */
public class BusSmsChannel {
    /**
     * 短信接口Id
     */
    private String id;

    /**
     * 短信接口名称
     */
    private String channelName;

    /**
     * 是否启用，是为1,0为未启用
     */
    private Integer use;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }
}