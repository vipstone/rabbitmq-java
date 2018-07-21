package com.mq.rabbit;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

/**
 * 发送方确认模式
 * 
 * @author stone
 *
 */
public class confirmExample {

	public static void publish() throws IOException, TimeoutException, InterruptedException {
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare(config.QueueName, false, false, false, null);
		

		channel.confirmSelect();
		for (int i = 0; i < 10; i++) {
			String message = String.format("时间 => %s", new Date().getTime());
			channel.basicPublish("", config.QueueName, null, message.getBytes("UTF-8"));
		
		}
		
		channel.addConfirmListener(new ConfirmListener() {
			
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("未确认消息，标识："+deliveryTag);
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println( String.format("已确认消息，标识：%d，多个消息：%b",deliveryTag,multiple));
			}
		});
		
		System.out.println("程序执行完成："+new Date().getTime() );

//		if (channel.waitForConfirms()) {
//			System.out.println("信息发送成功：" + message);
//		}
		
//		channel.waitForConfirmsOrDie(1);  //直到所有信息都发布，只要有一个未确认就会IOException
//		System.out.println("全部执行完成");

	}
}
