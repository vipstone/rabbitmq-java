package com.mq.rabbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionFactoryUtil {

	// 获取rabbit连接
	public static Connection GetRabbitConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(Config.UserName);
		factory.setPassword(Config.Password);
		factory.setVirtualHost(Config.VHost);
		factory.setHost(Config.Host);
		factory.setPort(Config.Port);
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
		String uri = String.format("amqp://%s:%s@%s:%d%s", Config.UserName, Config.Password, Config.Host, Config.Port,
				Config.VHost);
		factory.setUri(uri);
		factory.setVirtualHost(Config.VHost);
		Connection conn = factory.newConnection();
		return conn;
	}
}
