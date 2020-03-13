package com.oa.platform.service;

import com.github.pagehelper.PageInfo;
import com.oa.platform.entity.LoginLog;
import com.oa.platform.entity.OperateLog;

import java.util.List;

/**
 * 日志业务处理
 * @author Feng
 * @date 2018/08/23
 */
public interface LogService extends BaseService<LoginLog, String> {

    /**
     * 保存登录日志
     * @param loginLog
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 保存登录日志
     * @param userId 用户id
     * @param ipAddr ip地址
     */
    void saveLoginLog(String userId, String ipAddr);

    /**
     * 保存操作日志
     * @param operateLog
     */
    void saveOperateLog(OperateLog operateLog);

    /**
     * 保存操作日志
     * @param userId 用户id
     * @param ipAddr IP地址
     * @param operateDesc 操作内容
     * @param operateType 操作类型
     */
    void saveOperateLog(String userId, String ipAddr, String operateDesc, String operateType);

    /**
     * 根据日志ID查询操作日志
     * @param logId
     * @return
     */
    OperateLog findOperateLogById(String logId);

    /**
     * 查询登录日志
     * @param loginLog
     * @return
     */
    List<LoginLog> findLoginLog(LoginLog loginLog);

    /**
     * 查询操作日志
     * @param operateLog
     * @return
     */
    List<OperateLog> findOperateLog(OperateLog operateLog);

    /**
     * 更新操作日志
     * @param operateLog
     */
    void updateOperateLog(OperateLog operateLog);

    /**
     * 分页查询登录日志
     * @param loginLog 登录日志查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<LoginLog> searchLoginLog(LoginLog loginLog, int pageNum, int pageSize);

    /**
     * 分页查询操作日志
     * @param operateLog 操作日志查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    PageInfo<OperateLog> searchOperateLog(OperateLog operateLog, int pageNum, int pageSize);
}
