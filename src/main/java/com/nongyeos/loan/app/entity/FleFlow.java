package com.nongyeos.loan.app.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-24
 */
public class FleFlow {
    private String flowId;

    private short type;

    private String finsId;

    private short category;

    private String cname;

    private String ename;

    private short status;

    private String memo;

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

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getFinsId() {
        return finsId;
    }

    public void setFinsId(String finsId) {
        this.finsId = finsId == null ? null : finsId.trim();
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
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

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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