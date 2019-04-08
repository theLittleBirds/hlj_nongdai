package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-24
 */
public class BusLenderOrg {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 出借人/出借银行ID
     */
    private String lenderId;

    /**
     * 机构ID
     */
    private String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId == null ? null : lenderId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}