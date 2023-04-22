$(function() {
    // Initialize the charts with default values
    var eventChartCtx = document.getElementById('eventChart').getContext('2d');
    var eventChart = new Chart(eventChartCtx, {
        type: 'pie',
        data: {
            labels: ['Completed Events', 'Incomplete Events'],
            datasets: [{
                label: 'Event Completion Ratio',
                data: [0, 0],
                backgroundColor: ['#36A2EB', '#FFCE56']
            }]
        },
        options: {}
    });

    var todoListChartCtx = document.getElementById('todoListChart').getContext('2d');
    var todoListChart = new Chart(todoListChartCtx, {
        type: 'pie',
        data: {
            labels: ['Completed Todo Lists', 'Incomplete Todo Lists'],
            datasets: [{
                label: 'Todo List Completion Ratio',
                data: [0, 0],
                backgroundColor: ['#FF6384', '#36A2EB']
            }]
        },
        options: {}
    });

    var taskChartCtx = document.getElementById('taskChart').getContext('2d');
    var taskChart = new Chart(taskChartCtx, {
        type: 'pie',
        data: {
            labels: ['Completed Tasks', 'Incomplete Tasks'],
            datasets: [{
                label: 'Task Completion Ratio',
                data: [0, 0],
                backgroundColor: ['#FFCE56', '#FF6384']
            }]
        },
        options: {}
    });

    function updateCharts(data) {
        var eventLabels = data.eventLabels;
        var eventData = data.eventData;
        var todoListLabels = data.todoListLabels;
        var todoListData = data.todoListData;
        var taskLabels = data.taskLabels;
        var taskData = data.taskData;

        // Update the data for the Event chart
        eventChart.data.labels = eventLabels;
        eventChart.data.datasets[0].data = eventData;
        eventChart.update();

        // Update the data for the Todo List chart
        todoListChart.data.labels = todoListLabels;
        todoListChart.data.datasets[0].data = todoListData;
        todoListChart.update();

        // Update the data for the Task chart
        taskChart.data.labels = taskLabels;
        taskChart.data.datasets[0].data = taskData;
        taskChart.update();
    }

    // Execute the AJAX request and update the charts on page load
    $.ajax({
        url: "/admin/statistics",
        type: "GET",
        dataType: 'json',
        success: function(data) {
            updateCharts({
                eventData: [data.numCompletedEvents, data.numIncompleteEvents],
                eventLabels: data.eventLabels,
                todoListData: [data.numCompletedTodoLists, data.numIncompleteTodoLists],
                todoListLabels: data.todoListLabels,
                taskData: [data.numCompletedTasks, data.numIncompleteTasks],
                taskLabels: data.taskLabels,
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("Error fetching data from server: " + textStatus + " - " + errorThrown);
        }
    });
});