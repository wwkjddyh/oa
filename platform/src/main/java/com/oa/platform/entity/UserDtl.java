package com.oa.platform.entity;

import java.io.Serializable;

/**
 * 用户详细信息
 * @author Feng
 * @date 2019/03/01
 */
public class UserDtl implements Serializable {

	
	private static final long serialVersionUID = 1L;
	/**
     * 主键，唯一标识(与t_user表的user_id一致)
     */
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
    //入党时间
    private String joinPartyTime;
    //转正时间
    private String turnRightTime;
    //籍贯
    private String hometown;
    //学位
    private String bachelor;
    //学历
    private String education;
    //办公电话
    private String officeNumber;
    //现居住地
    private String liveAddress;
    //邮箱
    private String mail;
    //图片路径
    private String imageUrl;
    //创建人
    private String createBy;
    //更新人
    private String updateBy;
    //所在党组织
    private String orgId;
    //是否为部门负责人
    private String leader;
    //身份证号码
    private String idCard;
	
	
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	public String getJoinPartyTime() {
		return joinPartyTime;
	}
	public void setJoinPartyTime(String joinPartyTime) {
		this.joinPartyTime = joinPartyTime;
	}
	public String getTurnRightTime() {
		return turnRightTime;
	}
	public void setTurnRightTime(String turnRightTime) {
		this.turnRightTime = turnRightTime;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getBachelor() {
		return bachelor;
	}
	public void setBachelor(String bachelor) {
		this.bachelor = bachelor;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getOfficeNumber() {
		return officeNumber;
	}
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	public String getLiveAddress() {
		return liveAddress;
	}
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
