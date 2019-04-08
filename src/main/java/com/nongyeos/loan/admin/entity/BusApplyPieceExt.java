package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-25
 */
public class BusApplyPieceExt {
    /**
     * 扩展数据ID
     */
    private String extId;

    /**
     * 申请进件ID
     */
    private String intoPieceId;

    /**
     * 数据项ID
     */
    private String itemId;

    /**
     * 数据值
     */
    private String itemValue;

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId == null ? null : extId.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue == null ? null : itemValue.trim();
    }
}