package com.oa.platform.biz;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oa.platform.entity.Res;
import com.oa.platform.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oa.platform.common.Constants;
import com.oa.platform.entity.Mail;
import com.oa.platform.entity.News;
import com.oa.platform.entity.NewsSendRecord;
import com.oa.platform.util.DateUtil;
import com.oa.platform.util.StringUtil;
import com.oa.platform.util.ThreadUtil;

import javax.annotation.Resource;

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

    @Resource
    private ResService resService;

    //短信权限码
    @Value("${phoneCode.appKey}")
	private String appKey;
    //短信权限码
    @Value("${phoneCode.masterSecret}")
	private String masterSecret;
    //url
    @Value("${phoneCode.mutiModelSmsUrl}")
	private String mutiModelSmsUrl;
    //模板id
    @Value("${phoneCode.muti_model_temp_id}")
	private String muti_model_temp_id;

    /**
     * 保存信息
     * @param news 消息
     * @return
     */
    public Map<String, Object> save(News news,String senderUserName) {
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

                    // 保存上传图片信息
                    String[] attaFileNames = StringUtil.trim(news.getFileName()).split(",");
                    String[] attaFileUrls = StringUtil.trim(news.getFileUrl()).split(",");
                    int attaCount = attaFileNames.length;
                    if (attaCount != 0 && attaCount == attaFileUrls.length) {
                        for (int i = 0; i < attaCount; i ++) {
                            String aFileName = attaFileNames[i];
                            String aFileUrl = attaFileUrls[i];
                            
                            Res res = new Res();
                            res.setRecordId(StringUtil.getRandomUUID());
                            res.setResName(aFileName);
                            // 分类：消息附件
                            res.setTypeId("4bfeaaa6-054f-4c1c-9399-17c957d32b09");
                            res.setAccessUrl(aFileUrl);
                            res.setAssId(newsId);
                            res.setAssTypeId("message");
                            res.setOriginalName(aFileName);
                            res.setCurrName(aFileUrl.substring(aFileUrl.lastIndexOf(File.separator) + 1));
                            res.setRecordFlag(Constants.INT_NORMAL);
                            //res.setRecordTime(DateUtil.currDateFormat(null));
                            res.setAnnouncerId(senderId);
                            resService.save(res);
                        }
                    }


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
                        //发邮件
                        if(news.getSendMail() == 1) {
	                        List<String> sendToList = newsService.getMailByUserIds(receiverId);
	                        List<String> sendMail = newsService.getMailByUserIds(senderId);
	                        if(sendToList != null && sendToList.size() > 0 && sendMail != null 
	                        		&& sendMail.size() > 0) {
		                        Mail mail = new Mail();
		                        mail.setSubject(news.getTitle());
		                        mail.setContent(news.getContent());
		                        mail.setSendTo(sendToList.toArray(new String[sendToList.size()]));
		                        mail.setForm("tjzjdw@126.com");
		                        mail.setFileUrl(news.getFileUrl());
		                        mail.setFileName(news.getFileName());
		                        mailService.sendSimpleMail(mail);
	                        }
                        }
                        //发短信
                        if(news.getSendSms() == 1) {
                        	List<String> phoneList = newsService.getPhoneByUserIds(receiverId);
                        	if(phoneList != null && phoneList.size() > 0 ) {
	                        	Map<String,Object> params = new HashMap<String,Object>();
	                        	params.put("temp_id", muti_model_temp_id);
	                        	List<Map<String,Object>> recipients = new ArrayList<Map<String,Object>>();
	                        	for (String phone : phoneList) {
	                        		Map<String,Object> receiveParam = new HashMap<String,Object>();
	                        		receiveParam.put("mobile", phone);
	                        		Map<String,String> temp_para = new HashMap<String,String>();
	                        		temp_para.put("userName", senderUserName);
	                        		temp_para.put("announceSubject", news.getTitle());
	                        		receiveParam.put("temp_para", temp_para);
	                        		recipients.add(receiveParam);
								}
	                        	params.put("recipients", recipients);
	                        	roleService.sendAnnounceSMS(mutiModelSmsUrl, JSONObject.toJSONString(params), appKey, masterSecret);
                        	}
                        }
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
            //mailService.sendSimpleMail();
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
            List<News> newsList = newsService.getUserReceivedNewestNews(this.getUserIdOfSecurity());
            ret = this.getSuccessVo("", newsList);
        } catch (Exception e) {
            e.printStackTrace();
            loggerError(ThreadUtil.getCurrentFullMethodName(), e);
            ret = this.getErrorVo();
        }
        return ret;
    }
}
