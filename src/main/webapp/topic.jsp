<%@ page language="java" import="com.mq.rabbit.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>topic exchange</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

</head>

<body>

routingKey：<input id="routingkey" value="com.mq.rabbit.log.error" />
<button onclick="pushClick()">推送消息</button>
<br>

<%
topicExchange.consumer("cn.mq.rabbit.*");
topicExchange.consumer("cn.mq.rabbit.#");

topicExchange.consumer("cn.mq.*");
topicExchange.consumer("cn.mq.#");

topicExchange.consumer("*.error");
topicExchange.consumer("#.error");
topicExchange.consumer("*");
topicExchange.consumer("#");

topicExchange.publisher("cn.mq.rabbit.error");
 %>



</body> 
</html>