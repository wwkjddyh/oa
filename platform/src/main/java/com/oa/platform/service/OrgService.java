package com.oa.platform.service;

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

}
