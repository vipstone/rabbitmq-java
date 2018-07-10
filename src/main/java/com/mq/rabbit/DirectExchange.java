package com.mq.rabbit;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;

public class directExchange {

	/**
	 * 推送消息
	 */
	public static void publisher() throws IOException, TimeoutException {
		// 创建一个连接
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		// 创建通道
		Channel channel = conn.createChannel();
		// 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
		channel.queueDeclare(config.QueueName, false, false, false, null);

		for (int i = 0; i < 10; i++) {
			String message = String.format("当前时间：%s", new Date().getTime());
			// 发送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-路由的headers信息；参数四：消息主体】
			channel.basicPublish("", config.QueueName, null, message.getBytes("UTF-8"));
			System.out.println("发送消息 => " + message);
		}

		// 关闭连接
		channel.close();
		conn.close();
	}

	/**
	 * 消费消息
	 */
	public static void consumer(String workName) throws IOException, TimeoutException, InterruptedException {
		// 创建一个连接
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		// 创建通道
		Channel channel = conn.createChannel();
		// 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
		channel.queueDeclare(config.QueueName, false, false, false, null);

		// 创建订阅器
		Consumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				// String routingKey = envelope.getRoutingKey(); // 队列名称
				// String contentType = properties.getContentType(); // 内容类型
				String content = new String(body, "utf-8"); // 消息正文
				System.out.println(workName + "收到消息 => " + content);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					channel.basicAck(envelope.getDeliveryTag(), false); // 手动确认消息【参数说明：参数一：该消息的index；参数二：是否批量应答，true批量确认小于index的消息】
				}

			}
		};

		// 接受消息
		channel.basicConsume(config.QueueName, false, "", defaultConsumer);
	}

}
