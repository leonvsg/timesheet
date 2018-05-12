<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 10.05.2018
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="header">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/">Timesheet system</a>
                <p class="navbar-text navbar-right">Signed in as <c:out value="${sessionScope.userName}"/> <c:out value="${sessionScope.userSurname}"/> (<c:out value="${sessionScope.userLogin}"/>)</p>
                <a class="btn btn-default navbar-btn btn-logout btn-head" href="${pageContext.request.contextPath}/timesheet/user?id=<c:out value="${sessionScope.userId}"/>">My page</a>
                <a class="btn btn-default navbar-btn btn-my-page btn-head" href="${pageContext.request.contextPath}/timesheet/auth?exit=true">Logout</a>
            </div>
        </div>
    </nav>
</header>
