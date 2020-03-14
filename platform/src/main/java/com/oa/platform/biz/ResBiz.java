package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Res;
import com.oa.platform.entity.ResDl;
import com.oa.platform.service.ResService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                if ("".equals(recordId)) {
                    res.setRecordId(StringUtil.getRandomUUID());
                    res.setRecordFlag(Constants.INT_NORMAL);
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
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String, Object> search(String typeId, String assId, String assTypeId, String announcerId,
                                      String editorId, String key, int pageNum, int pageSize) {
        ret = null;
        try {
            Res res = new Res();
            res.setTypeId(StringUtil.trim(typeId));
            res.setAssId(StringUtil.trim(assId));
            res.setAssTypeId(StringUtil.trim(assTypeId));
            res.setAnnouncerId(StringUtil.trim(announcerId));
            res.setEditorId(StringUtil.trim(editorId));
            res.setKey(StringUtil.trim(key));
            res.setRecordFlag(Constants.INT_NORMAL);
            PageInfo<Res> pageInfo = resService.search(res, pageNum, pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
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

}
