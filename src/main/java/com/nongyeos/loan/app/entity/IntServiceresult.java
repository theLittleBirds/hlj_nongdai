package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-12
 */
public class IntServiceresult {
    private String servresCode;

    private String servimplCode;

    private String resultText;

    private String resultValue;

    private Integer seqno;

    public String getServresCode() {
        return servresCode;
    }

    public void setServresCode(String servresCode) {
        this.servresCode = servresCode == null ? null : servresCode.trim();
    }

    public String getServimplCode() {
        return servimplCode;
    }

    public void setServimplCode(String servimplCode) {
        this.servimplCode = servimplCode == null ? null : servimplCode.trim();
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText == null ? null : resultText.trim();
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue == null ? null : resultValue.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}