<%@ page import="com.mq.rabbit.transaction"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>

	消息
	<br>

	<%
		transaction.send();
		transaction.consume();
	%>

<script>
	
</script>

</body>
</html>
