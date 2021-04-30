package com.atguigu.scw.project.service.impl;

import java.util.List;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.enums.ProjectImageTypeEnume;
import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectExample;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectImagesExample;
import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TProjectInitiatorExample;
import com.atguigu.scw.project.bean.TProjectTag;
import com.atguigu.scw.project.bean.TProjectType;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TReturnExample;
import com.atguigu.scw.project.constant.ProjectConstant;
import com.atguigu.scw.project.mapper.TProjectImagesMapper;
import com.atguigu.scw.project.mapper.TProjectInitiatorMapper;
import com.atguigu.scw.project.mapper.TProjectMapper;
import com.atguigu.scw.project.mapper.TProjectTagMapper;
import com.atguigu.scw.project.mapper.TProjectTypeMapper;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.service.TProjectService;
import com.atguigu.scw.project.vo.req.ProjectRedisStorageVo;
import com.atguigu.scw.project.vo.resp.ProjectVo;
import com.atguitu.scw.utils.AppDateUtils;

import io.swagger.annotations.Example;
import lombok.extern.slf4j.Slf4j;

@Transactional(readOnly = true)
@Slf4j
@Service
public class TProjectServiceImpl implements TProjectService {

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	TProjectMapper projectMapper;

	@Autowired
	TProjectImagesMapper projectImagesMapper;
	
	@Autowired
	TReturnMapper returnMapper;
	@Autowired
	TProjectTypeMapper projectTypeMapper;
	@Autowired
	TProjectTagMapper projectTagMapper;
	
	@Autowired
	TProjectInitiatorMapper projectInitiatorMapper;
	@Transactional
	@Override
	public void save(String accessToken, String projectToken, byte code) {
		Integer memberId = Integer.parseInt(stringRedisTemplate.opsForValue().get(accessToken)) ;
		//1.get bigVo from redis
		String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEM_PROJECT_PREFIX+projectToken);
		ProjectRedisStorageVo bigVo = JSON.parseObject(bigStr,ProjectRedisStorageVo.class);
		//2.save project
		TProject project = new TProject();
		project.setName(bigVo.getName());
		project.setRemark(bigVo.getRemark());
		project.setMoney(bigVo.getMoney());
		project.setDay(bigVo.getDay());
		project.setStatus(""+code);
		project.setMemberid(memberId);
		project.setCreatedate(AppDateUtils.getFormatTime());
		
		project.setMemberid(memberId);
		projectMapper.insertSelective(project);
		int projectId = project.getId();
		log.debug("saved project id={}",projectId);
		//3.save image 
		//3.1 save header image
		String headerImageUrl = bigVo.getHeaderImage();
		TProjectImages projectImage = new TProjectImages();
		projectImage.setProjectid(projectId);
		projectImage.setImgurl(headerImageUrl);
		projectImage.setImgtype(ProjectImageTypeEnume.HEADER_IMAGE.getCode());
		projectImagesMapper.insertSelective(projectImage);
		//3.2 save detail images
		List<String> imageList = bigVo.getDetailImages();
		for (String url : imageList) {
			TProjectImages image = new TProjectImages();
			image.setProjectid(projectId);
			image.setImgtype(ProjectImageTypeEnume.DETAIL_IMAGE.getCode());
			image.setImgurl(url);
			projectImagesMapper.insertSelective(image);
		}
		//4.save return
		List<TReturn> retunList = bigVo.getRetunList();
		for (TReturn tReturn : retunList) {
			tReturn.setProjectid(projectId);
			returnMapper.insertSelective(tReturn);
		}
		//5.save project_type
		List<Integer> typeids = bigVo.getTypeids();
		for (Integer typeid : typeids) {
			TProjectType projectType = new TProjectType();
			projectType.setProjectid(projectId);
			projectType.setTypeid(typeid);
			projectTypeMapper.insertSelective(projectType);
		}
		
		//6.save project_tags
		List<Integer> tagids = bigVo.getTagids();
		for (Integer tagid : tagids) {
			TProjectTag projectTag = new TProjectTag();
			projectTag.setProjectid(projectId);
			projectTag.setTagid(tagid);
			projectTagMapper.insertSelective(projectTag);
		}
		//7.save initiator(not included)
		
		
		//8.save company account(not included)
		
		//9. clear redis
		stringRedisTemplate.delete(ProjectConstant.TEM_PROJECT_PREFIX+projectToken);
	}


	@Override
	public TProject getTProject(Integer projectId) {
		
		return projectMapper.selectByPrimaryKey(projectId);
	}

	/**
	 * This is a service method to get top 3 projectVo ordered by followers
	 */
	@Override
	public List<ProjectVo> getProjectList() {
		//list to contain result
		List<ProjectVo> voList = new ArrayStack<ProjectVo>();
		
		//get all top 3 projects.
		TProjectExample example = new TProjectExample();
		example.setOrderByClause("follower DESC LIMIT 3");
		List<TProject> list = projectMapper.selectByExample(example);
		log.debug("TProject list retrived:{}",list);
		
		/*Iterate project list, copy properties in each project and 
		corresponding imageUrl property in ProjectImages to a ProjectVo, then put it in result list*/
		for (TProject project : list) {
			ProjectVo vo = new ProjectVo();
			vo.setId(project.getId());
			vo.setName(project.getName());
			vo.setRemark(project.getRemark());
			vo.setMoney(project.getMoney());
			TProjectImagesExample imagesExample = new TProjectImagesExample();
			imagesExample.createCriteria().andProjectidEqualTo(project.getId()).andImgtypeEqualTo(ProjectImageTypeEnume.HEADER_IMAGE.getCode());
			List<TProjectImages> imageList = projectImagesMapper.selectByExample(imagesExample);
			if(imageList!=null && imageList.size()!=0) {
				vo.setHeaderImageUrl(imageList.get(0).getImgurl());
			}
			voList.add(vo);
		}
	
		return voList;
	}


	@Override
	public List<TReturn> getReturnList(Integer projectId) {
		TReturnExample example = new TReturnExample();
		example.createCriteria().andProjectidEqualTo(projectId);
		return returnMapper.selectByExample(example);
	}


	@Override
	public List<TProjectImages> getProjectImageList(Integer projectId) {
		TProjectImagesExample example = new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(projectId);
		return projectImagesMapper.selectByExample(example);
	}


	@Override
	public TProjectInitiator getTProjectInitiator(Integer projectId) {
		TProjectInitiatorExample example = new TProjectInitiatorExample();
		example.createCriteria().andProjectidEqualTo(projectId);
		List<TProjectInitiator> list = projectInitiatorMapper.selectByExample(example);
		if(list==null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
