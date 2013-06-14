<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${pageTitle}" /> | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        
        <div id="container" class="well">
            <legend><c:out value="${title}" /></legend>
            <p><c:out value="${text}" /></p>
            <a href="home.htm">Home</a>
        </div>
            
        
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
