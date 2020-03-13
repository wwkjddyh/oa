package com.oa.platform.service;

import com.oa.platform.entity.News;
import com.oa.platform.entity.NewsSendRecord;

import java.util.List;
import java.util.Map;

/**
 * 邮件发送
 * @author jianbo.feng
 * @date 2020/03/13
 */
public interface MailService {

    /**
     * 发送简单邮件
     * @return
     */
    Map<String, Object> sendSimpleMail();

    /**
     * 发送带附件邮件
     * @return
     */
    Map<String, Object> sendAttachmentsMail();

    /**
     * 包含静态html内容发送
     * @return
     * @throws Exception
     * @description 这里需要注意的是addInline函数中资源名称meinv需要与正文中cid:meinv对应起来
     */
    Map<String, Object> sendInlineMail();

    /**
     * 包含静态html内容发送
     * @return
     * @throws Exception
     */
    Map<String, Object> sendExcel();

    /**
     * 根据模版发送邮件
     * @return
     * @throws Exception
     */
    Map<String, Object> sendTemplateMail();

    /**
     * 发送消息</br>
     * 若成功，则返回状态码：200; 以及各记录发送统计信息，成功和失败信息列表;
     * @param records 发送信息列表
     * @return
     */
    Map<String, Object> sendNews(List<NewsSendRecord> records);
}
