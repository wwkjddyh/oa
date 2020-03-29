package com.oa.platform.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 党组织
 * @author 俞灶森
 * @date 2020/03/11
 */
public class Organization implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//组织主键
	private String orgId;
	//组织简称
	private String orgName;
	//组织类型
	private String orgType;
	//组织代码
	private String orgCode;
	//上级党组织
	private String upperOrg;
	//根党组织
	private String rootOrg;
	//成立时间
	private String foundTime;
	//党组织属地关系
	private String orgAddressRelation;
	//选举方式
	private String elctType;
	//任期年限
	private String leadTime;
	//本届班子当选日期
	private String currentLeaderTime;
	//本届班子届满日期
	private String currentLeadOutTime;
	//选举应到人数
	private Integer elctShoudPeopleCount;
	//选举实到人数
	private Integer elctActPeopleCount;
	//上级核定委员会名额
	private Integer upperSureOrgCount;
	//实有委员数
	private Integer actUpperOrgPerCount;
	//转接组织关系权限
	private String changeOrgRelationAuth;
	//是否拥有党员删除权限
	private String isDelPartPersonAuth;
	//党组织联系人
	private String concatPersion;
	//党组织联系人手机号
	private String phone;
	//党组织办公电话
	private String orgJobPhone;
	//传真号码
	private String fixPhone;
	//所属地区
	private String belongArea;
	//邮政编码
	private String transCode;
	//详细地址
	private String address;
	//创建人
	private String createBy;
	//更新人
	private String updateBy;
	//是否叶子
	private String isLeaf;
	//预留1
	private String field1;
	//预留2
	private String field2;
	//预留3
	private String field3;
	//预留4
	private String field4;
	//预留5
	private String field5;
	//是否为部门
	private String isDept;
	//书记
	private String leader;
	
//	//单位信息
//	private List<OrgDeptDetail> deptDetails;
//	//奖惩信息
//	private List<OrgRewardDetail> rewardDetails;
//	//领导班子
//	private List<OrgLeaderDetail> leaderDetails;
	
	
//	public List<OrgDeptDetail> getDeptDetails() {
//		return deptDetails;
//	}
//	public void setDeptDetails(List<OrgDeptDetail> deptDetails) {
//		this.deptDetails = deptDetails;
//	}
//	public List<OrgRewardDetail> getRewardDetails() {
//		return rewardDetails;
//	}
//	public void setRewardDetails(List<OrgRewardDetail> rewardDetails) {
//		this.rewardDetails = rewardDetails;
//	}
//	public List<OrgLeaderDetail> getLeaderDetails() {
//		return leaderDetails;
//	}
//	public void setLeaderDetails(List<OrgLeaderDetail> leaderDetails) {
//		this.leaderDetails = leaderDetails;
//	}
	
	public String getOrgId() {
		return orgId;
	}
	public String getRootOrg() {
		return rootOrg;
	}
	public void setRootOrg(String rootOrg) {
		this.rootOrg = rootOrg;
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
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getUpperOrg() {
		return upperOrg;
	}
	public void setUpperOrg(String upperOrg) {
		this.upperOrg = upperOrg;
	}
	public String getFoundTime() {
		return foundTime;
	}
	public void setFoundTime(String foundTime) {
		this.foundTime = foundTime;
	}
	public String getOrgAddressRelation() {
		return orgAddressRelation;
	}
	public void setOrgAddressRelation(String orgAddressRelation) {
		this.orgAddressRelation = orgAddressRelation;
	}
	public String getElctType() {
		return elctType;
	}
	public void setElctType(String elctType) {
		this.elctType = elctType;
	}
	public String getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}
	public String getCurrentLeaderTime() {
		return currentLeaderTime;
	}
	public void setCurrentLeaderTime(String currentLeaderTime) {
		this.currentLeaderTime = currentLeaderTime;
	}
	public String getCurrentLeadOutTime() {
		return currentLeadOutTime;
	}
	public void setCurrentLeadOutTime(String currentLeadOutTime) {
		this.currentLeadOutTime = currentLeadOutTime;
	}
	public Integer getElctShoudPeopleCount() {
		return elctShoudPeopleCount;
	}
	public void setElctShoudPeopleCount(Integer elctShoudPeopleCount) {
		this.elctShoudPeopleCount = elctShoudPeopleCount;
	}
	public Integer getElctActPeopleCount() {
		return elctActPeopleCount;
	}
	public void setElctActPeopleCount(Integer elctActPeopleCount) {
		this.elctActPeopleCount = elctActPeopleCount;
	}
	public Integer getUpperSureOrgCount() {
		return upperSureOrgCount;
	}
	public void setUpperSureOrgCount(Integer upperSureOrgCount) {
		this.upperSureOrgCount = upperSureOrgCount;
	}
	public Integer getActUpperOrgPerCount() {
		return actUpperOrgPerCount;
	}
	public void setActUpperOrgPerCount(Integer actUpperOrgPerCount) {
		this.actUpperOrgPerCount = actUpperOrgPerCount;
	}
	public String getChangeOrgRelationAuth() {
		return changeOrgRelationAuth;
	}
	public void setChangeOrgRelationAuth(String changeOrgRelationAuth) {
		this.changeOrgRelationAuth = changeOrgRelationAuth;
	}
	public String getIsDelPartPersonAuth() {
		return isDelPartPersonAuth;
	}
	public void setIsDelPartPersonAuth(String isDelPartPersonAuth) {
		this.isDelPartPersonAuth = isDelPartPersonAuth;
	}
	public String getConcatPersion() {
		return concatPersion;
	}
	public void setConcatPersion(String concatPersion) {
		this.concatPersion = concatPersion;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrgJobPhone() {
		return orgJobPhone;
	}
	public void setOrgJobPhone(String orgJobPhone) {
		this.orgJobPhone = orgJobPhone;
	}
	public String getFixPhone() {
		return fixPhone;
	}
	public void setFixPhone(String fixPhone) {
		this.fixPhone = fixPhone;
	}
	public String getBelongArea() {
		return belongArea;
	}
	public void setBelongArea(String belongArea) {
		this.belongArea = belongArea;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	public String getIsDept() {
		return isDept;
	}
	public void setIsDept(String isDept) {
		this.isDept = isDept;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}

}
