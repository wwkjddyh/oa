package com.oa.platform.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.common.ResultVo;
import com.oa.platform.entity.Dict;
import com.oa.platform.entity.EChartsData;
import com.oa.platform.entity.OrgDeptDetail;
import com.oa.platform.entity.OrgLeaderDetail;
import com.oa.platform.entity.OrgRewardDetail;
import com.oa.platform.entity.OrgUser;
import com.oa.platform.entity.Organization;
import com.oa.platform.entity.User;
import com.oa.platform.entity.UserDtl;
import com.oa.platform.service.DictService;
import com.oa.platform.service.OrgService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.StringUtil;

/**
 * biz
 * @author 俞灶森
 *
 */
@Component
public class OrgBiz extends BaseBiz {
	
	@Autowired
    UserService userService;
	
	@Autowired
	private OrgService orgSerivce;
	
	@Value("${userDefaultPwd}")
	private String userDefaultPwd;
	@Autowired
    private DictService dictService;
	
	private final static String[] AGE_AXIS = new String[] {"20-30","30-40","40-50","50-60","60以上"};
	private final static String[] PARTYAGE_AXIS = new String[] {"0-5年","5-10年","10-15年","15-20年","20年以上"};
	private final static String[] GENDER_AXIS = new String[] {"男","女"};
	/**
	 * 获取党组织列表
	 * @param userId
	 * @return
	 */
	public List<Organization> getOrgList(String userId,boolean isSuperAdmin) {
		if(isSuperAdmin) {
			List<Organization> result = orgSerivce.getOrgList("admin",isSuperAdmin);
			return result;
		}else {
			String orgId = null;
			//根据用户id获取所在组织主键
			List<Organization> orgInfo = orgSerivce.getOrgIdByuserId(userId);
			if(orgInfo == null || orgInfo.size() == 0) {
				//用户无组织，无查询结果
				return new ArrayList<Organization>();
			}
			List<Organization> result = orgSerivce.getOrgList(orgInfo.get(0).getOrgId(),isSuperAdmin);
			return result;
		}
	}
	/**
	 * 党组织新增
	 * @param organization
	 */
	@Transactional
	public void orgAdd(Organization organization,List<OrgDeptDetail> deptDetails,List<OrgRewardDetail> rewardDetails,List<OrgLeaderDetail> leaderDetails) {
		organization.setOrgId(StringUtil.getRandomUUID());
		if(organization.getUpperOrg() == null || "".equals(organization.getUpperOrg())) {
			organization.setRootOrg(organization.getOrgId());
		}
		orgSerivce.orgAdd(organization);
		orgSerivce.orgAddDetail(organization);
		//领导班子，单位信息，奖惩信息操作
		orgDetailsOperate(organization,deptDetails,rewardDetails,leaderDetails);
	}
	/**
	 * 党组织修改
	 * @param organization
	 */
	@Transactional
	public void orgEdit(Organization organization,List<OrgDeptDetail> deptDetails,List<OrgRewardDetail> rewardDetails,List<OrgLeaderDetail> leaderDetails) {
		if(organization.getOrgId() != null && !"".equals(organization.getOrgId())) {
			orgSerivce.orgEdit(organization);
			
			orgSerivce.orgEditDetail(organization);
			//领导班子，单位信息，奖惩信息操作
			orgDetailsOperate(organization,deptDetails,rewardDetails,leaderDetails);
		}
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
	/**
	 * 获取所属党组织情况信息
	 * @param userId
	 * @return
	 */
	public List<Organization> getDeptList(String userId) {
		return orgSerivce.getDeptList(userId);
	}
	/**
	 * 根据组织获取领导班子成员
	 * @param orgId
	 * @return
	 */
	public List<OrgLeaderDetail> getOrgLeaderList(String orgId) {
		return orgSerivce.getOrgLeaderList(orgId);
	}
	public List<OrgRewardDetail> getOrgRewardList(String orgId) {
		return orgSerivce.getOrgRewardList(orgId);
	}
	public List<OrgDeptDetail> getOrgDeptList(String orgId) {
		return orgSerivce.getOrgDeptList(orgId);
	}
	/**
	 * 组织附加详情操作
	 */
	private void orgDetailsOperate(Organization organization,List<OrgDeptDetail> deptDetails,List<OrgRewardDetail> rewardDetails,List<OrgLeaderDetail> leaderDetails) {
		//班子成员
		//删除原有数据
		orgSerivce.delOrgLeaderById(organization.getOrgId());
		//新增现有数据
		for (OrgLeaderDetail orgLeaderDetail : leaderDetails) {
			orgLeaderDetail.setStaticsId(StringUtil.getRandomUUID());
			orgLeaderDetail.setOrgId(organization.getOrgId());
			orgSerivce.saveOrgLeaderDetail(orgLeaderDetail);
		}
		//奖罚信息
		orgSerivce.delOrgRewardById(organization.getOrgId());
		for (OrgRewardDetail orgRewardDetail : rewardDetails) {
			orgRewardDetail.setStaticsId(StringUtil.getRandomUUID());
			orgRewardDetail.setOrgId(organization.getOrgId());
			orgSerivce.saveOrgRewardDetail(orgRewardDetail);
		}
		//单位信息
		orgSerivce.delOrgDeptById(organization.getOrgId());
		for (OrgDeptDetail orgDeptDetail : deptDetails) {
			orgDeptDetail.setStaticsId(StringUtil.getRandomUUID());
			orgDeptDetail.setOrgId(organization.getOrgId());
			orgSerivce.saveOrgDeptDetail(orgDeptDetail);
		}
		
	}
	/**
	 * 班子成员数据提交
	 * @param orgLeaderDetails
	 * @param userName
	 */
	@Transactional
	public void orgLeaderDetailSubmit(List<OrgLeaderDetail> orgLeaderDetails, String userName,String orgId) {
		//删除原有数据
		orgSerivce.delOrgLeaderById(orgId);
		//新增现有数据
		for (OrgLeaderDetail orgLeaderDetail : orgLeaderDetails) {
			if(orgLeaderDetail.getStaticsId() == null || "".equals(orgLeaderDetail.getStaticsId())){
				orgLeaderDetail.setStaticsId(StringUtil.getRandomUUID());
			}
			orgSerivce.saveOrgLeaderDetail(orgLeaderDetail);
		}
		
	}
	/**
	 * 获取年度党员信息集合
	 * @param userId
	 * @return
	 */
	public List<OrgUser> getOrgUserList(String userId,String userName,String year,boolean isSuperAdmin) {
		String orgId = null;
		List<OrgUser> result = null;
		
		if(!isSuperAdmin) {
			//根据用户id获取所在组织主键
			List<Organization> orgInfo = orgSerivce.getOrgIdByuserId(userId);
			if(orgInfo == null || orgInfo.size() == 0) {
				//用户无组织，无查询结果
				return new ArrayList<OrgUser>();
			}
			result = orgSerivce.getOrgUserList(orgInfo.get(0).getOrgId(),userName,isSuperAdmin);
		}else {
			result = orgSerivce.getOrgUserList("admin",userName,isSuperAdmin);
		}
		
		if(result == null || result.size() == 0) {
			return new ArrayList<OrgUser>();
		}
		List<String> orgIds = new ArrayList<String>();
		for (OrgUser orgUser : result) {
			orgIds.add(orgUser.getOrgId());
		}
		List<OrgUser> users = orgSerivce.getOrgUserListByOrgIds(orgIds,userName,year);
		if(users == null || users.size() == 0) {
			return result;
		}
		users.addAll(result);
		return users;
	}
	public UserDtl getOrgUserDetailByUserId(String userId) {
		
		return orgSerivce.getOrgUserDetailByUserId(userId);
	}
	@Transactional
	public void orgUserAdd(UserDtl userDtl) {
		userDtl.setUserId(StringUtil.getRandomUUID());
		User user = new User();
		user.setUserId(userDtl.getUserId());
		user.setUserName(userDtl.getUserName());
		user.setUserPwd(userDefaultPwd);
		user.setUserPwdOrigi("123456");
		user.setRecordFlag(1);
		user.setUserType(3);
		List<String> leaders = orgSerivce.getLeaderByOrgId(userDtl.getOrgId());
		if(leaders == null || leaders.size() == 0) {
			userDtl.setLeader("1");
		}
		if("1".equals(userDtl.getLeader())) {
			//将原来的部门负责人换下
			orgSerivce.downOrgUserById(userDtl.getOrgId());
		}
		userService.save(user);
		//保存详情
		userDtl.setParty("1");
		userService.saveUserDtl(userDtl);
		//保存用户部门关系
		orgSerivce.saveOrgUser(userDtl.getUserId(),userDtl.getOrgId());
	}
	@Transactional
	public ResultVo orgUserEdit(UserDtl userDtl) {
		User user = new User();
		user.setUserId(userDtl.getUserId());
		user.setUserName(userDtl.getUserName());
		//查询用户信息
		UserDtl findDetailByUserId = userService.findDetailByUserId(userDtl.getUserId());
		//判断此次修改是否修改组织
		List<Organization> orgIdByuserId = orgSerivce.getOrgIdByuserId(userDtl.getUserId());
		//该用户为某组织负责人
		if(findDetailByUserId != null && "1".equals(findDetailByUserId.getLeader())) {
			
			if(orgIdByuserId != null && orgIdByuserId.size() != 0) {
				if(!userDtl.getOrgId().equals(orgIdByuserId.get(0).getOrgId())) {
					//已修改组织，先返回，不做编辑
					return getErroResultVo(2000, "该党员为组织部门负责人,更换组织部门时请先更换部门负责人", null);
				}
				if("1".equals(findDetailByUserId.getLeader()) && !"1".equals(userDtl.getLeader())) {
					return getErroResultVo(2000, "该党员为组织部门负责人,请先认定其他党员为负责人", null);
				}
			}
		}
		if(orgIdByuserId != null && orgIdByuserId.size() != 0) {
			if(userDtl.getOrgId() != null && !"".equals(userDtl.getOrgId()) && !userDtl.getOrgId().equals(orgIdByuserId.get(0).getOrgId())) {
				//组织修改
				orgSerivce.updateOrgUser(userDtl.getOrgId(),userDtl.getUserId());
			}
		}
			
		if("1".equals(userDtl.getLeader()) && !"1".equals(findDetailByUserId.getLeader())) {
			//将原来的部门负责人换下
			orgSerivce.downOrgUserById(userDtl.getOrgId());
		}
		userService.update(user);
		userService.updateUserDtl(userDtl);
		return getSuccessResultVo(null);
	}
	/**
	 * 删除党员信息
	 * @param userId
	 */
	@Transactional
	public ResultVo delOrgUser(String userId) {
		//查询用户信息
		UserDtl findDetailByUserId = userService.findDetailByUserId(userId);
		if(findDetailByUserId != null && "1".equals(findDetailByUserId.getLeader())) {
			return getErroResultVo(2000, "该党员为组织部门负责人,删除前请先更换部门负责人", null);
		}
		//删除党员用户
		orgSerivce.delUser(userId);
		//删除党员用户详情
		orgSerivce.delOrgUserDtl(userId);
		//删除党员与组织关系
		orgSerivce.delUserOrg(userId);
		return getSuccessResultVo(null);
	}
	/**
	 * 根据用户获取用户所在组织及下级组织
	 * @param userId
	 * @return
	 */
	public List<Organization> getUserUpperOrgList(String userId) {
		List<Organization> orgIdByuserId = orgSerivce.getOrgIdByuserId(userId);
		if(orgIdByuserId == null || orgIdByuserId.size() == 0) {
			return new ArrayList<Organization>();
		}
		List<Organization> result = orgSerivce.getUserUpperOrgList(orgIdByuserId.get(0).getOrgId());
		return result;
	}
	public String getOrgIdByUserId(String userId) {
		List<String> result = orgSerivce.getOrgIdByUserId(userId);
		if(result == null || result.size()==0) {
			return null;
		}else {
			return result.get(0);
		}
	}
	
	/**
	 * 获取echarts数据结果集
	 * @param userId
	 * @return
	 */
	public List<EChartsData> getEchartsDataByCurrentUser(String userId) {
		//获取当前用户可见组织机构
		List<Organization> orgIdByuserId = orgSerivce.getOrgIdByuserId(userId);
		if(orgIdByuserId == null || orgIdByuserId.size() == 0) {
			return null;
		}
		List<EChartsData> result = new ArrayList<EChartsData>();
		List<String> rootOrgs = new ArrayList<String>();
		List<String> rootOrgNames = new ArrayList<String>();
		List<Organization> getRootOrgId = orgSerivce.getRootOrgId(orgIdByuserId.get(0).getOrgId());
		for (Organization organization : getRootOrgId) {
			rootOrgs.add(organization.getOrgId());
			rootOrgNames.add(organization.getOrgName());
		}
		//性别饼状图数据
		List<Map> genderData = orgSerivce.getGenderEchartData(rootOrgs);
		EChartsData gender = new EChartsData();
		gender.setTitle("党员性别统计");
		gender.setLegend(rootOrgNames);
		gender.setAxis(Arrays.asList(GENDER_AXIS));
		genderEchartData(gender,genderData,getRootOrgId);
		//年龄柱状图
		List<Map> ageData = orgSerivce.getAgeEchartBarData(rootOrgs);
		result.add(gender);
		EChartsData age = new EChartsData();
		age.setTitle("党员年龄统计");
		age.setLegend(rootOrgNames);
		age.setAxis(Arrays.asList(AGE_AXIS));
		ageEchartBarDataTrans(age,ageData,getRootOrgId);
		result.add(age);
		//党龄柱状图
		List<Map> partyAgeData = orgSerivce.getPartyAgeEchartBarData(rootOrgs);
		EChartsData partyAge = new EChartsData();
		partyAge.setTitle("党员党龄统计");
		partyAge.setLegend(rootOrgNames);
		partyAge.setAxis(Arrays.asList(PARTYAGE_AXIS));
		partyAgeEchartBarDataTrans(partyAge,partyAgeData,getRootOrgId);
		result.add(partyAge);
		//学历柱状图
		List<Map> educationData = orgSerivce.getEducationEchartBarData(rootOrgs);
		Dict dict = new Dict();
        dict.setDictType(StringUtil.trim("education"));
        dict.setRecordFlag(Constants.INT_NORMAL);
        PageInfo<Dict> pageInfo = dictService.search(dict,1,99999);
        List<Dict> education = pageInfo.getList();
        List<String> educationAxis = new ArrayList<String>();
        for (Dict etcDict : education) {
        	educationAxis.add(etcDict.getDictName());
		}
        EChartsData educationEb = new EChartsData();
        educationEb.setTitle("党员学历统计");
        educationEb.setLegend(rootOrgNames);
        educationEb.setAxis(educationAxis);
        educationEchartBarDataTrans(educationEb,educationData,educationAxis,getRootOrgId);
        result.add(educationEb);
		return result;
	}
	/**
	 * 党员性别统计
	 * @param gender
	 * @param genderData
	 * @param getRootOrgId
	 */
	private void genderEchartData(EChartsData gender, List<Map> genderData, List<Organization> getRootOrgId) {


		if(gender.getLegend()!=null && gender.getLegend().size() > 0) {
			List<Object> result = new ArrayList<Object>(); 
			for (Organization rootOrg : getRootOrgId) {
				Map<String,Object> singleData = new HashMap<String,Object>();
				singleData.put("name", rootOrg.getOrgName());
				List<String> data = new ArrayList<String>();
				for(int i = 0 ; i< GENDER_AXIS.length;i++) {
					boolean hasData = false;
					for (Map map : genderData) {
						if(rootOrg.getOrgId().equals(map.get("orgId")) && GENDER_AXIS[i].equals(map.get("gender"))) {
							data.add(String.valueOf(map.get("partyNum")));
							hasData = true;
							break;
						}
					}
					if(!hasData) {
						data.add("0");
					}
				}
				singleData.put("data", data);
				result.add(singleData);
			}
			gender.setData(result);
		}
		
	
		
	
		
	}
	/**
	 * 学历柱状图数据转换
	 * @param educationEb
	 * @param educationData
	 * @param educationLegend
	 */
	private void educationEchartBarDataTrans(EChartsData educationEb, List<Map> educationData,
			List<String> educationLegend,List<Organization> getRootOrgId) {
		if(educationEb.getLegend()!=null && educationEb.getLegend().size() > 0) {
			List<Object> result = new ArrayList<Object>(); 
			for (Organization rootOrg : getRootOrgId) {
				Map<String,Object> singleData = new HashMap<String,Object>();
				singleData.put("name", rootOrg.getOrgName());
				List<String> data = new ArrayList<String>();
				for(int i = 0 ; i< educationLegend.size();i++) {
					boolean hasData = false;
					for (Map map : educationData) {
						if(rootOrg.getOrgId().equals(map.get("orgId")) && educationLegend.get(i).equals(map.get("educationName"))) {
							data.add(String.valueOf(map.get("partyNum")));
							hasData = true;
							break;
						}
					}
					if(!hasData) {
						data.add("0");
					}
				}
				singleData.put("data", data);
				result.add(singleData);
			}
			educationEb.setData(result);
		}
		
	}
	/**
	 * 党龄柱状图数据转换
	 * @param partyAge
	 * @param partyAgeData
	 */
	private void partyAgeEchartBarDataTrans(EChartsData partyAge, List<Map> partyAgeData,List<Organization> getRootOrgId) {

		if(partyAge.getLegend()!=null && partyAge.getLegend().size() > 0) {
			List<Object> result = new ArrayList<Object>(); 
			for (Organization rootOrg : getRootOrgId) {
				Map<String,Object> singleData = new HashMap<String,Object>();
				singleData.put("name", rootOrg.getOrgName());
				List<String> data = new ArrayList<String>();
				for(int i = 0 ; i< PARTYAGE_AXIS.length;i++) {
					boolean hasData = false;
					for (Map map : partyAgeData) {
						if(rootOrg.getOrgId().equals(map.get("orgId")) && PARTYAGE_AXIS[i].equals(map.get("ageTemp"))) {
							data.add(String.valueOf(map.get("partyNum")));
							hasData = true;
							break;
						}
					}
					if(!hasData) {
						data.add("0");
					}
				}
				singleData.put("data", data);
				result.add(singleData);
			}
			partyAge.setData(result);
		}
		
	
		
	}
	/**
	 * 年龄柱状图数据转换
	 * @param age
	 * @param ageData
	 */
	private void ageEchartBarDataTrans(EChartsData age, List<Map> ageData,List<Organization> getRootOrgId) {
		if(age.getLegend()!=null && age.getLegend().size() > 0) {
			List<Object> result = new ArrayList<Object>(); 
			for (Organization rootOrg : getRootOrgId) {
				Map<String,Object> singleData = new HashMap<String,Object>();
				singleData.put("name", rootOrg.getOrgName());
				List<String> data = new ArrayList<String>();
				for(int i = 0 ; i< AGE_AXIS.length;i++) {
					boolean hasData = false;
					for (Map map : ageData) {
						if(rootOrg.getOrgId().equals(map.get("orgId")) && AGE_AXIS[i].equals(map.get("ageTemp"))) {
							data.add(String.valueOf(map.get("partyNum")));
							hasData = true;
							break;
						}
					}
					if(!hasData) {
						data.add("0");
					}
				}
				singleData.put("data", data);
				result.add(singleData);
			}
			age.setData(result);
		}
		
	}
	
	
}
