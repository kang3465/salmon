package cn.ele.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.Map;

/**
 * JMS工具类
 * @author Administrator
 *
 */
@Component
public class JmsUtil {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送消息
	 * @param destination 发送到哪一个队列或者广播中
	 * @param map 需要发送的消息(Map集合)
	 */
	public void send(Destination destination, final Map<String ,String> map){
		System.out.println("---------------生产者发送消息-----------------");   
		jmsTemplate.send(destination, new MessageCreator() {			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				MapMessage mapMessage = session.createMapMessage();
				
				for(String key:map.keySet()){
					mapMessage.setString(key, map.get(key));			
				}				
				
				return mapMessage;
			}			
		} );			
	}
	
	
	/**
	 * 发送消息（文本）
	 * @param destination
	 * @param text
	 */
	public void send(Destination destination, final String text){
		System.out.println("---------------生产者发送消息(Text)-----------------");   
		jmsTemplate.send(destination, new MessageCreator() {			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}			
		} );			
	}
	
}
