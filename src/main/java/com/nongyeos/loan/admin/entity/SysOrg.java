package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * InnoDB free: 10240 kB
 * 
 * @author dzp
 * 
 * @date 2018-03-22
 */
public class SysOrg {
    private String orgId;

    private String fullCname;

    private String fullEname;

    private String shortCname;

    private String shortEname;

    private String parentOrgId;

    private String parentOrgIds;
    
    private String productBrand;

    private Integer seqno;

    private Short status;

    private String phone;

    private String fax;
    
    private String leader;
    
    private String idCard;
    
    private String cardNo;
    
    private String cardBank;
    
    private String address;
    
    private String filepath;

    private String email;

    private String homeUrl;

    private String addrCn;

    private String addrEn;

    private String contactName;

    private String contactPhone;

    private String contactEmail;

    private Short isDeleted;

    private String creOperId;

    private String creOperName;

    private String creOrgId;

    private Date creDate;

    private String updOperId;

    private String updOperName;

    private String updOrgId;

    private Date updDate;

    /**
     * 商户下子订单的总价（暂存数据用的）
     */
    private String totalPrice;

    /**
     * 标记（判断订单详细下商户table的按钮用）
     */
    private String flag;

    /**
     * 担保金比例
     */
    private String warrantRate;

    /**
     * 机构类型（1，服务站 2，商户）
     */
    private String orgType;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getFullCname() {
        return fullCname;
    }

    public void setFullCname(String fullCname) {
        this.fullCname = fullCname == null ? null : fullCname.trim();
    }

    public String getFullEname() {
        return fullEname;
    }

    public void setFullEname(String fullEname) {
        this.fullEname = fullEname == null ? null : fullEname.trim();
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

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId == null ? null : parentOrgId.trim();
    }

    public String getParentOrgIds() {
        return parentOrgIds;
    }

    public void setParentOrgIds(String parentOrgIds) {
        this.parentOrgIds = parentOrgIds == null ? null : parentOrgIds.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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
    
    public String getLeader() {
    	return leader;
    }
    
    public void setLeader(String leader) {
    	this.leader = leader == null ? null : leader.trim();
    }
    
    public String getAddress() {
    	return address;
    }
    
    public void setAddress(String address) {
    	this.address = address == null ? null : address.trim();
    }
    
    public String getFilepath() {
    	return filepath;
    }
    
    public void setFilepath(String filepath) {
    	this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl == null ? null : homeUrl.trim();
    }

    public String getAddrCn() {
        return addrCn;
    }

    public void setAddrCn(String addrCn) {
        this.addrCn = addrCn == null ? null : addrCn.trim();
    }

    public String getAddrEn() {
        return addrEn;
    }

    public void setAddrEn(String addrEn) {
        this.addrEn = addrEn == null ? null : addrEn.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getWarrantRate() {
		return warrantRate;
	}

	public void setWarrantRate(String warrantRate) {
		this.warrantRate = warrantRate;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}