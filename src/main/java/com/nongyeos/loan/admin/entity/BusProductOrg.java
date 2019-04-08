package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-19
 */
public class BusProductOrg {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 产品ID
     */
    private String productId;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}