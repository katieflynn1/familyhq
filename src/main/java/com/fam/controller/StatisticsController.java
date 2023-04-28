package com.fam.controller;

import com.fam.model.Event;
import com.fam.model.Statistics;
import com.fam.model.User;
import com.fam.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private final EventRepository eventRepository;
    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;

    public StatisticsController(EventRepository eventRepository, TodoListRepository todoListRepository,
                                TaskRepository taskRepository, UserRepository userRepository,
                                StatisticsRepository statisticsRepository) {
        this.eventRepository = eventRepository;
        this.todoListRepository = todoListRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statisticsRepository = statisticsRepository;
    }

    @GetMapping("/api/statistics/event")
    public ResponseEntity<Map<String, Object>> getEventStatisticsData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        long numEvents = eventRepository.countByCreator(currentUser);
        long numCompletedEvents = eventRepository.countByCreatorAndCompleted(currentUser, true);
        long numIncompleteEvents = numEvents - numCompletedEvents;
        double eventCompletionRatio = (double) numCompletedEvents / (double) numEvents;

        List<List<Object>> eventChartData = new ArrayList<>();
        eventChartData.add(List.of("Events Completed", numCompletedEvents));
        eventChartData.add(List.of("Events Not Completed", numIncompleteEvents));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("chartData", eventChartData);
        responseBody.put("chartLabels", List.of("Events Completed", "Events Not Completed"));

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/api/statistics/todoList")
    public ResponseEntity<Map<String, Object>> getToDoListStatisticsData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        long numTodoLists = todoListRepository.countByCreatorId(currentUser.getId());
        long numCompletedTodoLists = todoListRepository.countByCreatorIdAndCompleted(currentUser.getId(), true);
        long numIncompleteTodoLists = numTodoLists - numCompletedTodoLists;
        double todoListCompletionRatio = (double) numCompletedTodoLists / (double) numTodoLists;

        List<List<Object>> todoListChartData = new ArrayList<>();
        todoListChartData.add(List.of("Todo Lists Completed", numCompletedTodoLists));
        todoListChartData.add(List.of("Todo Lists Not Completed", numIncompleteTodoLists));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("chartData", todoListChartData);
        responseBody.put("chartLabels", List.of("Todo Lists Completed", "Todo Lists Not Completed"));

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/api/statistics/task")
    public ResponseEntity<Map<String, Object>> getTaskStatisticsData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        long numTasks = taskRepository.countByTodoList_CreatorId(currentUser.getId());
        long numCompletedTasks = taskRepository.countByTodoList_CreatorIdAndCompleted(currentUser.getId(), true);
        long numIncompleteTasks = numTasks - numCompletedTasks;
        double taskCompletionRatio = (double) numCompletedTasks / (double) numTasks;

        List<List<Object>> taskChartData = new ArrayList<>();
        taskChartData.add(List.of("Tasks Completed", numCompletedTasks));
        taskChartData.add(List.of("Tasks Not Completed", numIncompleteTasks));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("chartData", taskChartData);
        responseBody.put("chartLabels", List.of("Tasks Completed", "Tasks Not Completed"));

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/api/statistics/eventByCategory")
    public ResponseEntity<Map<String, Object>> getEventStatisticsByCategoryData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Event> userEvents = eventRepository.findByCreator(currentUser);

        // Calculate the number of events in each category
        Map<String, Long> categoryCounts = userEvents.stream()
                .collect(Collectors.groupingBy(Event::getCategory, Collectors.counting()));

        // Create the data for the event by category chart
        List<List<Object>> eventByCategoryChartData = new ArrayList<>();
        categoryCounts.forEach((category, count) -> eventByCategoryChartData.add(List.of(category, count)));

        // Construct the response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("chartData", eventByCategoryChartData);
        responseBody.put("chartLabels", List.of("Event Categories", "Number of Events"));

        return ResponseEntity.ok(responseBody);
    }
}