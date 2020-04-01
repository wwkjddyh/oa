package com.oa.platform.service.impl;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oa.platform.entity.News;
import com.oa.platform.service.JSMSService;
@Service
public class JSMSServiceImpl implements JSMSService {
	
	
	//短信权限码
    @Value("${phoneCode.appKey}")
	private String appKey;
    //短信权限码
    @Value("${phoneCode.masterSecret}")
	private String masterSecret;
    //获取验证码url
    @Value("${phoneCode.getSmsUrl}")
	private String smsUrl;
    //验证码校验url
    @Value("${phoneCode.validateSmsUrl}")
	private String validateSmsUrl;
    //模板id
    @Value("${phoneCode.temp_id}")
	private String temp_id;
    
    
	@Override
	@Async("asyncServiceExecutor")
	public void sendValidateSMS(String params) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application","JSON",Charset.forName("UTF-8")));
			headers.setAccept(Arrays.asList(new MediaType[] {new MediaType("application","JSON",Charset.forName("UTF-8"))}));

			HttpEntity<String> requestEntity = new HttpEntity<String>(params,headers);
			HttpMethod post = HttpMethod.POST;
			//ResponseEntity<String> exchange = new ResponseEntity<String>("",HttpStatus.OK);
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
			RestTemplate restTemplate = restTemplateBuilder.basicAuthentication(appKey, masterSecret).build();
			restTemplate.exchange(smsUrl, post,requestEntity,String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Async("asyncServiceExecutor")
	public void getNoticeSMS(String receiverId, News news) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ValidateSMS(String userName, String smsCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
