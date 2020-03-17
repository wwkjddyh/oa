package com.oa.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
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
	public List<Organization> getOrgList(String orgId) {
		return orgRepository.getOrgList(orgId);
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
	public List<OrgUser> getOrgUserList(String orgId,String userName) {
		return orgRepository.getOrgUserList(orgId,userName);
	}

}
