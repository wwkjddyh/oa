package com.oa.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
import com.oa.platform.entity.UserDtl;
import org.apache.ibatis.annotations.Param;

/**
 * orgservice
 * @author 俞灶森
 *
 */
public interface OrgService extends BaseService<Organization, String> {
	/**
	 * 组织新增
	 * @param organization
	 */
	void orgAdd(Organization organization);
	/**
	 * 组织详情新增
	 * @param organization
	 */
	void orgAddDetail(Organization organization);
	/**
	 * 获取组织列表
	 * @param orgId
	 * @return
	 */
	List<Organization> getOrgList(String orgId,boolean isSuperAdmin);
	/**
	 * 根据用户id获取组织信息
	 * @param userId
	 * @return
	 */
	List<Organization> getOrgIdByuserId(String userId);
	
	void delOrg(String orgId);
	
	void delOrgUser(String orgId);
	
	void delLeader(String orgId);
	
	List<Organization> getUpperOrgList();
	
	List<Organization> getOrgDetailById(String orgId);
	
	void orgEdit(Organization organization);
	
	void orgEditDetail(Organization organization);
	
	List<Organization> getDeptList(String userId);
	
	/**
	 * 领导班子
	 */
	List<OrgLeaderDetail> getOrgLeaderList(String orgId);
	void delOrgLeaderById(String orgId);
	void saveOrgLeaderDetail(OrgLeaderDetail orgLeaderDetail);
	
	/**
	 * 奖惩情况
	 */
	List<OrgRewardDetail> getOrgRewardList(String orgId);
	void saveOrgRewardDetail(OrgRewardDetail orgRewardDetail);
	void delOrgRewardById(String orgId);
	
	/**
	 * 单位信息
	 */
	List<OrgDeptDetail> getOrgDeptList(String orgId);
	void saveOrgDeptDetail(OrgDeptDetail orgDeptDetail);
	void delOrgDeptById(String orgId);
	
	List<OrgUser> getOrgUserList(String orgId,String userName,boolean isSuperAdmin);
	List<OrgUser> getOrgUserListByOrgIds(List<String> orgIds,String userName,String year);
	UserDtl getOrgUserDetailByUserId(String userId);
	void saveOrgUser(String userId, String org_id);
	void delUser(String userId);
	void delOrgUserDtl(String userId);
	void delUserOrg(String userId);
	void downOrgUserById(String orgId);
	void updateOrgUser(String orgId,String userId);
	List<Organization> getUserUpperOrgList(String orgId);
	List<String> getOrgIdByUserId(String userId);
	List<String> getLeaderByOrgId(String orgId);
	List<Organization> getRootOrgId(String orgId);
	List<Map> getGenderEchartData(List<String> rootOrgs);
	List<Map> getAgeEchartBarData(List<String> rootOrgs);
	List<Map> getEducationEchartBarData(List<String> rootOrgs);
	List<Map> getPartyAgeEchartBarData(List<String> rootOrgs);
	List<Organization> getRootOrgList(String userId);
	List<String> getBottomOrgByOrgId(String orgId);
	PageInfo<OrgUser> getOrgUserListByOrg(String userName, String year, List<String> orgList, int pageNum,
			int pageSize);

	/**
	 * 根据用户ID查询组织ID
	 * @param userId 用户ID
	 * @return
	 */
	List<String> findOrgIdByUserId(@Param("userId") String userId);
	
	void insertExcelUsers(List<OrgUser> orgUsers);
	void insertExcelUsersDtl(List<OrgUser> orgUsers);
	void insertExcelUsersOrg(List<OrgUser> orgUsers);
	List<String> getOrgIdByName(String orgName);
	List<OrgUser> getPartyExcelList(List<String> arrayList);
}
