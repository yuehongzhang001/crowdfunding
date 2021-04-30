package com.atguigu.scw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.user.bean.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Api(tags = "to test the swagger")
@RestController
public class HelloController {
	
	@ApiImplicitParams(value= {
			@ApiImplicitParam(value = "name",name="name",required = true),
			@ApiImplicitParam(value = "age",name="age",required = true)
	})
	@ApiOperation("show api")
	@GetMapping("/hello")
	public String hello(String name,Integer age) {
		return "OK:"+name+"-age:"+age;
	}
	
	@ApiOperation("test api")
	@GetMapping("/test")
	public User getUser() {
		User user = new User();
		user.setId(1);
		user.setUname("Jay");
		return user;
	}
}
