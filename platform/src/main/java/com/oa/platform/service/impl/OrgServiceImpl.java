package com.oa.platform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.repository.OrgRepository;
import com.oa.platform.service.OrgService;
/**
 * orgservice
 * @author 俞灶森
 *
 */
@Service
public class OrgServiceImpl extends AbstractBaseService<Organization, String> implements OrgService {

	public OrgServiceImpl() {
		super(Organization.class);
	}
	
	@Autowired
	private OrgRepository orgRepository;
	/**
	 * 组织新增
	 */
	@Override
	public void orgAdd(Organization organization) {
		orgRepository.orgAdd(organization);
		
	}
	/**
	 * 组织详情新增
	 */
	@Override
	public void orgAddDetail(Organization organization) {
		orgRepository.orgAddDetail(organization);
		
	}
	/**
	 * 获取组织列表
	 */
	@Override
	public List<Organization> getOrgList(String orgId,boolean isSuperAdmin) {
		return orgRepository.getOrgList(orgId,isSuperAdmin);
	}
	@Override
	public List<Organization> getOrgIdByuserId(String userId) {
		return orgRepository.getOrgIdByuserId(userId);
	}
	@Override
	public void delOrg(String orgId) {
		orgRepository.delOrg(orgId);
		
	}
	@Override
	public void delOrgUser(String orgId) {
		orgRepository.delOrgUser(orgId);
		
	}
	@Override
	public void delLeader(String orgId) {
		orgRepository.delLeader(orgId);
		
	}
	@Override
	public List<Organization> getUpperOrgList() {
		return orgRepository.getUpperOrgList();
	}
	@Override
	public List<Organization> getOrgDetailById(String orgId) {
		return orgRepository.getOrgDetailById(orgId);
	}
	@Override
	public void orgEdit(Organization organization) {
		orgRepository.orgEdit(organization);
		
	}
	@Override
	public void orgEditDetail(Organization organization) {
		orgRepository.orgEditDetail(organization);
		
	}
	@Override
	public List<Organization> getDeptList(String userId) {
		return orgRepository.getDeptList(userId);
	}
	@Override
	public List<OrgLeaderDetail> getOrgLeaderList(String orgId) {
		return orgRepository.getOrgLeaderList(orgId);
	}
	@Override
	public void delOrgLeaderById(String orgId) {
		orgRepository.delOrgLeaderById(orgId);
		
	}
	@Override
	public void saveOrgLeaderDetail(OrgLeaderDetail orgLeaderDetail) {
		orgRepository.saveOrgLeaderDetail(orgLeaderDetail);
		
	}
	@Override
	public List<OrgRewardDetail> getOrgRewardList(String orgId) {
		return orgRepository.getOrgRewardList(orgId);
	}
	@Override
	public void saveOrgRewardDetail(OrgRewardDetail orgRewardDetail) {
		orgRepository.saveOrgRewardDetail(orgRewardDetail);
	}
	@Override
	public void delOrgRewardById(String orgId) {
		orgRepository.delOrgRewardById(orgId);
	}
	@Override
	public List<OrgDeptDetail> getOrgDeptList(String orgId) {
		return orgRepository.getOrgDeptList(orgId);
	}
	@Override
	public void saveOrgDeptDetail(OrgDeptDetail orgDeptDetail) {
		orgRepository.saveOrgDeptDetail(orgDeptDetail);
		
	}
	@Override
	public void delOrgDeptById(String orgId) {
		orgRepository.delOrgDeptById(orgId);
		
	}
	@Override
	public List<OrgUser> getOrgUserList(String orgId,String userName,boolean isSuperAdmin) {
		return orgRepository.getOrgUserList(orgId,userName,isSuperAdmin);
	}
	@Override
	public List<OrgUser> getOrgUserListByOrgIds(List<String> orgIds,String userName,String year) {
		return orgRepository.getOrgUserListByOrgIds(orgIds,userName,year);
	}
	@Override
	public UserDtl getOrgUserDetailByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void saveOrgUser(String userId, String org_id) {
		orgRepository.saveOrgUser(userId,org_id);
	}
	@Override
	public void delUser(String userId) {
		orgRepository.delUser(userId);
	}
	@Override
	public void delOrgUserDtl(String userId) {
		orgRepository.delOrgUserDtl(userId);
		
	}
	@Override
	public void delUserOrg(String userId) {
		orgRepository.delUserOrg(userId);
		
	}
	@Override
	public void downOrgUserById(String orgId) {
		orgRepository.downOrgUserById(orgId);
	}
	@Override
	public void updateOrgUser(String orgId,String userId) {
		orgRepository.updateOrgUser(orgId,userId);
		
	}
	@Override
	public List<Organization> getUserUpperOrgList(String orgId) {
		return orgRepository.getUserUpperOrgList(orgId);
	}
	@Override
	public List<String> getOrgIdByUserId(String userId) {
		return orgRepository.getOrgIdByUserId(userId);
	}
	@Override
	public List<String> getLeaderByOrgId(String orgId) {
		return orgRepository.getLeaderByOrgId(orgId);
	}
	@Override
	public List<Organization> getRootOrgId(String orgId) {
		return orgRepository.getRootOrgId(orgId);
	}
	@Override
	public List<Map> getGenderEchartData(List<String> rootOrgs) {
		return orgRepository.getGenderEchartData(rootOrgs);
	}
	@Override
	public List<Map> getAgeEchartBarData(List<String> rootOrgs) {
		return orgRepository.getAgeEchartBarData(rootOrgs);
	}
	@Override
	public List<Map> getEducationEchartBarData(List<String> rootOrgs) {
		return orgRepository.getEducationEchartBarData(rootOrgs);
	}
	@Override
	public List<Map> getPartyAgeEchartBarData(List<String> rootOrgs) {
		return orgRepository.getPartyAgeEchartBarData(rootOrgs);
	}
	@Override
	public List<Organization> getRootOrgList(String userId) {
		return null;
	}
	@Override
	public List<String> getBottomOrgByOrgId(String orgId) {
		return orgRepository.getBottomOrgByOrgId(orgId);
	}
	@Override
	public PageInfo<OrgUser> getOrgUserListByOrg(String userName, String year, List<String> orgList, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo(orgRepository.getOrgUserListByOrg(userName,year,orgList));
	}

	@Override
	public List<String> findOrgIdByUserId(String userId) {
		return orgRepository.findOrgIdByUserId(userId);
	}
	@Override
	public void insertExcelUsers(List<OrgUser> orgUsers) {
		orgRepository.insertExcelUsers(orgUsers);
		
	}
	@Override
	public void insertExcelUsersDtl(List<OrgUser> orgUsers) {
		orgRepository.insertExcelUsersDtl(orgUsers);
		
	}
	@Override
	public void insertExcelUsersOrg(List<OrgUser> orgUsers) {
		orgRepository.insertExcelUsersOrg(orgUsers);
		
	}
	@Override
	public List<String> getOrgIdByName(String orgName) {
		return orgRepository.getOrgIdByName(orgName);
	}
	@Override
	public List<OrgUser> getPartyExcelList(List<String> arrayList) {
		return orgRepository.getPartyExcelList(arrayList);
	}
}
