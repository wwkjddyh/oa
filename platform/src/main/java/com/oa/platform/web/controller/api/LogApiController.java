package com.oa.platform.web.controller.api;

import com.oa.platform.biz.LogBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 日志API
 * @author Feng
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/api/log")
public class LogApiController extends BaseController {

    @Autowired
    LogBiz logBiz;

    /**
     * 检索登录日志
     * @param logId 日志id
     * @param userId 用户id
     * @param ipAddr ip地址
     * @param recordTime 记录时间(yyyy-MM-dd)
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return Map&lt;String,Object&gt;
     */
    @GetMapping("searchLoginLog")
    public Map<String,Object> searchLoginLog(@RequestParam(defaultValue = "",required = false) String logId,
                                             @RequestParam(defaultValue = "",required = false) String userId,
                                             @RequestParam(defaultValue = "",required = false) String ipAddr,
                                             @RequestParam(defaultValue = "",required = false) String recordTime,
                                             @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
                                             @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return logBiz.searchLoginLog(logId,userId,ipAddr,recordTime,pageNum,pageSize);
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
     * @return Map&lt;String,Object&gt;
     */
    @GetMapping("searchOperateLog")
    public Map<String,Object> searchOperateLog(@RequestParam(defaultValue = "",required = false) String logId,
                                               @RequestParam(defaultValue = "",required = false) String userId,
                                               @RequestParam(defaultValue = "",required = false) String ipAddr,
                                               @RequestParam(defaultValue = "",required = false) String recordTime,
                                               @RequestParam(defaultValue = "",required = false) String operateDesc,
                                               @RequestParam(defaultValue = "",required = false) String operateType,
                                               @RequestParam(defaultValue = "",required = false) String updateTime,
                                               @RequestParam(defaultValue = "",required = false) String updateUserId,
                                               @RequestParam(defaultValue = "1",required = false) int recordFlag,
                                               @RequestParam(defaultValue = PAGE_NUM_STR,required = false) int pageNum,
                                               @RequestParam(defaultValue = PAGE_SIZE_STR,required = false) int pageSize) {
        return logBiz.searchOperateLog(logId,userId,ipAddr,recordTime,
                operateDesc,operateType,updateTime,updateUserId,recordFlag,pageNum,pageSize);
    }
}
