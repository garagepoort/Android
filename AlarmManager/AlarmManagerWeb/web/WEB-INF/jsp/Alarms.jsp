<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alarms | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        <div id="container" class="well">
            <ul class="breadcrumb">
                <li><a href="home.htm">Home</a><span class="divider">/</span></li>
                <li class="active">Alarms</li>
            </ul>
            <legend>Alarm Overview</legend>
            <table class="table-striped table-bordered table-hover overview" style="width: 940px;">

                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Date</th>
                    <th>Repeated</th>
                    <th>Unit</th>
                    <th>Quantity</th>
                    <th>End Date</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>

                <c:forEach items="${alarms}" var="a">
                    <tr>
                        <td>${a.id}</td>
                        <td>${a.title}</td>
                        <td title="${a.info}">
                            <c:choose>
                                <c:when test="${fn:length(a.info)>25}">
                                    ${fn:substring(a.info, 0, 24)}...
                                </c:when>
                                <c:otherwise>
                                    ${a.info}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${a.eventDateTimeString}</td>
                        <td>
                            <c:choose>
                                <c:when test="${a.repeated == true}">
                                    <i class="icon-ok"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="icon-ban-circle"></i>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <c:choose>
                            <c:when test="${a.repeated == true}">
                                <td>${a.repeatUnitName}</td>
                                <td>${a.repeatQuantity}</td>
                                <td>${a.endRepeatDateTimeString}</td>
                            </c:when>
                            <c:otherwise>
                                <td>N/A</td>
                                <td>N/A</td>
                                <td>N/A</td>
                            </c:otherwise>
                        </c:choose>
                        <td><a href="alarmForm.htm?id=${a.id}"><i class="icon-edit"></i></a></td>
                        <td><a href="editAlarmUsers.htm?aID=${a.id}"><i class="icon-user"></i></a></td>
                        <td><a href="deleteAlarm.htm?id=${a.id}"><i class="icon-remove"></i></a></td>
                    </tr>
                </c:forEach>
            </table>

            <div style="height: 15px"></div>
            <a href="alarmForm.htm" class="btn pull-left" style="margin-right: 10px;"><i class="icon-plus"></i>Add</a>
            <a href="cleanupAlarms.htm" class="btn"><i class="icon-trash"></i>Clean</a>
            <div style="height: 15px"></div>
            
            <h1 style="font-size: 14pt; margin-bottom: -5px;">Symbols</h1>
            <table class="table table-condensed">
                <tr>
                    <td><i class="icon-edit"></i> </td>
                    <td>Click here to edit the properties of this alarm.</td>
                </tr>
                <tr>
                    <td><i class="icon-user"></i> </td>
                    <td>Click here to subscribe users to or unsubscribe users from this alarm.</td>
                </tr>
                <tr>
                    <td><i class="icon-remove"></i> </td>
                    <td>Click here to remove this alarm from the database.</td>
                </tr>
                <tr>
                    <td><i class="icon-ok"></i> </td>
                    <td>This icon means that it's a repeated alarm.</td>
                </tr>
                <tr>
                    <td><i class="icon-ban-circle"></i> </td>
                    <td>This icon means that it's not a repeated alarm.</td>
                </tr>
            </table>
        </div>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
