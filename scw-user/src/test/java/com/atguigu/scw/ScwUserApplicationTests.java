package com.atguigu.scw;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class ScwUserApplicationTests {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	void contextLoads() throws SQLException {
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		connection.close();
	}

	@Test
	void test1() {
		stringRedisTemplate.opsForValue().set("k1", "v1");
	}
}
