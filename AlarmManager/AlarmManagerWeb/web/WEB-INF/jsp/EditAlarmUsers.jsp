<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Alarm Users | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        <div id="container" class="well">
            <ul class="breadcrumb">
                <li><a href="home.htm">Home</a><span class="divider">/</span></li>
                <li><a href="alarms.htm">Alarms</a><span class="divider">/</span></li>
                <li class="active">Edit Alarm Users</li>
            </ul>
            <legend>Alarm Info</legend>
            <table class="table">
                <tr>
                    <td>ID: </td>
                    <td>${alarm.id}</td>
                </tr>
                <tr>
                    <td>Title: </td>
                    <td>${alarm.title}</td>
                </tr>
                <tr>
                    <td>Info: </td>
                    <td>${alarm.info}</td>
                </tr>
                <tr>
                    <td>Date: </td>
                    <td>${alarm.eventDateTimeString}</td>
                </tr>
                <tr>
                    <td>Repeated: </td>
                    <td><label>
                            <c:choose>
                                <c:when test="${alarm.repeated == true}">
                                    <i class="icon-check"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="icon-remove"></i>
                                </c:otherwise>
                            </c:choose>
                        </label>
                    </td>
                </tr>
                <c:if test="${alarm.repeated == true}">
                    <tr>
                        <td>Repeat unit: </td>
                        <td>${alarm.repeatUnitName}</td>
                    </tr>
                    <tr>
                        <td>Repeat quantity: </td>
                        <td>${alarm.repeatQuantity}</td>
                    </tr>
                    <tr>
                        <td>Repeat End Date: </td>
                        <td>${alarm.endRepeatDateTimeString}</td>
                    </tr>
                </c:if>
            </table>

            <legend>Available Users</legend>

            <table class="table table-hover table-striped">
                <tr>
                    <th>Naam</th>
                    <th>Achternaam</th>
                    <th>E-mail</th>
                    <th></th>
                </tr>
                <c:forEach items="${usersAvailable}" var="u">
                    <tr>
                        <td>${u.naam}</td>
                        <td>${u.achternaam}</td>
                        <td>${u.email}</td>
                        <td><a href="addUserToAlarmAction.htm?uID=${u.userid}&aID=${alarm.id}"><i class="icon-plus-sign"></i></a></td>
                    </tr>
                </c:forEach>
            </table>

            <legend>Linked Users</legend>
            <table class="table table-hover table-striped">
                <tr>
                    <th>Naam</th>
                    <th>Achternaam</th>
                    <th>E-mail</th>
                    <th></th>
                </tr>
                <c:forEach items="${usersLinked}" var="u">
                    <tr>
                        <td>${u.naam}</td>
                        <td>${u.achternaam}</td>
                        <td>${u.email}</td>
                        <td><a href="removeUserFromAlarm.htm?uID=${u.userid}&aID=${alarm.id}"><i class="icon-minus-sign"></i></a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
