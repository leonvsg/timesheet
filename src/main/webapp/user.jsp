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
                <table>
                    <tr>
                        <td>Id</td>
                        <td>Name</td>
                        <td>Middlename</td>
                        <td>Surname</td>
                        <td>Email</td>
                        <td>Role</td>
                    </tr>
                    <tr>
                        <td><c:out value="${user.getId()}"/></td>
                        <td><c:out value="${user.getName()}"/></td>
                        <td><c:out value="${user.getMiddleName()}"/></td>
                        <td><c:out value="${user.getSurname()}"/></td>
                        <td><c:out value="${user.getLogin()}"/></td>
                        <td><c:out value="${user.getRole()}"/></td>
                    </tr>
                </table>
            </main><!-- .content -->
        </div><!-- .container-->
    </div><!-- .middle-->
</div><!-- .wrapper -->
<!-- .footer -->
</body>
</html>
