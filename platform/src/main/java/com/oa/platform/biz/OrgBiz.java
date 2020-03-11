package com.oa.platform.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oa.platform.entity.Organization;
import com.oa.platform.service.OrgService;
import com.oa.platform.util.StringUtil;

/**
 * biz
 * @author 俞灶森
 *
 */
@Component
public class OrgBiz{
	
	
	@Autowired
	private OrgService orgSerivce;
	/**
	 * 获取党组织列表
	 * @param userId
	 * @return
	 */
	public List<Organization> getOrgList(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 党组织新增
	 * @param organization
	 */
	@Transactional
	public void orgAdd(Organization organization) {
		organization.setOrgId(StringUtil.getRandomUUID());
		orgSerivce.orgAdd(organization);
		orgSerivce.orgAddDetail(organization);
	}
	/**
	 * 党组织修改
	 * @param organization
	 */
	public void orgEdit(Organization organization) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 党组织删除
	 * @param organization
	 */
	public void orgDel(Organization organization) {
		// TODO Auto-generated method stub
		
	}
	
}
