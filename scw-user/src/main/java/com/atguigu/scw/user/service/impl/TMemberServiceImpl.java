package com.atguigu.scw.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.javassist.compiler.ast.Member;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.scw.enums.UserExceptionEnum;
import com.atguigu.scw.exp.UserException;
import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberExample;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.atguigu.scw.user.service.TMemberService;
import com.atguigu.scw.user.vo.req.UserRegistVo;
import com.atguigu.scw.user.vo.resp.UserRespVo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional(readOnly = true)
@Service
public class TMemberServiceImpl implements TMemberService {

	@Autowired
	TMemberMapper memberMapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Transactional
	@Override
	public int saveTMember(UserRegistVo vo) {
		try {
			TMember record=new TMember();
			BeanUtils.copyProperties(vo, record);
			record.setUsername(vo.getLoginacct());
			
			//encode the password
			String userpswd = vo.getUserpswd();
			BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
			record.setUserpswd(encoder.encode(userpswd));
			
			//set authentication status:0-unauthenticated,1-authenticated
			record.setAuthstatus("0");
			int result = memberMapper.insertSelective(record);
			log.debug("Member register succeed!-{}",record);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Member register failed!-{}",e.getMessage());
			throw new UserException(UserExceptionEnum.USER_SAVE_ERROR);
		}
	}

	@Override
	public boolean loginacctExist(UserRegistVo vo) {
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(vo.getLoginacct());
		List<TMember> list = memberMapper.selectByExample(example);
		if(list==null || list.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public boolean emailExist(UserRegistVo vo) {
		TMemberExample example = new TMemberExample();
		example.createCriteria().andEmailEqualTo(vo.getEmail());
		List<TMember> list = memberMapper.selectByExample(example);
		if(list==null || list.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public UserRespVo getUserByLogin(String loginacct, String password) {
		UserRespVo userRespVo = new UserRespVo();
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		List<TMember> list = memberMapper.selectByExample(example);
		if(list==null || list.size()!=1) {
			throw new UserException(UserExceptionEnum.USER_UNEXITS);
		}
		TMember member = list.get(0);
		
		//check if the password correct
		BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
		if(!encoder.matches(password, member.getUserpswd())) {
			throw new UserException(UserExceptionEnum.USER_PASSWORD_ERROR);
		}
		
		//set the userRespVo by copying data from member
		BeanUtils.copyProperties(member, userRespVo);
		String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
		userRespVo.setAccessToken(accessToken);
		
		//put the accessToken and member real id into redis system
		stringRedisTemplate.opsForValue().set(accessToken, member.getId().toString());
		return userRespVo;
	}

}
