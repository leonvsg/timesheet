<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 09.05.2018
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Course Detail Page</title>
</head>
<body>
<a href="courses">Back</a>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Description</td>
        <td>Duration</td>
    </tr>
    <tr>
        <td><c:out value="${course.getId()}"/></td>
        <td><c:out value="${course.getName()}"/></td>
        <td><c:out value="${course.getDescription()}"/></td>
        <td><c:out value="${course.getDuration()}"/></td>
    </tr>
</table>
<br>
<h4>Groups</h4>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Description</td>
        <td>Start Date</td>
        <td>Expiration Date</td>
    </tr>
    <c:forEach items="groups" var="group">
    <tr>
        <td><c:out value="${group.getId()}"/></td>
        <td>
            <a href="${pageContext.request.contextPath}group?id=<c:out value="${group.getId()}"/>">
                <c:out value="${group.getName()}"/>
            </a>
        </td>
        <td><c:out value="${group.getDescription()}"/></td>
        <td><c:out value="${group.getStartDate()}"/></td>
        <td><c:out value="${group.getExpDate()}"/></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
