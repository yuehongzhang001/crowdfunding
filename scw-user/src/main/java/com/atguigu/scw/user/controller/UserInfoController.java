package com.atguigu.scw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.vo.resp.AppResponse;

@RestController
@RequestMapping("/user/info")
public class UserInfoController {
	
	@GetMapping("/address")
	public AppResponse<Object> address(){
		
		
		
		AppResponse<Object> fail = AppResponse.fail(null);
		
		return fail;
	}
}
