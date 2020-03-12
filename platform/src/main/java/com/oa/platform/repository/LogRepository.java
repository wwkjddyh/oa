package com.oa.platform.repository;

import com.oa.platform.entity.LoginLog;
import com.oa.platform.entity.OperateLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日志
 * @author Feng
 * @date 2019/03/01
 */
@Repository
public interface LogRepository extends BaseRepository<LoginLog, String>{

    /**
     * 插入登录日志
     * @param loginLog
     */
    void insertLoginLog(LoginLog loginLog);

    /**
     * 插入操作日志
     * @param operateLog
     */
    void insertOperateLog(OperateLog operateLog);

    /**
     * 根据logId查询操作日志
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
}
