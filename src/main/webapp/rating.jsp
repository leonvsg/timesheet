<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 12.05.2018
  Time: 17:14
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
                    <div class="panel-heading">
                        <div class="btn-group panel-btn">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Select group
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <c:forEach items="${groups}" var="group">
                                    <li>
                                        <a href="rating?group=<c:out value="${group.getId()}"/>">
                                            <c:out value="${group.getName()}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="lessons-title"><h4>Lessons</h4></div>
                    </div>
                    <table class="table table-bordered">
                        <tr>
                            <td>Students</td>
                            <c:forEach items="${lessons}" var="lesson">
                                <td data-toggle="tooltip" data-placement="top" title="<c:out value="${lesson.getDescription()}"/>">
                                    <c:out value="${lesson.getDate()}"/>
                                </td>
                            </c:forEach>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <c:if test="${user.getRole().equals(\"STUDENT\")}">
                                <tr>
                                    <td data-toggle="tooltip" data-placement="left" title="<c:out value="${user.getLogin()}"/>">
                                        <c:out value="${user.getName()}"/> <c:out value="${user.getSurname()}"/>
                                    </td>
                                    <c:forEach items="${lessons}" var="lesson">
                                        <td>
                                            <c:forEach items="${ratings}" var="rating">
                                                <c:if test="${rating.getLessonId() == lesson.getId() && rating.getUserId() == user.getId()}">
                                                    <div data-toggle="tooltip" data-placement="left" title="<c:out value="${rating.getDescription()}"/>">
                                                        <c:out value="${rating.getValue()}"/>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:if>
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