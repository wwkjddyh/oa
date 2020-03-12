package com.oa.platform.repository;

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

}
