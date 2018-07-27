package com.mq.rabbit;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 发送方确认模式
 * 
 * @author stone
 *
 */
public class confirmExample {

	public static void publish() throws IOException, TimeoutException, InterruptedException {
		// 创建连接
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(config.UserName);
		factory.setPassword(config.Password);
		factory.setVirtualHost(config.VHost);
		factory.setHost(config.Host);
		factory.setPort(config.Port);
		Connection conn = factory.newConnection();
		// 创建信道
		Channel channel = conn.createChannel();
		// 声明队列
		channel.queueDeclare(config.QueueName, false, false, false, null);

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 10000; i++) {
			// 开启发送方确认模式
			channel.confirmSelect();
			String message = String.format("时间 => %s", new Date().getTime());
			channel.basicPublish("", config.QueueName, null, message.getBytes("UTF-8"));
		}

		channel.addConfirmListener(new ConfirmListener() {

			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
//				System.out.println("未确认消息，标识：" + deliveryTag);
			}

			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
//				System.out.println(String.format("已确认消息，标识：%d，多个消息：%b", deliveryTag, multiple));
			}
		});

		long endTime = System.currentTimeMillis();

		System.out.println("执行花费时间：" + (endTime - startTime) + "s");

		// //方式一：普通Confirm模式
		// if (channel.waitForConfirms()) {
		// System.out.println("消息发送成功");
		// }

		// 方式二：批量确认模式
		// channel.waitForConfirmsOrDie(); // 直到所有信息都发布，只要有一个未确认就会IOException
		// System.out.println("全部执行完成");

		// 方式三：异步确认模式
		// channel.addConfirmListener(new ConfirmListener() {
		//
		// @Override
		// public void handleNack(long deliveryTag, boolean multiple) throws
		// IOException {
		// System.out.println("未确认消息，标识：" + deliveryTag);
		// }
		//
		// @Override
		// public void handleAck(long deliveryTag, boolean multiple) throws
		// IOException {
		// System.out.println(String.format("已确认消息，标识：%d，多个消息：%b", deliveryTag,
		// multiple));
		// }
		// });
		//
		// System.out.println("程序执行完成：" + new Date().getTime());

	}
}
