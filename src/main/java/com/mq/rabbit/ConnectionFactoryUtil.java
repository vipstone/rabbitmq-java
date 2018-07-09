package com.mq.rabbit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 获取RabbitMQ连接
 * 
 * @author stone
 *
 */
public class ConnectionFactoryUtil {

	// 获取rabbit连接
	public static Connection GetRabbitConnection() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(Config.UserName);
		factory.setPassword(Config.Password);
		factory.setVirtualHost(Config.VHost);
		factory.setHost(Config.Host);
		factory.setPort(Config.Port);
		Connection conn = null;
		try {
			// // 高级连接使用线程池
			// ExecutorService es = Executors.newFixedThreadPool(20);
			// conn = factory.newConnection(es);
			conn = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 获取rabbit连接（方式二）
	public static Connection GetRabbitConnection2() {
		ConnectionFactory factory = new ConnectionFactory();
		// 连接格式：amqp://userName:password@hostName:portNumber/virtualHost
		String uri = String.format("amqp://%s:%s@%s:%d%s", Config.UserName, Config.Password, Config.Host, Config.Port,
				Config.VHost);
		Connection conn = null;
		try {
			factory.setUri(uri);
			factory.setVirtualHost(Config.VHost);
			conn = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
