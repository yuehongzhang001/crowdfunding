package com.atguigu.scw.webui.service.exp.handler;

import org.springframework.stereotype.Component;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.service.TMemberServiceFeign;
import com.atguigu.scw.webui.vo.resp.UserRespVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TMemberServiceFeignExceptionHandler implements TMemberServiceFeign{

	@Override
	public AppResponse<UserRespVo> login(String loginacct, String password) {
		AppResponse<UserRespVo> fail = AppResponse.fail(null);
		fail.setMsg("Calling remote service[login] failed!");
		log.error("Calling remote service[login] failed!");
		return fail;
	}

	
}
