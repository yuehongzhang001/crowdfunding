package com.atguigu.scw.project.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.enums.ProjectImageTypeEnume;
import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TTag;
import com.atguigu.scw.project.bean.TType;
import com.atguigu.scw.project.service.TProjectService;
import com.atguigu.scw.project.service.TTagService;
import com.atguigu.scw.project.service.TTypeService;
import com.atguigu.scw.project.vo.resp.ProjectDetailVo;
import com.atguigu.scw.project.vo.resp.ProjectVo;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "projectInfo Module")
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectInfoController {

	@Autowired
	TProjectService projectService;
	
	@Autowired
	TTagService tagService;
	
	@Autowired
	TTypeService typeService;
	
	@ApiOperation(value = "[+] Get projectInfo details")
	@GetMapping("/details/info/{projectId}")
	public AppResponse<ProjectDetailVo> getProjectDetail(@PathVariable("projectId")Integer projectId){
		try {
			ProjectDetailVo vo = new ProjectDetailVo();
			//get project
			TProject project = projectService.getTProject(projectId);
			//get TProjectImages list
			List<TProjectImages> imageList = projectService.getProjectImageList(projectId); 
			for (TProjectImages image : imageList) {
				if(image.getImgtype()==ProjectImageTypeEnume.HEADER_IMAGE.getCode()) {
					vo.setHeaderImage(image.getImgurl());
				}else {
					vo.getDetailImageList().add(image.getImgurl());
				}
			}
			//get TReturn list
			List<TReturn> returnlList = projectService.getReturnList(projectId);
			vo.setReturnList(returnlList);
			//get TProjectInitiator
			TProjectInitiator projectInitiator = projectService.getTProjectInitiator(projectId);
			vo.setProjectInitiator(projectInitiator);
			//copy properties to projectDetailVo
			BeanUtils.copyProperties(project, vo);
			
			AppResponse<ProjectDetailVo> ok = AppResponse.ok(vo);
			log.debug("ProjectDetailVo retrived:{}",vo);
			return ok;
		} catch (Exception e) {
			e.printStackTrace();
			AppResponse<ProjectDetailVo> fail = AppResponse.fail(null);
			fail.setMsg(e.getMessage());
			log.debug("ProjectDetailVo retriving failed");
			return fail;
		}
	}
	
	
	@ApiOperation(value = "Get a project list with top 3 projects")
	@GetMapping("/getProjectList")
	public AppResponse<List<ProjectVo>> getProjectList(){
		
		try {
			List<ProjectVo> list =projectService.getProjectList();
			log.debug("ProjectVo list:{}",list);
			AppResponse<List<ProjectVo>> ok = AppResponse.ok(list);
			return ok;
		} catch (Exception e) {
			AppResponse<List<ProjectVo>> fail = AppResponse.fail(null);
			log.debug("ProjectVo list retriving failed");
			return fail;
		}
	}

	
	@GetMapping("/tags")
	public AppResponse<List<TTag>> tags(){
		List<TTag> list = tagService.getAllProjectTags();
		return AppResponse.ok(list);
	}
	
	@GetMapping("/types")
	public AppResponse<List<TType>> types(){
		List<TType> list = typeService.getAllProjectTypes();
		return AppResponse.ok(list);
	}
	
}
