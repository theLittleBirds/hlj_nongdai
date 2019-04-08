package com.nongyeos.loan.app.entity;

import java.io.Serializable;

public class FlowNode implements Serializable {
    private String nodeId;

    private String appId;

    private Short type;

    private String cname;

    private String ename;

    private Short approverType;

    private Short approverCount;

    private Short approveMode;

    private String approverIds;

    private Short nodeStatus;

    private Short initStatus;

    private Short openStatus;

    private Short saveStatus;

    private String editSections;

    private String editItems;

    private String readonlyItems;

    private String hideItems;

    private String checkItems;

    private String commentItems;

    private String hideSections;

    private String actions;

    private String nextNodeCode;

    private String backNodeCode;

    private Short traceStep;

    private String memo;

    private Integer seqno;

    private Short isDelete;

    private static final long serialVersionUID = 1L;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
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

    public Short getApproverType() {
        return approverType;
    }

    public void setApproverType(Short approverType) {
        this.approverType = approverType;
    }

    public Short getApproverCount() {
        return approverCount;
    }

    public void setApproverCount(Short approverCount) {
        this.approverCount = approverCount;
    }

    public Short getApproveMode() {
        return approveMode;
    }

    public void setApproveMode(Short approveMode) {
        this.approveMode = approveMode;
    }

    public String getApproverIds() {
        return approverIds;
    }

    public void setApproverIds(String approverIds) {
        this.approverIds = approverIds == null ? null : approverIds.trim();
    }

    public Short getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(Short nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Short getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(Short initStatus) {
        this.initStatus = initStatus;
    }

    public Short getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Short openStatus) {
        this.openStatus = openStatus;
    }

    public Short getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Short saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getEditSections() {
        return editSections;
    }

    public void setEditSections(String editSections) {
        this.editSections = editSections == null ? null : editSections.trim();
    }

    public String getEditItems() {
        return editItems;
    }

    public void setEditItems(String editItems) {
        this.editItems = editItems == null ? null : editItems.trim();
    }

    public String getReadonlyItems() {
        return readonlyItems;
    }

    public void setReadonlyItems(String readonlyItems) {
        this.readonlyItems = readonlyItems == null ? null : readonlyItems.trim();
    }

    public String getHideItems() {
        return hideItems;
    }

    public void setHideItems(String hideItems) {
        this.hideItems = hideItems == null ? null : hideItems.trim();
    }

    public String getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(String checkItems) {
        this.checkItems = checkItems == null ? null : checkItems.trim();
    }

    public String getCommentItems() {
        return commentItems;
    }

    public void setCommentItems(String commentItems) {
        this.commentItems = commentItems == null ? null : commentItems.trim();
    }

    public String getHideSections() {
        return hideSections;
    }

    public void setHideSections(String hideSections) {
        this.hideSections = hideSections == null ? null : hideSections.trim();
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions == null ? null : actions.trim();
    }

    public String getNextNodeCode() {
        return nextNodeCode;
    }

    public void setNextNodeCode(String nextNodeCode) {
        this.nextNodeCode = nextNodeCode == null ? null : nextNodeCode.trim();
    }

    public String getBackNodeCode() {
        return backNodeCode;
    }

    public void setBackNodeCode(String backNodeCode) {
        this.backNodeCode = backNodeCode == null ? null : backNodeCode.trim();
    }
    public Short getTraceStep() {
        return traceStep;
    }

    public void setTraceStep(Short traceStep) {
        this.traceStep = traceStep;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }
}