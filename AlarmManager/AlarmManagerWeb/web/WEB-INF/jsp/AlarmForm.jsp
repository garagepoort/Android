<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../jspf/css.jspf" %>
        <link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/ui/1.10.0/jquery-ui.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
        <script type="text/javascript" src="js/jquery-ui-sliderAccess.js"></script>
        <script type="text/javascript" src="js/functions.js"></script>
        <title>Alarm Editor | RemoteAlarmManager</title>
    </head>
    <body onload="createDatepickers();repeatedChecked();">

        <%@include file="../jspf/navbar.jspf" %>

        <div id="container">
            <div class="well">
                <ul class="breadcrumb">
                    <li><a href="home.htm">Home</a><span class="divider">/</span></li>
                    <li><a href="alarms.htm">Alarms</a><span class="divider">/</span></li>
                    <li class="active">Alarm Editor</li>
                </ul>
                <legend>Alarm Editor</legend>
                <form:form commandName="editAlarm" method="POST">
                    <form:hidden path="id"></form:hidden>
                    <% if (request.getHeader("Referer") != null && request.getHeader("Referer").contains("alarmForm.htm")) {%>
                    <div class="alert alert-error alert-block">
                        <form:errors path="*"/>
                    </div>
                    <% }%>
                    <table>
                        <tr>
                            <td><label>Title:</label> </td>
                            <td><form:input path="title"></form:input> </td>
                            </tr>
                            <tr>
                                <td><label>Description:</label></td>
                                <td><form:input path="info"></form:input> </td>
                            </tr>
                            <tr>
                                <td><label>Date:</label></td>
                                <td><form:input path="eventDateTimeString" id="eventDate" readonly="true"></form:input></td>
                            </tr>
                            <tr>
                                <td><label for="repeated">Repeated:</label></td>
                                <td><form:checkbox cssClass="checkbox" path="repeated" id="repeated" onclick="repeatedChecked();"></form:checkbox> </td>
                            </tr>
                            <tr>
                                <td><label>Repeat unit:</label></td>
                                <td>
                                <form:select path="repeatunit" id="repeatunit">
                                    <form:option value="12" label="Minute"></form:option>
                                    <form:option value="10" label="Hour"></form:option>
                                    <form:option value="5" label="Day"></form:option>
                                    <form:option value="3" label="Week"></form:option>
                                    <form:option value="2" label="Month"></form:option>
                                    <form:option value="1" label="Year"></form:option>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Repeat Quantity:</label></td>
                            <td><form:input path="repeatQuantity" id="repeatQuantity" onkeypress="return numbersonly(this, event)"></form:input></td>
                            </tr>
                            <tr>
                                <td><label>Repeat End Date:</label></td>
                                <td><form:input path="endRepeatDateTimeString" id="endDate" readonly="true"></form:input></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="submit" class="btn"/></td>
                            </tr>
                        </table>

                </form:form>
            </div>
        </div>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
