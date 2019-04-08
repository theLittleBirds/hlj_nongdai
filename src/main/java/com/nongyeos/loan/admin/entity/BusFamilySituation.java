package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 家庭情况表
 * 
 * @author wcyong
 * 
 * @date 2018-05-07
 */
public class BusFamilySituation {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 进件ID
     */
    private String intoPieceId;

    /**
     * 消费者id
     */
    private String memberId;

    /**
     * 父母是否去世（1，是  2，否）
     */
    private Integer isDead;

    /**
     * 家属（1：配偶  2：子女  3：兄弟  4：父母  5：其他 ）
     */
    private Integer type;
    
    /**
     * 序号
     */
    private Integer seqno;

	/**
     * 姓名
     */
    private String name;

    /**
     * 性别（1：男  2：女）
     */
    private Integer sex;

    /**
     * 居住情况（1：是  2：否）
     */
    private Integer status;

    /**
     * 不同住时居住地址
     */
    private String liveAddress;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;
    
    /**
     * 身份证正面
     */
    private String idCardP;
    
    /**
     * 身份证反面
     */
    private String idCardN;    

	/**
     * 上学及学校班次（子女）
     */
    private String school;

    /**
     * 文化程度
     */
    private Integer educationLevel;

    /**
     * 子女婚姻状况（1：已婚  2：未婚）
     */
    private Integer maritalStatus;

    /**
     * 健康状况(1很好 2一般 3有疾病,有疾病时候描述)
     */
    private Integer healthStatus;

    /**
     * 疾病描述
     */
    private String diseaseRemark;

    /**
     * 能否作为共同借款人（1：能  0：否）
     */
    private Integer coBorrower;
    
    /**
     * 是否为土地共有人（1 是 2否）
     */
    private Integer coLand;

	/**
     * 从事农业相关生产经营（1 是 2否）
     */
    private String duty;

    /**
     * 从事非农职业单位名称
     */
    private String nonfarmComName;

    /**
     * 从事非农职业地址
     */
    private String nonfarmComAddress;

    /**
     * 从事非农职业电话
     */
    private String nonfarmComPhone;

    private Short isDeleted;

    private String creOperId;

    private String creOperName;

    private String creOrgId;

    private Date creDate;

    private String updOperId;

    private String updOperName;

    private String updOrgId;

    private Date updDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public Integer getIsDead() {
        return isDead;
    }

    public void setIsDead(Integer isDead) {
        this.isDead = isDead;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress == null ? null : liveAddress.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }
    
    public String getIdCardP() {
		return idCardP;
	}

	public void setIdCardP(String idCardP) {
		this.idCardP = idCardP;
	}

	public String getIdCardN() {
		return idCardN;
	}

	public void setIdCardN(String idCardN) {
		this.idCardN = idCardN;
	}
	
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public Integer getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(Integer healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getDiseaseRemark() {
        return diseaseRemark;
    }

    public void setDiseaseRemark(String diseaseRemark) {
        this.diseaseRemark = diseaseRemark == null ? null : diseaseRemark.trim();
    }

    public Integer getCoBorrower() {
        return coBorrower;
    }

    public void setCoBorrower(Integer coBorrower) {
        this.coBorrower = coBorrower;
    }
    
    public Integer getCoLand() {
		return coLand;
	}

	public void setCoLand(Integer coLand) {
		this.coLand = coLand;
	}
	
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getNonfarmComName() {
        return nonfarmComName;
    }

    public void setNonfarmComName(String nonfarmComName) {
        this.nonfarmComName = nonfarmComName == null ? null : nonfarmComName.trim();
    }

    public String getNonfarmComAddress() {
        return nonfarmComAddress;
    }

    public void setNonfarmComAddress(String nonfarmComAddress) {
        this.nonfarmComAddress = nonfarmComAddress == null ? null : nonfarmComAddress.trim();
    }

    public String getNonfarmComPhone() {
        return nonfarmComPhone;
    }

    public void setNonfarmComPhone(String nonfarmComPhone) {
        this.nonfarmComPhone = nonfarmComPhone == null ? null : nonfarmComPhone.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreOperId() {
        return creOperId;
    }

    public void setCreOperId(String creOperId) {
        this.creOperId = creOperId == null ? null : creOperId.trim();
    }

    public String getCreOperName() {
        return creOperName;
    }

    public void setCreOperName(String creOperName) {
        this.creOperName = creOperName == null ? null : creOperName.trim();
    }

    public String getCreOrgId() {
        return creOrgId;
    }

    public void setCreOrgId(String creOrgId) {
        this.creOrgId = creOrgId == null ? null : creOrgId.trim();
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public String getUpdOperId() {
        return updOperId;
    }

    public void setUpdOperId(String updOperId) {
        this.updOperId = updOperId == null ? null : updOperId.trim();
    }

    public String getUpdOperName() {
        return updOperName;
    }

    public void setUpdOperName(String updOperName) {
        this.updOperName = updOperName == null ? null : updOperName.trim();
    }

    public String getUpdOrgId() {
        return updOrgId;
    }

    public void setUpdOrgId(String updOrgId) {
        this.updOrgId = updOrgId == null ? null : updOrgId.trim();
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}