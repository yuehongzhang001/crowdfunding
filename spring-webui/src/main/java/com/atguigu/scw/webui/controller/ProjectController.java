package com.atguigu.scw.webui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.service.TProjectServiceFeign;
import com.atguigu.scw.webui.vo.resp.ProjectDetailVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	TProjectServiceFeign projectServiceFeign;
	@RequestMapping("/projectInfo")
	public String projectInfo(Integer id, Model model) {
		//call remote project service to get projectDetailVo
		AppResponse<ProjectDetailVo> resp = projectServiceFeign.getProjectDetail(id);
		//put vo into model
		ProjectDetailVo data=resp.getData();
		model.addAttribute("projectDetail", data);
		log.debug("projectDetail:{}",data);
		return "project/index";
	}
}
