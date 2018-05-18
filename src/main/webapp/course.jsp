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
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title"><c:out value="${course.getName()}"/></h3>
                    </div>
                    <table class="table">
                        <tr>
                            <td><strong>Id: </strong><c:out value="${course.getId()}"/></td>
                            <td><strong>Duration: </strong><c:out value="${course.getDuration()}"/></td>
                        </tr>
                    </table>
                    <div class="panel-footer"><c:out value="${course.getDescription()}"/></div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading collapsed-btn" data-toggle="collapse" href="#collapseGroups" aria-expanded="true" aria-controls="collapseGroups">
                        <h4 class="panel-title">Groups</h4>
                    </div>
                    <div class="collapse in" id="collapseGroups">
                        <table class="table">
                            <tr>
                                <td>Id</td>
                                <td>Name</td>
                                <td>Course</td>
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
                                    <td>
                                        <a href="<c:url value="/timesheet/course?id=${group.getCourse().getId()}"/>">
                                            <c:out value="${group.getCourse().getName()}"/>
                                        </a>
                                    </td>
                                    <td><c:out value="${group.getDescription()}"/></td>
                                    <td><c:out value="${group.getStartDate()}"/></td>
                                    <td><c:out value="${group.getExpDate()}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </main><!-- .content -->
        </div><!-- .container-->
    </div><!-- .middle-->
</div><!-- .wrapper -->
<!-- .footer -->
</body>
</html>