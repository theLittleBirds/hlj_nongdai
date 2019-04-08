package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 进件信息表
 * 
 * @author wcyong
 * 
 * @date 2018-08-07
 */
public class BusIntoPiece {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 模型ID
     */
    private String modelId;

    /**
     * 产业链
     */
    private String orgId;

    /**
     * 产品
     */
    private String product;

    /**
     * 操作人
     */
    private String name;

    /**
     * 授信额度
     */
    private BigDecimal creditCapital;

    /**
     * 申请金额（单位：元）
     */
    private BigDecimal capital;

    /**
     * 借款期限（单月：月）
     */
    private Integer term;

    /**
     * 客户类型 1新增  2转贷
     */
    private Integer use;

    /**
     * 借款用途备注
     */
    private String useRemark;

    /**
     * 客户ID
     */
    private String memberId;

    /**
     * 客户姓名
     */
    private String memberName;

    /**
     * 性别（1：男  2：女）
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 银行卡开户行
     */
    private String bank;

    /**
     * 银行卡账号
     */
    private String bankCardNo;

    /**
     * 近6个月详细住址
     */
    private String address;

    /**
     * 文化程度（1：初中以下  2：中专/高中  3：大专  4：本科及以上）
     */
    private Integer educationLevel;

    /**
     * 婚姻状况（1：已婚  2：未婚  3：离异  4：丧偶）
     */
    private Integer maritalStatus;

    /**
     * 从事职业（1：在家务农  2：从事非农职业）多选
     */
    private String duty;

    /**
     * 从事非农职业单位名称
     */
    private String nonfarmComName;

    /**
     * 从事非农职业地址
     */
    private String nonfarmComAddress;

    /**
     * 从事非农职业电话
     */
    private String nonfarmComPhone;

    /**
     * 初审人
     */
    private String trialBy;

    /**
     * 初审人姓名
     */
    private String trialName;

    /**
     * 初审日期
     */
    private Date trialDate;

    /**
     * 初审意见
     */
    private String trialOpinion;

    /**
     * 复审人
     */
    private String reviewBy;

    /**
     * 复审人姓名
     */
    private String reviewName;

    /**
     * 复审日期
     */
    private Date reviewDate;

    /**
     * 复审意见
     */
    private String reviewOpinion;

    /**
     * 终审人
     */
    private String lastBy;

    /**
     * 终审人姓名
     */
    private String lastName;

    /**
     * 终审日期
     */
    private Date lastDate;

    /**
     * 终审意见
     */
    private String lastOpinion;
    
    /**
     * 是否直接扣除担保费（1，是 2，否）
     */
    private Integer serviceFeeWay;
    
    /**
     * 客户资料核实情况(1,较大出入，已核实修改 2,较小出入，已核实并修改 3,基本吻合，已核实无修改)
     */
    private Integer memberInfoChecking;

    /**
     * 客户家庭情况记录与总结
     */
    private String familyRecordSummary;

    /**
     * 资产负债核实情况
     */
    private String assetChecking;

    /**
     * 经营情况
     */
    private String asset;

    private Integer type;
    
    /**
     * 订单状态  1:待打款     2：待支付保证金      3：待下单
     */
    private Integer orderStatus;

	/**
     * 对于不同app权限标识
     */
    private String channel;

    /**
     * 出借人id
     */
    private String lenderId;

    /**
     * 同盾贷前接口report_id
     */
    private String reportId;

    /**
     * 操作人员id
     */
    private String operUserId;

    private Short isDeleted;

    private String creOperId;

    private String creOperName;

    private String creOrgId;

    private Date creDate;

    private String updOperId;

    private String updOperName;

    private String updOrgId;

    private Date updDate;
    
  //拒件标识
    private Integer reject;
    //已选商品总结算价格（元）
    private String  productSettlePrice;
    //已选商品总显示价格
    private String  productPrice;
    //已选商品清单
    private String  productListInfo;
    //到手农资（元）
    private String  recieveNongZi;
    //到手现金（元）
    private String  recieveCash;
    
