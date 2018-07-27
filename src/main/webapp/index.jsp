<%@page import="com.mq.rabbit.directExchange"%>
<%@ page import="com.mq.rabbit.transactionExample"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>

	消息
	<br>

	<%
		directExchange.publisher();
	%>

<script>
	
</script>

</body>
</html>
