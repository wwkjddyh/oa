package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.PartyDues;
import com.oa.platform.service.PartyDuesService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 党费(缴纳)业务处理
 * @author jianbo.feng
 * @date 2020/03/16
 */
@Component
public class PartyDuesBiz extends BaseBiz {

    @Autowired
    private PartyDuesService partyDuesService;

    @Autowired
    private UserService userService;

    /**
     * 是否重复
     * @param recordId 记录唯一标识
     * @param userId 用户ID
     * @param payPeriod 缴纳期(如：上缴哪季度党费)
     * @param payTime 上缴时间
     * @return
     */
    boolean validRepeat(String recordId, String userId , String payPeriod, String payTime) {
        boolean isRepeat = false;
        recordId = StringUtil.trim(recordId);
        userId = StringUtil.trim(userId);
        payPeriod = StringUtil.trim(payPeriod);
        payTime = StringUtil.trim(payTime);
        PartyDues dues = new PartyDues();
        dues.setUserId(userId);
        dues.setPayPeriod(payPeriod);
//        dues.setPayTime(payTime);
        dues.setRecordFlag(Constants.INT_NORMAL);
        List<PartyDues> duesList = partyDuesService.find(dues);
        if(duesList != null && !duesList.isEmpty()) {
            if("".equals(recordId)) {
                isRepeat = true;
            }
            else {
                final String finalRecordId = recordId;
                isRepeat = duesList.parallelStream().anyMatch(e -> !e.getRecordId().equals(finalRecordId));
            }
        }
        return isRepeat;
    }

    /**
     * 保存或更新
     * @param recordId 唯一标识
     * @param payTime 缴纳时间
     * @param payAmount 缴纳金额
     * @param remark 备注
     * @param payPeriod 缴纳期(如：上缴哪季度党费)
     * @param recordFlag 信息标识
     * @return
     */
    public Map<String, Object> save(String recordId, String payTime, String payAmount, String remark,
                                    String payPeriod, Integer recordFlag) {
        recordId = StringUtil.trim(recordId);
        payTime = StringUtil.trim(payTime);
        payAmount = StringUtil.trim(payAmount);
        remark = StringUtil.trim(remark);
        payPeriod = StringUtil.trim(payPeriod);

        if ("".equals(payTime) || "".equals(payAmount) || "".equals(payPeriod)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                String userId = this.getUserIdOfSecurity();
                boolean isRepeat = this.validRepeat(recordId, userId, payPeriod, payTime);
                if (isRepeat) {
                    ret = this.getParamRepeatErrorVo("同一季(党费)已缴纳");
                }
                else {
                    PartyDues dues = new PartyDues();
                    dues.setUserId(userId);
                    dues.setPayPeriod(payPeriod);
                    dues.setPayTime(payTime);
                    dues.setRemark(remark);
                    dues.setPayAmount(new BigDecimal(payAmount));
                    String currTime = DateUtil.currDateFormat(null);
                    if ("".equals(recordId)) {
                        dues.setRecordId(StringUtil.getRandomUUID());
                        dues.setRecordTime(currTime);
                        dues.setRecordFlag(Constants.INT_NORMAL);
                        partyDuesService.save(dues);
                    }
                    else {
                        dues.setRecordId(recordId);
                        dues.setUpdatorId(userId);
                        dues.setUpdateTime(currTime);
                        recordFlag = recordFlag == null ? Constants.INT_NORMAL : recordFlag;
                        dues.setRecordFlag(recordFlag);
                        partyDuesService.update(dues);
                    }
                    ret = this.getSuccessVo("", "");
                }

            } catch (Exception e) {
                e.printStackTrace();
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }

        return ret;
    }

    /**
     * 根据id获得党员缴费信息
     * @param id 唯一标识
     * @return
     */
    public Map<String, Object> get(String id) {
        id = StringUtil.trim(id);
        if ("".equals(id)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                PartyDues dues = partyDuesService.getById(id);
                if (dues == null) {
                    ret = this.getParamErrorVo();
                }
                else {
                    ret = this.getSuccessVo("", dues);
                }
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据id删除缴费信息
     * @param id 唯一标识
     * @return
     */
    public Map<String, Object> deleteById(String id) {
        id = StringUtil.trim(id);
        if ("".equals(id)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                PartyDues dues = new PartyDues();
                dues.setRecordId(id);
                dues.setRecordFlag(Constants.INT_DEL);
                partyDuesService.update(dues);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 检索
     * @param userId 用户ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String, Object> search(String userId, String key, int pageNum, int pageSize) {
        ret = null;
        try {
            PartyDues dues = new PartyDues();
//            dues.setUserId(userId);
            userId = StringUtil.trim(userId);
            List<String> userIds = new ArrayList<>(0);
            if (!"".equals(userId)) {
                userIds = userService.getUsersByCurrentUser(userId);
                if (userIds == null || userIds.isEmpty()) {
                    userIds = Lists.newArrayList(userId);
                }
                else {
                    if (!userIds.contains(userId)) {
                        userIds.add(userId);
                    }
                }
            }
            dues.setUserIds(userIds);
            dues.setKey(StringUtil.trim(key));
            dues.setRecordFlag(Constants.INT_NORMAL);
            PageInfo<PartyDues> pageInfo = partyDuesService.search(dues, pageNum, pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
