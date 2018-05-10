<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 10.05.2018
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="left-sidebar">
    <strong>Menu</strong>
    <ul>
        <li><a href="${pageContext.request.contextPath}/timesheet/user">User</a></li>
        <li><a href="${pageContext.request.contextPath}/timesheet/course">Courses</a></li>
        <li><a href="${pageContext.request.contextPath}/timesheet/auth?exit=true">Logout</a></li>
    </ul>
</aside>
