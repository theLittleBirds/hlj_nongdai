package com.nongyeos.loan.admin.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-18
 */
public class SysSeno {
    private String name;

    private Long value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}