package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 家庭经营情况-2
 * 
 * @author wcyong
 * 
 * @date 2018-08-01
 */
public class BusFamilyOperate {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 进件ID
     */
    private String intoPieceId;

    /**
     * 经营类型（1，大田作物 2，经济作物【蔬菜/水果/药材】 3，养殖 4，农资/农产品经销 5,非农职业(含打工)）
     */
    private String operateType;

    /**
     * 大田作物类型（1，水稻 2，玉米 3，小麦  4，蔬菜 5，水果 6，药材  7，其他）
     */
    private String bigLandCropType;

    /**
     * 其它大田作物描述
     */
    private String otherBlcRemark;

    /**
     * 大田作物种植规模（亩）
     */
    private String blcScale;

    /**
     * 大田作物种植年限（年）
     */
    private String blcYears;

    /**
     * 大田作物种植预计收入（万元）
     */
    private String blcExpectedValue;

    /**
     * 大田作物收货时间（月份）
     */
    private String blcHarvestTime;

    /**
     * 大田作物化肥等投入（万元/亩/年）
     */
    private String blcInvestment;

    /**
     * 大田作物历史经营情况（单选1盈亏平衡、2略有盈利、3较大部分盈利、4略有亏损）
     */
    private Integer blcHistoryOperate;

    /**
     * 经济作物类型（1，蔬菜 2，水果 3，药材 4，其他）
     */
    private String cashCropType;

    /**
     * 其它经济作物描述
     */
    private String otherCcRemark;

    /**
     * 经济作物种植规模（亩）
     */
    private String ccScale;

    /**
     * 经济作物种植年限（年）
     */
    private String ccYears;

    /**
     * 经济作物种植预计收入（万元）
     */
    private String ccExpectedValue;

    /**
     * 经济作物收货时间（月份）
     */
    private String ccHarvestTime;

    /**
     * 经济作物化肥等投入（万元/亩/年）
     */
    private String ccInvestment;

    /**
     * 经济作物历史经营情况（单选1盈亏平衡、2略有盈利、3较大部分盈利、4略有亏损）
     */
    private Integer ccHistoryOperate;

    /**
     * 养殖种类（1，牛 2，猪 3，羊 4，鸡 5鱼 6其他）
     */
    private String livestockType;
    
    /**
     * 其它养殖类型描述
     */
    private String otherLivestockRemark;

    /**
     * 主要养殖种类规模（只/亩）
     */
    private String livestockScale;

    /**
     * 养殖年限（年）
     */
    private String livestockYears;

    /**
     * 养殖预计价值（年）
     */
    private String livestockExpectedValue;

    /**
     * 养殖场地类型（1,租用 2，自建）
     */
    private Integer livestockSiteType;

    /**
     * 养殖场地租金（万元）
     */
    private String livestockSiteRent;

    /**
     * 养殖场地自建投入（万元）
     */
    private String livestockSiteInvestment;

    /**
     * 环评证照（1，有 2，无）
     */
    private String eiaCertificate;

    /**
     * 养殖历史经营情况（单选1盈亏平衡、2略有盈利、3较大部分盈利、4略有亏损）
     */
    private Integer livestockHistoryOperate;

    /**
     * 农资/农产品经销种类（1，化肥 2，种子 3，饲料 4，农产品 5其他 ）
     */
    private String nongSellType;

    /**
     * 其它农资/农产品经销描述
     */
    private String otherNsRemark;

    /**
     * 销售规模规模（万元/月）
     */
    private String nsScale;

    /**
     * 从事销售年限（年）
     */
    private String nsYears;

    /**
     * 销售场地类型（1,租用 2，自建）
     */
    private Integer nsSiteType;

    /**
     * 销售场地租金（万元）
     */
    private String nsSiteRent;

    /**
     * 销售场地自建投入（万元）
     */
    private String nsSiteInvestment;

    /**
     * 销售历史经营情况（单选1盈亏平衡、2略有盈利、3较大部分盈利、4略有亏损）
     */
    private Integer nsHistoryOperate;

    /**
     * 非农职业名称/类型
     */
    private String nonenongType;

