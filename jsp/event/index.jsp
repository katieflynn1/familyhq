<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link
	href="${pageContext.request.contextPath }/assets/css/fullcalendar.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath }/assets/css/fullcalendar.print.css"
	rel="stylesheet" media="print" />
<script src="${pageContext.request.contextPath }/assets/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/fullcalendar.min.js"></script>
<script>
	$(document).ready(function() {

		$('#calendar').fullCalendar({
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'listDay,listWeek,month'
			},

			// customize the button names,
			// otherwise they'd all just say "list"
			views : {
				listDay : {
					buttonText : 'list day'
				},
				listWeek : {
					buttonText : 'list week'
				}
			},

			defaultView : 'listWeek',
			defaultDate : $('#calendar').fullCalendar('today'),
			navLinks : true, // can click day/week names to navigate views
			editable : true,
			eventLimit : true, // allow "more" link when too many events
			events : 'http://localhost:9079/FullCalendarWithSpringMVC/api/event/findall.html'
		});

	});
</script>

<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}

</style>	
</head>
<body>
	<a href="${pageContext.request.contextPath }/event/add.html">Add New Event</a> |
	<a href="${pageContext.request.contextPath }/event/list.html">Event List</a>
	<br><br>
	<div id='calendar'></div>
</body>
</html>