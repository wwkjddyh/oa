package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 组织班子成员
 * @author 俞灶森
 *
 */
public class OrgLeaderDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//数据主键
	private String staticsId;
	//组织主键
	private String orgId;
	//姓名
	private String userName;
	//职务
	private String positon;
	//批准任职时间
	private String allowLeaderTime;
	//职务级别
	private String positionLevel;
	//职务说明
	private String positionDesc;
	//创建人
	private String createBy;
	//修改人
	private String updateBy;
	
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	public String getPositionDesc() {
		return positionDesc;
	}
	public void setPositionDesc(String positionDesc) {
		this.positionDesc = positionDesc;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPositon() {
		return positon;
	}
	public void setPositon(String positon) {
		this.positon = positon;
	}
	public String getAllowLeaderTime() {
		return allowLeaderTime;
	}
	public void setAllowLeaderTime(String allowLeaderTime) {
		this.allowLeaderTime = allowLeaderTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
