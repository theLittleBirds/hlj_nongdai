package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-25
 */
public class ReportSourcesql {
    private String dsId;

    private String tplId;

    private String name;

    private String sqlCode;

    private Integer startRow;

    private String startCol;

    private Short type;

    private Integer stepRow;

    private Integer stepCol;

    private Short status;

    private Integer seqno;

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId == null ? null : dsId.trim();
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId == null ? null : tplId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSqlCode() {
        return sqlCode;
    }

    public void setSqlCode(String sqlCode) {
        this.sqlCode = sqlCode == null ? null : sqlCode.trim();
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public String getStartCol() {
        return startCol;
    }

    public void setStartCol(String startCol) {
        this.startCol = startCol == null ? null : startCol.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getStepRow() {
        return stepRow;
    }

    public void setStepRow(Integer stepRow) {
        this.stepRow = stepRow;
    }

    public Integer getStepCol() {
        return stepCol;
    }

    public void setStepCol(Integer stepCol) {
        this.stepCol = stepCol;
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
}