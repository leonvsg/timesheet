<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 07.05.2018
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:import url="/head.jsp"/>
<body>
<div class="panel panel-success auth-panel">
    <div class="panel-heading">
        <h3 class="panel-title">Authentication</h3>
    </div>
    <div class="panel-body">
        <form class="auth-form" action="${pageContext.request.contextPath}/auth" method="post">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Login</span>
                <input name="login" type="email" class="form-control" placeholder="mail@example.com" aria-describedby="basic-addon1">
            </div>
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon2">Password</span>
                <input name="password" type="password" class="form-control" placeholder="******" aria-describedby="basic-addon2">
            </div>
            <div class="input-group entr">
                <button type="submit" class="btn btn-default btn-entr">Enter</button>
            </div>
        </form>
        <c:choose>
            <c:when test="${param.errorMessage.equals(\"authenticationFail\")}">
                <div class="alert alert-warning" role="alert">Authentication failed. Invalid login/password</div>
            </c:when>
            <c:when test="${param.errorMessage.equals(\"authenticateReq\")}">
                <div class="alert alert-warning" role="alert">Authentication Required</div>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>