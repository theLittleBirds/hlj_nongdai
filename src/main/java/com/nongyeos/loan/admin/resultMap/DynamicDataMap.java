package com.nongyeos.loan.admin.resultMap;


public class DynamicDataMap {
	
	private String itemId;

    private String entityId;

    private Short designType;

    private String cname;

    private String ename;

    private String fieldName;

    private String htmlId;

    private Short dataType;

    private Short inputType;

    private String optionsGroup;
    
    private String unit;

    private Integer inputWidth;

    private Short isEmpty;

    private Integer seqno;
    
    private String itemValue;
    
    private String intoPieceId;
    
    private String sectionId;
    
    public String getIntoPieceId() {
		return intoPieceId;
	}

	public void setIntoPieceId(String intoPieceId) {
		this.intoPieceId = intoPieceId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId == null ? null : entityId.trim();
    }

    public Short getDesignType() {
        return designType;
    }

    public void setDesignType(Short designType) {
        this.designType = designType;
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getHtmlId() {
        return htmlId;
    }

    public void setHtmlId(String htmlId) {
        this.htmlId = htmlId == null ? null : htmlId.trim();
    }

    public Short getDataType() {
        return dataType;
    }

    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }
    

    public Short getInputType() {
		return inputType;
	}

	public void setInputType(Short inputType) {
		this.inputType = inputType;
	}

    public String getOptionsGroup() {
        return optionsGroup;
    }

    public void setOptionsGroup(String optionsGroup) {
        this.optionsGroup = optionsGroup == null ? null : optionsGroup.trim();
    }

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

    public Integer getInputWidth() {
        return inputWidth;
    }

    public void setInputWidth(Integer inputWidth) {
        this.inputWidth = inputWidth;
    }

    public Short getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Short isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }
}
