<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
    <head>
        <link rel="stylesheet" href="main.css" />
        <script src="main.js"></script>
    </head>
<body>
    <table>
        <tr><td></td><td colspan="2">Temple 5</td><td></td><td rowspan="3">Card 1</td><td rowspan="3">Card 2</td></tr>
        <tr><td colspan="2">Temple 3</td><td colspan="2">Temple 4</td></tr>
        <tr><td colspan="2">Temple 1</td><td colspan="2">Temple 2</td></tr>
    </table>

    <!--
	<c:url value="/resources/text.txt" var="url"/>
	<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
	Spring URL: ${springUrl} at ${time}
	<br>
	JSTL URL: ${url}
	<br>
	Message: ${message}
	Name: ${name}
	-->
</body>

</html>