package com.atguigu.scw.webui.config;

import java.net.UnknownHostException;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.atguigu.scw.webui.vo.resp.ProjectVo;

@SpringBootConfiguration
public class RedisTemplateConifg {

	@Bean
	public RedisTemplate<String, ProjectVo> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<String, ProjectVo> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
}
