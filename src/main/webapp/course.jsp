<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 09.05.2018
  Time: 11:29
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
                <table>
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
                <table>
                    <tr>
                        <td>Id</td>
                        <td>Name</td>
                        <td>Description</td>
                        <td>Start Date</td>
                        <td>Expiration Date</td>
                    </tr>
                    <c:forEach items="${groups}" var="group">
                        <tr>
                            <td><c:out value="${group.getId()}"/></td>
                            <td>
                                <a href="<c:url value="/timesheet/group?id=${group.getId()}"/>">
                                    <c:out value="${group.getName()}"/>
                                </a>
                            </td>
                            <td><c:out value="${group.getDescription()}"/></td>
                            <td><c:out value="${group.getStartDate()}"/></td>
                            <td><c:out value="${group.getExpDate()}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </main><!-- .content -->
        </div><!-- .container-->
    </div><!-- .middle-->
</div><!-- .wrapper -->
<!-- .footer -->
</body>
</html>