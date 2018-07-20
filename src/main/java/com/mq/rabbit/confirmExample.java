package com.mq.rabbit;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
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
		String message = String.format("时间 => %s", new Date().getTime());

		channel.confirmSelect();
		channel.basicPublish("", config.QueueName, null, message.getBytes("UTF-8"));

		if (channel.waitForConfirms()) {
			System.out.println("发送信息成功：" + message);
		}

	}
}
