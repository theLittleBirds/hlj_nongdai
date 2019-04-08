package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-07-10
 */
public class BusFollowItem {
    private String id;

    /**
     * 跟进信息类型
     */
    private String type;

    /**
     * 数据项名称
     */
    private String name;

    /**
     * 数据类型(1输入框  2 单选 3多选)
     */
    private Integer inputType;

    /**
     * 输入框单位
     */
    private String unit;

    /**
     * 输入框长度
     */
    private Integer inputWidth;

    /**
     * 选项
     */
    private String inputOption;

    /**
     * 排序号
     */
    private Integer seqno;

    private Short isDeleted;

    private String creOperId;

    private String creOperName;

    private String creOrgId;

    private Date creDate;

    private String updOperId;

    private String updOperName;

    private String updOrgId;

    private Date updDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getInputWidth() {
        return inputWidth;
    }

    public void setInputWidth(Integer inputWidth) {
        this.inputWidth = inputWidth;
    }

    public String getInputOption() {
        return inputOption;
    }

    public void setInputOption(String inputOption) {
        this.inputOption = inputOption == null ? null : inputOption.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreOperId() {
        return creOperId;
    }

    public void setCreOperId(String creOperId) {
        this.creOperId = creOperId == null ? null : creOperId.trim();
    }

    public String getCreOperName() {
        return creOperName;
    }

    public void setCreOperName(String creOperName) {
        this.creOperName = creOperName == null ? null : creOperName.trim();
    }

    public String getCreOrgId() {
        return creOrgId;
    }

    public void setCreOrgId(String creOrgId) {
        this.creOrgId = creOrgId == null ? null : creOrgId.trim();
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public String getUpdOperId() {
        return updOperId;
    }

    public void setUpdOperId(String updOperId) {
        this.updOperId = updOperId == null ? null : updOperId.trim();
    }

    public String getUpdOperName() {
        return updOperName;
    }

    public void setUpdOperName(String updOperName) {
        this.updOperName = updOperName == null ? null : updOperName.trim();
    }

    public String getUpdOrgId() {
        return updOrgId;
    }

    public void setUpdOrgId(String updOrgId) {
        this.updOrgId = updOrgId == null ? null : updOrgId.trim();
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}