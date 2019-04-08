package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 放款表
 * 
 * @author wcyong
 * 
 * @date 2018-11-28
 */
public class BusLoan {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 进件ID
     */
    private String intoPieceId;

    /**
     * 进件名称
     */
    private String intoPieceName;

    /**
     * 客户ID
     */
    private String memberId;

    /**
     * 客户姓名
     */
    private String memberName;

    /**
     * 借款额度（单位：元）
     */
    private BigDecimal capital;

    /**
     * 服务费
     */
    private BigDecimal serviceFee;

    /**
     * 需要还款的总额（本金+利息）（单位：元）
     */
    private BigDecimal loanedManFee;

    /**
     * 年利率%
     */
    private BigDecimal rate;

    /**
     * 逾期天利率%
     */
    private BigDecimal overdueRate;

    /**
     * 服务费利率类型（1：月利率  2：总利率）
     */
    private Integer serviceRateType;

    /**
     * 服务费年利率%
     */
    private BigDecimal serviceRate;

    /**
     * 借款期限（单月：月）
     */
    private Integer term;

    /**
     * 还款方式（1：先息后本  2：等额本息）
     */
    private Integer repaymentType;

    /**
     * 月还款金额
     */
    private BigDecimal monthRepayment;

    /**
     * 借款用途
     */
    private String use;

    /**
     * 已收本金（单位：元）
     */
    private BigDecimal receiveCapital;

    /**
     * 已收利息（单位：元）
     */
    private BigDecimal receiveInterest;

    /**
     * 已收逾期费用（单位：元）
     */
    private BigDecimal receiveOverdue;

    /**
     * 放款时间
     */
    private Date loanDate;

    /**
     * 打款时间
     */
    private String remitDate;

    /**
     * 借款开始日期
     */
    private String startDate;

    /**
     * 借款结束日期
     */
    private String endDate;

    /**
     * 最新一笔还款日期
     */
    private Date firstRepaymentDate;

    /**
     * 最后一笔还款日期
     */
    private Date lastRepaymentDate;

    /**
     * 状态（1：合同制作与签署中  2：待初审  3：待复审  4：审核未通过  5：放款中  6：贷后中  7：贷后）
     */
    private Integer status;

    /**
     * 不同app权限标识（公司类型）
     */
    private String channel;

    /**
     * 易行通签约状态（1：待签约  2：签约中  3：签约成功  4：签约失败）
     */
    private Integer signStatus;

    /**
     * 放款方式（1：线下  2：线上）
     */
    private Integer loanWay;

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
     * 备注
     */
    private String info;

    /**
     * 负责人ID
     */
    private String userId;

    /**
     * 放款手续费方式(1：不贷手续费  2：贷手续费并打款手续费  3：贷手续费但不打手续费)
     */
    private Integer serviceFeeType;

    /**
     * 先息期限
     */
    private Integer duration2;

    /**
     * 是否受托放款
     */
    private String entrusted;

    /**
     * 收款商家名称
     */
    private String sellerName;

    /**
     * 收款商家银行账户
     */
    private String sellerBankCardNo;

    /**
     * 收款商家银行编码
     */
    private String sellerBankCode;

    /**
     * 收款商家银行名称
     */
    private String sellerBankName;

    /**
     * 收款商家开户行省份
     */
    private String sellerBankProvince;

    /**
     * 收款商家开户行市级
     */
    private String sellerBankCity;

    /**
     * 收款商家开户行地址
     */
    private String sellerBankAddress;

    /**
     * 金融机构id
     */
    private String lenderId;

    /**
     * 金融产品id
     */
    private String productId;

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

    /**
     * 已选商品总结算价格（元）
     */
    private String productSettlePrice;

    /**
     * 已选商品总显示价格
     */
    private String productPrice;

    /**
     * 已选商品清单
     */
    private String productListInfo;

    /**
     * 到手农资（元）
     */
    private String recieveNongZi;

    /**
     * 到手现金
     */
    private String recieveCash;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getIntoPieceName() {
        return intoPieceName;
    }

