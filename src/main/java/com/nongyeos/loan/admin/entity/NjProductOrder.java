package com.nongyeos.loan.admin.entity;

import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-10-17
 */
public class NjProductOrder {
    /**
     * 主键
     */
    private String id;

    /**
     * 人员编号
     */
    private String personId;

    /**
     * 组织机构编码
     */
    private String orgId;

    /**
     * 套餐名称
     */
    private String orderName;

    /**
     * 套餐显示价格
     */
    private String orderPrice;

    /**
     * 套餐结算价格
     */
    private String orderSettleprice;

    /**
     * 套餐商品明显
     */
    private String orderProductinfo;

    /**
     * 套餐明细
     */
    private String orderDesc;

    /**
     * 套餐状态  1：上架，2：下架
     */
    private String status;

    /**
     * 套餐包含商品id
     */
    private String orderProductids;
    
    /**
     * 封面图片在七牛的key
     */
    private String coverPic;

    /**
     * 详情图片在七牛的key
     */
    private String detailPic;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * ****以下是njProductOrder的dto属性********************************************************
     */
    private String productId;//记录套餐商品id
    
    private String productNum;//记录套餐商品数量    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName == null ? null : orderName.trim();
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice == null ? null : orderPrice.trim();
    }

    public String getOrderSettleprice() {
        return orderSettleprice;
    }

    public void setOrderSettleprice(String orderSettleprice) {
        this.orderSettleprice = orderSettleprice == null ? null : orderSettleprice.trim();
    }

    public String getOrderProductinfo() {
        return orderProductinfo;
    }

    public void setOrderProductinfo(String orderProductinfo) {
        this.orderProductinfo = orderProductinfo == null ? null : orderProductinfo.trim();
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc == null ? null : orderDesc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic == null ? null : coverPic.trim();
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

	public String getOrderProductids() {
		return orderProductids;
	}

	public void setOrderProductids(String orderProductids) {
		this.orderProductids = orderProductids;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
    
}