package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-07-04
 */
public class ScoreComvalue {
    private String cvId;

    private String scvarId;

    private String comValue;

    private String comText;

    private Integer seqno;

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

    public String getComValue() {
        return comValue;
    }

    public void setComValue(String comValue) {
        this.comValue = comValue == null ? null : comValue.trim();
    }

    public String getComText() {
        return comText;
    }

    public void setComText(String comText) {
        this.comText = comText == null ? null : comText.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}