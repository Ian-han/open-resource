package com.online.taxi.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author 马士兵教育:chaopengfei
 * @date 2020/12/22
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@GetMapping("/lock")
	public String redis(){
		String key = "order";
		ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
		stringStringValueOperations.setIfAbsent("key","value",30L,TimeUnit.SECONDS);
		// watch dog()
		// 挂了



		// 业务逻辑，机器停电了。2000s




		return "fail";

	}

}
