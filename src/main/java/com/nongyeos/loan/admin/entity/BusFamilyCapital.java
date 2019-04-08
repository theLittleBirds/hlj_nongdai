package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 家庭资产-2
 * 
 * @author wcyong
 * 
 * @date 2018-08-01
 */
public class BusFamilyCapital {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 进件ID
     */
    private String intoPieceId;

    /**
     * 土地经营权证书编号
     */
    private String landCertId;

    /**
     * 家庭资产总额（万元）
     */
    private String totalFamilyCapital;

    /**
     * 自有土地（1,有 2，无）
     */
    private Integer selLandType;

    /**
     * 自有土地亩数(单位：亩)
     */
    private String selLandNum;

    /**
     * 承包土地（1,有 2，无）
     */
    private Integer certLandType;

    /**
     * 承包土地亩数(单位：亩)
     */
    private String certLandNum;

    /**
     * 在册水田数（公顷）
     */
    private String waterFarmlandRegistered;

    /**
     * 在册旱地/田数（公顷）
     */
    private String dryFarmlandRegistered;

    /**
     * 册外水田数（公顷）
     */
    private String waterFarmlandUnregistered;

    /**
     * 册外旱地/田数（公顷）
     */
    private String dryFarmlandUnregistered;

    /**
     * 承包水田数（公顷）
     */
    private String waterFarmlandContract;

    /**
     * 承包旱地/田数（公顷）
     */
    private String dryFarmlandContract;

    /**
     * 在外租房(1是，2否)
     */
    private Integer rentHouse;

    /**
     * 农村房屋（1，有 2，无）
     */
    private Integer countrysideHouse;

    /**
     * 自购小产权房/安置房（1，有，2，无）
     */
    private Integer smallPropertyHouse;

    /**
     * 自购小产权房/安置房面积(单位：m2)
     */
    private String smallPropertyHouseArea;

    /**
     * 自购小产权房/安置房预估价值（单位：万元）
     */
    private String smallPropertyHouseValue;

    /**
     * 自购大产权房/商品房(1,有 2，无)
     */
    private Integer bigPropertyHouse;

    /**
     * 自购大产权房/商品房面积（单位：m2）
     */
    private String bigPropertyHouseArea;

    /**
     * 自购大产权房/商品房预估价值（单位：万元）
     */
    private String bigPropertyHouseValue;

    /**
     * 轿车(1有，2无)
     */
    private Integer car;

    /**
     * 名称
     */
    private String carName;

    /**
     * 轿车预估价值（单位：万元）
     */
    private String carValue;

    /**
     * 面包车(1有，2无)
     */
    private Integer minibus;

    /**
     * 面包车预估价值（单位：万元）
     */
    private String minibusValue;

    /**
     * 货车(1有，2无)
     */
    private Integer truck;

    /**
     * 货车预估价值（单位：万元）
     */
    private String truckValue;

    /**
     * 三轮车/其他农用车(1有，2无)
     */
    private Integer otherTricycle;

    /**
     * 农用机械（1，有，2，无）
     */
    private Integer nongMachine;
    
    /**
     * 农机使用年限
     */
    private String nongMachineYear;

    /**
     * 农机描述
     */
    private String nongMachineRemark;

    /**
     * 农机预估价值（万元）
     */
    private String nongMachineEstimatedValue;

    /**
     * 家庭负债总额（万元）
     */
    private String totalFamilyDebtedness;

    /**
     * 已核实隐性负债(万元)
     */
    private String invisibleDebtedness;

    /**
     * 银行（贷款/信用卡）（1，有，2，无）
     */
    private Integer bankLoan;

    /**
     * 银行（贷款/信用卡）总额（万元）
     */
    private String bankLoanIndebtedness;

    /**
     * p2p/小贷借款（1，有，2，无）
     */
    private Integer p2pPettyLoan;

    /**
     * p2p/小贷借款总额（万元）
     */
    private String p2pPettyLoanIndebtedness;

    /**
     * 亲朋借款（1，有，2，无）
     */
    private Integer friendLoan;

    /**
     * 亲朋借款总额（万元）
     */
    private String friendLoanIndebtedness;

    /**
     * 为他人担保贷款（单选：1有/2无）
     */
    private Integer guaranteeLoan;

    /**
     * 为他人担保贷款总额（万元）
     */
    private String guaranteeLoanIndebtedness;

