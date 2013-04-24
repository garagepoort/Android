<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../jspf/css.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register | RemoteAlarmManager</title>
    </head>
    <body>
        <%@include file="../jspf/navbar.jspf" %>
        <div id="container">
            <div class="well" margin: 0 auto;">
                 <ul class="breadcrumb">
                    <li><a href="home.htm">Home</a><span class="divider">/</span></li>
                    <li class="active">Register</li>
                </ul>
                <legend>Register</legend>

                <form:form commandName="registerUser" method="POST">

                    <table>
                        <tr>
                            <td><label>Name:</label></td>
                            <td><form:input path="naam"></form:input></td>
                            <td><span class="help-inline control-group"><form:errors path="naam"/></span></td>
                        </tr>
                        <tr>
                            <td><label>Last Name:</label></td>
                            <td><form:input path="achternaam"></form:input></td>
                            <td><span class="help-inline control-group"><form:errors path="achternaam"/></span></td>
                        </tr>
                        <tr>
                            <td><label>E-mail:</label></td>
                            <td><form:input path="email"></form:input></td>
                            <td><span class="help-inline control-group"><form:errors path="email"/></span></td>
                        </tr>
                        <tr>
                            <td><label>Password:</label></td>
                            <td><form:password path="paswoord"></form:password></td>
                            <td><span class="help-inline control-group"><form:errors path="paswoord"/></span></td>
                        </tr>
                        <tr>
                            <td><label>Repeat Password:</label></td>
                            <td><form:password path="repeatPaswoord"></form:password></td>
                            <td><span class="help-inline control-group"><form:errors path="repeatPaswoord"/></span></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="text-align: right;"><input type="submit" class="btn" value="Submit" /></td>
                        </tr>
                    </table>

                </form:form>
            </div>
        </div>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </body>
</html>
