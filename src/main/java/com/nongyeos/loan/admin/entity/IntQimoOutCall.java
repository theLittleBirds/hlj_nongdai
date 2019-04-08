package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-05-10
 */
public class IntQimoOutCall {
    /**
     * 主键
     */
    private String id;

    /**
     * 家庭成员信息id
     */
    private String familysituationid;

    /**
     * 进件id
     */
    private String intopieceid;

    /**
     * 七陌 对应操作的唯一标记
     */
    private String actionid;

    /**
     * 客户名字
     */
    private String name;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 手机号
     */
    private String phoneno;

    /**
     * 保存录音地址
     */
    private String voiceurl;

    /**
     * 七陌外呼开始时间
     */
    private String qimostart;

    /**
     * 七陌结束时间
     */
    private String qimoend;

    /**
     * 七陌录音文件地址
     */
    private String qimovoiceurl;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFamilysituationid() {
        return familysituationid;
    }

    public void setFamilysituationid(String familysituationid) {
        this.familysituationid = familysituationid == null ? null : familysituationid.trim();
    }

    public String getIntopieceid() {
        return intopieceid;
    }

    public void setIntopieceid(String intopieceid) {
        this.intopieceid = intopieceid == null ? null : intopieceid.trim();
    }

    public String getActionid() {
        return actionid;
    }

    public void setActionid(String actionid) {
        this.actionid = actionid == null ? null : actionid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getVoiceurl() {
        return voiceurl;
    }

    public void setVoiceurl(String voiceurl) {
        this.voiceurl = voiceurl == null ? null : voiceurl.trim();
    }

    public String getQimostart() {
        return qimostart;
    }

    public void setQimostart(String qimostart) {
        this.qimostart = qimostart == null ? null : qimostart.trim();
    }

    public String getQimoend() {
        return qimoend;
    }

    public void setQimoend(String qimoend) {
        this.qimoend = qimoend == null ? null : qimoend.trim();
    }

    public String getQimovoiceurl() {
        return qimovoiceurl;
    }

    public void setQimovoiceurl(String qimovoiceurl) {
        this.qimovoiceurl = qimovoiceurl == null ? null : qimovoiceurl.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}