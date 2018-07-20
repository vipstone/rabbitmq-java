<%@ page import="com.mq.rabbit.transactionExample"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>

	消息
	<br>

	<%
		transactionExample.publish();
		transactionExample.consume();
	%>

<script>
	
</script>

</body>
</html>
