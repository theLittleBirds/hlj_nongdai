package com.nongyeos.loan.app.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-05-15
 */
public class ScoreClass {
    private String classId;

    private String scoreId;

    private String name;

    private Integer value;

    private Float startScore;

    private Float endScore;

    private Integer seqno;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId == null ? null : scoreId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Float getStartScore() {
        return startScore;
    }

    public void setStartScore(Float startScore) {
        this.startScore = startScore;
    }

    public Float getEndScore() {
        return endScore;
    }

    public void setEndScore(Float endScore) {
        this.endScore = endScore;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}