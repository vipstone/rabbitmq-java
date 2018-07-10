package com.mq.rabbit;

/**
 * 程序入口
 * 
 * @author stone
 *
 */
public class App {

	public static void main(String[] args) {
		// 1、direct 交换器实现 => 请运行DirctExchange.java
		// TODO:其他路由模式实现
		DirectExchange.Publisher();
		System.out.println(" hello world ");
	}

}
