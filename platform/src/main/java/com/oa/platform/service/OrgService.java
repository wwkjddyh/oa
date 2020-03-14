package com.oa.platform.service;

import java.util.List;

import com.oa.platform.entity.Organization;
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
	List<Organization> getOrgList(String orgId);
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

}
