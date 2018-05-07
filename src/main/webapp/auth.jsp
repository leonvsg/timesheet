<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 07.05.2018
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Authentication</title>
</head>
<body>
<%=("authenticationFail".equals(request.getParameter("errorMessage")))?"Authentication failed. Invalid login/password":""%>
<br>
<%=("authenticateReq".equals(request.getParameter("errorMessage")))?"Authentication Required":""%>
<br>
<form action="${pageContext.request.contextPath}/auth" method="post">
    <input type="text" placeholder="Type login" name="login"><br>
    <input type="text" placeholder="Type password" name="password"><br>
    <input type="submit" value="Enter">
</form>
</body>
</html>
