package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.LoginLog;
import com.oa.platform.entity.OperateLog;
import com.oa.platform.service.LogService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 日志
 * @author Feng
 * @date 2019/03/01
 */
@Component
public class LogBiz extends BaseBiz {

    @Autowired
    private LogService logService;

    /**
     * 保存登录日志
     * @param userId 用户id
     * @param ipAddr ip地址
     */
    public void saveLoginLog(String userId, String ipAddr) {
        logService.saveLoginLog(userId,ipAddr);
    }

    /**
     * 保存(或更新)操作日志
     * @param logId 日志id(若为null或空，则新增)
     * @param userId
     * @param ipAddr
     * @param operateDesc
     * @param operateType
     */
    public void saveOperateLog(String logId,String userId, String ipAddr, String operateDesc,
                               String operateType,Integer recordFlag) {
        logId = StringUtil.trim(logId);
        userId = StringUtil.trim(userId);
        ipAddr = StringUtil.trim(ipAddr);
        operateDesc = StringUtil.trim(operateDesc);
        operateType = StringUtil.trim(operateType);
        if("".equals(logId)) {
            logService.saveOperateLog(userId,ipAddr,operateDesc,operateType);
        }
        else {
            recordFlag = recordFlag == null ? Constants.INT_NORMAL : recordFlag;
            OperateLog operateLog = new OperateLog();
            operateLog.setLogId(logId);
            operateLog.setIpAddr(ipAddr);
            operateLog.setOperateDesc(operateDesc);
            operateLog.setRecordFlag(recordFlag);
            operateLog.setUpdateTime(DateUtil.currDateFormat(null));
            operateLog.setUpdateUserId(userId);
            operateLog.setOperateType(operateType);
            logService.updateOperateLog(operateLog);
        }
    }

    /**
     * 检索登录日志
     * @param logId 日志id
     * @param userId 用户id
     * @param ipAddr ip地址
     * @param recordTime 记录时间(yyyy-MM-dd)
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> searchLoginLog(String logId,String userId,String ipAddr,String recordTime,
                                             int pageNum,int pageSize) {
        recordTime = StringUtil.trim(recordTime);
        LoginLog loginLog = new LoginLog();
        loginLog.setLogId(StringUtil.trim(logId));
        loginLog.setUserId(StringUtil.trim(userId));
        loginLog.setIpAddr(StringUtil.trim(ipAddr));
        if(!"".equals(recordTime)) {
            loginLog.setRecordTime(recordTime);
        }
        PageInfo<LoginLog> pageInfo = logService.searchLoginLog( loginLog, getPageNum(pageNum), getPageSize(pageSize));
        return this.getPageInfo(pageInfo);
    }

    /**
     * 检索操作日志
     * @param logId 日志id
     * @param userId 用户id
     * @param ipAddr ip地址
     * @param recordTime 信息录入时间
     * @param operateDesc 操作描述
     * @param operateType 操作类型
     * @param updateTime 更新时间
     * @param updateUserId 更新者id
     * @param recordFlag 记录标识
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> searchOperateLog(String logId,String userId,String ipAddr,String recordTime,
                                               String operateDesc,String operateType,String updateTime,
                                               String updateUserId,int recordFlag,int pageNum,int pageSize) {
        recordTime = StringUtil.trim(recordTime);
        updateTime = StringUtil.trim(updateTime);
        OperateLog operateLog = new OperateLog();
        operateLog.setLogId(StringUtil.trim(logId));
        operateLog.setUserId(StringUtil.trim(userId));
        operateLog.setIpAddr(StringUtil.trim(ipAddr));
        operateLog.setOperateDesc(StringUtil.trim(operateDesc));
        operateLog.setOperateType(StringUtil.trim(operateType));
        operateLog.setUpdateUserId(StringUtil.trim(updateUserId));
        operateLog.setRecordFlag(recordFlag);
        if(!"".equals(recordTime)) {
            operateLog.setRecordTime(null);
        }
        if(!"".equals(updateTime)) {
            operateLog.setUpdateTime(updateTime);
        }
        PageInfo<OperateLog> pageInfo = logService.searchOperateLog(operateLog,getPageNum(pageNum),getPageSize(pageSize));
        return getPageInfo(pageInfo);
    }
}
