package com.fam.controller;

import com.fam.model.Statistics;
import com.fam.model.User;
import com.fam.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {

    private final EventRepository eventRepository;
    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;

    public DashboardController(EventRepository eventRepository, TodoListRepository todoListRepository,
                            TaskRepository taskRepository, UserRepository userRepository,
                            StatisticsRepository statisticsRepository) {
        this.eventRepository = eventRepository;
        this.todoListRepository = todoListRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statisticsRepository = statisticsRepository;
    }

    // CALENDAR PAGE
    @RequestMapping(value = {"/calendar"}, method = RequestMethod.GET)
    public String dashBoardCalendar(){
        return "/calendar";
    }

    // TO DO LIST PAGE
    @RequestMapping(value = {"/todolists"}, method = RequestMethod.GET)
    public String dashBoardTodolist(){
        return "/todolists/todolist";
    }

    // INSTANT MESSAGE PAGE
    @RequestMapping(value = {"/instantmessage"}, method = RequestMethod.GET)
    public String dashBoardInstantMessage(){
        return "/instantmessage";
    }

    // STATISTICS PAGE
    @GetMapping("/statistics") public String dashBoardStats() {
        calculateAndSaveStatistics();
        return "/statistics";
    }

    // STATISTICS CALCULATIONS
    private void calculateAndSaveStatistics() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        long numEvents = eventRepository.countByCreator(currentUser);
        long numCompletedEvents = eventRepository.countByCreatorAndCompleted(currentUser, true);
        long numIncompleteEvents = numEvents - numCompletedEvents;
        double eventCompletionRatio = (double) numCompletedEvents / (double) numEvents;

        long numTodoLists = todoListRepository.countByCreatorId(currentUser.getId());
        long numCompletedTodoLists = todoListRepository.countByCreatorIdAndCompleted(currentUser.getId(), true);
        long numIncompleteTodoLists = numTodoLists - numCompletedTodoLists;
        double todoListCompletionRatio = (double) numCompletedTodoLists / (double) numTodoLists;

        long numTasks = taskRepository.countByTodoList_CreatorId(currentUser.getId());
        long numCompletedTasks = taskRepository.countByTodoList_CreatorIdAndCompleted(currentUser.getId(), true);
        long numIncompleteTasks = numTasks - numCompletedTasks;
        double taskCompletionRatio = (double) numCompletedTasks / (double) numTasks;

        Statistics statistics = new Statistics(
                numEvents,
                numCompletedEvents,
                numIncompleteEvents,
                eventCompletionRatio,
                numTodoLists,
                numCompletedTodoLists,
                numIncompleteTodoLists,
                todoListCompletionRatio,
                numTasks,
                numCompletedTasks,
                numIncompleteTasks,
                taskCompletionRatio
        );
        statistics.setUser(currentUser);
        statisticsRepository.save(statistics);
    }


    // MEALS PAGE
    @RequestMapping(value = {"/meals"}, method = RequestMethod.GET)
    public String dashBoardMeals(){
        return "/meals";
    }
}
