package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-12
 */
public class IntServiceimpl {
    private String servimplCode;

    private String servifaCode;

    private String providerCode;

    private String remotePrdcode;

    private String localPrdcode;

    private String cname;

    private String ename;

    private String version;

    private String className;

    private String classMethod;

    private short site;

    private Float costPertimeLocal;

    private Float costPertimeSource;

    private String validResult;

    private Short status;

    private Integer seqno;

    private short isDelete;

    public String getServimplCode() {
        return servimplCode;
    }

    public void setServimplCode(String servimplCode) {
        this.servimplCode = servimplCode == null ? null : servimplCode.trim();
    }

    public String getServifaCode() {
        return servifaCode;
    }

    public void setServifaCode(String servifaCode) {
        this.servifaCode = servifaCode == null ? null : servifaCode.trim();
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode == null ? null : providerCode.trim();
    }

    public String getRemotePrdcode() {
        return remotePrdcode;
    }

    public void setRemotePrdcode(String remotePrdcode) {
        this.remotePrdcode = remotePrdcode == null ? null : remotePrdcode.trim();
    }

    public String getLocalPrdcode() {
        return localPrdcode;
    }

    public void setLocalPrdcode(String localPrdcode) {
        this.localPrdcode = localPrdcode == null ? null : localPrdcode.trim();
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod == null ? null : classMethod.trim();
    }

    public short getSite() {
        return site;
    }

    public void setSite(short site) {
        this.site = site;
    }

    public Float getCostPertimeLocal() {
        return costPertimeLocal;
    }

    public void setCostPertimeLocal(Float costPertimeLocal) {
        this.costPertimeLocal = costPertimeLocal;
    }

    public Float getCostPertimeSource() {
        return costPertimeSource;
    }

    public void setCostPertimeSource(Float costPertimeSource) {
        this.costPertimeSource = costPertimeSource;
    }

    public String getValidResult() {
        return validResult;
    }

    public void setValidResult(String validResult) {
        this.validResult = validResult == null ? null : validResult.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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
}