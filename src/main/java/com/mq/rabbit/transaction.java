package com.mq.rabbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ事物demo
 * 
 * @author stone
 *
 */
public class transaction {

	private static final String _queueName = "rabbitqueue"; // 队列名称

	public static void main(String[] args)
			throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {

		// 发送消息
		send();

	}

	/**
	 * 发生消息
	 * 
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private static void send()
			throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		// 创建连接
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		// 创建信道
		Channel channel = conn.createChannel();
		// 声明队列
		channel.queueDeclare(_queueName, false, false, false, null);
		String message = String.format("时间 => %s", new Date().getTime());
		// 发送消息
		channel.basicPublish("", _queueName, null, message.getBytes("UTF-8"));
		System.out.println("发送内容：" + message);
		channel.close();
		conn.close();
	}

	private static void consume() throws IOException, TimeoutException {
		// 创建连接
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		// 创建信道
		Channel channel = conn.createChannel();
		// 声明队列
		channel.queueDeclare(_queueName, false, false, false, null);
		// 声明使用事物
		channel.txSelect();
		// channel.txCommit();
		// channel.txRollback();

	}

}
