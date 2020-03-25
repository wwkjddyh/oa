package com.oa.platform.web.controller.api;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 党委组织
 * @author 俞灶森
 *
 */

import com.alibaba.fastjson.JSONArray;
import com.oa.platform.biz.OrgBiz;
import com.oa.platform.common.ResultVo;
import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.web.controller.BaseController;
@RestController
@RequestMapping("/api/org")
public class OrgApiController extends BaseController{
	
	@Autowired
	private OrgBiz orgBiz;
	
	
	/**
	 * 党委组织列表
	 * @return
	 */
	@GetMapping("getOrgList")
	public ResultVo getOrgList(boolean isSuperAdmin) {
		User user = getUserOfSecurity();
		List<Organization> result = orgBiz.getOrgList(user.getUserId(),isSuperAdmin);
		return getSuccessResultVo(result);
	}
	/**
	 * 获取年度党员信息集合
	 * @return
	 */
	@GetMapping("getOrgUserList")
	public ResultVo getOrgUserList(
			@RequestParam(defaultValue = "",required = false) String year,
			@RequestParam(defaultValue = "",required = false) String userName,
			boolean isSuperAdmin) {
		User user = getUserOfSecurity();
		List<OrgUser> result = orgBiz.getOrgUserList(user.getUserId(),userName,year,isSuperAdmin);
		return getSuccessResultVo(result);
	}
	/**
	 * 获取上级党组织列表
	 * @return
	 */
	@GetMapping("getUpperOrgList")
	public ResultVo getUpperOrgList() {
		List<Organization> result = orgBiz.getUpperOrgList();
		return getSuccessResultVo(result);
	}
	/**
	 * 根据用户获取用户所在组织及下级组织
	 * @return
	 */
	@GetMapping("getUserUpperOrgList")
	public ResultVo getUserUpperOrgList() {
		User user = getUserOfSecurity();
		List<Organization> result = orgBiz.getUserUpperOrgList(user.getUserId());
		return getSuccessResultVo(result);
	}
	/**
	 * 获取组织详情
	 * @param orgId
	 * @return
	 */
	@GetMapping("getOrgDetailById")
	public ResultVo getOrgDetailById(String orgId) {
		List<Organization> result = orgBiz.getOrgDetailById(orgId);
		if(result == null || result.size() == 0) {
			return getSuccessResultVo(null);
		}
		return getSuccessResultVo(result.get(0));
	}
	/**
	 * 党员操作
	 * @param userDtl
	 * @return
	 */
	@PostMapping("orgUserOpreate")
	public ResultVo orgUserOpreate(UserDtl userDtl) {
		User user = getUserOfSecurity();
		ResultVo resultVo = null;
		userDtl.setCreateBy(user.getUserName());
		userDtl.setUpdateBy(user.getUserName());
		if(userDtl.getUserId() == null || "".equals(userDtl.getUserId())) {
			//新增
			orgBiz.orgUserAdd(userDtl);
			resultVo = getSuccessResultVo(null);
		}else {
			//修改
			resultVo = orgBiz.orgUserEdit(userDtl);
		}
		return resultVo;
	}
	/**
	 * 党组织操作
	 * @param organization 组织信息
	 * @return
	 */
	@PostMapping("orgOpreate")
	public ResultVo orgOpreate(Organization organization,
			@RequestParam(value = "deptDetails")String deptDetails,
			@RequestParam(value = "rewardDetails")String rewardDetails,
			@RequestParam(value = "leaderDetails")String leaderDetails) {
		User user = getUserOfSecurity();
		ResultVo resultVo = null;
		organization.setCreateBy(user.getUserName());
		organization.setUpdateBy(user.getUserName());
		if(organization.getOrgId() == null || "".equals(organization.getOrgId())) {
			 List<OrgDeptDetail> deptDetail = JSONArray.parseArray(deptDetails,OrgDeptDetail.class);
			 List<OrgRewardDetail> rewardDetail = JSONArray.parseArray(rewardDetails,OrgRewardDetail.class);
			 List<OrgLeaderDetail> leaderDetail = JSONArray.parseArray(leaderDetails,OrgLeaderDetail.class);
			orgBiz.orgAdd(organization,deptDetail,rewardDetail,leaderDetail);
			resultVo = getSuccessResultVo(null);
		}else {
			 List<OrgDeptDetail> deptDetail = JSONArray.parseArray(deptDetails,OrgDeptDetail.class);
			 List<OrgRewardDetail> rewardDetail = JSONArray.parseArray(rewardDetails,OrgRewardDetail.class);
			 List<OrgLeaderDetail> leaderDetail = JSONArray.parseArray(leaderDetails,OrgLeaderDetail.class);
			orgBiz.orgEdit(organization,deptDetail,rewardDetail,leaderDetail);
			resultVo = getSuccessResultVo(null);
		}
		return resultVo;
	}
	/**
	 * 删除指定组织
	 * @param orgId
	 * @return
	 */
	@PostMapping("delOrg")
	public ResultVo delOrg(String orgId) {
		orgBiz.orgDel(orgId);
		return getSuccessResultVo(null);
	}
	/**
	 * 删除党员信息
	 * @param userId
	 * @return
	 */
	@PostMapping("delOrgUser")
	public ResultVo delOrgUser(String userId) {
		
		return orgBiz.delOrgUser(userId);
	}
	/**
	 * 获取所属党组织情况信息
	 * @return
	 */
	@GetMapping("getDeptList")
	public ResultVo getDeptList() {
		User user = getUserOfSecurity();
		List<Organization> result = orgBiz.getDeptList(user.getUserId());
		return getSuccessResultVo(result);
	}
	/**
	 * 根据组织id获取班子成员
	 * @param org_id
	 * @return
	 */
	@GetMapping("getOrgLeaderList")
	public ResultVo getOrgLeaderList(String orgId) {
		List<OrgLeaderDetail> result = orgBiz.getOrgLeaderList(orgId);
		return getSuccessResultVo(result);
	}
	/**
	 * 根据组织奖惩情况
	 * @param org_id
	 * @return
	 */
	@GetMapping("getOrgRewardList")
	public ResultVo getOrgRewardList(String orgId) {
		List<OrgRewardDetail> result = orgBiz.getOrgRewardList(orgId);
		return getSuccessResultVo(result);
	}
	/**
	 * 根据组织奖惩情况
	 * @param org_id
	 * @return
	 */
	@GetMapping("getOrgDeptList")
	public ResultVo getOrgDeptList(String orgId) {
		List<OrgDeptDetail> result = orgBiz.getOrgDeptList(orgId);
		return getSuccessResultVo(result);
	}
	/**
	 * 根据id获取党员信息详情
	 * @param userId
	 * @return
	 */
	@GetMapping("getOrgUserDetailByUserId")
	public ResultVo getOrgUserDetailByUserId(String userId) {
		UserDtl userDtl = orgBiz.getOrgUserDetailByUserId(userId);
		return getSuccessResultVo(userDtl);
	}
	/**
	 * 获取当前用户所在组织id
	 * @return
	 */
	@GetMapping("getOrgIdByUserId")
	public ResultVo getOrgIdByUserId() {
		User user = getUserOfSecurity();
		String orgId = orgBiz.getOrgIdByUserId(user.getUserId());
		return getSuccessResultVo(orgId);
	}
}
