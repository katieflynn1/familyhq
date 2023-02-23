<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<fieldset>
		<legend>Event List</legend>
		<table border="1" cellpadding="5" cellspacing="5">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Option</th>
			</tr>
			<c:forEach var="event" items="${events }">
				<tr>
					<td>${event.id }</td>
					<td>${event.name }</td>
					<td>${event.startDate }</td>
					<td>${event.endDate }</td>
					<td>
						<a href="${pageContext.request.contextPath }/event/edit/${event.id }.html">Edit</a> | 
						<a href="${pageContext.request.contextPath }/event/delete/${event.id }.html">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<a href="${pageContext.request.contextPath }/event.html">Back</a>
	</fieldset>
</body>
</html>