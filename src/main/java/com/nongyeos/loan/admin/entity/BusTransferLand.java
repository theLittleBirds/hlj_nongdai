package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-01-15
 */
public class BusTransferLand {
    /**
     * 流转土地id
     */
    private String id;

    /**
     * 进件ID
     */
    private String intoPieceId;

    /**
     * 土地名称
     */
    private String landName;

    /**
     * 土地等级
     */
    private String landLevel;

    /**
     * 土地面积
     */
    private String landArea;
    
    /**
     * 土地产量
     */
    private String yield;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 东边界
     */
    private String easternBorder;

    /**
     * 西边界
     */
    private String westernBorder;

    /**
     * 北边界
     */
    private String northernBorder;

    /**
     * 南边界
     */
    private String southernBorder;

    /**
     * 是否为外包地（1,是 2，否）
     */
    private Integer outsource;

    /**
     * 承包期限（单位：年）
     */
    private String outsourcingTerm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName == null ? null : landName.trim();
    }

    public String getLandLevel() {
        return landLevel;
    }

    public void setLandLevel(String landLevel) {
        this.landLevel = landLevel == null ? null : landLevel.trim();
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea == null ? null : landArea.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getEasternBorder() {
        return easternBorder;
    }

    public void setEasternBorder(String easternBorder) {
        this.easternBorder = easternBorder == null ? null : easternBorder.trim();
    }

    public String getWesternBorder() {
        return westernBorder;
    }

    public void setWesternBorder(String westernBorder) {
        this.westernBorder = westernBorder == null ? null : westernBorder.trim();
    }

    public String getNorthernBorder() {
        return northernBorder;
    }

    public void setNorthernBorder(String northernBorder) {
        this.northernBorder = northernBorder == null ? null : northernBorder.trim();
    }

    public String getSouthernBorder() {
        return southernBorder;
    }

    public void setSouthernBorder(String southernBorder) {
        this.southernBorder = southernBorder == null ? null : southernBorder.trim();
    }

    public Integer getOutsource() {
        return outsource;
    }

    public void setOutsource(Integer outsource) {
        this.outsource = outsource;
    }

    public String getOutsourcingTerm() {
        return outsourcingTerm;
    }

    public void setOutsourcingTerm(String outsourcingTerm) {
        this.outsourcingTerm = outsourcingTerm == null ? null : outsourcingTerm.trim();
    }

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}
}