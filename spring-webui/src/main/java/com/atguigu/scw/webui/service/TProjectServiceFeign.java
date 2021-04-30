package com.atguigu.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.service.exp.handler.TProjectServiceFeignExceptionHandler;
import com.atguigu.scw.webui.vo.resp.ProjectDetailVo;
import com.atguigu.scw.webui.vo.resp.ProjectVo;

@FeignClient(value = "SCW-PROJECT", fallback = TProjectServiceFeignExceptionHandler.class)
public interface TProjectServiceFeign {
	
	@GetMapping("/project/getProjectList")
	public AppResponse<List<ProjectVo>> getProjectList();

	@GetMapping("/project/details/info/{projectId}")
	public AppResponse<ProjectDetailVo> getProjectDetail(@PathVariable("projectId")Integer projectId);
}
