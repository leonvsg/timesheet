<%@ page import="ru.leonvsg.education.timesheet.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 08.05.2018
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Detail Page</title>
</head>
<body>
<a href="/timesheet/">To main menu</a>
<%
    User user = (User) request.getAttribute("user");
%>
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
        <td><%=user.getId()%></td>
        <td><%=user.getName()%></td>
        <td><%=user.getMiddleName()%></td>
        <td><%=user.getSurname()%></td>
        <td><%=user.getLogin()%></td>
        <td><%=user.getRole()%></td>
    </tr>
</table>
</body>
</html>