    /**
     * 重大开支投资（单选：1有/2无）
     */
    private Integer mainInvest;

    /**
     * 当前行业从业年限
     */
    private Integer currentWorkYears;

    /**
     * 投资启动资金来源（单选：1原始积累、2较少部分筹借、3较多部分筹借）
     */
    private Integer initialCapitalFrom;

    /**
     * 投资经营场地（单选：1无投资、2投资较小、3投资较大）
     */
    private Integer investPremises;

    /**
     * 适量投资固定资产（房屋、车辆、设备等）（单选：1是/2否）
     */
    private Integer investFixedAssets;

    /**
     * 曾经经营亏损（单选：1较少、2一般、3较大）
     */
    private Integer operatingLoss;

    /**
     * 家庭因成员疾病出现重大支出（单选：1是/2否）
     */
    private Integer diseasePay;

    /**
     * 家庭成员有赌博行为（单选：1是/2否）
     */
    private Integer gambling;

    /**
     * 家庭成员有吸毒（单选：1是/2否）
     */
    private Integer drugTaking;

    /**
     * 经营欠款（单选：1是/2否）
     */
    private Integer operateDebt;

    /**
     * 负债比（%）
     */
    private String liabilityAssetRatio;

    /**
     * 网查互联网征信次数
     */
    private String queryCreditTimes;

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

    public String getLandCertId() {
        return landCertId;
    }

    public void setLandCertId(String landCertId) {
        this.landCertId = landCertId == null ? null : landCertId.trim();
    }

    public String getTotalFamilyCapital() {
        return totalFamilyCapital;
    }

    public void setTotalFamilyCapital(String totalFamilyCapital) {
        this.totalFamilyCapital = totalFamilyCapital == null ? null : totalFamilyCapital.trim();
    }

    public Integer getSelLandType() {
        return selLandType;
    }

    public void setSelLandType(Integer selLandType) {
        this.selLandType = selLandType;
    }

    public String getSelLandNum() {
        return selLandNum;
    }

    public void setSelLandNum(String selLandNum) {
        this.selLandNum = selLandNum == null ? null : selLandNum.trim();
    }

    public Integer getCertLandType() {
        return certLandType;
    }

    public void setCertLandType(Integer certLandType) {
        this.certLandType = certLandType;
    }

    public String getCertLandNum() {
        return certLandNum;
    }

    public void setCertLandNum(String certLandNum) {
        this.certLandNum = certLandNum == null ? null : certLandNum.trim();
    }

    public String getWaterFarmlandRegistered() {
        return waterFarmlandRegistered;
    }

    public void setWaterFarmlandRegistered(String waterFarmlandRegistered) {
        this.waterFarmlandRegistered = waterFarmlandRegistered == null ? null : waterFarmlandRegistered.trim();
    }

    public String getDryFarmlandRegistered() {
        return dryFarmlandRegistered;
    }

    public void setDryFarmlandRegistered(String dryFarmlandRegistered) {
        this.dryFarmlandRegistered = dryFarmlandRegistered == null ? null : dryFarmlandRegistered.trim();
    }

    public String getWaterFarmlandUnregistered() {
        return waterFarmlandUnregistered;
    }

    public void setWaterFarmlandUnregistered(String waterFarmlandUnregistered) {
        this.waterFarmlandUnregistered = waterFarmlandUnregistered == null ? null : waterFarmlandUnregistered.trim();
    }

    public String getDryFarmlandUnregistered() {
        return dryFarmlandUnregistered;
    }

    public void setDryFarmlandUnregistered(String dryFarmlandUnregistered) {
        this.dryFarmlandUnregistered = dryFarmlandUnregistered == null ? null : dryFarmlandUnregistered.trim();
    }

    public String getWaterFarmlandContract() {
        return waterFarmlandContract;
    }

    public void setWaterFarmlandContract(String waterFarmlandContract) {
        this.waterFarmlandContract = waterFarmlandContract == null ? null : waterFarmlandContract.trim();
    }

    public String getDryFarmlandContract() {
        return dryFarmlandContract;
    }

    public void setDryFarmlandContract(String dryFarmlandContract) {
        this.dryFarmlandContract = dryFarmlandContract == null ? null : dryFarmlandContract.trim();
    }

    public Integer getRentHouse() {
        return rentHouse;
    }

    public void setRentHouse(Integer rentHouse) {
        this.rentHouse = rentHouse;
    }

