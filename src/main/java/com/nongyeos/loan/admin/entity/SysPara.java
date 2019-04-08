package com.nongyeos.loan.admin.entity;

/**
 * InnoDB free: 10240 kB
 * 
 * @author dzp
 * 
 * @date 2018-03-22
 */
public class SysPara {
    private Integer paraId;

    private Integer pgroupId;

    private String name;

    private String value;

    private String descr;

    private Integer seqno;

    public Integer getParaId() {
        return paraId;
    }

    public void setParaId(Integer paraId) {
        this.paraId = paraId;
    }

    public Integer getPgroupId() {
        return pgroupId;
    }

    public void setPgroupId(Integer pgroupId) {
        this.pgroupId = pgroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}