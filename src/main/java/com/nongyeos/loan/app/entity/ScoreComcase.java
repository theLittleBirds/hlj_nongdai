package com.nongyeos.loan.app.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-07-04
 */
public class ScoreComcase {
    private Integer id;

    private String cvId;

    private String scvarId;

    private String cdesc;

    private String edesc;

    private Integer start;

    private Integer end;

    private String startText;

    private String endText;

    private String highLimit;

    private String lowLimit;

    private Integer seqno;

    private Short isDelete;

    private String creOperCode;

    private String creOperName;

    private String creOrgCode;

    private Date creDate;

    private String updOperCode;

    private String updOperName;

    private String updOrgCode;

    private Date updDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId == null ? null : cvId.trim();
    }

    public String getScvarId() {
        return scvarId;
    }

    public void setScvarId(String scvarId) {
        this.scvarId = scvarId == null ? null : scvarId.trim();
    }

    public String getCdesc() {
        return cdesc;
    }

    public void setCdesc(String cdesc) {
        this.cdesc = cdesc == null ? null : cdesc.trim();
    }

    public String getEdesc() {
        return edesc;
    }

    public void setEdesc(String edesc) {
        this.edesc = edesc == null ? null : edesc.trim();
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getStartText() {
        return startText;
    }

    public void setStartText(String startText) {
        this.startText = startText == null ? null : startText.trim();
    }

    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText == null ? null : endText.trim();
    }

    public String getHighLimit() {
        return highLimit;
    }

    public void setHighLimit(String highLimit) {
        this.highLimit = highLimit == null ? null : highLimit.trim();
    }

    public String getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(String lowLimit) {
        this.lowLimit = lowLimit == null ? null : lowLimit.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
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