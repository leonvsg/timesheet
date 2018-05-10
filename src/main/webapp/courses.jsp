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
        <div class="container">
            <main class="content">
                <c:choose>
                    <c:when test="${user.getRole().toUpperCase().equals(\"ADMIN\")}">
                        <h3>Add new course</h3>
                        <form action="${pageContext.request.contextPath}course" method="post">
                            <input type="text" placeholder="Type name" name="name"><br>
                            <input type="text" placeholder="Type description" name="description"><br>
                            <input type="number" placeholder="Type duration" name="duration" value="0"><br>
                            <input type="submit" value="Add">
                            <c:out value="${param.errorMessage}" default="Result"/>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}course?display=all">All courses</a>
                        <a href="${pageContext.request.contextPath}course?display=my">My courses</a>
                    </c:otherwise>
                </c:choose>
                <table>
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
            </main><!-- .content -->
        </div><!-- .container-->
        <c:import url="/menu.jsp"/>
    </div><!-- .middle-->
</div><!-- .wrapper -->
<!-- .footer -->
</body>
</html>