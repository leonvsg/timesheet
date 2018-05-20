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
                    <div class="panel-heading panel-heading-cst">
                        <div class="btn-group panel-btn">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Select group
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <c:forEach items="${context.getGroups()}" var="group">
                                    <li>
                                        <a href="rating?group=<c:out value="${group.getId()}"/>">
                                            <c:out value="${group.getName()}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="lessons-title"><h3 class="lessons-title-cst">Rating</h3></div>
                    </div>
                    <table class="table table-bordered">
                        <tr>
                            <td rowspan="2" class="td-rating-cst">Students</td>
                            <td colspan="<c:out value="${context.getLessons().size()}"/>" class="td-rating-cst">Lessons</td>
                        </tr>
                        <tr>
                            <c:forEach items="${context.getLessons()}" var="lesson">
                                <td class="td-rating-cst" title="Description" data-toggle="popover" data-container="body" data-placement="top" data-trigger="hover" data-content="<c:out value="${lesson.getDescription()}"/>">
                                    <c:out value="${lesson.getDate()}"/>
                                </td>
                            </c:forEach>
                        </tr>
                        <c:forEach items="${context.getUsers()}" var="user">
                            <c:if test="${user.getRole().equals(\"STUDENT\")}">
                                <tr>
                                    <td title="E-Mail" data-toggle="popover" data-trigger="hover" data-container="body" data-placement="left" data-content="<c:out value="${user.getLogin()}"/>">
                                        <c:out value="${user.getName()}"/> <c:out value="${user.getSurname()}"/>
                                    </td>
                                    <c:forEach items="${context.getLessons()}" var="lesson">
                                        <td class="td-rating-cst">
                                            <c:forEach items="${context.getRatings()}" var="rating">
                                                <c:if test="${rating.getLessonId() == lesson.getId() && rating.getUserId() == user.getId()}">
                                                    <div title="Description" data-toggle="popover" data-container="body" data-placement="top" data-trigger="hover" data-content="<c:out value="${rating.getDescription()}"/>">
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

                <div class="panel panel-info">
                    <div class="panel-heading collapsed-btn" data-toggle="collapse" href="#collapseGroups" aria-expanded="true" aria-controls="collapseGroups">
                        <h4 class="panel-title">Rate</h4>
                    </div>
                    <div class="collapse in" id="collapseGroups">
                        <form role="form" class="form-inline" action="${pageContext.request.contextPath}rating" method="post">
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="input-group rating-input-group">
                                        <span class="input-group-addon rating-select-addon" id="student-addon">Student</span>
                                        <select name="student" class="form-control" id="student" aria-describedby="student-addon">
                                            <c:forEach items="${context.getUsers()}" var="user">
                                                <c:if test="${user.getRole().equals(\"STUDENT\")}">
                                                <option>
                                                    <c:out value="${user.getName()}"/> <c:out value="${user.getSurname()}"/>
                                                </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-5">
                                    <div class="input-group rating-input-group">
                                        <span class="input-group-addon rating-select-addon" id="lesson-addon">Lesson</span>
                                        <select name="lesson" class="form-control" id="lesson" aria-describedby="lesson-addon">
                                            <c:forEach items="${context.getLessons()}" var="lesson">
                                                    <option>
                                                        <c:out value="${lesson.getDate()}"/>
                                                    </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="input-group rating-input-group">
                                        <span class="input-group-addon rating-select-addon" id="value-addon">Value</span>
                                        <input type="number" id="value" name="value" value="0" min="0" max="100" class="form-control" aria-describedby="value-addon">
                                    </div>
                                </div>
                            </div>
                            <div class="row">

                            </div>
                            <div class="row">
                                <div class="col-lg-4">

                                </div>
                                <div class="col-lg-4">

                                </div>
                                <div class="col-lg-4">

                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </main><!-- .content -->
        </div><!-- .container-->
    </div><!-- .middle-->
</div><!-- .wrapper -->
<c:import url="/footer.jsp"/>
<script>
    $(document).ready(function(){
        $('[data-toggle="popover"]').popover();
    });
</script>
</body>
</html>