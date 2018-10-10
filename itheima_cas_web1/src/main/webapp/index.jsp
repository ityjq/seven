<%--
  Created by IntelliJ IDEA.
  User: lj520
  Date: 2018/9/25
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title1</title>
</head>
<body>
欢迎来到CAS_WEB1工程:<%=request.getRemoteUser()%>
<br/>
<a href="http://localhost:9100/cas/logout">退出登录</a>
</body>
</html>
