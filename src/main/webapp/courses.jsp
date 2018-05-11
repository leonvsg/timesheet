<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 09.05.2018
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <div class="panel-heading">Courses</div>
                    <!-- Таблица -->
                    <c:choose>
                        <c:when test="${user.getRole().toUpperCase().equals(\"ADMIN\")}">
                            <div class="alert alert-info error-message" role="alert">
                                <c:out value="${param.errorMessage}" default="Add new course"/>
                            </div>
                            <table class="table">
                                <form role="form" class="form-inline" action="${pageContext.request.contextPath}course" method="post">
                                    <tr>
                                        <td>
                                            <button type="submit" class="btn btn-success">+</button>
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                                            </div>
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" id="description" name="description" placeholder="Description">
                                        </td>
                                        <td>
                                            <div class="form-group">
                                                <input type="number" class="form-control" id="duration" name="duration" value="0" placeholder="Duration">
                                            </div>
                                        </td>
                                    </tr>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}course?display=all">All courses</a>
                                <a href="${pageContext.request.contextPath}course?display=my">My courses</a>
                                <table class="table">
                            </c:otherwise>
                        </c:choose>
                        <tr>
                            <td>Id</td>
                            <td>Name</td>
                            <td>Description</td>
                            <td>Duration</td>
                        </tr>
                        <c:forEach items="${courses}" var="course">
                            <tr>
                                <td><c:out value="${course.getId()}"/></td>
                                <td>
                                    <a href="<c:url value="/timesheet/course?id=${course.getId()}"/>">
                                        <c:out value="${course.getName()}"/>
                                    </a>
                                </td>
                                <td><c:out value="${course.getDescription()}"/></td>
                                <td><c:out value="${course.getDuration()}"/> hours</td>
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