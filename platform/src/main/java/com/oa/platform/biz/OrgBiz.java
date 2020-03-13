package com.oa.platform.biz;

import java.util.ArrayList;
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
public class OrgBiz extends BaseBiz {
	
	
	@Autowired
	private OrgService orgSerivce;
	/**
	 * 获取党组织列表
	 * @param userId
	 * @return
	 */
	public List<Organization> getOrgList(String userId) {
		String orgId = null;
		//根据用户id获取所在组织主键
		List<Organization> orgInfo = orgSerivce.getOrgIdByuserId(userId);
		if(orgInfo == null || orgInfo.size() == 0) {
			//用户无组织，无查询结果
			return new ArrayList<Organization>();
		}
		List<Organization> result = orgSerivce.getOrgList(orgInfo.get(0).getOrgId());
		return result;
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
	@Transactional
	public void orgEdit(Organization organization) {
		
		orgSerivce.orgEdit(organization);
		
		orgSerivce.orgEditDetail(organization);
		
	}
	/**
	 * 党组织删除
	 * @param organization
	 */
	@Transactional
	public void orgDel(String orgId) {
		
		//删除指定组织及其下属组织
		orgSerivce.delOrg(orgId);
		//删除指定组织机器下属组织下人员
		orgSerivce.delOrgUser(orgId);
		//删除指定组织机器下属组织书记
		orgSerivce.delLeader(orgId);
		
	}
	public List<Organization> getUpperOrgList() {
		return orgSerivce.getUpperOrgList();
	}
	public List<Organization> getOrgDetailById(String orgId) {
		return orgSerivce.getOrgDetailById(orgId);
	}
	
}
