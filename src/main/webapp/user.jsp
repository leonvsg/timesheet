<%@ page import="ru.leonvsg.education.timesheet.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 08.05.2018
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Detail Page</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/">To main menu</a>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Middlename</td>
        <td>Surname</td>
        <td>Email</td>
        <td>Role</td>
    </tr>
    <tr>
        <td><c:out value="${user.getId()}"/></td>
        <td><c:out value="${user.getName()}"/></td>
        <td><c:out value="${user.getMiddleName()}"/></td>
        <td><c:out value="${user.getSurname()}"/></td>
        <td><c:out value="${user.getLogin()}"/></td>
        <td><c:out value="${user.getRole()}"/></td>
    </tr>
</table>
</body>
</html>
