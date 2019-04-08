package com.nongyeos.loan.app.entity;

import java.io.Serializable;

public class AppItemScvar implements Serializable {
    private Integer itemscvarId;

    private String appscId;

    private String itemId;

    private String scvarId;

    private String pcId;

    private Integer seqno;
    
    private String scoreId;
    
    private String appId;

    private static final long serialVersionUID = 1L;

    public Integer getItemscvarId() {
        return itemscvarId;
    }

    public void setItemscvarId(Integer itemscvarId) {
        this.itemscvarId = itemscvarId;
    }

    public String getAppscId() {
        return appscId;
    }

    public void setAppscId(String appscId) {
        this.appscId = appscId == null ? null : appscId.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getScvarId() {
        return scvarId;
    }

    public void setScvarId(String scvarId) {
        this.scvarId = scvarId == null ? null : scvarId.trim();
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId == null ? null : pcId.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

	public String getScoreId() {
		return scoreId;
	}

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
    
}