package com.oa.platform.service;

import com.oa.platform.entity.News;

public interface JSMSService {
	/**
	 * 根据用户名发送短信验证码
	 * @param userName 用户名
	 */
	void sendValidateSMS(String params);
	/**
	 * 根据接收人(多人用,隔开)发送消息通知短信
	 * @param receiverId 接收人
	 * @param news 通知通告
	 */
	void getNoticeSMS(String receiverId,News news);
	/**
	 * 短信验证码校验
	 * @param userName 用户名
	 * @param smsCode 验证码
	 * @return
	 */
	boolean ValidateSMS(String userName,String smsCode);
}
