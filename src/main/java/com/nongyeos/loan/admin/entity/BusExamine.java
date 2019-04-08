package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-11-28
 */
public class BusExamine {
    /**
     * id
     */
    private String id;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 当前节点
     */
    private String node;

    /**
     * 金融产品id
     */
    private String productId;

    /**
     * 授信额度
     */
    private BigDecimal capital;

    /**
     * 借款期限（月）
     */
    private Integer term;

    /**
     * 审核人id
     */
    private String examineBy;

    /**
     * 审核人姓名
     */
    private String examineName;

    /**
     * 审核时间
     */
    private Date examineDate;

    /**
     * 审核意见
     */
    private String examineOpinion;

    /**
     * 已选商品总结算价格（元）
     */
    private String productSettlePrice;

    /**
     * 已选商品总显示价格
     */
    private String productPrice;

    /**
     * 已选商品清单
     */
    private String productListInfo;

    /**
     * 到手农资（元）
     */
    private String recieveNongZi;

    /**
     * 到手现金
     */
    private String recieveCash;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node == null ? null : node.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getExamineBy() {
        return examineBy;
    }

    public void setExamineBy(String examineBy) {
        this.examineBy = examineBy == null ? null : examineBy.trim();
    }

    public String getExamineName() {
        return examineName;
    }

    public void setExamineName(String examineName) {
        this.examineName = examineName == null ? null : examineName.trim();
    }

    public Date getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(Date examineDate) {
        this.examineDate = examineDate;
    }

    public String getExamineOpinion() {
        return examineOpinion;
    }

    public void setExamineOpinion(String examineOpinion) {
        this.examineOpinion = examineOpinion == null ? null : examineOpinion.trim();
    }

    public String getProductSettlePrice() {
        return productSettlePrice;
    }

    public void setProductSettlePrice(String productSettlePrice) {
        this.productSettlePrice = productSettlePrice == null ? null : productSettlePrice.trim();
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice == null ? null : productPrice.trim();
    }

    public String getProductListInfo() {
        return productListInfo;
    }

    public void setProductListInfo(String productListInfo) {
        this.productListInfo = productListInfo == null ? null : productListInfo.trim();
    }

    public String getRecieveNongZi() {
        return recieveNongZi;
    }

    public void setRecieveNongZi(String recieveNongZi) {
        this.recieveNongZi = recieveNongZi == null ? null : recieveNongZi.trim();
    }

    public String getRecieveCash() {
        return recieveCash;
    }

    public void setRecieveCash(String recieveCash) {
        this.recieveCash = recieveCash == null ? null : recieveCash.trim();
    }
}