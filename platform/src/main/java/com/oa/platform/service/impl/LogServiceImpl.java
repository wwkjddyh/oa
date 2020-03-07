package com.oa.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.LoginLog;
import com.oa.platform.entity.OperateLog;
import com.oa.platform.repository.LogRepository;
import com.oa.platform.service.LogService;
import com.oa.platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 日志
 * @author Feng
 * @date 2018/08/23
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public OperateLog findOperateLogById(String logId) {
        return logRepository.findOperateLogById(logId);
    }

    @Transactional
    @Override
    public void saveLoginLog(LoginLog loginLog) {
        logRepository.insertLoginLog(loginLog);
    }

    @Transactional
    @Override
    public void saveOperateLog(OperateLog operateLog) {
        logRepository.insertOperateLog(operateLog);
    }

    @Override
    public List<LoginLog> findLoginLog(LoginLog loginLog) {
        return logRepository.findLoginLog(loginLog);
    }

    @Override
    public List<OperateLog> findOperateLog(OperateLog operateLog) {
        return logRepository.findOperateLog(operateLog);
    }

    @Transactional
    @Override
    public void updateOperateLog(OperateLog operateLog) {
        logRepository.updateOperateLog(operateLog);
    }

    @Override
    public PageInfo<LoginLog> searchLoginLog(LoginLog loginLog, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LoginLog> logs = logRepository.findLoginLog(loginLog == null ? new LoginLog() : loginLog);
        return new PageInfo(logs);
    }

    @Override
    public PageInfo<OperateLog> searchOperateLog(OperateLog operateLog, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(logRepository.findOperateLog(operateLog == null ? new OperateLog() : operateLog));
    }

    @Transactional
    @Override
    public void saveLoginLog(String userId, String ipAddr) {
        this.saveLoginLog(new LoginLog(StringUtil.getRandomUUID(),userId,ipAddr,null));
    }

    @Transactional
    @Override
    public void saveOperateLog(String userId, String ipAddr, String operateDesc, String operateType) {
        this.saveOperateLog(new OperateLog(StringUtil.getRandomUUID(), userId, ipAddr, null, operateDesc,
                operateType, null, null, Constants.INT_NORMAL));
    }


}
