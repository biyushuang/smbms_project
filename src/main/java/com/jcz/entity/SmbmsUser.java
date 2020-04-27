package com.jcz.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SmbmsUser {
    private Long id;

    private String usercode;

    private String username;

    private String userpassword;

    private Integer gender;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private String phone;

    private String address;

    private Long userrole;

    private Long createdby;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationdate;

    private Long modifyby;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date modifydate;

    private String idpicpath;

    private String workpicpath;
    
	private Integer age;
    
    public Integer getAge() {
    	Date date=new Date();
		return date.getYear()-birthday.getYear();
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getUserrole() {
        return userrole;
    }

    public void setUserrole(Long userrole) {
        this.userrole = userrole;
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Long getModifyby() {
        return modifyby;
    }

    public void setModifyby(Long modifyby) {
        this.modifyby = modifyby;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getIdpicpath() {
        return idpicpath;
    }

    public void setIdpicpath(String idpicpath) {
        this.idpicpath = idpicpath == null ? null : idpicpath.trim();
    }

    public String getWorkpicpath() {
        return workpicpath;
    }

    public void setWorkpicpath(String workpicpath) {
        this.workpicpath = workpicpath == null ? null : workpicpath.trim();
    }
}