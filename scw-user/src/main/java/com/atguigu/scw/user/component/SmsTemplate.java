package com.atguigu.scw.user.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.atguigu.scw.vo.resp.AppResponse;

@Component
public class SmsTemplate {

	@Value("${sms.host}")
	String host;
	
	@Value("${sms.path}")
	String path;
	@Value("${sms.method}")
	String method;
	@Value("${sms.appcode}")
	String appcode;
	
	
	public AppResponse<String> sendSms(Map<String, String> bodys) {
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	   
		/*
		 * bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
		 * bodys.put("channel", "0"); bodys.put("mobile", "+12046985843");
		 * bodys.put("templateID", "0000000"); bodys.put("templateParamSet", "1234, 1");
		 */
	    try {
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	return AppResponse.ok(response.toString());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return AppResponse.fail(null);
	    }
	}
}
