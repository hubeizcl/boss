package cn.itcast.bos.test.activemq;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.activemq.producer.queue.QueueSender;
import cn.itcast.activemq.producer.topic.TopicSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ActiveMQSpringTest {
	
	@Resource
	private QueueSender queueSender;
	
	@Resource
	private TopicSender topicSender;

	@Test
	public void testActivemqSpring(){
		//1.使用队列发送字符串
		queueSender.send("test.queue", "这是queue的字符串消息！");
		//2.使用队列发送map
		Map<String, String> map = new HashMap<String,String>();
		map.put("company", "传智播客");
		queueSender.send("test.map", map);
		//3.使用topic发送字符串
		topicSender.send("test.topic", "这是topic的字符串消息");
	}
}
