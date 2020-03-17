package com.oa.platform.entity;

import java.io.Serializable;

public class OrgUser implements Serializable{

	private static final long serialVersionUID = 1L;
	//组织id
	private String orgId;
	//组织名称
	private String orgName;
	//上级组织
	private String upperOrg;
	//用户id
	private String userId;
	//姓名
	private String userName;
	//性别
	private String gender;
	//联系电话
	private String phone;
	//出生日期
	private String birthTime;
	//民族
	private String nation;
	//学历
	private String education;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBirthTime() {
		return birthTime;
	}
	public void setBirthTime(String birthTime) {
		this.birthTime = birthTime;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getUpperOrg() {
		return upperOrg;
	}
	public void setUpperOrg(String upperOrg) {
		this.upperOrg = upperOrg;
	}
	
	
}
