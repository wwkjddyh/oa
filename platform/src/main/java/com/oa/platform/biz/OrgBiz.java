package com.oa.platform.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
import com.oa.platform.service.OrgService;
import com.oa.platform.util.StringUtil;

/**
 * biz
 * @author 俞灶森
 *
 */
@Component
public class OrgBiz extends BaseBiz {
	
	
	@Autowired
	private OrgService orgSerivce;
	/**
	 * 获取党组织列表
	 * @param userId
	 * @return
	 */
	public List<Organization> getOrgList(String userId) {
		String orgId = null;
		//根据用户id获取所在组织主键
		List<Organization> orgInfo = orgSerivce.getOrgIdByuserId(userId);
		if(orgInfo == null || orgInfo.size() == 0) {
			//用户无组织，无查询结果
			return new ArrayList<Organization>();
		}
		List<Organization> result = orgSerivce.getOrgList(orgInfo.get(0).getOrgId());
		return result;
	}
	/**
	 * 党组织新增
	 * @param organization
	 */
	@Transactional
	public void orgAdd(Organization organization,List<OrgDeptDetail> deptDetails,List<OrgRewardDetail> rewardDetails,List<OrgLeaderDetail> leaderDetails) {
		organization.setOrgId(StringUtil.getRandomUUID());
		orgSerivce.orgAdd(organization);
		orgSerivce.orgAddDetail(organization);
		//领导班子，单位信息，奖惩信息操作
		orgDetailsOperate(organization,deptDetails,rewardDetails,leaderDetails);
	}
	/**
	 * 党组织修改
	 * @param organization
	 */
	@Transactional
	public void orgEdit(Organization organization,List<OrgDeptDetail> deptDetails,List<OrgRewardDetail> rewardDetails,List<OrgLeaderDetail> leaderDetails) {
		if(organization.getOrgId() != null && !"".equals(organization.getOrgId())) {
			orgSerivce.orgEdit(organization);
			
			orgSerivce.orgEditDetail(organization);
			//领导班子，单位信息，奖惩信息操作
			orgDetailsOperate(organization,deptDetails,rewardDetails,leaderDetails);
		}
	}
	/**
	 * 党组织删除
	 * @param organization
	 */
	@Transactional
	public void orgDel(String orgId) {
		
		//删除指定组织及其下属组织
		orgSerivce.delOrg(orgId);
		//删除指定组织机器下属组织下人员
		orgSerivce.delOrgUser(orgId);
		//删除指定组织机器下属组织书记
		orgSerivce.delLeader(orgId);
		
	}
	public List<Organization> getUpperOrgList() {
		return orgSerivce.getUpperOrgList();
	}
	
	public List<Organization> getOrgDetailById(String orgId) {
		return orgSerivce.getOrgDetailById(orgId);
	}
	/**
	 * 获取所属党组织情况信息
	 * @param userId
	 * @return
	 */
	public List<Organization> getDeptList(String userId) {
		return orgSerivce.getDeptList(userId);
	}
	/**
	 * 根据组织获取领导班子成员
	 * @param orgId
	 * @return
	 */
	public List<OrgLeaderDetail> getOrgLeaderList(String orgId) {
		return orgSerivce.getOrgLeaderList(orgId);
	}
	public List<OrgRewardDetail> getOrgRewardList(String orgId) {
		return orgSerivce.getOrgRewardList(orgId);
	}
	public List<OrgDeptDetail> getOrgDeptList(String orgId) {
		return orgSerivce.getOrgDeptList(orgId);
	}
	/**
	 * 组织附加详情操作
	 */
	private void orgDetailsOperate(Organization organization,List<OrgDeptDetail> deptDetails,List<OrgRewardDetail> rewardDetails,List<OrgLeaderDetail> leaderDetails) {
		//班子成员
		//删除原有数据
		orgSerivce.delOrgLeaderById(organization.getOrgId());
		//新增现有数据
		for (OrgLeaderDetail orgLeaderDetail : leaderDetails) {
			orgLeaderDetail.setStaticsId(StringUtil.getRandomUUID());
			orgLeaderDetail.setOrgId(organization.getOrgId());
			orgSerivce.saveOrgLeaderDetail(orgLeaderDetail);
		}
		//奖罚信息
		orgSerivce.delOrgRewardById(organization.getOrgId());
		for (OrgRewardDetail orgRewardDetail : rewardDetails) {
			orgRewardDetail.setStaticsId(StringUtil.getRandomUUID());
			orgRewardDetail.setOrgId(organization.getOrgId());
			orgSerivce.saveOrgRewardDetail(orgRewardDetail);
		}
		//单位信息
		orgSerivce.delOrgDeptById(organization.getOrgId());
		for (OrgDeptDetail orgDeptDetail : deptDetails) {
			orgDeptDetail.setStaticsId(StringUtil.getRandomUUID());
			orgDeptDetail.setOrgId(organization.getOrgId());
			orgSerivce.saveOrgDeptDetail(orgDeptDetail);
		}
		
	}
	/**
	 * 班子成员数据提交
	 * @param orgLeaderDetails
	 * @param userName
	 */
	@Transactional
	public void orgLeaderDetailSubmit(List<OrgLeaderDetail> orgLeaderDetails, String userName,String orgId) {
		//删除原有数据
		orgSerivce.delOrgLeaderById(orgId);
		//新增现有数据
		for (OrgLeaderDetail orgLeaderDetail : orgLeaderDetails) {
			if(orgLeaderDetail.getStaticsId() == null || "".equals(orgLeaderDetail.getStaticsId())){
				orgLeaderDetail.setStaticsId(StringUtil.getRandomUUID());
			}
			orgSerivce.saveOrgLeaderDetail(orgLeaderDetail);
		}
		
	}
	/**
	 * 获取年度党员信息集合
	 * @param userId
	 * @return
	 */
	public List<OrgUser> getOrgUserList(String userId,String userName) {
		String orgId = null;
		//根据用户id获取所在组织主键
		List<Organization> orgInfo = orgSerivce.getOrgIdByuserId(userId);
		if(orgInfo == null || orgInfo.size() == 0) {
			//用户无组织，无查询结果
			return new ArrayList<OrgUser>();
		}
		List<OrgUser> result = orgSerivce.getOrgUserList(orgInfo.get(0).getOrgId(),userName);
		return result;
	}
	
	
}