    public String getRecieveNongZi() {
		return recieveNongZi;
	}

	public void setRecieveNongZi(String recieveNongZi) {
		this.recieveNongZi = recieveNongZi;
	}

	public String getRecieveCash() {
		return recieveCash;
	}

	public void setRecieveCash(String recieveCash) {
		this.recieveCash = recieveCash;
	}

	public String getProductSettlePrice() {
		return productSettlePrice;
	}

	public void setProductSettlePrice(String productSettlePrice) {
		this.productSettlePrice = productSettlePrice;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductListInfo() {
		return productListInfo;
	}

	public void setProductListInfo(String productListInfo) {
		this.productListInfo = productListInfo;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(BigDecimal creditCapital) {
        this.creditCapital = creditCapital;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }

    public String getUseRemark() {
        return useRemark;
    }

    public void setUseRemark(String useRemark) {
        this.useRemark = useRemark == null ? null : useRemark.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getNonfarmComName() {
        return nonfarmComName;
    }

    public void setNonfarmComName(String nonfarmComName) {
        this.nonfarmComName = nonfarmComName == null ? null : nonfarmComName.trim();
    }

    public String getNonfarmComAddress() {
        return nonfarmComAddress;
    }

    public void setNonfarmComAddress(String nonfarmComAddress) {
        this.nonfarmComAddress = nonfarmComAddress == null ? null : nonfarmComAddress.trim();
    }

    public String getNonfarmComPhone() {
        return nonfarmComPhone;
    }

    public void setNonfarmComPhone(String nonfarmComPhone) {
        this.nonfarmComPhone = nonfarmComPhone == null ? null : nonfarmComPhone.trim();
    }

    public String getTrialBy() {
        return trialBy;
    }

    public void setTrialBy(String trialBy) {
        this.trialBy = trialBy == null ? null : trialBy.trim();
    }

    public String getTrialName() {
        return trialName;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName == null ? null : trialName.trim();
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public String getTrialOpinion() {
        return trialOpinion;
    }

    public void setTrialOpinion(String trialOpinion) {
        this.trialOpinion = trialOpinion == null ? null : trialOpinion.trim();
    }

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy == null ? null : reviewBy.trim();
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName == null ? null : reviewName.trim();
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewOpinion() {
        return reviewOpinion;
    }

    public void setReviewOpinion(String reviewOpinion) {
        this.reviewOpinion = reviewOpinion == null ? null : reviewOpinion.trim();
    }

    public String getLastBy() {
        return lastBy;
    }

    public void setLastBy(String lastBy) {
        this.lastBy = lastBy == null ? null : lastBy.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastOpinion() {
        return lastOpinion;
    }

    public void setLastOpinion(String lastOpinion) {
        this.lastOpinion = lastOpinion == null ? null : lastOpinion.trim();
    }

    public Integer getMemberInfoChecking() {
        return memberInfoChecking;
    }

    public void setMemberInfoChecking(Integer memberInfoChecking) {
        this.memberInfoChecking = memberInfoChecking;
    }

    public String getFamilyRecordSummary() {
        return familyRecordSummary;
    }

    public void setFamilyRecordSummary(String familyRecordSummary) {
        this.familyRecordSummary = familyRecordSummary == null ? null : familyRecordSummary.trim();
    }

    public String getAssetChecking() {
        return assetChecking;
    }

    public void setAssetChecking(String assetChecking) {
        this.assetChecking = assetChecking == null ? null : assetChecking.trim();
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset == null ? null : asset.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId == null ? null : lenderId.trim();
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId == null ? null : reportId.trim();
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId == null ? null : operUserId.trim();
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

	public Integer getReject() {
		return reject;
	}

	public void setReject(Integer reject) {
		this.reject = reject;
	}

	public Integer getServiceFeeWay() {
		return serviceFeeWay;
	}

	public void setServiceFeeWay(Integer serviceFeeWay) {
		this.serviceFeeWay = serviceFeeWay;
	}
}
