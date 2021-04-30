package com.atguigu.scw.project.service;

import java.util.List;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.vo.resp.ProjectVo;

public interface TProjectService {

	void save(String accessToken, String projectToken, byte code);

	TProject getTProject(Integer projectId);

	List<ProjectVo> getProjectList();

	List<TReturn> getReturnList(Integer projectId);

	List<TProjectImages> getProjectImageList(Integer projectId);

	TProjectInitiator getTProjectInitiator(Integer id);

}
