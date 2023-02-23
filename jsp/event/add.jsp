<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<fieldset>
		<legend>Event Info</legend>
		<s:form method="post" commandName="event"
			action="${pageContext.request.contextPath }/event/add.html">
			<table>
				<tr>
					<td>Name</td>
					<td><s:input path="name" /></td>
				</tr>
				<tr>
					<td valign="top">Description</td>
					<td><s:textarea path="description" cols="20" rows="5" /></td>
				</tr>
				<tr>
					<td>Start Date</td>
					<td><input type="text" name="startDate" /></td>
				</tr>
				<tr>
					<td>End Date</td>
					<td><input type="text" name="endDate" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Save" /></td>
				</tr>
			</table>
		</s:form>
	</fieldset>
</body>
</html>