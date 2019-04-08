package com.nongyeos.loan.app.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-12
 */
public class IntProvider {
    private String providerCode;

    private String cname;

    private String ename;

    private String shortCname;

    private String shortEname;

    private String logo;

    private String address;

    private String phone;

    private String fax;

    private String postcode;

    private String contactPerson;

    private Integer seqno;

    private short isDelete;

    private String creOperCode;

    private String creOperName;

    private String creOrgCode;

    private Date creDate;

    private String updOperCode;

    private String updOperName;

    private String updOrgCode;

    private Date updDate;

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode == null ? null : providerCode.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public String getShortCname() {
        return shortCname;
    }

    public void setShortCname(String shortCname) {
        this.shortCname = shortCname == null ? null : shortCname.trim();
    }

    public String getShortEname() {
        return shortEname;
    }

    public void setShortEname(String shortEname) {
        this.shortEname = shortEname == null ? null : shortEname.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(short isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreOperCode() {
        return creOperCode;
    }

    public void setCreOperCode(String creOperCode) {
        this.creOperCode = creOperCode == null ? null : creOperCode.trim();
    }

    public String getCreOperName() {
        return creOperName;
    }

    public void setCreOperName(String creOperName) {
        this.creOperName = creOperName == null ? null : creOperName.trim();
    }

    public String getCreOrgCode() {
        return creOrgCode;
    }

    public void setCreOrgCode(String creOrgCode) {
        this.creOrgCode = creOrgCode == null ? null : creOrgCode.trim();
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public String getUpdOperCode() {
        return updOperCode;
    }

    public void setUpdOperCode(String updOperCode) {
        this.updOperCode = updOperCode == null ? null : updOperCode.trim();
    }

    public String getUpdOperName() {
        return updOperName;
    }

    public void setUpdOperName(String updOperName) {
        this.updOperName = updOperName == null ? null : updOperName.trim();
    }

    public String getUpdOrgCode() {
        return updOrgCode;
    }

    public void setUpdOrgCode(String updOrgCode) {
        this.updOrgCode = updOrgCode == null ? null : updOrgCode.trim();
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}