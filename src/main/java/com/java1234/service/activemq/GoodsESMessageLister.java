package com.java1234.service.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听器，消息的消费者
 * @author back
 */
public class GoodsESMessageLister implements MessageListener{

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println("=======商品添加成功,从消息队列中读取同步索引请求================"+textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
