<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 08.05.2018
  Time: 23:32
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
                    <div class="panel-heading collapsed-btn" data-toggle="collapse" href="#collapseUserInfo" aria-expanded="true" aria-controls="collapseUserInfo">
                        <h3 class="panel-title">Detail user information</h3>
                    </div>
                    <c:if test="${param.errorMessage != null}">
                        <div class="alert alert-warning" role="alert">
                            <c:out value="${param.errorMessage}"/>
                        </div>
                    </c:if>
                    <div class="collapse in" id="collapseUserInfo">
                    <form class="auth-form" role="form" class="form-inline" action="${pageContext.request.contextPath}user" method="post">
                        <input name="id" value="<c:out value="${context.getUser().getId()}"/>" hidden/>
                        <table class="table">
                            <tr>
                                <td>Id</td>
                                <td><c:out value="${context.getUser().getId()}"/></td>
                                <td>
                                    <div class="form-group">
                                        <input type="password" class="form-control" id="password" name="password" placeholder="New password">
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>Name</td>
                                <td><c:out value="${context.getUser().getName()}"/></td>
                                <td>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="name" name="name" placeholder="New name">
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>Middlename</td>
                                <td><c:out value="${context.getUser().getMiddleName()}"/></td>
                                <td>
                                    <input type="text" class="form-control" id="middlename" name="middlename" placeholder="New middlename">
                                </td>
                            </tr>
                            <tr>
                                <td>Surname</td>
                                <td><c:out value="${context.getUser().getSurname()}"/></td>
                                <td>
                                    <input type="text" class="form-control" id="surname" name="surname" placeholder="New surname">
                                </td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td><c:out value="${context.getUser().getLogin()}"/></td>
                                <td>
                                    <input type="email" class="form-control" name="login" id="email" placeholder="New email">
                                </td>
                            </tr>
                            <tr>
                                <td>Role</td>
                                <td><c:out value="${context.getUser().getRole()}"/></td>
                                <td>
                                    <button type="submit" class="btn btn-default btn-entr">Submit new options</button>
                                </td>
                            </tr>
                        </table>
                    </form>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading collapsed-btn" data-toggle="collapse" href="#collapseGroups" aria-expanded="true" aria-controls="collapseGroups">
                        <h3 class="panel-title">Groups</h3>
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
                        <c:forEach items="${context.getGroups()}" var="group">
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
