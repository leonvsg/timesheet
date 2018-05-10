<%@ page import="ru.leonvsg.education.timesheet.entities.Course" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.leonvsg.education.timesheet.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 09.05.2018
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course list</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/timesheet/">To main menu</a><br>
<%
    User user = (User) request.getAttribute("user");
    if (user.getRole().toUpperCase().equals("ADMIN")) {
%>
<h3>Add new course</h3>
<form action="${pageContext.request.contextPath}course" method="post">
    <input type="text" placeholder="Type name" name="name"><br>
    <input type="text" placeholder="Type description" name="description"><br>
    <input type="number" placeholder="Type duration" name="duration"><br>
    <input type="submit" value="Add">
    <%
        String message = request.getParameter("errorMessage");
        if (message != null) {
    %>
    <%=message%>
    <%}%>
</form>
<%} else {%>
<a href="${pageContext.request.contextPath}course?display=all">All courses</a>
<a href="${pageContext.request.contextPath}course?display=my">My courses</a>
<%}
    ArrayList<Course> courses = (ArrayList<Course>) request.getAttribute("courses");
%>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Description</td>
        <td>Duration</td>
    </tr>
    <%
        for (Course course: courses) {
    %>
    <tr>
        <td><%=course.getId()%></td>
        <td><a href="/timesheet/course?id=<%=course.getId()%>"><%=course.getName()%></a></td>
        <td><%=course.getDescription()%></td>
        <td><%=course.getDuration()%> hours</td>
    </tr>
    <%}%>
</table>
</body>
</html>
