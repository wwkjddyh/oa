package com.oa.platform.entity;

import java.io.Serializable;

public class OrgRewardDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//数据主键
	private String staticsId;
	//组织主键
	private String orgId;
	//奖惩名称
	private String rewardName;
	//批准组织
	private String allowOrg;
	//批准时间
	private String allowTime;
	
	//创建人
	private String createBy;
	
	//修改人
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

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getAllowOrg() {
		return allowOrg;
	}

	public void setAllowOrg(String allowOrg) {
		this.allowOrg = allowOrg;
	}

	public String getAllowTime() {
		return allowTime;
	}

	public void setAllowTime(String allowTime) {
		this.allowTime = allowTime;
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
