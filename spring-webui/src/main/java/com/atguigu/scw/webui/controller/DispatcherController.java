package com.atguigu.scw.webui.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.service.TMemberServiceFeign;
import com.atguigu.scw.webui.service.TProjectServiceFeign;
import com.atguigu.scw.webui.vo.resp.ProjectVo;
import com.atguigu.scw.webui.vo.resp.UserRespVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DispatcherController {

	@Autowired
	TProjectServiceFeign projectServiceFeign;

	@Autowired
	RedisTemplate<String, ProjectVo> redisTemplate;

	@Autowired
	TMemberServiceFeign memberServiceFeign;

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null) {
			session.removeAttribute("loginMember");
			session.invalidate();
		}
		return "redirect:/index";
	}

	@RequestMapping("/doLogin")
	public String doLogin(String loginacct, String userpwd, HttpSession session) {
		AppResponse<UserRespVo> resp = memberServiceFeign.login(loginacct, userpwd);

		UserRespVo data = resp.getData();
		if (data == null) {
			log.error("Data not got!");
			return "login";
		}
		log.error("Data got!");
		session.setAttribute("loginMember", data);
		return "redirect:index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/index")
	public String index(Model model) {

		// fetch data from redis
		
		List<ProjectVo> data = redisTemplate.opsForList().range("topProjects", 0, 3);
		log.debug("Get data from redis:{}",data);
		if (data == null || data.isEmpty()) {// if not in reids, them fetch it from remote project service.
			AppResponse<List<ProjectVo>> resp = projectServiceFeign.getProjectList();
			data = resp.getData();
			log.debug("Get data from remote project service:{}",data);
			redisTemplate.opsForList().rightPushAll("topProjects", data);
		}
		model.addAttribute("topProjects", data);// data put in model
		return "index";
	}

}
