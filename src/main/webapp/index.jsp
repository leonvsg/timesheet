<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 05.05.2018
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Main menu</title>
  </head>
  <body>
  <h2>Main menu</h2>
  <ul>
  <%
      if (request.getSession().getAttribute("token") != null) {
  %>
      <li><a href="/timesheet/user">User</a></li>
      <li><a href="/timesheet/course">Courses</a></li>
      <li><a href="/auth?exit=true">Logout</a></li>
  <%
      } else {
  %>
      <li><a href="/auth">Login</a></li>
  <%
      }
  %>
  </ul>
  </body>
</html>
