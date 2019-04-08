package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-20
 */
public class ScorePara {
    private Integer id;

    private String groupName;

    private String groupDescr;

    private String name;

    private String value;

    private String descr;

    private Integer seqno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupDescr() {
        return groupDescr;
    }

    public void setGroupDescr(String groupDescr) {
        this.groupDescr = groupDescr == null ? null : groupDescr.trim();
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