    public Integer getCountrysideHouse() {
        return countrysideHouse;
    }

    public void setCountrysideHouse(Integer countrysideHouse) {
        this.countrysideHouse = countrysideHouse;
    }

    public Integer getSmallPropertyHouse() {
        return smallPropertyHouse;
    }

    public void setSmallPropertyHouse(Integer smallPropertyHouse) {
        this.smallPropertyHouse = smallPropertyHouse;
    }

    public String getSmallPropertyHouseArea() {
        return smallPropertyHouseArea;
    }

    public void setSmallPropertyHouseArea(String smallPropertyHouseArea) {
        this.smallPropertyHouseArea = smallPropertyHouseArea == null ? null : smallPropertyHouseArea.trim();
    }

    public String getSmallPropertyHouseValue() {
        return smallPropertyHouseValue;
    }

    public void setSmallPropertyHouseValue(String smallPropertyHouseValue) {
        this.smallPropertyHouseValue = smallPropertyHouseValue == null ? null : smallPropertyHouseValue.trim();
    }

    public Integer getBigPropertyHouse() {
        return bigPropertyHouse;
    }

    public void setBigPropertyHouse(Integer bigPropertyHouse) {
        this.bigPropertyHouse = bigPropertyHouse;
    }

    public String getBigPropertyHouseArea() {
        return bigPropertyHouseArea;
    }

    public void setBigPropertyHouseArea(String bigPropertyHouseArea) {
        this.bigPropertyHouseArea = bigPropertyHouseArea == null ? null : bigPropertyHouseArea.trim();
    }

    public String getBigPropertyHouseValue() {
        return bigPropertyHouseValue;
    }

    public void setBigPropertyHouseValue(String bigPropertyHouseValue) {
        this.bigPropertyHouseValue = bigPropertyHouseValue == null ? null : bigPropertyHouseValue.trim();
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName == null ? null : carName.trim();
    }

    public String getCarValue() {
        return carValue;
    }

    public void setCarValue(String carValue) {
        this.carValue = carValue == null ? null : carValue.trim();
    }

    public Integer getMinibus() {
        return minibus;
    }

    public void setMinibus(Integer minibus) {
        this.minibus = minibus;
    }

    public String getMinibusValue() {
        return minibusValue;
    }

    public void setMinibusValue(String minibusValue) {
        this.minibusValue = minibusValue == null ? null : minibusValue.trim();
    }

    public Integer getTruck() {
        return truck;
    }

    public void setTruck(Integer truck) {
        this.truck = truck;
    }

    public String getTruckValue() {
        return truckValue;
    }

    public void setTruckValue(String truckValue) {
        this.truckValue = truckValue == null ? null : truckValue.trim();
    }

    public Integer getOtherTricycle() {
        return otherTricycle;
    }

    public void setOtherTricycle(Integer otherTricycle) {
        this.otherTricycle = otherTricycle;
    }

    public Integer getNongMachine() {
        return nongMachine;
    }

    public void setNongMachine(Integer nongMachine) {
        this.nongMachine = nongMachine;
    }

    public String getNongMachineRemark() {
        return nongMachineRemark;
    }

    public void setNongMachineRemark(String nongMachineRemark) {
        this.nongMachineRemark = nongMachineRemark == null ? null : nongMachineRemark.trim();
    }

    public String getNongMachineEstimatedValue() {
        return nongMachineEstimatedValue;
    }

    public void setNongMachineEstimatedValue(String nongMachineEstimatedValue) {
        this.nongMachineEstimatedValue = nongMachineEstimatedValue == null ? null : nongMachineEstimatedValue.trim();
    }

    public String getTotalFamilyDebtedness() {
        return totalFamilyDebtedness;
    }

    public void setTotalFamilyDebtedness(String totalFamilyDebtedness) {
        this.totalFamilyDebtedness = totalFamilyDebtedness == null ? null : totalFamilyDebtedness.trim();
    }

    public String getInvisibleDebtedness() {
        return invisibleDebtedness;
    }

    public void setInvisibleDebtedness(String invisibleDebtedness) {
        this.invisibleDebtedness = invisibleDebtedness == null ? null : invisibleDebtedness.trim();
    }

    public Integer getBankLoan() {
        return bankLoan;
    }

    public void setBankLoan(Integer bankLoan) {
        this.bankLoan = bankLoan;
    }

    public String getBankLoanIndebtedness() {
        return bankLoanIndebtedness;
    }

