<%@ page import="com.mq.rabbit.fanoutExchange"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>

	消息测试
	<br>

	<%
			fanoutExchange.publisher();
			//fanoutExchange.consumer("work1");
			//fanoutExchange.consumer("work2");
	%>


</body>
</html>
