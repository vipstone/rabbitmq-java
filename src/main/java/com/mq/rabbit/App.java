package com.mq.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 程序入口
 * 
 * @author stone
 *
 */
public class App {

	public static void main(String[] args) throws IOException, TimeoutException {
		// 1、direct 交换器实现 => 请运行DirctExchange.java
		// TODO:其他路由模式实现
		directExchange.publisher();
	}

}
