<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 07.05.2018
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Authentication</title>
</head>
<body>
<c:choose>
    <c:when test="${param.errorMessage.equals(\"authenticationFail\")}">
        Authentication failed. Invalid login/password
    </c:when>
    <c:when test="${param.errorMessage.equals(\"authenticateReq\")}">
        Authentication Required
    </c:when>
</c:choose>
<br>
<form action="${pageContext.request.contextPath}/auth" method="post">
    <input type="text" placeholder="Type login" name="login"><br>
    <input type="password" placeholder="Type password" name="password"><br>
    <input type="submit" value="Enter">
</form>
</body>
</html>
