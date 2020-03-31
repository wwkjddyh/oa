package com.oa.platform.util.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oa.platform.entity.Mail;
import com.oa.platform.service.MailService;
/**
 * 邮件发送线程
 * @author 俞灶森
 *
 */
@Component
public class MailThread implements Runnable{
	
	@Autowired
    private MailService mailService;
	
	private Mail mail;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
