package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-03-28
 */
public class BusNjmerorder {
    private Integer merOrderId;

    /**
     * 主订单Id
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
     * 显示总金额
     */
    private String totalPrice;

    /**
     * 结算总金额
     */
    private String totalSettlePrice;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 付款凭证
     */
    private String payPic;

    /**
     * 尾款凭证
     */
    private String payPicTail;

    /**
     *  发货凭证
     */
    private String sendPic;

    /**
     *  收货凭证
     */
    private String receivePic;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 收货地址
     */
    private String addressZh;

    private Date createDate;

    private Date updateDate;

    public Integer getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(Integer merOrderId) {
        this.merOrderId = merOrderId;
    }

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

    public String getPayPicTail() {
        return payPicTail;
    }

    public void setPayPicTail(String payPicTail) {
        this.payPicTail = payPicTail == null ? null : payPicTail.trim();
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

    public String getSendPic() {
        return sendPic;
    }

    public void setSendPic(String sendPic) {
        this.sendPic = sendPic;
    }

    public String getReceivePic() {
        return receivePic;
    }

    public void setReceivePic(String receivePic) {
        this.receivePic = receivePic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressZh() {
        return addressZh;
    }

    public void setAddressZh(String addressZh) {
        this.addressZh = addressZh;
    }
}