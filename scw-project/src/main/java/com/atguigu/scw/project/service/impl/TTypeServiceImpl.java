package com.atguigu.scw.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.project.bean.TType;
import com.atguigu.scw.project.mapper.TTypeMapper;
import com.atguigu.scw.project.service.TTypeService;

@Service
public class TTypeServiceImpl implements TTypeService {

	@Autowired
	TTypeMapper typeMapper;
	
	@Override
	public List<TType> getAllProjectTypes() {
		return typeMapper.selectByExample(null);
	}

}
