package com.oa.platform.entity;

import java.io.Serializable;

public class OrgDeptDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//数据主键
	private String staticsId;
	//组织主键
	private String orgId;
	//单位名称
	private String deptName;
	//单位性质类别
	private String deptNatureType;
	//单位(机构)类型
	private String deptType;
	//创建人
	private String createBy;
	//更新人
	private String updateBy;
	public String getStaticsId() {
		return staticsId;
	}
	public void setStaticsId(String staticsId) {
		this.staticsId = staticsId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptNatureType() {
		return deptNatureType;
	}
	public void setDeptNatureType(String deptNatureType) {
		this.deptNatureType = deptNatureType;
	}
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}
