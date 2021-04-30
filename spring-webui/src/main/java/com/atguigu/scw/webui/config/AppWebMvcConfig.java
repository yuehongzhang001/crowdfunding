package com.atguigu.scw.webui.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableDiscoveryClient
@SpringBootConfiguration
public class AppWebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index").setViewName("index");
	}
}
