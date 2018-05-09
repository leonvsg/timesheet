<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 07.05.2018
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.leonvsg.education.timesheet.entities.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.leonvsg.education.timesheet.entities.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<a href="/timesheet/">To main menu</a>
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
<h3>Add new user</h3>
<form action="${pageContext.request.contextPath}user" method="post">
    <input type="text" placeholder="Type email(login)" name="login"><br>
    <input type="text" placeholder="Type password" name="password"><br>
    <input type="text" placeholder="Type name" name="name"><br>
    <input type="text" placeholder="Type middlename" name="middlename"><br>
    <input type="text" placeholder="Type surname" name="surname"><br>
    <select name="role">
    <%
        for (Role role: Role.values()){
    %>
        <option><%=role.toString()%></option>
    <%}%>
    </select><br>
    <input type="submit" value="Add">
    <%
        String message = request.getParameter("errorMessage");
        if (message != null) {
    %>
    <%=message%>
    <%}%>
</form>
</body>
</html>
