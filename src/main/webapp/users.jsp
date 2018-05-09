<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 07.05.2018
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.leonvsg.education.timesheet.entities.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<a href="${pageContext.request.contextPath}">To main menu</a>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Surname</td>
        <td>Email</td>
        <td>Role</td>
    </tr>
<c:forEach items="${users}" var="user">
    <tr>
        <td><c:out value="${user.getId()}"/></td>
        <td><c:out value="${user.getName()}"/></td>
        <td><c:out value="${user.getSurname()}"/></td>
        <td><c:out value="${user.getLogin()}"/></td>
        <td><c:out value="${user.getRole()}"/></td>
    </tr>
</c:forEach>
    <tr>
        <form action="${pageContext.request.contextPath}user" method="post">
            <td><input type="submit" value="+"></td>
            <td>
                <input type="text" placeholder="Name" name="name"><br>
                <input type="text" placeholder="Middlename(opt)" name="middlename">
            </td>
            <td>
                <input type="text" placeholder="Surname" name="surname"><br>
                <input type="text" placeholder="Password" name="password">
            </td>
            <td>
                <input type="text" placeholder="Email(login)" name="login"><br>
                <c:out value="${param.errorMessage}" default="Result"/>
            </td>
            <td>
                <select name="role">
                    <%
                        for (Role role: Role.values()){
                    %>
                    <option><%=role.toString()%></option>
                    <%}%>
                </select>
            </td>
        </form>
    </tr>
</table>
</body>
</html>
