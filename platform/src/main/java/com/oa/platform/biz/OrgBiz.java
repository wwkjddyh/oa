package com.oa.platform.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oa.platform.common.ResultVo;
import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.service.OrgService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.StringUtil;

/**
 * biz
 * @author 俞灶森
 *
 */
@Component
public class OrgBiz extends BaseBiz {
	
	@Autowired
    UserService userService;
	
	@Autowired
	private OrgService orgSerivce;
	
	@Value("${userDefaultPwd}")
	private String userDefaultPwd;
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
	public List<OrgUser> getOrgUserList(String userId,String userName,String year) {
		String orgId = null;
		//根据用户id获取所在组织主键
		List<Organization> orgInfo = orgSerivce.getOrgIdByuserId(userId);
		if(orgInfo == null || orgInfo.size() == 0) {
			//用户无组织，无查询结果
			return new ArrayList<OrgUser>();
		}
		List<OrgUser> result = orgSerivce.getOrgUserList(orgInfo.get(0).getOrgId(),userName);
		if(result == null || result.size() == 0) {
			return new ArrayList<OrgUser>();
		}
		List<String> orgIds = new ArrayList<String>();
		for (OrgUser orgUser : result) {
			orgIds.add(orgUser.getOrgId());
		}
		List<OrgUser> users = orgSerivce.getOrgUserListByOrgIds(orgIds,userName,year);
		if(users == null || users.size() == 0) {
			return result;
		}
		users.addAll(result);
		return users;
	}
	public UserDtl getOrgUserDetailByUserId(String userId) {
		
		return orgSerivce.getOrgUserDetailByUserId(userId);
	}
	@Transactional
	public void orgUserAdd(UserDtl userDtl) {
		userDtl.setUserId(StringUtil.getRandomUUID());
		User user = new User();
		user.setUserId(userDtl.getUserId());
		user.setUserName(userDtl.getUserName());
		user.setUserPwd(userDefaultPwd);
		user.setUserPwdOrigi("123456");
		user.setRecordFlag(1);
		user.setUserType(3);
		if("1".equals(userDtl.getLeader())) {
			//将原来的部门负责人换下
			orgSerivce.downOrgUserById(userDtl.getOrgId());
		}
		userService.save(user);
		//保存详情
		userService.saveUserDtl(userDtl);
		//保存用户部门关系
		orgSerivce.saveOrgUser(userDtl.getUserId(),userDtl.getOrgId());
	}
	@Transactional
	public ResultVo orgUserEdit(UserDtl userDtl) {
		User user = new User();
		user.setUserId(userDtl.getUserId());
		user.setUserName(userDtl.getUserName());
		//查询用户信息
		UserDtl findDetailByUserId = userService.findDetailByUserId(userDtl.getUserId());
		//判断此次修改是否修改组织
		List<Organization> orgIdByuserId = orgSerivce.getOrgIdByuserId(userDtl.getUserId());
		//该用户为某组织负责人
		if(findDetailByUserId != null && "1".equals(findDetailByUserId.getLeader())) {
			
			if(orgIdByuserId != null && orgIdByuserId.size() != 0) {
				if(!userDtl.getOrgId().equals(orgIdByuserId.get(0).getOrgId())) {
					//已修改组织，先返回，不做编辑
					return getErroResultVo(2000, "该党员为组织部门负责人,更换组织部门时请先更换部门负责人", null);
				}
			}
		}
		if(orgIdByuserId != null && orgIdByuserId.size() != 0) {
			if(userDtl.getOrgId() != null && !"".equals(userDtl.getOrgId()) && !userDtl.getOrgId().equals(orgIdByuserId.get(0).getOrgId())) {
				//组织修改
				orgSerivce.updateOrgUser(userDtl.getOrgId(),userDtl.getUserId());
			}
		}
			
		if("1".equals(userDtl.getLeader()) && !"1".equals(findDetailByUserId.getLeader())) {
			//将原来的部门负责人换下
			orgSerivce.downOrgUserById(userDtl.getOrgId());
		}
		userService.update(user);
		userService.updateUserDtl(userDtl);
		return getSuccessResultVo(null);
	}
	/**
	 * 删除党员信息
	 * @param userId
	 */
	@Transactional
	public void delOrgUser(String userId) {
		
		//删除党员用户
		orgSerivce.delUser(userId);
		//删除党员用户详情
		orgSerivce.delOrgUserDtl(userId);
		//删除党员与组织关系
		orgSerivce.delUserOrg(userId);
	}
	
	
}
