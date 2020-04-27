package com.oa.platform.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
/**
 * dao
 * @author 俞灶森
 *
 */
@Repository
public interface OrgRepository extends BaseRepository<Organization, String> {

	void orgAdd(Organization organization);

	void orgAddDetail(Organization organization);
	
	List<Organization> getOrgList(@Param("orgId") String org_id,@Param("isSuperAdmin") boolean isSuperAdmin);

	List<Organization> getOrgIdByuserId(@Param("userId") String userId);
	/**
	 * 删除指定组织及其下属组织
	 * @param org_id
	 */
	void delOrg(@Param("orgId") String org_id);
	/**
	 * 删除指定组织机器下属组织下人员
	 * @param org_id
	 */
	void delOrgUser(@Param("orgId") String org_id);
	/**
	 * 删除指定组织机器下属组织书记
	 * @param org_id
	 */
	void delLeader(@Param("orgId") String org_id);

	List<Organization> getUpperOrgList();

	List<Organization> getOrgDetailById(@Param("orgId") String orgId);

	void orgEdit(Organization organization);

	void orgEditDetail(Organization organization);

	List<Organization> getDeptList(@Param("userId") String userId);

	/**
	 * 领导班子
	 */
	List<OrgLeaderDetail> getOrgLeaderList(@Param("orgId") String orgId);
	void saveOrgLeaderDetail(OrgLeaderDetail orgLeaderDetail);
	void delOrgLeaderById(@Param("orgId") String orgId);
	
	/**
	 * 奖惩情况
	 */
	List<OrgRewardDetail> getOrgRewardList(@Param("orgId") String orgId);
	void saveOrgRewardDetail(OrgRewardDetail orgRewardDetail);
	void delOrgRewardById(@Param("orgId") String orgId);
	
	
	/**
	 * 单位信息
	 */
	List<OrgDeptDetail> getOrgDeptList(@Param("orgId") String orgId);
	void saveOrgDeptDetail(OrgDeptDetail orgDeptDetail);
	void delOrgDeptById(@Param("orgId") String orgId);

	List<OrgUser> getOrgUserList(@Param("orgId") String orgId,@Param("userName") String userName,@Param("isSuperAdmin") boolean isSuperAdmin);
	List<OrgUser> getOrgUserListByOrgIds(@Param("list")List<String> orgIds
			,@Param("userName") String userName, @Param("year")String year);

	void saveOrgUser(@Param("userId") String userId, @Param("orgId") String orgId);

	void delUser(@Param("userId") String userId);

	void delOrgUserDtl(@Param("userId") String userId);

	void delUserOrg(@Param("userId") String userId);

	void downOrgUserById(@Param("orgId") String orgId);

	void updateOrgUser(@Param("orgId") String orgId,@Param("userId")String userId);

	List<Organization> getUserUpperOrgList(@Param("orgId") String orgId);

	List<String> getOrgIdByUserId(@Param("userId") String userId);

	List<String> getLeaderByOrgId(@Param("orgId") String orgId);
	/**
	 * 党员年龄柱状图数据
	 * @param orgIds
	 * @return
	 */
	List<Map> getAgeEchartBarData(@Param("orgIds")List<String> orgIds);
	/**
	 * 党员党龄柱状图数据
	 * @param orgIds
	 * @return
	 */
	List<Map> getPartyAgeEchartBarData(@Param("orgIds")List<String> orgIds);
	/**
	 * 党员学历柱状图数据
	 * @param orgIds
	 * @return
	 */
	List<Map> getEducationEchartBarData(@Param("orgIds")List<String> orgIds);
	/**
	 * 党员性别饼状图数据
	 * @param orgIds
	 * @return
	 */
	List<Map> getGenderEchartData(@Param("orgIds")List<String> orgIds);
	
	/**
	 * 获取组织的根组织
	 * @param orgId
	 * @return
	 */
	List<Organization> getRootOrgId(@Param("orgId") String orgId);
	List<String> getBottomOrgByOrgId(@Param("orgId") String orgId);

	List<OrgUser> getOrgUserListByOrg(@Param("userName") String userName, @Param("year") String year,@Param("orgList") List<String> orgList);

	/**
	 * 根据用户ID查询组织ID
	 * @param userId 用户ID
	 * @return
	 */
	List<String> findOrgIdByUserId(@Param("userId") String userId);
	void insertExcelUsers(List<OrgUser> orgUsers);
	void insertExcelUsersDtl(List<OrgUser> orgUsers);
	void insertExcelUsersOrg(List<OrgUser> orgUsers);
	List<String> getOrgIdByName(@Param("orgName") String orgName);
	
	List<OrgUser> getPartyExcelList(@Param("orgIds") List<String> orgIds);

	List<Organization> getdwjbxxExcelOrg(@Param("orgId") String org_id,@Param("isSuperAdmin") boolean isSuperAdmin);
	List<String> getUserIdByIdCard(@Param("idCard") String idCard);
}
