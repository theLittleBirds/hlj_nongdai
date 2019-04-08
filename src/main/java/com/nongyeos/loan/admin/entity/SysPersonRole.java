package com.nongyeos.loan.admin.entity;

/**
 * InnoDB free: 10240 kB
 * 
 * @author dzp
 * 
 * @date 2018-03-22
 */
public class SysPersonRole {
    private Integer id;

    private String personId;

    private String roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}