    public void setIntoPieceName(String intoPieceName) {
        this.intoPieceName = intoPieceName == null ? null : intoPieceName.trim();
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

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getLoanedManFee() {
        return loanedManFee;
    }

    public void setLoanedManFee(BigDecimal loanedManFee) {
        this.loanedManFee = loanedManFee;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(BigDecimal overdueRate) {
        this.overdueRate = overdueRate;
    }

    public Integer getServiceRateType() {
        return serviceRateType;
    }

    public void setServiceRateType(Integer serviceRateType) {
        this.serviceRateType = serviceRateType;
    }

    public BigDecimal getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(BigDecimal serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(Integer repaymentType) {
        this.repaymentType = repaymentType;
    }

    public BigDecimal getMonthRepayment() {
        return monthRepayment;
    }

    public void setMonthRepayment(BigDecimal monthRepayment) {
        this.monthRepayment = monthRepayment;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use == null ? null : use.trim();
    }

    public BigDecimal getReceiveCapital() {
        return receiveCapital;
    }

    public void setReceiveCapital(BigDecimal receiveCapital) {
        this.receiveCapital = receiveCapital;
    }

    public BigDecimal getReceiveInterest() {
        return receiveInterest;
    }

    public void setReceiveInterest(BigDecimal receiveInterest) {
        this.receiveInterest = receiveInterest;
    }

    public BigDecimal getReceiveOverdue() {
        return receiveOverdue;
    }

    public void setReceiveOverdue(BigDecimal receiveOverdue) {
        this.receiveOverdue = receiveOverdue;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public String getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(String remitDate) {
        this.remitDate = remitDate == null ? null : remitDate.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public Date getFirstRepaymentDate() {
        return firstRepaymentDate;
    }

    public void setFirstRepaymentDate(Date firstRepaymentDate) {
        this.firstRepaymentDate = firstRepaymentDate;
    }

    public Date getLastRepaymentDate() {
        return lastRepaymentDate;
    }

    public void setLastRepaymentDate(Date lastRepaymentDate) {
        this.lastRepaymentDate = lastRepaymentDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Integer getLoanWay() {
        return loanWay;
    }

    public void setLoanWay(Integer loanWay) {
        this.loanWay = loanWay;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getServiceFeeType() {
        return serviceFeeType;
    }

    public void setServiceFeeType(Integer serviceFeeType) {
        this.serviceFeeType = serviceFeeType;
    }

    public Integer getDuration2() {
        return duration2;
    }

    public void setDuration2(Integer duration2) {
        this.duration2 = duration2;
    }

    public String getEntrusted() {
        return entrusted;
    }

    public void setEntrusted(String entrusted) {
        this.entrusted = entrusted == null ? null : entrusted.trim();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public String getSellerBankCardNo() {
        return sellerBankCardNo;
    }

    public void setSellerBankCardNo(String sellerBankCardNo) {
        this.sellerBankCardNo = sellerBankCardNo == null ? null : sellerBankCardNo.trim();
    }

    public String getSellerBankCode() {
        return sellerBankCode;
    }

    public void setSellerBankCode(String sellerBankCode) {
        this.sellerBankCode = sellerBankCode == null ? null : sellerBankCode.trim();
    }

    public String getSellerBankName() {
        return sellerBankName;
    }

    public void setSellerBankName(String sellerBankName) {
        this.sellerBankName = sellerBankName == null ? null : sellerBankName.trim();
    }

    public String getSellerBankProvince() {
        return sellerBankProvince;
    }

    public void setSellerBankProvince(String sellerBankProvince) {
        this.sellerBankProvince = sellerBankProvince == null ? null : sellerBankProvince.trim();
    }

    public String getSellerBankCity() {
        return sellerBankCity;
    }

    public void setSellerBankCity(String sellerBankCity) {
        this.sellerBankCity = sellerBankCity == null ? null : sellerBankCity.trim();
    }

    public String getSellerBankAddress() {
        return sellerBankAddress;
    }

    public void setSellerBankAddress(String sellerBankAddress) {
        this.sellerBankAddress = sellerBankAddress == null ? null : sellerBankAddress.trim();
    }

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId == null ? null : lenderId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short commonIsnotDelete) {
        this.isDeleted = commonIsnotDelete;
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

    public String getProductSettlePrice() {
        return productSettlePrice;
    }

    public void setProductSettlePrice(String productSettlePrice) {
        this.productSettlePrice = productSettlePrice == null ? null : productSettlePrice.trim();
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice == null ? null : productPrice.trim();
    }

    public String getProductListInfo() {
        return productListInfo;
    }

    public void setProductListInfo(String productListInfo) {
        this.productListInfo = productListInfo == null ? null : productListInfo.trim();
    }

    public String getRecieveNongZi() {
        return recieveNongZi;
    }

    public void setRecieveNongZi(String recieveNongZi) {
        this.recieveNongZi = recieveNongZi == null ? null : recieveNongZi.trim();
    }

    public String getRecieveCash() {
        return recieveCash;
    }

    public void setRecieveCash(String recieveCash) {
        this.recieveCash = recieveCash == null ? null : recieveCash.trim();
    }
}