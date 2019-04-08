package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author zd
 * 
 * @date 2019-03-25
 */
public class BusNjshopingcar {
    /**
     * 购物车id
     */
    private String shopingCarId;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 用户id
     */
    private String personId;

    /**
     * 用户名称
     */
    private String memberName;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品品牌名称
     */
    private String productBrandName;

    /**
     * 商品分类名称
     */
    private String productCategoryName;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 商品单价
     */
    private String productPrice;

    /**
     * 商品结算单价
     */
    private String productSettlePrice;

    /**
     * 详情图片在七牛的key
     */
    private String detailPic;

    private Date createDate;

    private Date updateDate;

    public String getShopingCarId() {
        return shopingCarId;
    }

    public void setShopingCarId(String shopingCarId) {
        this.shopingCarId = shopingCarId == null ? null : shopingCarId.trim();
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
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

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName == null ? null : productCategoryName.trim();
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice == null ? null : productPrice.trim();
    }

    public String getProductSettlePrice() {
        return productSettlePrice;
    }

    public void setProductSettlePrice(String productSettlePrice) {
        this.productSettlePrice = productSettlePrice == null ? null : productSettlePrice.trim();
    }

    public String getDetailPic() {
        return detailPic;
    }

    public void setDetailPic(String detailPic) {
        this.detailPic = detailPic == null ? null : detailPic.trim();
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
}