package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author zd
 * 
 * @date 2019-03-25
 */
public class BusNjorderinfo {
    /**
     * 订单明细id
     */
    private String orderInfoId;

    /**
     * 主订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商户id（org_id）
     */
    private String merId;

    /**
     * 商户名字
     */
    private String merName;

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
     * 付款凭证
     */
    private String payPic;

    /**
     * 尾款凭证
     */
    private String payPicTail;

    /**
     * 订单状态
     */
    private String status;

    private Date createDate;

    private Date updateDate;

    public String getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(String orderInfoId) {
        this.orderInfoId = orderInfoId == null ? null : orderInfoId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getPayPic() {
        return payPic;
    }

    public void setPayPic(String payPic) {
        this.payPic = payPic;
    }

    public String getPayPicTail() {
        return payPicTail;
    }

    public void setPayPicTail(String payPicTail) {
        this.payPicTail = payPicTail;
    }
}