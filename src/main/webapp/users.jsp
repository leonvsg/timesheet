<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 07.05.2018
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.leonvsg.education.timesheet.entities.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<%
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
%>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Surname</td>
        <td>Email</td>
        <td>Role</td>
    </tr>

<%
    for (User user: users) {
%>
    <tr>
        <td><%=user.getId()%></td>
        <td><%=user.getName()%></td>
        <td><%=user.getSurname()%></td>
        <td><%=user.getLogin()%></td>
        <td><%=user.getRole()%></td>
    </tr>
<%}%>
</table>
</body>
</html>
