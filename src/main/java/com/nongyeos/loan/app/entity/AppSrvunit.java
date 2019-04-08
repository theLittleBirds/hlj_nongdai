package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-15
 */
public class AppSrvunit {
    private String srvunId;

    private String appId;

    private Short type;

    private String cname;

    private String ename;

    private String servifaId;

    private String servimplId;

    private String tardataId;

    private Integer seqno;

    public String getSrvunId() {
        return srvunId;
    }

    public void setSrvunId(String srvunId) {
        this.srvunId = srvunId == null ? null : srvunId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
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

    public String getServifaId() {
        return servifaId;
    }

    public void setServifaId(String servifaId) {
        this.servifaId = servifaId == null ? null : servifaId.trim();
    }

    public String getServimplId() {
        return servimplId;
    }

    public void setServimplId(String servimplId) {
        this.servimplId = servimplId == null ? null : servimplId.trim();
    }

    public String getTardataId() {
        return tardataId;
    }

    public void setTardataId(String tardataId) {
        this.tardataId = tardataId == null ? null : tardataId.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}