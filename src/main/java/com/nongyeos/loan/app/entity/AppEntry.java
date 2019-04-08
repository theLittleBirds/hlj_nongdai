package com.nongyeos.loan.app.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-05-07
 */
public class AppEntry {
    /**
     * 记录ID
     */
    private String entryId;

    /**
     * 进件渠道ID
     */
    private String channelId;

    /**
     * 业务表主键_ID
     */
    private String modeId;

    /**
     * 业务表名字
     */
    private String fromId;

    /**
     * 所属机构ID
     */
    private String orgId;

    /**
     * 金融机构ID
     */
    private String finsId;

    /**
     * 金融机构名称
     */
    private String finsName;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点状态ID
     */
    private Integer nodeStatusId;

    /**
     * 节点状态名称
     */
    private String nodeStatusName;

    /**
     * 审批实体类型
     */
    private Short approverType;

    /**
     * 审批实体IDs
     */
    private String approverIds;

    /**
     * 已审批人员IDs
     */
    private String aprPersonCodes;

    /**
     * 已审批角色IDs
     */
    private String aprRoleIds;

    /**
     * 已审批机构IDs
     */
    private String aprOrgIds;

    /**
     * 目标数据记录ID
     */
    private String recordId;

    /**
     * 标题
     */
    private String title;

    /**
     * 排列序号
     */
    private Integer seqno;

    /**
     * 软删除标志
     */
    private Short isDelete;

    /**
     * 建立人编号
     */
    private String creOperCode;

    /**
     * 建立人名称
     */
    private String creOperName;

    /**
     * 建立人机构编号
     */
    private String creOrgCode;

    /**
     * 建立日期
     */
    private Date creDate;

    /**
     * 修改人编号
     */
    private String updOperCode;

    /**
     * 修改人名称
     */
    private String updOperName;

    /**
     * 修改人机构编号
     */
    private String updOrgCode;

    /**
     * 修改日期
     */
    private Date updDate;

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId == null ? null : entryId.trim();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId == null ? null : channelId.trim();
    }

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId == null ? null : modeId.trim();
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId == null ? null : fromId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getFinsId() {
        return finsId;
    }

    public void setFinsId(String finsId) {
        this.finsId = finsId == null ? null : finsId.trim();
    }

    public String getFinsName() {
        return finsName;
    }

    public void setFinsName(String finsName) {
        this.finsName = finsName == null ? null : finsName.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public Integer getNodeStatusId() {
        return nodeStatusId;
    }

    public void setNodeStatusId(Integer nodeStatusId) {
        this.nodeStatusId = nodeStatusId;
    }

    public String getNodeStatusName() {
        return nodeStatusName;
    }

    public void setNodeStatusName(String nodeStatusName) {
        this.nodeStatusName = nodeStatusName == null ? null : nodeStatusName.trim();
    }

    public Short getApproverType() {
        return approverType;
    }

    public void setApproverType(Short approverType) {
        this.approverType = approverType;
    }

    public String getApproverIds() {
        return approverIds;
    }

    public void setApproverIds(String approverIds) {
        this.approverIds = approverIds == null ? null : approverIds.trim();
    }

    public String getAprPersonCodes() {
        return aprPersonCodes;
    }

    public void setAprPersonCodes(String aprPersonCodes) {
        this.aprPersonCodes = aprPersonCodes == null ? null : aprPersonCodes.trim();
    }

    public String getAprRoleIds() {
        return aprRoleIds;
    }

    public void setAprRoleIds(String aprRoleIds) {
        this.aprRoleIds = aprRoleIds == null ? null : aprRoleIds.trim();
    }

    public String getAprOrgIds() {
        return aprOrgIds;
    }

    public void setAprOrgIds(String aprOrgIds) {
        this.aprOrgIds = aprOrgIds == null ? null : aprOrgIds.trim();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public String getCreOperCode() {
        return creOperCode;
    }

    public void setCreOperCode(String creOperCode) {
        this.creOperCode = creOperCode == null ? null : creOperCode.trim();
    }

    public String getCreOperName() {
        return creOperName;
    }

    public void setCreOperName(String creOperName) {
        this.creOperName = creOperName == null ? null : creOperName.trim();
    }

    public String getCreOrgCode() {
        return creOrgCode;
    }

    public void setCreOrgCode(String creOrgCode) {
        this.creOrgCode = creOrgCode == null ? null : creOrgCode.trim();
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public String getUpdOperCode() {
        return updOperCode;
    }

    public void setUpdOperCode(String updOperCode) {
        this.updOperCode = updOperCode == null ? null : updOperCode.trim();
    }

    public String getUpdOperName() {
        return updOperName;
    }

    public void setUpdOperName(String updOperName) {
        this.updOperName = updOperName == null ? null : updOperName.trim();
    }

    public String getUpdOrgCode() {
        return updOrgCode;
    }

    public void setUpdOrgCode(String updOrgCode) {
        this.updOrgCode = updOrgCode == null ? null : updOrgCode.trim();
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}