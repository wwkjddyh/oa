package com.oa.platform.service.impl;

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

}
