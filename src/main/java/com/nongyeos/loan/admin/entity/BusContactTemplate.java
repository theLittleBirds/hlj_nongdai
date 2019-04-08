package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-08-28
 */
public class BusContactTemplate {
    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 变量
     */
    private String variable;

    /**
     * 金融机构id
     */
    private String finsId;

    /**
     * 甲方
     */
    private String first;

    /**
     * 甲方标识
     */
    private String firstMark;

    /**
     * 页码
     */
    private Integer firstP;

    /**
     * x坐标
     */
    private Float firstX;

    /**
     * y坐标
     */
    private Float firstY;

    /**
     * 乙方
     */
    private String second;

    /**
     * 乙方标识
     */
    private String secondMark;

    /**
     * 页码
     */
    private Integer secondP;

    /**
     * x坐标
     */
    private Float secondX;

    /**
     * y坐标
     */
    private Float secondY;

    /**
     * 丙方
     */
    private String third;

    /**
     * 丙方标识
     */
    private String thirdMark;

    /**
     * 页码
     */
    private Integer thirdP;

    /**
     * x坐标
     */
    private Float thirdX;

    /**
     * y坐标
     */
    private Float thirdY;

    /**
     * 是否启用 1启用  2禁用
     */
    private Integer isOpean;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable == null ? null : variable.trim();
    }

    public String getFinsId() {
        return finsId;
    }

    public void setFinsId(String finsId) {
        this.finsId = finsId == null ? null : finsId.trim();
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first == null ? null : first.trim();
    }

    public String getFirstMark() {
        return firstMark;
    }

    public void setFirstMark(String firstMark) {
        this.firstMark = firstMark == null ? null : firstMark.trim();
    }

    public Integer getFirstP() {
        return firstP;
    }

    public void setFirstP(Integer firstP) {
        this.firstP = firstP;
    }

    public Float getFirstX() {
        return firstX;
    }

    public void setFirstX(Float firstX) {
        this.firstX = firstX;
    }

    public Float getFirstY() {
        return firstY;
    }

    public void setFirstY(Float firstY) {
        this.firstY = firstY;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second == null ? null : second.trim();
    }

    public String getSecondMark() {
        return secondMark;
    }

    public void setSecondMark(String secondMark) {
        this.secondMark = secondMark == null ? null : secondMark.trim();
    }

    public Integer getSecondP() {
        return secondP;
    }

    public void setSecondP(Integer secondP) {
        this.secondP = secondP;
    }

    public Float getSecondX() {
        return secondX;
    }

    public void setSecondX(Float secondX) {
        this.secondX = secondX;
    }

    public Float getSecondY() {
        return secondY;
    }

    public void setSecondY(Float secondY) {
        this.secondY = secondY;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third == null ? null : third.trim();
    }

    public String getThirdMark() {
        return thirdMark;
    }

    public void setThirdMark(String thirdMark) {
        this.thirdMark = thirdMark == null ? null : thirdMark.trim();
    }

    public Integer getThirdP() {
        return thirdP;
    }

    public void setThirdP(Integer thirdP) {
        this.thirdP = thirdP;
    }

    public Float getThirdX() {
        return thirdX;
    }

    public void setThirdX(Float thirdX) {
        this.thirdX = thirdX;
    }

    public Float getThirdY() {
        return thirdY;
    }

    public void setThirdY(Float thirdY) {
        this.thirdY = thirdY;
    }

    public Integer getIsOpean() {
        return isOpean;
    }

    public void setIsOpean(Integer isOpean) {
        this.isOpean = isOpean;
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