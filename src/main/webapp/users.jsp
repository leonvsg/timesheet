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
<!DOCTYPE html>
<html>
<c:import url="/head.jsp"/>
<body>
<div class="wrapper">
    <c:import url="/header.jsp"/>
    <div class="middle">
        <div class="container">
            <main class="content">

                <div class="panel panel-default">
                    <!-- Содержание панели по умолчанию -->
                    <div class="panel-heading">Users</div>

                    <!-- Таблица -->
                    <table class="table">
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
                </div>
            </main><!-- .content -->
        </div><!-- .container-->
        <c:import url="/menu.jsp"/>
    </div><!-- .middle-->
</div><!-- .wrapper -->
<!-- .footer -->
</body>
</html>
