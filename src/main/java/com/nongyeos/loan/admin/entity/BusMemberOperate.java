package com.nongyeos.loan.admin.entity;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-07-05
 */
@ExcelTarget("BusMemberOperate")
public class BusMemberOperate implements Serializable{
    @Override
	public String toString() {
		return "BusMemberOperate [id=" + id + ", name=" + name + ", age=" + age
				+ ", phone=" + phone + ", address=" + address
				+ ", operateAddress=" + operateAddress + ", orgId=" + orgId
				+ ", operUserId=" + operUserId + ", completingDegree="
				+ completingDegree + ", operateType=" + operateType
				+ ", cropType=" + cropType + ", otherCropRemark="
				+ otherCropRemark + ", cropScale=" + cropScale + ", cropYears="
				+ cropYears + ", cropExpectedValue=" + cropExpectedValue
				+ ", cropPeriod=" + cropPeriod + ", cropInvestment="
				+ cropInvestment + ", cropMainNeeds=" + cropMainNeeds
				+ ", livestockType=" + livestockType + ", livestockScale="
				+ livestockScale + ", livestockYears=" + livestockYears
				+ ", livestockExpectedValue=" + livestockExpectedValue
				+ ", livestockSiteType=" + livestockSiteType
				+ ", livestockPeriod=" + livestockPeriod
				+ ", livestockMainNeeds=" + livestockMainNeeds
				+ ", nongsellOtherType=" + nongsellOtherType
				+ ", otherNoRemark=" + otherNoRemark + ", workNoRemark="
				+ workNoRemark + ", noYears=" + noYears + ", noIncome="
				+ noIncome + ", noSite=" + noSite + ", noMainNeeds="
				+ noMainNeeds + ", isDeleted=" + isDeleted + ", creOperId="
				+ creOperId + ", creOperName=" + creOperName + ", creOrgId="
				+ creOrgId + ", creDate=" + creDate + ", updOperId="
				+ updOperId + ", updOperName=" + updOperName + ", updOrgId="
				+ updOrgId + ", updDate=" + updDate + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8663810004272077537L;

	/**
     * 主键ID
     */
    private String id;

    /**
     * 部门id
     */
    @Excel(name = "部门", height = 10, width = 20)
    private String orgId;

    /**
     * 操作人员ID（personId）
     */
    @Excel(name = "操作人员", height = 10, width = 20)
    private String operUserId;
    
    /**
     * 客户信息
     */
    @Excel(name = "姓名", height = 10, width = 20)
    private String name;

    /**
     * 年龄
     */
    @Excel(name = "年龄", height = 10, width = 20)
    private Integer age;

    /**
     * 联系电话
     */
    @Excel(name = "手机号", height = 10, width = 20)
    private String phone;

    /**
     * 所在区域（村）
     */
    @Excel(name = "所在村", height =10, width = 20)
    private String address;

    /**
     * 生产经营详细地址
     */
    @Excel(name = "生产经营详细地址", height = 10, width = 20)
    private String operateAddress;


    /**
     * 资料完整度
     */
    
    private String completingDegree;

    /**
     * 经营类型（1，大田作物 2,经济作物 3，养殖 4，经商/个体经营 5,其他 ）
     */
    @Excel(name = "经营类型", height = 10, width = 20)
    private String operateType;

    /**
     * 大田作物/经济作物类型（1，水稻 2，玉米 3，小麦  4，蔬菜 5，水果  7，其他）
     */
    @Excel(name = "大田作物/经济作物类型（1，水稻 2，玉米 3，小麦  4，蔬菜 5，水果  7，其他）",replace = { "水稻_1", "玉米_2", "小麦_3", "蔬菜_4", "水果 _5", "其他 _7" }, height = 10, width = 20)
    private String cropType;

    /**
     * 其它大田作物描述
     */
    private String otherCropRemark;

    /**
     * 种植规模（亩）
     */
    @Excel(name = "种植规模（亩）", height = 10, width = 20)
    private String cropScale;

    /**
     * 种植年限（年）
     */
    @Excel(name = "种植年限（年）", height = 10, width = 20)
    private String cropYears;

    /**
     * 种植物目前预估价值（万元）
     */
    @Excel(name = "种植物目前预估价值（万元）", height = 10, width = 20)
    private String cropExpectedValue;

    /**
     * 种植周期
     */
    @Excel(name = "种植周期(月)", height = 10, width = 20)
    private String cropPeriod;

    /**
     * 化肥等投入品投入（万元/周期）
     */
    @Excel(name = "化肥等投入品投入（万元/周期）", height = 10, width = 20)
    private String cropInvestment;

    /**
     * 大田作物/经济作物主要需求
     */
    @Excel(name = "大田作物/经济作物主要需求", height = 10, width = 20)
    private String cropMainNeeds;

    /**
     * 养殖种类（1，牛 2，猪 3，羊 4，鸡 5鱼 6其他）
     */
    @Excel(name = "养殖种类",replace = { "牛_1", "猪_2", "羊_3", "鸡_4", "鱼 _5", "其他 _6" }, height = 10, width = 20)
    private String livestockType;

    /**
     * 主要养殖种类规模（只/亩）
     */
    @Excel(name = "主要养殖种类规模（只/亩）", height = 10, width = 20)
    private String livestockScale;

    /**
     * 养殖年限（年）
     */
    @Excel(name = "养殖年限（年）", height = 10, width = 20)
    private String livestockYears;

    /**
     * 养殖预计价值（万元）
     */
    @Excel(name = "养殖预计价值（万元）", height = 10, width = 20)
    private String livestockExpectedValue;

    /**
     * 养殖场地类型（1,租用 2，自建）
     */
    @Excel(name = "养殖场地类型（1,租用 2，自建）",replace = { "-_0","租用_1", "自建_2" }, height = 10, width = 20)
    private Integer livestockSiteType;

    /**
     * 养殖周期(月)
     */
    @Excel(name = "养殖周期(月)", height = 10, width = 20)
    private String livestockPeriod;

    /**
     * 养殖主要需求（输入框）
     */
    @Excel(name = "养殖主要需求（输入框）", height = 10, width = 20)
    private String livestockMainNeeds;

    /**
     * 经商/其他类型（1，农资销售 2，批发 3，饭店等个体经营 4，上班 5其他 ）
     */
    @Excel(name = "经商/其他类型",replace = { "农资销售_1", "批发_2", "饭店等个体经营_3", "上班_4", "其他 _5" }, height = 10, width = 20)
    private String nongsellOtherType;

    /**
     * 其它描述
     */
    private String otherNoRemark;

    /**
     * 上班描述
     */
    private String workNoRemark;

    /**
     * 经营年限（年）
     */
    @Excel(name = "经营年限（年）", height = 10, width = 20)
    private String noYears;

    /**
     * 年收入（万元）
     */
    @Excel(name = "年收入（万元）", height = 10, width = 20)
    private String noIncome;

    /**
     * 经营场地（单选：自建、租用）
     */
    @Excel(name = "经营场地（单选：自建、租用）",replace = { "-_0","租用_1", "自建_2" }, height = 10, width = 20)
    private Integer noSite;

    /**
     * 经商/其他主要需求
     */
    @Excel(name = "经商/其他主要需求", height = 10, width = 20)
    private String noMainNeeds;

    /**
     * 是否删除
     */
    private Short isDeleted;

    /**
     * 创建者ID
     */
    private String creOperId;

    /**
     * 创建者名字
     */
    private String creOperName;

    /**
     * 创建者机构名称
     */
    private String creOrgId;

    /**
     * 创建时间
     */
    @Excel(name = "建档时间", databaseFormat = "yyyy-MM-dd HH:m:mss", format = "yyyy-MM-dd", height = 10, width = 20)
    private Date creDate;

    /**
     * 更新者ID
     */
    private String updOperId;

    /**
     * 更新者名称
     */
    private String updOperName;

    /**
     * 更新者机构ID
     */
    private String updOrgId;

    /**
     * 更新时间
     */
    private Date updDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOperateAddress() {
        return operateAddress;
    }

    public void setOperateAddress(String operateAddress) {
        this.operateAddress = operateAddress == null ? null : operateAddress.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId == null ? null : operUserId.trim();
    }

    public String getCompletingDegree() {
        return completingDegree;
    }

    public void setCompletingDegree(String completingDegree) {
        this.completingDegree = completingDegree;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType == null ? null : cropType.trim();
    }

    public String getOtherCropRemark() {
        return otherCropRemark;
    }

    public void setOtherCropRemark(String otherCropRemark) {
        this.otherCropRemark = otherCropRemark == null ? null : otherCropRemark.trim();
    }

    public String getCropScale() {
        return cropScale;
    }

    public void setCropScale(String cropScale) {
        this.cropScale = cropScale == null ? null : cropScale.trim();
    }

    public String getCropYears() {
        return cropYears;
    }

    public void setCropYears(String cropYears) {
        this.cropYears = cropYears == null ? null : cropYears.trim();
    }

    public String getCropExpectedValue() {
        return cropExpectedValue;
    }

    public void setCropExpectedValue(String cropExpectedValue) {
        this.cropExpectedValue = cropExpectedValue == null ? null : cropExpectedValue.trim();
    }

    public String getCropPeriod() {
        return cropPeriod;
    }

    public void setCropPeriod(String cropPeriod) {
        this.cropPeriod = cropPeriod == null ? null : cropPeriod.trim();
    }

    public String getCropInvestment() {
        return cropInvestment;
    }

    public void setCropInvestment(String cropInvestment) {
        this.cropInvestment = cropInvestment == null ? null : cropInvestment.trim();
    }

    public String getCropMainNeeds() {
        return cropMainNeeds;
    }

    public void setCropMainNeeds(String cropMainNeeds) {
        this.cropMainNeeds = cropMainNeeds == null ? null : cropMainNeeds.trim();
    }

    public String getLivestockType() {
        return livestockType;
    }

    public void setLivestockType(String livestockType) {
        this.livestockType = livestockType == null ? null : livestockType.trim();
    }

    public String getLivestockScale() {
        return livestockScale;
    }

    public void setLivestockScale(String livestockScale) {
        this.livestockScale = livestockScale == null ? null : livestockScale.trim();
    }

    public String getLivestockYears() {
        return livestockYears;
    }

    public void setLivestockYears(String livestockYears) {
        this.livestockYears = livestockYears == null ? null : livestockYears.trim();
    }

    public String getLivestockExpectedValue() {
        return livestockExpectedValue;
    }

    public void setLivestockExpectedValue(String livestockExpectedValue) {
        this.livestockExpectedValue = livestockExpectedValue == null ? null : livestockExpectedValue.trim();
    }

    public Integer getLivestockSiteType() {
        return livestockSiteType;
    }

    public void setLivestockSiteType(Integer livestockSiteType) {
        this.livestockSiteType = livestockSiteType;
    }

    public String getLivestockPeriod() {
        return livestockPeriod;
    }

    public void setLivestockPeriod(String livestockPeriod) {
        this.livestockPeriod = livestockPeriod == null ? null : livestockPeriod.trim();
    }

    public String getLivestockMainNeeds() {
        return livestockMainNeeds;
    }

    public void setLivestockMainNeeds(String livestockMainNeeds) {
        this.livestockMainNeeds = livestockMainNeeds == null ? null : livestockMainNeeds.trim();
    }

    public String getNongsellOtherType() {
        return nongsellOtherType;
    }

    public void setNongsellOtherType(String nongsellOtherType) {
        this.nongsellOtherType = nongsellOtherType == null ? null : nongsellOtherType.trim();
    }

    public String getOtherNoRemark() {
        return otherNoRemark;
    }

    public void setOtherNoRemark(String otherNoRemark) {
        this.otherNoRemark = otherNoRemark == null ? null : otherNoRemark.trim();
    }

    public String getWorkNoRemark() {
        return workNoRemark;
    }

    public void setWorkNoRemark(String workNoRemark) {
        this.workNoRemark = workNoRemark == null ? null : workNoRemark.trim();
    }

    public String getNoYears() {
        return noYears;
    }

    public void setNoYears(String noYears) {
        this.noYears = noYears == null ? null : noYears.trim();
    }

    public String getNoIncome() {
        return noIncome;
    }

    public void setNoIncome(String noIncome) {
        this.noIncome = noIncome == null ? null : noIncome.trim();
    }

    public Integer getNoSite() {
        return noSite;
    }

    public void setNoSite(Integer noSite) {
        this.noSite = noSite;
    }

    public String getNoMainNeeds() {
        return noMainNeeds;
    }

    public void setNoMainNeeds(String noMainNeeds) {
        this.noMainNeeds = noMainNeeds == null ? null : noMainNeeds.trim();
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