<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        <div id="container" class="well" style="width: 350px;">
            <ul class="breadcrumb">
                <li><a href="home.htm">Home</a><span class="divider">/</span></li>
                <li class="active">Login</li>
            </ul>
            <c:if test="${error != null}">
                <div class="alert alert-error">
                    <p><c:out value="${error}"></c:out></p>
                </div>
            </c:if>
            <c:if test="${info != null}">
                <div class="alert alert-info">
                    <p style="text-align: center;">
                        <c:set var="search" value='\'' />
                        <c:set var="replace" value='' />
                        <c:out value="${fn:replace(info, search, replace)}"/>
                    </p>
                </div>
            </c:if>
            <form:form method="POST" commandName="login">
                <table style="width: 315px; margin: 0 auto;">
                    <tr>
                        <td><label>Username:</label></td>
                        <td style="text-align: right"><form:input path="email" /></td>
                    </tr>
                    <tr>
                        <td><label>Password:</label></td>
                        <td style="text-align: right"><form:password  path="paswoord" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align: right">
                            <input type="submit" name="Add" value="Submit" class="btn btn-primary"></input>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