    public void setBankLoanIndebtedness(String bankLoanIndebtedness) {
        this.bankLoanIndebtedness = bankLoanIndebtedness == null ? null : bankLoanIndebtedness.trim();
    }

    public Integer getP2pPettyLoan() {
        return p2pPettyLoan;
    }

    public void setP2pPettyLoan(Integer p2pPettyLoan) {
        this.p2pPettyLoan = p2pPettyLoan;
    }

    public String getP2pPettyLoanIndebtedness() {
        return p2pPettyLoanIndebtedness;
    }

    public void setP2pPettyLoanIndebtedness(String p2pPettyLoanIndebtedness) {
        this.p2pPettyLoanIndebtedness = p2pPettyLoanIndebtedness == null ? null : p2pPettyLoanIndebtedness.trim();
    }

    public Integer getFriendLoan() {
        return friendLoan;
    }

    public void setFriendLoan(Integer friendLoan) {
        this.friendLoan = friendLoan;
    }

    public String getFriendLoanIndebtedness() {
        return friendLoanIndebtedness;
    }

    public void setFriendLoanIndebtedness(String friendLoanIndebtedness) {
        this.friendLoanIndebtedness = friendLoanIndebtedness == null ? null : friendLoanIndebtedness.trim();
    }

    public Integer getGuaranteeLoan() {
        return guaranteeLoan;
    }

    public void setGuaranteeLoan(Integer guaranteeLoan) {
        this.guaranteeLoan = guaranteeLoan;
    }

    public String getGuaranteeLoanIndebtedness() {
        return guaranteeLoanIndebtedness;
    }

    public void setGuaranteeLoanIndebtedness(String guaranteeLoanIndebtedness) {
        this.guaranteeLoanIndebtedness = guaranteeLoanIndebtedness == null ? null : guaranteeLoanIndebtedness.trim();
    }

    public Integer getMainInvest() {
        return mainInvest;
    }

    public void setMainInvest(Integer mainInvest) {
        this.mainInvest = mainInvest;
    }

    public Integer getCurrentWorkYears() {
        return currentWorkYears;
    }

    public void setCurrentWorkYears(Integer currentWorkYears) {
        this.currentWorkYears = currentWorkYears;
    }

    public Integer getInitialCapitalFrom() {
        return initialCapitalFrom;
    }

    public void setInitialCapitalFrom(Integer initialCapitalFrom) {
        this.initialCapitalFrom = initialCapitalFrom;
    }

    public Integer getInvestPremises() {
        return investPremises;
    }

    public void setInvestPremises(Integer investPremises) {
        this.investPremises = investPremises;
    }

    public Integer getInvestFixedAssets() {
        return investFixedAssets;
    }

    public void setInvestFixedAssets(Integer investFixedAssets) {
        this.investFixedAssets = investFixedAssets;
    }

    public Integer getOperatingLoss() {
        return operatingLoss;
    }

    public void setOperatingLoss(Integer operatingLoss) {
        this.operatingLoss = operatingLoss;
    }

    public Integer getDiseasePay() {
        return diseasePay;
    }

    public void setDiseasePay(Integer diseasePay) {
        this.diseasePay = diseasePay;
    }

    public Integer getGambling() {
        return gambling;
    }

    public void setGambling(Integer gambling) {
        this.gambling = gambling;
    }

    public Integer getDrugTaking() {
        return drugTaking;
    }

    public void setDrugTaking(Integer drugTaking) {
        this.drugTaking = drugTaking;
    }

    public Integer getOperateDebt() {
        return operateDebt;
    }

    public void setOperateDebt(Integer operateDebt) {
        this.operateDebt = operateDebt;
    }

    public String getLiabilityAssetRatio() {
        return liabilityAssetRatio;
    }

    public void setLiabilityAssetRatio(String liabilityAssetRatio) {
        this.liabilityAssetRatio = liabilityAssetRatio == null ? null : liabilityAssetRatio.trim();
    }

    public String getQueryCreditTimes() {
        return queryCreditTimes;
    }

    public void setQueryCreditTimes(String queryCreditTimes) {
        this.queryCreditTimes = queryCreditTimes == null ? null : queryCreditTimes.trim();
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

	public String getNongMachineYear() {
		return nongMachineYear;
	}

	public void setNongMachineYear(String nongMachineYear) {
		this.nongMachineYear = nongMachineYear;
	}
}