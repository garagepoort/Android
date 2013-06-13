<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        <div id="container">
            <div class="well">
                <legend>Home</legend>
                <c:choose>
                    <c:when test="${user != null}">
                        <c:if test="${user.admin == true}">
                            <a href="alarms.htm" class="btn" style="width:80px; text-align: left;"><i class="icon-cog"></i>Alarms</a>
                            <div style="height: 5px"></div>
                            <a href="users.htm" class="btn" style="width:80px; text-align: left;"><i class="icon-cog"></i>Users</a>
                            <div style="height: 5px"></div>
                        </c:if>
                        <a href="userinfopage.htm" class="btn" style="width: 80px; text-align: left;"><i class="icon-info-sign"></i>My Info</a>
                    </c:when>
                    <c:otherwise>
                        <p>To use this application, please log in.</p>
                        <a href="loginForm.htm" class="btn">Login</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <script type="text/javascript" src="js/bootstrap.min.js"></script>

    </body>
</html>
