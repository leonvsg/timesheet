<%--
  Created by IntelliJ IDEA.
  User: leonvsg
  Date: 05.05.2018
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${token != null}">
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
                        <h2>Select menu item</h2>
                    </main><!-- .content -->
                </div><!-- .container-->
            </div><!-- .middle-->
        </div><!-- .wrapper -->
        <!-- .footer -->
  </body>
</html>
    </c:when>
    <c:otherwise>
        <c:import url="/auth.jsp"/>
    </c:otherwise>
</c:choose>