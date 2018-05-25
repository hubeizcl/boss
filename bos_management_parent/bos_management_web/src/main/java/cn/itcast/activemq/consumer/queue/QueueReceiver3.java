
package cn.itcast.activemq.consumer.queue;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

/**
 * 
 * @description 队列消息监听器
 * 
 */
@Component
public class QueueReceiver3 implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("QueueReceiver3接收到消息:" + ((MapMessage) message).getString("company"));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
