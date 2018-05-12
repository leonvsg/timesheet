<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 10.05.2018
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="left-sidebar">
    <div class="panel panel-success">
        <div class="panel-heading"><strong>Menu</strong></div>
        <div class="panel-body">
            <ul class="nav nav-pills nav-stacked">
                <li><a href="${pageContext.request.contextPath}/timesheet/user">User</a></li>
                <li><a href="${pageContext.request.contextPath}/timesheet/course">Courses</a></li>
            </ul>
        </div>
    </div>
</aside>