    /**
     * 月收入（元/月）
     */
    private String nonenongIncome;

    /**
     * 从事此项工作的年限（年）
     */
    private String nonenongYears;

    /**
     * 社保（1,有 2，无）
     */
    private Integer socialSecurity;

    /**
     * 社保金额（合计元/月）
     */
    private String socialSecurityMoney;

    /**
     * 公积金（1,有 2，无）
     */
    private Integer accumulationFund;

    /**
     * 公积金（合计元/月）
     */
    private String accumulationFundMoney;

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

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getBigLandCropType() {
        return bigLandCropType;
    }

    public void setBigLandCropType(String bigLandCropType) {
        this.bigLandCropType = bigLandCropType == null ? null : bigLandCropType.trim();
    }

    public String getOtherBlcRemark() {
        return otherBlcRemark;
    }

    public void setOtherBlcRemark(String otherBlcRemark) {
        this.otherBlcRemark = otherBlcRemark == null ? null : otherBlcRemark.trim();
    }

    public String getBlcScale() {
        return blcScale;
    }

    public void setBlcScale(String blcScale) {
        this.blcScale = blcScale == null ? null : blcScale.trim();
    }

    public String getBlcYears() {
        return blcYears;
    }

    public void setBlcYears(String blcYears) {
        this.blcYears = blcYears == null ? null : blcYears.trim();
    }

    public String getBlcExpectedValue() {
        return blcExpectedValue;
    }

    public void setBlcExpectedValue(String blcExpectedValue) {
        this.blcExpectedValue = blcExpectedValue == null ? null : blcExpectedValue.trim();
    }

    public String getBlcHarvestTime() {
        return blcHarvestTime;
    }

    public void setBlcHarvestTime(String blcHarvestTime) {
        this.blcHarvestTime = blcHarvestTime == null ? null : blcHarvestTime.trim();
    }

    public String getBlcInvestment() {
        return blcInvestment;
    }

    public void setBlcInvestment(String blcInvestment) {
        this.blcInvestment = blcInvestment == null ? null : blcInvestment.trim();
    }

    public Integer getBlcHistoryOperate() {
        return blcHistoryOperate;
    }

    public void setBlcHistoryOperate(Integer blcHistoryOperate) {
        this.blcHistoryOperate = blcHistoryOperate;
    }

    public String getCashCropType() {
        return cashCropType;
    }

    public void setCashCropType(String cashCropType) {
        this.cashCropType = cashCropType == null ? null : cashCropType.trim();
    }

    public String getOtherCcRemark() {
        return otherCcRemark;
    }

    public void setOtherCcRemark(String otherCcRemark) {
        this.otherCcRemark = otherCcRemark == null ? null : otherCcRemark.trim();
    }

    public String getCcScale() {
        return ccScale;
    }

    public void setCcScale(String ccScale) {
        this.ccScale = ccScale == null ? null : ccScale.trim();
    }

    public String getCcYears() {
        return ccYears;
    }

    public void setCcYears(String ccYears) {
        this.ccYears = ccYears == null ? null : ccYears.trim();
    }

    public String getCcExpectedValue() {
        return ccExpectedValue;
    }

    public void setCcExpectedValue(String ccExpectedValue) {
        this.ccExpectedValue = ccExpectedValue == null ? null : ccExpectedValue.trim();
    }

    public String getCcHarvestTime() {
        return ccHarvestTime;
    }

    public void setCcHarvestTime(String ccHarvestTime) {
        this.ccHarvestTime = ccHarvestTime == null ? null : ccHarvestTime.trim();
    }

    public String getCcInvestment() {
        return ccInvestment;
    }

    public void setCcInvestment(String ccInvestment) {
        this.ccInvestment = ccInvestment == null ? null : ccInvestment.trim();
    }

    public Integer getCcHistoryOperate() {
        return ccHistoryOperate;
    }

