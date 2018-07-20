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
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.MessageProperties;
import com.sun.javafx.binding.StringFormatter;

/**
 * RabbitMQ事物demo
 * 
 * @author stone
 *
 */
public class transactionExample {

	private static final String _queueName = "rabbitqueue"; // 队列名称

	static int timer = 0;

	/**
	 * 发生消息
	 * 
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public static void publish()
			throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		// 创建连接
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		// 创建信道
		Channel channel = conn.createChannel();
		// 声明队列
		channel.queueDeclare(_queueName, true, false, false, null);
		String message = String.format("时间 => %s", new Date().getTime());

		try {
			long startTime = System.nanoTime();
			channel.txSelect(); // 声明事务
			// 发送消息
			channel.basicPublish("", _queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			// int result = 1 / timer;
			channel.txCommit(); // 提交事务
			long endTime = System.nanoTime();
			// System.out.println(String.format("\n---------------------------------------------\n发送内容：%s
			// 消耗时间（非事务）：%d纳秒",
			// message, (endTime - startTime)));

			System.out.println(String.format("\n---------------------------------------------\n发送内容：%s 消耗时间（事务）：%d纳秒",
					message, (endTime - startTime)));

		} catch (Exception e) {
			System.out.println("发送失败，事务回滚");
			++timer;
			channel.txRollback();
			publish();
		} finally {
			channel.close();
			conn.close();
		}
	}

	/**
	 * 消费消息
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws InterruptedException
	 */
	public static void consume() throws IOException, TimeoutException, InterruptedException {

		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare(_queueName, true, false, false, null);
		channel.txSelect();
		GetResponse resp = channel.basicGet(_queueName, false);
		String message = new String(resp.getBody(), "UTF-8");
		System.out.println("收到消息：" + message);
		// // 消息拒绝
		// channel.basicReject(resp.getEnvelope().getDeliveryTag(), true);
		channel.basicAck(resp.getEnvelope().getDeliveryTag(), false); // 消息确认
		channel.txRollback();
		channel.close();
		conn.close();
	}

}
