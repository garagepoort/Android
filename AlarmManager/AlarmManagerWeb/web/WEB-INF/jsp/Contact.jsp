<%-- 
    Document   : Contact
    Created on : Mar 25, 2013, 9:33:02 AM
    Author     : ivarv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contact | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        <div id="container" class="well">
            <ul class="breadcrumb">
                <li><a href="home.htm">Home</a><span class="divider">/</span></li>
                <li class="active">Contact</li>
            </ul>
            <legend>Contact</legend>
            
            <p>Use this form to send us a message.</p>
            
            <table>
                <tr>
                    <td><label>Name:</label></td>
                    <td><input type="text" /></td>
                </tr>
                <tr>
                    <td><label>E-mail:</label></td>
                    <td><input type="text" /></td>
                </tr>
                <tr>
                    <td><label>Message:</label></td>
                    <td><textarea></textarea></td>
                </tr>
                <tr>
                    <td></td>
                    <td style="text-align: right;"><button class="btn" style="clear: both;">Send</button></td>
                </tr>
            </table>
        </div>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
