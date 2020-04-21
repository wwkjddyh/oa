package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Organization;
import com.oa.platform.entity.Res;
import com.oa.platform.entity.ResDl;
import com.oa.platform.service.OrgService;
import com.oa.platform.service.ResService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资源业务处理
 * @author jianbo.feng
 * @date 2020/03/14
 */
@Component
public class ResBiz extends BaseBiz {

    @Autowired
    private ResService resService;

    @Autowired
    private UserService userService;
    @Autowired
	private OrgService orgSerivce;
    /**
     * 保存或更新资源
     * @param res 资源信息
     * @return
     */
    public Map<String, Object> save(Res res) {
        if (res == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                String recordId = StringUtil.trim(res.getRecordId());
                res.setOrgId(StringUtil.trim(res.getOrgId()));
                if ("".equals(recordId)) {
                    res.setRecordId(StringUtil.getRandomUUID());
                    res.setAnnouncerId(this.getUserIdOfSecurity());
                    res.setRecordFlag(Constants.INT_NORMAL);
					/*
					 * if("01ef5219-464e-44a3-890a-557e3bbabd4e".equals(res.getTypeId()) ||
					 * "1e9941a0-2a6f-4c2f-b74c-970d0351469f".equals(res.getTypeId()) ||
					 * "3dea99ab-ec00-4633-b24c-7c44a5ce57b8".equals(res.getTypeId()) ||
					 * "2e9941a0-2a6f-4c2f-b74c-970d0351469f".equals(res.getTypeId())) {
					 * //获取组织挂接组织机构 List<String> orgIds =
					 * resService.getOrgIdByUserId(this.getUserIdOfSecurity()); if(orgIds!= null &&
					 * orgIds.size() > 0 ) { res.setOrgId(orgIds.get(0)); }
					 * 
					 * }
					 */
                    // 针对法规制度上传，不需要发布时间的问题，做特殊处理
                    String publishTime = StringUtil.trim(res.getPublishTime());
                    if ("".equals(publishTime)) {
                        publishTime = DateUtil.currDateFormat(null);
                    }
                    res.setPublishTime(publishTime);
                    resService.save(res);
                }
                else {
                    res.setEditorId(this.getUserIdOfSecurity());
                    res.setModifyTime(DateUtil.currDateFormat(null));
                    if (res.getRecordFlag() == null) {
                        res.setRecordFlag(Constants.INT_NORMAL);
                    }
                    resService.update(res);
                }
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 保存下载信息
     * @param resDl 下载信息
     * @return
     */
    public Map<String, Object> saveResDl(ResDl resDl) {
        if (resDl == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                resDl.setRecordId(StringUtil.getRandomUUID());
                resDl.setRecordFlag(Constants.INT_NORMAL);
                resService.saveResDl(resDl);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据resId获得资源信息
     * @param resId 唯一标识
     * @return
     */
    public Map<String, Object> get(String resId) {
        resId = StringUtil.trim(resId);
        if ("".equals(resId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Res res = resService.getById(resId);
                if (res == null) {
                    ret = this.getParamErrorVo();
                }
                else {
                    ret = this.getSuccessVo("", res);
                }
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据resId删除信息
     * @param resId 唯一标识
     * @return
     */
    public Map<String, Object> deleteById(String resId) {
        resId = StringUtil.trim(resId);
        if ("".equals(resId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Res res = new Res();
                res.setRecordId(resId);
                res.setRecordFlag(Constants.INT_DEL);
                resService.update(res);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据resDlId删除资源下载信息
     * @param resDlId 唯一标识
     * @return
     */
    public Map<String, Object> deleteByResDlId(String resDlId) {
        resDlId = StringUtil.trim(resDlId);
        if ("".equals(resDlId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                ResDl resDl = new ResDl();
                resDl.setRecordId(resDlId);
                resDl.setRecordFlag(Constants.INT_DEL);
                resService.updateResDl(resDl);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 检索资源
     * @param typeId 分类ID
     * @param assId 关联信息ID
     * @param assTypeId 关联类型ID
     * @param announcerId 发布者ID
     * @param editorId 编辑者ID
     * @param orgId 组织ID
     * @param yearMonth 年月(格式: yyyy-MM)
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String, Object> search(String typeId, String assId, String assTypeId, String announcerId,
                                      String editorId, String orgId, String yearMonth, String key, int pageNum, int pageSize) {
        ret = null;
        try {
            Res res = new Res();

            res.setTypeId(StringUtil.trim(typeId));
            res.setAssId(StringUtil.trim(assId));
            res.setAssTypeId(StringUtil.trim(assTypeId));
            res.setEditorId(StringUtil.trim(editorId));
            res.setYearMonth(StringUtil.trim(yearMonth));
            res.setKey(StringUtil.trim(key));
            res.setRecordFlag(Constants.INT_NORMAL);
            orgId = StringUtil.trim(orgId);

            String currUserId = this.getUserIdOfSecurity();
			if("01ef5219-464e-44a3-890a-557e3bbabd4e".equals(typeId) || "1e9941a0-2a6f-4c2f-b74c-970d0351469f".equals(typeId)
					|| "3dea99ab-ec00-4633-b24c-7c44a5ce57b8".equals(typeId)) {
				  List<String> announcerIds = Lists.newArrayList(); 
//				  if ("".equals(orgId)) {
					  //查询全部 
					  announcerIds = userService.getAllUsersByCurrentUser(currUserId); 
//				  } else {
//					  // 匹配传入的组织ID 
//					  announcerIds = userService.getUsersByCurrentUser(currUserId,orgId); 
//					}
					 
		            if (announcerIds == null || announcerIds.isEmpty()) {
		                announcerIds = Lists.newArrayList(currUserId);
		            }
		            else {
		                if (!announcerIds.contains(currUserId)) {
		                    announcerIds.add(currUserId);
		                }
		            }
		            res.setAnnouncerIds(announcerIds);
			}else {
	            List<String> orgIds = new ArrayList<String>();
	            List<Organization> result = orgSerivce.getUserUpperOrgList(orgId);
				for (Organization organization : result) {
					orgIds.add(organization.getOrgId());
				}
				res.setOrgIds(orgIds);
			}
            PageInfo<Res> pageInfo = resService.search(res, pageNum, pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 检索资源下载信息
     * @param recordId 唯一标识
     * @param resId 资源ID
     * @param userId 下载用户ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String, Object> searchDl(String recordId, String resId, String userId,
                                        String key, int pageNum, int pageSize) {
        ret = null;
        try {
            recordId = StringUtil.trim(recordId);
            ResDl resDl = new ResDl();
            resDl.setRecordId(recordId);
            if (!"".equals(recordId)) {
                List<ResDl> dlList = resService.findResDl(resDl);
                ret = this.getSingleInfo(dlList);
            }
            else {
                resDl.setResId(StringUtil.trim(resId));
                resDl.setUserId(StringUtil.trim(userId));
                resDl.setKey(StringUtil.trim(key));
                resDl.setRecordFlag(Constants.INT_NORMAL);
                PageInfo<ResDl> pageInfo = resService.searchResDl(resDl, pageNum, pageSize);
                ret = this.getPageInfo(pageInfo);
            }
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 更新资源附件信息
     * @param recordId 信息标识
     * @param originalName 原始文件名
     * @param currName 当前文件名
     * @param accessUrl 访问路径
     * @param resSize 资源大小
     * @return
     */
    public Map<String, Object> uploadAttachmentInfo(String recordId, String originalName, String currName,
                                                    String accessUrl, String resSize, String attaContent) {
        recordId = StringUtil.trim(recordId);
        originalName = StringUtil.trim(originalName);
        currName = StringUtil.trim(currName);
        accessUrl = StringUtil.trim(accessUrl);
        resSize = StringUtil.trim(resSize, "0");
        if ("".equals(recordId) || "".equals(originalName) || "".equals(currName) || "".equals(accessUrl)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                Res res = new Res();
                res.setEditorId(this.getUserIdOfSecurity());
                res.setModifyTime(DateUtil.currDateFormat(null));
                res.setRecordId(recordId);
                res.setOriginalName(originalName);
                res.setCurrName(currName);
                res.setAccessUrl(accessUrl);
                res.setResSize(resSize);
                res.setAttaContent(StringUtil.trim(attaContent));
                resService.update(res);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }
}
