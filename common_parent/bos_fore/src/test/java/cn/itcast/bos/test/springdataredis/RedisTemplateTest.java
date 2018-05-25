package cn.itcast.bos.test.springdataredis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTemplateTest {

	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void testRedis(){
		//1.设置key-value
		redisTemplate.opsForValue().set("test2", "value2", 10, TimeUnit.SECONDS);
		//2.获取value
		String value = redisTemplate.opsForValue().get("test2");
		System.out.println(value);
	}
}
