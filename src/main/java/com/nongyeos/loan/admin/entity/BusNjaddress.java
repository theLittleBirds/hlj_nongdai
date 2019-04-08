package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author zd
 * 
 * @date 2019-03-25
 */
public class BusNjaddress {
    /**
     * 收货地址id
     */
    private String addressId;

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

    /**
     * 是否默认为收货地址
     */
    private String isDefault;

    private Date createDate;

    private Date updateDate;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddressZh() {
        return addressZh;
    }

    public void setAddressZh(String addressZh) {
        this.addressZh = addressZh == null ? null : addressZh.trim();
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
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