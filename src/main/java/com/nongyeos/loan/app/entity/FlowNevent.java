package com.nongyeos.loan.app.entity;


/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-15
 */
public class FlowNevent {
    private String neventId;

    private String nodeId;

    private Short runtime;

    private Short starttime;

    private String srvunId;

    private Integer seqno;

    public String getNeventId() {
        return neventId;
    }

    public void setNeventId(String neventId) {
        this.neventId = neventId == null ? null : neventId.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Short getRuntime() {
        return runtime;
    }

    public void setRuntime(Short runtime) {
        this.runtime = runtime;
    }

    public Short getStarttime() {
        return starttime;
    }

    public void setStarttime(Short starttime) {
        this.starttime = starttime;
    }

    public String getSrvunId() {
        return srvunId;
    }

    public void setSrvunId(String srvunId) {
        this.srvunId = srvunId == null ? null : srvunId.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}