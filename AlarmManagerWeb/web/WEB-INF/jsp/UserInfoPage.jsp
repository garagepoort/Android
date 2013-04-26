<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${user.naam}" /> | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        <div id="container" class="well">
            <legend>User Info</legend>
            <table class="table">
                <tr>
                    <td>ID: </td>
                    <td>${user.userid}</td>
                </tr>
                <tr>
                    <td>Naam: </td>
                    <td>${user.naam}</td>
                </tr>
                <tr>
                    <td>Achternaam: </td>
                    <td>${user.achternaam}</td>
                </tr>
                <tr>
                    <td>E-mail: </td>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <td>Status: </td>
                    <c:choose>
                        <c:when test="${user.admin == true}">
                            <td>Admin</td>
                        </c:when>
                        <c:otherwise>
                            <td>User</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </table>
            <legend>Linked Alarms</legend>

            <table class="table table-hover table-striped">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Date</th>
                    <th>Repeated</th>
                    <th>Unit</th>
                    <th>Quantity</th>
                    <th>End Date</th>
                </tr>
                <c:forEach items="${alarmsLinked}" var="a">
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
                        <td>${a.repeated}</td>
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
                    </tr>
                </c:forEach>
            </table>
        </div>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
