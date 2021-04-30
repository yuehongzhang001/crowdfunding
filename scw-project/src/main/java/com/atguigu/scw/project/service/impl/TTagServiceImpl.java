package com.atguigu.scw.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.project.bean.TTag;
import com.atguigu.scw.project.mapper.TTagMapper;
import com.atguigu.scw.project.service.TTagService;

@Service
public class TTagServiceImpl implements TTagService {

	@Autowired
	TTagMapper tagMapper;
	@Override
	public List<TTag> getAllProjectTags() {
		// TODO Auto-generated method stub
		return tagMapper.selectByExample(null);
	}

}
