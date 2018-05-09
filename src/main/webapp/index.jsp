<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 05.05.2018
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Main menu</title>
  </head>
  <body>
  <h2>Main menu</h2>
  <ul>
<c:choose>
    <c:when test="${token != null}">
        <li><a href="${pageContext.request.contextPath}/timesheet/user">User</a></li>
        <li><a href="${pageContext.request.contextPath}/timesheet/course">Courses</a></li>
        <li><a href="${pageContext.request.contextPath}/timesheet/auth?exit=true">Logout</a></li>
    </c:when>
    <c:otherwise>
        <c:import url="auth.jsp"/>
    </c:otherwise>
</c:choose>
  </ul>
  </body>
</html>
