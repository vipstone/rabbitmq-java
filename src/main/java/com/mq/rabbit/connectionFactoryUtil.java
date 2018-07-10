package com.mq.rabbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class connectionFactoryUtil {

	// 获取rabbit连接
	public static Connection GetRabbitConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(config.UserName);
		factory.setPassword(config.Password);
		factory.setVirtualHost(config.VHost);
		factory.setHost(config.Host);
		factory.setPort(config.Port);
		// // 高级连接使用线程池
		// ExecutorService es = Executors.newFixedThreadPool(20);
		// conn = factory.newConnection(es);
		Connection conn = factory.newConnection();
		return conn;
	}

	// 获取rabbit连接（方式二）
	public static Connection GetRabbitConnection2()
			throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		// 连接格式：amqp://userName:password@hostName:portNumber/virtualHost
		String uri = String.format("amqp://%s:%s@%s:%d%s", config.UserName, config.Password, config.Host, config.Port,
				config.VHost);
		factory.setUri(uri);
		factory.setVirtualHost(config.VHost);
		Connection conn = factory.newConnection();
		return conn;
	}
}
