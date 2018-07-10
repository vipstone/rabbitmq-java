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

public class fanoutExchange {

	private static final String ExchangeName = "fanoutEc"; // 交换器名称

	/**
	 * 生产消息
	 * 
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public static void publisher() throws IOException, TimeoutException {
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(ExchangeName, "fanout"); // 声明fanout交换器
		String message = "时间：" + new Date().getTime();
		channel.basicPublish(ExchangeName, "", null, message.getBytes("UTF-8"));
		System.out.println("发送消息 => " + message);
		channel.close();
		conn.close();
	}

	/**
	 * 消费消息
	 * 
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public static void consumer(String workName) throws IOException, TimeoutException {
		Connection conn = connectionFactoryUtil.GetRabbitConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(ExchangeName, "fanout"); // 声明fanout交换器
		String queueName = channel.queueDeclare().getQueue(); // 声明队列
		channel.queueBind(queueName, ExchangeName, "");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(workName + "接受消息 => " + message);
			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

}
