package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-10-25
 */
public class NjOrderGather {
    /**
     * 主键
     */
    private String id;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 进件人员的人员编号
     */
    private String personId;

    /**
     * 进件部门组织机构编码
     */
    private String orgId;

    /**
     * 进件部门名称（组织机构名称）
     */
    private String orgName;

    /**
     * 客户姓名
     */
    private String memberName;

    /**
     * 商户组织机构编码
     */
    private String merOrgId;

    /**
     * 商家名字
     */
    private String merName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品品牌名字
     */
    private String productBrandName;

    /**
     * 商品品牌，  1：沃肯多，2：金正大，3：沃夫特，4：科雨，5：东北吉化，6：万通盛泰，7：施地壮
     */
    private String productBrand;

    /**
     * 商品分类名字
     */
    private String productCategoryName;

    /**
     * 商品分类，  1：化肥，2：种子，3：农药，4：农机，5：其他农用品
     */
    private String productCategory;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 结算单价
     */
    private String settlePrice;

    /**
     * 结算总价
     */
    private String settleTotalprice;

    /**
     * 商品数量
     */
    private String productNum;

    /**
     * 商品用货时间
     */
    private Date productUsetime;

    /**
     * 定金图片
     */
    private String advancePic;

    /**
     * 尾款图片
     */
    private String finalPic;

    /**
     * 发货图片
     */
    private String consignmentPic;
    
    /**
     * 是否失效
     */
    private String isDeleted;

    /**
     * 收货单时间
     */
    private String receiveDocTime;
    
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMerOrgId() {
        return merOrgId;
    }

    public void setMerOrgId(String merOrgId) {
        this.merOrgId = merOrgId == null ? null : merOrgId.trim();
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName == null ? null : productBrandName.trim();
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand == null ? null : productBrand.trim();
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName == null ? null : productCategoryName.trim();
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory == null ? null : productCategory.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(String settlePrice) {
        this.settlePrice = settlePrice == null ? null : settlePrice.trim();
    }

    public String getSettleTotalprice() {
        return settleTotalprice;
    }

    public void setSettleTotalprice(String settleTotalprice) {
        this.settleTotalprice = settleTotalprice == null ? null : settleTotalprice.trim();
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum == null ? null : productNum.trim();
    }

    public Date getProductUsetime() {
        return productUsetime;
    }

    public void setProductUsetime(Date productUsetime) {
        this.productUsetime = productUsetime;
    }

    public String getAdvancePic() {
        return advancePic;
    }

    public void setAdvancePic(String advancePic) {
        this.advancePic = advancePic == null ? null : advancePic.trim();
    }

    public String getFinalPic() {
        return finalPic;
    }

    public void setFinalPic(String finalPic) {
        this.finalPic = finalPic == null ? null : finalPic.trim();
    }

    public String getConsignmentPic() {
        return consignmentPic;
    }

    public void setConsignmentPic(String consignmentPic) {
        this.consignmentPic = consignmentPic == null ? null : consignmentPic.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getReceiveDocTime() {
		return receiveDocTime;
	}

	public void setReceiveDocTime(String receiveDocTime) {
		this.receiveDocTime = receiveDocTime;
	}
    
}