package com.nongyeos.loan.app.entity;

import java.io.Serializable;

public class FlowDirection implements Serializable {
    private Integer directionId;

    private String nodeId;

    private Short type;

    private String pcId;

    private String resultId;

    private String toNodeIds;

    private Integer seqno;

    private static final long serialVersionUID = 1L;

    public Integer getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId == null ? null : pcId.trim();
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId == null ? null : resultId.trim();
    }

    public String getToNodeIds() {
        return toNodeIds;
    }

    public void setToNodeIds(String toNodeIds) {
        this.toNodeIds = toNodeIds == null ? null : toNodeIds.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}