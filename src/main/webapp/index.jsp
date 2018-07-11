<%@ page import="com.mq.rabbit.directExchange"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>

	消息测试
	<br>
	
	

	<%
		directExchange.singleConsumer();
		
		//directExchange.publisher();
	%>

<script>
	
</script>

</body>
</html>
