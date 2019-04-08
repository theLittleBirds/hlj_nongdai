package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author zd
 * 
 * @date 2019-03-25
 */
public class BusNjorder {
    /**
     * 主订单id
     */
    private String orderId;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 用户名称
     */
    private String memberName;

    /**
     * 显示总金额
     */
    private String totalPrice;

    /**
     * 结算总金额
     */
    private String totalSettlePrice;

    /**
     * 订单收货地址
     */
    private String orderAddress;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 付款凭证
     */
    private String payPic;

    private Date createDate;

    private Date updateDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice == null ? null : totalPrice.trim();
    }

    public String getTotalSettlePrice() {
        return totalSettlePrice;
    }

    public void setTotalSettlePrice(String totalSettlePrice) {
        this.totalSettlePrice = totalSettlePrice == null ? null : totalSettlePrice.trim();
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress == null ? null : orderAddress.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPayPic() {
        return payPic;
    }

    public void setPayPic(String payPic) {
        this.payPic = payPic == null ? null : payPic.trim();
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