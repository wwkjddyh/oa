package com.oa.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.platform.entity.Organization;
import com.oa.platform.repository.OrgRepository;
import com.oa.platform.service.OrgService;
/**
 * orgservice
 * @author 俞灶森
 *
 */
@Service
public class OrgServiceImpl implements OrgService {
	
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

}
