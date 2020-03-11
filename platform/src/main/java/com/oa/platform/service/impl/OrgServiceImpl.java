package com.oa.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.platform.entity.Organization;
import com.oa.platform.repository.OrgDao;
import com.oa.platform.service.OrgService;
/**
 * orgservice
 * @author 俞灶森
 *
 */
@Service
public class OrgServiceImpl implements OrgService {
	
	@Autowired
	private OrgDao orgDao;
	/**
	 * 组织新增
	 */
	@Override
	public void orgAdd(Organization organization) {
		orgDao.orgAdd(organization);
		
	}
	/**
	 * 组织详情新增
	 */
	@Override
	public void orgAddDetail(Organization organization) {
		orgDao.orgAddDetail(organization);
		
	}

}
