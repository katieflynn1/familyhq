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
public class AdminController {

    private final EventRepository eventRepository;
    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;

    public AdminController(EventRepository eventRepository, TodoListRepository todoListRepository,
                                TaskRepository taskRepository, UserRepository userRepository,
                                StatisticsRepository statisticsRepository) {
        this.eventRepository = eventRepository;
        this.todoListRepository = todoListRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statisticsRepository = statisticsRepository;
    }

    @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
    public String adminDasboard(){ return "admin/dashboard";}

    @RequestMapping(value = {"/admin/calendar"}, method = RequestMethod.GET)
    public String adminCalendar(){
        return "admin/calendar";
    }

    @RequestMapping(value = {"/admin/todolists"}, method = RequestMethod.GET)
    public String adminTodolist(){
        return "admin/todolists/todolist";
    }

    @RequestMapping(value = {"/admin/instantmessage"}, method = RequestMethod.GET)
    public String adminInstantMessage(){
        return "admin/instantmessage";
    }

    // STATISTICS PAGE + CALCULATIONS
    @GetMapping("/admin/statistics") public String adminStats() {
        calculateAndSaveStatistics();
        return "admin/statistics";
    }

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

    @RequestMapping(value = {"/admin/budget"}, method = RequestMethod.GET)
    public String adminBudget(){
        return "admin/budget";
    }

    @RequestMapping(value = {"/admin/mealplan"}, method = RequestMethod.GET)
    public String adminMealPlan(){
        return "admin/mealplan";
    }

    @RequestMapping(value = {"/admin/friendlist"}, method = RequestMethod.GET)
    public String adminFriendList(){
        return "admin/friendlist/friends";
    }
}