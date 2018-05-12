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
        <c:import url="/menu.jsp"/>
        <div class="container">
            <main class="content">
                <div class="panel panel-default">
                    <!-- Содержание панели по умолчанию -->
                    <div class="panel-heading">Users</div>
                    <div class="alert alert-info error-message" role="alert">
                        <c:out value="${param.errorMessage}" default="Add new user"/>
                    </div>
                    <!-- Таблица -->
                    <table class="table">
                        <tr class="cst-table">
                            <form role="form" class="form-inline" action="${pageContext.request.contextPath}users" method="post">
                                <td>
                                    <button type="submit" class="btn btn-success">+</button>
                                </td>
                                <td colspan="2">
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                                    </div>
                                </td>
                                <td colspan="2">
                                    <input type="text" class="form-control" id="middlename" name="middlename" placeholder="Middlename">
                                </td>
                                <td colspan="2">
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="surname" name="surname" placeholder="Surname">
                                    </div>
                                </td>
                                <td colspan="2">
                                    <div class="form-group">
                                        <input type="email" class="form-control" name="login" id="email" placeholder="Email">
                                    </div>
                                </td>
                                <td colspan="2">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                </td>
                                <td colspan="2">
                                    <div class="form-group">
                                        <select name="role" class="form-control">
                                            <%
                                                for (Role role: Role.values()){
                                            %>
                                            <option><%=role.toString()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                </td>
                            </form>
                        </tr>
                        <tr>
                            <td>Id</td>
                            <td colspan="3">Name</td>
                            <td colspan="3">Surname</td>
                            <td colspan="3">Email</td>
                            <td colspan="3">Role</td>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td><c:out value="${user.getId()}"/></td>
                                <td colspan="3">
                                    <a href="user?id=<c:out value="${user.getId()}"/>">
                                        <c:out value="${user.getName()}"/>
                                    </a>
                                </td>
                                <td colspan="3"><c:out value="${user.getSurname()}"/></td>
                                <td colspan="3"><c:out value="${user.getLogin()}"/></td>
                                <td colspan="3"><c:out value="${user.getRole()}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </main><!-- .content -->
        </div><!-- .container-->
    </div><!-- .middle-->
</div><!-- .wrapper -->
<!-- .footer -->
</body>
</html>
