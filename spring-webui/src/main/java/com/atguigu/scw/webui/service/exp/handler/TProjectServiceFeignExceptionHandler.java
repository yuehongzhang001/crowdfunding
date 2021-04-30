package com.atguigu.scw.webui.service.exp.handler;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.service.TProjectServiceFeign;
import com.atguigu.scw.webui.vo.resp.ProjectDetailVo;
import com.atguigu.scw.webui.vo.resp.ProjectVo;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class TProjectServiceFeignExceptionHandler implements TProjectServiceFeign {

	@Override
	public AppResponse<List<ProjectVo>> getProjectList() {
		AppResponse<List<ProjectVo>> fail=AppResponse.fail(null);
		fail.setMsg("Calling remote service[getProjectList] failed!");
		log.error("Calling remote service[getProjectList] failed!");
		return fail;
	}

	@Override
	public AppResponse<ProjectDetailVo> getProjectDetail(@PathVariable("projectId")Integer projectId) {
		AppResponse<ProjectDetailVo> fail=AppResponse.fail(null);
		fail.setMsg("Calling remote service[getProjectDetail] failed!");
		log.error("Calling remote service[getProjectDetail] failed!");
		return fail;
	}

}
