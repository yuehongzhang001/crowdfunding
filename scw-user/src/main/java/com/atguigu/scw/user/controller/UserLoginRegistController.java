package com.atguigu.scw.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.enums.AccttypeEnume;
import com.atguigu.scw.enums.AuthEnume;
import com.atguigu.scw.enums.UserTypeEnume;
import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.component.SmsTemplate;
import com.atguigu.scw.user.service.TMemberService;
import com.atguigu.scw.user.vo.req.UserRegistVo;
import com.atguigu.scw.user.vo.resp.UserRespVo;
import com.atguigu.scw.vo.resp.AppResponse;
import com.netflix.discovery.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "Login/register Module")
@RequestMapping("/user")
@RestController
public class UserLoginRegistController {
	@Autowired
	TMemberService memberService;
	
	@Value("${sms.validity}")
	Integer validity;
	
	@Autowired
	SmsTemplate smsTemplate;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@ApiOperation(value="User login") 
	@ApiImplicitParams(value={
			@ApiImplicitParam(value="login account",name="loginacct"),
			@ApiImplicitParam(value="user password",name="password")
	})
	@PostMapping("/login")
	public AppResponse<UserRespVo> login(String loginacct,String password){
		log.debug("LoginForm data: loginacct={}",loginacct);
		log.debug("LoginForm data: password={}",password);
		try {
			UserRespVo respVo = memberService.getUserByLogin(loginacct,password);
			log.debug("Login Success: loginacct={}",loginacct);
			return AppResponse.ok(respVo);
		} catch (Exception e) {
			log.debug("Login failed: loginacct={}-{}",loginacct,e.getMessage());
			AppResponse<UserRespVo> fail = AppResponse.fail(null);
			fail.setMsg(e.getMessage());
			return fail;
		}
	} 
	
	@ApiOperation(value="User register") 
	@PostMapping("/register")
	public AppResponse<Object> register(UserRegistVo vo){
		String loginacct = vo.getLoginacct();
		//check if the loginacct empty
		if(StringUtils.isEmpty(loginacct)) {
			AppResponse<Object> resp = AppResponse.fail(null);
			resp.setMsg("Login account can't be empty!");
			return resp;
		}
		//get the code from redis cache and check if it expired
		String code = stringRedisTemplate.opsForValue().get(loginacct);
		if(StringUtils.isEmpty(code)) {
			AppResponse<Object> resp = AppResponse.fail(null);
			resp.setMsg("Verifying code has expired, please send it again!");
			return resp;
		}
		//check if the code correct
		if(!code.equals(vo.getCode())) {
			AppResponse<Object> resp = AppResponse.fail(null);
			resp.setMsg("Verifying code not correct!");
			return resp;
		}
		//check if the loginacct already exists
		boolean loginacctExist=memberService.loginacctExist(vo);
		if(loginacctExist) {
			AppResponse<Object> resp = AppResponse.fail(null);
			resp.setMsg("Login account already exists!");
			return resp;
		}
		//check if the email already exists
		boolean emailExist=memberService.emailExist(vo);
		if(emailExist) {
			AppResponse<Object> resp = AppResponse.fail(null);
			resp.setMsg("Email already exists!");
			return resp;
		}
		
		//save member
		int result = memberService.saveTMember(vo);
		stringRedisTemplate.delete(loginacct);
		if(result!=1) {
			return AppResponse.fail(null);
		}
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value="Send SMS") 
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(String loginaact){
		StringBuilder code = new StringBuilder();
		for(int i=0;i<4;i++) {
			code.append(new Random().nextInt(10));
		}
		
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("channel", "0"); bodys.put("mobile", loginaact);
		bodys.put("templateID", "0000000"); bodys.put("templateParamSet", code+", "+validity);
		smsTemplate.sendSms(bodys);
		
		stringRedisTemplate.opsForValue().set(loginaact, code.toString(),validity,TimeUnit.MINUTES);
		log.debug("Sms is send successfuly-code:{}"+code.toString());
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="verify sms code") 
	@PostMapping("/valide")
	public AppResponse<Object> valide(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="rest password") 
	@PostMapping("/reset")
	public AppResponse<Object> reset(){
		return AppResponse.ok("ok");
	} 
	
	

}
