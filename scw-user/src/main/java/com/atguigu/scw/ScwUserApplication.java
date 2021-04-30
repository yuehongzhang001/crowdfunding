package com.atguigu.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@MapperScan("com.atguigu.scw.user.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class ScwUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwUserApplication.class, args);
	}

}
