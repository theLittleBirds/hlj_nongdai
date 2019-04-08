package com.nongyeos.loan.app.entity;

import java.io.Serializable;

public class AppScore implements Serializable {
	private String appscId;

	private String appId;

	private String scoreId;

	private Integer seqno;

	private static final long serialVersionUID = 1L;

	public String getAppscId() {
		return appscId;
	}

	public void setAppscId(String appscId) {
		this.appscId = appscId == null ? null : appscId.trim();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId == null ? null : appId.trim();
	}

	public String getScoreId() {
		return scoreId;
	}

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId == null ? null : scoreId.trim();
	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
}