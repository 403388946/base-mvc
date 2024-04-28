package com.base;

import com.alibaba.fastjson.JSON;
import com.base.cache.service.CacheService;
import com.base.cache.service.CacheTService;
import com.base.shiro.mapper.UserMapper;
import com.base.shiro.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:spring/spring-context.xml", 
		"classpath:spring/spring-mvc.xml"})
@Transactional
public class BaseJunit4Test {


	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private CacheTService<User> cacheTService;

	@Test
	public void testTkMapper() {
		//这样会报错
		User user = userMapper.findUserByName("admin");
		cacheService.add("12343553", user);
		User user1 = cacheTService.get("12343553");
		System.out.println(JSON.toJSONString(user1));
	}
}