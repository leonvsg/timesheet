<%@ page import="ru.leonvsg.education.timesheet.entities.Course" %>
<%@ page import="ru.leonvsg.education.timesheet.entities.Group" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 09.05.2018
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course Detail Page</title>
</head>
<body>
<a href="/timesheet/course">Back</a>
<%
    Course course = (Course) request.getAttribute("course");
%>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Description</td>
        <td>Duration</td>
    </tr>
    <tr>
        <td><%=course.getId()%></td>
        <td><%=course.getName()%></td>
        <td><%=course.getDescription()%></td>
        <td><%=course.getDuration()%></td>
    </tr>
</table>
<br>
<h4>Groups</h4>
<%
    ArrayList<Group> groups = (ArrayList<Group>) request.getAttribute("groups");
%>
<table border="black 2px">
    <tr>
        <td>Id</td>
        <td>Name</td>
        <td>Description</td>
        <td>Start Date</td>
        <td>Expiration Date</td>
    </tr>
    <%
        for (Group group: groups) {
    %>
    <tr>
        <td><%=group.getId()%></td>
        <td><a href="/timesheet/group?id=<%=group.getId()%>"><%=group.getName()%></a></td>
        <td><%=group.getDescription()%></td>
        <td><%=group.getStartDate()%></td>
        <td><%=group.getExpDate()%></td>
    </tr>
    <%}%>
</table>
</body>
</html>
