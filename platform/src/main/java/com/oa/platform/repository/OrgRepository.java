package com.oa.platform.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
	
	List<Organization> getOrgList(@Param("orgId") String org_id);

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
}
