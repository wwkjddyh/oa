package com.oa.platform.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.News;
import com.oa.platform.entity.NewsSendRecord;
import com.oa.platform.service.MailService;
import com.oa.platform.service.NewsService;
import com.oa.platform.service.RoleService;
import com.oa.platform.service.UserService;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 消息业务处理
 * @author jianbo.feng
 * @date 2020/03/12
 */
@Component
public class NewsBiz extends BaseBiz {

    @Autowired
    private NewsService newsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    /**
     * 保存信息
     * @param news 消息
     * @return
     */
    public Map<String, Object> save(News news) {
        ret = null;
        if (news == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                String recordId = StringUtil.trimNull(news.getRecordId());
                news.setStartTime(StringUtil.trimNull(news.getStartTime()));
                news.setEndTime(StringUtil.trimNull(news.getEndTime()));
                news.setTypeId(StringUtil.trimNull(news.getTypeId(), "msg-notice"));
                String receiverId = StringUtil.trimNull(news.getReceiverId());
                List<String> reUserIds = Arrays.asList(receiverId.split(","));
                String senderId = this.getUserIdOfSecurity();
                if ("".equals(recordId)) {
                    String newsId = StringUtil.getRandomUUID();

                    news.setRecordId(newsId);
                    news.setRecordUserId(senderId);
                    news.setRecordFlag(Constants.INT_NORMAL);
                    List<NewsSendRecord> sendRecords = Lists.newArrayList();
                    if (!reUserIds.isEmpty()) {
                        for (String reUserId : receiverId.split(",")) {
                            NewsSendRecord sendRecord = new NewsSendRecord();
                            sendRecord.setNewsId(newsId);
                            sendRecord.setRecordFlag(Constants.INT_NORMAL);
                            sendRecord.setRecordId(StringUtil.getRandomUUID());
                            sendRecord.setStatus(Constants.UN_VIEWED);
                            sendRecord.setSenderId(senderId);
                            sendRecord.setReceiverId(reUserId);
                            sendRecord.setSenderRemark("");
                            sendRecord.setReceiverRemark("");
                            sendRecord.setSenderMail("");
                            sendRecord.setReceiverMail("");
                            sendRecord.setSenderMobileNumber("");
                            sendRecord.setReceiverMobileNumber("");
                            sendRecords.add(sendRecord);
                        }
                        newsService.batchSaveNewsSendRecord(sendRecords);
                        newsService.save(news);
                    }
                    else {
                        ret = this.getParamErrorVo();
                    }
                }
                else {
                    Integer recordFlag = news.getRecordFlag();
                    news.setUpdateTime(DateUtil.currDateFormat(null));
                    news.setUpdateUserId(senderId);
                    news.setRecordFlag(recordFlag == null ? Constants.INT_NORMAL : recordFlag);
                    newsService.update(news);
                }
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                e.printStackTrace();
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据Id更新信息标识
     * @param recordId 信息ID
     * @param recordFlag 信息标识
     * @return
     */
    public Map<String, Object> updateFlagById(String recordId, Integer recordFlag) {
        ret = null;
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId) || recordFlag == null) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                News news = new News();
                news.setRecordFlag(recordFlag);
                news.setRecordId(recordId);
                newsService.update(news);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据ID更新删除
     * @param recordId 信息ID
     * @return
     */
    public Map<String, Object> deleteById(String recordId) {
        ret = null;
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                News news = new News();
                news.setRecordId(recordId);
                newsService.delete(news);
                ret = this.getSuccessVo("", "");
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 根据ID获得信息
     * @param recordId 消息序号
     * @return
     */
    public Map<String, Object> get(String recordId) {
        ret = null;
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                News news = newsService.getById(recordId);
                String receiverId = StringUtil.trim(news.getReceiverId());
                if (!"".equals(receiverId)) {
                    List<String> ids = Arrays.asList(receiverId.split(","));
                    Integer receiverType = news.getReceiverType();
                    if (receiverType != null) {
                        if (receiverType == Constants.RECEIVER_TYPE_USER) {
                            news.setReceiveUsers(userService.findByIds(ids));
                        }
                        else if(receiverType == Constants.RECEIVER_TYPE_ROLE) {
                            news.setReceiveRoles(roleService.findRoleByIds(ids));
                        }
                    }
                }
                ret = this.getSuccessVo("", news);
            }
            catch (Exception e) {
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }

        return ret;
    }

    /**
     * 检索（模糊匹配名称、备注）
     * @param typeId 类型ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> search(String typeId, Integer isViewed, String viewerId,
                                     String key, int pageNum, int pageSize) {
        ret = null;
        try {
            News news = new News();
            news.setTypeId(StringUtil.trim(typeId));
            news.setIsViewed(isViewed);
            news.setViewerId(StringUtil.trim(viewerId));
            news.setKey(StringUtil.trim(key));
            news.setRecordFlag(Constants.INT_NORMAL);
            PageInfo<News> pageInfo = newsService.search(news, pageNum, pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 检索发送信息（模糊匹配名称、备注）
     * @param id 发送信息ID
     * @param newsId 消息ID
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @param status 状态(0, 未查看; 1, 已查看)
     * @param key 关键字
     * @param flag 信息标识(1,正常;0,删除;)
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Map<String,Object> searchSendRecord(String id, String newsId, String senderId, String receiverId,
                                               Integer status, String key, Integer flag, int pageNum, int pageSize) {
        ret = null;
        try {
            NewsSendRecord record = new NewsSendRecord();
            record.setRecordId(StringUtil.trim(id));
            record.setNewsId(StringUtil.trim(newsId));
            record.setSenderId(StringUtil.trim(senderId));
            record.setReceiverId(StringUtil.trim(receiverId));
            if (status != null) {
                record.setStatus(status);
            }
            record.setRecordFlag(flag == null ? Constants.INT_NORMAL : flag);
            record.setKey(StringUtil.trim(key));
            PageInfo<NewsSendRecord> pageInfo = newsService.searchNewsSendRecord(record, pageNum, pageSize);
            ret = this.getPageInfo(pageInfo);
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 邮件发送测试
     * @return
     */
    public Map<String, Object> sendMail() {
        ret = null;
        try {
            mailService.sendSimpleMail();
            ret = this.getSuccessVo("", "");
        }
        catch(Exception e) {
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }

    /**
     * 查看消息
     * @param recordId 接收信息记录(NewsSendRecord)的唯一标识
     * @return
     */
    public Map<String, Object> viewNews(String recordId) {
        ret = null;
        recordId = StringUtil.trim(recordId);
        if ("".equals(recordId)) {
            ret = this.getParamErrorVo();
        }
        else {
            try {
                NewsSendRecord sendRecord = new NewsSendRecord();
                sendRecord.setRecordId(recordId);
                sendRecord.setStatus(Constants.VIEWED);
                sendRecord.setViewTime(DateUtil.currDateFormat(null));
                newsService.updateNewsSendRecord(sendRecord);
                ret = this.getSuccessVo("", "");
            } catch (Exception e) {
                e.printStackTrace();
                loggerError(ThreadUtil.getCurrentFullMethodName(), e);
                ret = this.getErrorVo();
            }
        }
        return ret;
    }

    /**
     * 获得(当前)用户获得最新的消息通告
     * @return
     */
    public Map<String, Object> getCurrUserReceivedNewestNews() {
        try {
            News news = newsService.getUserReceivedNewestNews(this.getUserIdOfSecurity());
            ret = this.getSuccessVo("", news);
        } catch (Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