    public void setCcHistoryOperate(Integer ccHistoryOperate) {
        this.ccHistoryOperate = ccHistoryOperate;
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

    public String getLivestockSiteRent() {
        return livestockSiteRent;
    }

    public void setLivestockSiteRent(String livestockSiteRent) {
        this.livestockSiteRent = livestockSiteRent == null ? null : livestockSiteRent.trim();
    }

    public String getLivestockSiteInvestment() {
        return livestockSiteInvestment;
    }

    public void setLivestockSiteInvestment(String livestockSiteInvestment) {
        this.livestockSiteInvestment = livestockSiteInvestment == null ? null : livestockSiteInvestment.trim();
    }

    public String getEiaCertificate() {
        return eiaCertificate;
    }

    public void setEiaCertificate(String eiaCertificate) {
        this.eiaCertificate = eiaCertificate == null ? null : eiaCertificate.trim();
    }

    public Integer getLivestockHistoryOperate() {
        return livestockHistoryOperate;
    }

    public void setLivestockHistoryOperate(Integer livestockHistoryOperate) {
        this.livestockHistoryOperate = livestockHistoryOperate;
    }

    public String getNongSellType() {
        return nongSellType;
    }

    public void setNongSellType(String nongSellType) {
        this.nongSellType = nongSellType == null ? null : nongSellType.trim();
    }

    public String getOtherNsRemark() {
        return otherNsRemark;
    }

    public void setOtherNsRemark(String otherNsRemark) {
        this.otherNsRemark = otherNsRemark == null ? null : otherNsRemark.trim();
    }

    public String getNsScale() {
        return nsScale;
    }

    public void setNsScale(String nsScale) {
        this.nsScale = nsScale == null ? null : nsScale.trim();
    }

    public String getNsYears() {
        return nsYears;
    }

    public void setNsYears(String nsYears) {
        this.nsYears = nsYears == null ? null : nsYears.trim();
    }

    public Integer getNsSiteType() {
        return nsSiteType;
    }

    public void setNsSiteType(Integer nsSiteType) {
        this.nsSiteType = nsSiteType;
    }

    public String getNsSiteRent() {
        return nsSiteRent;
    }

    public void setNsSiteRent(String nsSiteRent) {
        this.nsSiteRent = nsSiteRent == null ? null : nsSiteRent.trim();
    }

    public String getNsSiteInvestment() {
        return nsSiteInvestment;
    }

    public void setNsSiteInvestment(String nsSiteInvestment) {
        this.nsSiteInvestment = nsSiteInvestment == null ? null : nsSiteInvestment.trim();
    }

    public Integer getNsHistoryOperate() {
        return nsHistoryOperate;
    }

    public void setNsHistoryOperate(Integer nsHistoryOperate) {
        this.nsHistoryOperate = nsHistoryOperate;
    }

    public String getNonenongType() {
        return nonenongType;
    }

    public void setNonenongType(String nonenongType) {
        this.nonenongType = nonenongType == null ? null : nonenongType.trim();
    }

    public String getNonenongIncome() {
        return nonenongIncome;
    }

    public void setNonenongIncome(String nonenongIncome) {
        this.nonenongIncome = nonenongIncome == null ? null : nonenongIncome.trim();
    }

    public String getNonenongYears() {
        return nonenongYears;
    }

    public void setNonenongYears(String nonenongYears) {
        this.nonenongYears = nonenongYears == null ? null : nonenongYears.trim();
    }

    public Integer getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(Integer socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getSocialSecurityMoney() {
        return socialSecurityMoney;
    }

    public void setSocialSecurityMoney(String socialSecurityMoney) {
        this.socialSecurityMoney = socialSecurityMoney == null ? null : socialSecurityMoney.trim();
    }

    public Integer getAccumulationFund() {
        return accumulationFund;
    }

    public void setAccumulationFund(Integer accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    public String getAccumulationFundMoney() {
        return accumulationFundMoney;
    }

    public void setAccumulationFundMoney(String accumulationFundMoney) {
        this.accumulationFundMoney = accumulationFundMoney == null ? null : accumulationFundMoney.trim();
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

	public String getOtherLivestockRemark() {
		return otherLivestockRemark;
	}

	public void setOtherLivestockRemark(String otherLivestockRemark) {
		this.otherLivestockRemark = otherLivestockRemark;
	}
}