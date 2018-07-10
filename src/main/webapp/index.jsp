<%@ page import="com.mq.rabbit.DirectExchange"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>

	消息测试
	<br>

	<%
		DirectExchange.consumer("客户端1");
		DirectExchange.consumer("客户端2");
	%>

	<button onclick="<%DirectExchange.publisher();%>">推送消息</button>


</body>
</html>
