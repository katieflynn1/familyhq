package com.fam.controller;

import com.fam.model.*;
import com.fam.repository.*;
import com.fam.service.FamilyGroupService;
import com.fam.service.InstantMessageService;
import com.fam.service.UserService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class DashboardController {

    private final EventRepository eventRepository;
    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;
    private final UserService userService;
    private final FamilyGroupService familyGroupService;
    private final InstantMessageService instantMessageService;

    public DashboardController(EventRepository eventRepository, TodoListRepository todoListRepository,
                            TaskRepository taskRepository, UserRepository userRepository,
                            StatisticsRepository statisticsRepository, UserService userService,
                            FamilyGroupService familyGroupService, InstantMessageService instantMessageService) {
        this.eventRepository = eventRepository;
        this.todoListRepository = todoListRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statisticsRepository = statisticsRepository;
        this.userService = userService;
        this.familyGroupService = familyGroupService;
        this.instantMessageService = instantMessageService;
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


    // FAMILY RECIPES PAGE
    @RequestMapping(value = {"/meals"}, method = RequestMethod.GET)
    public String dashBoardMeals(){
        return "/familyrecipes/family-recipes";
    }

    // FAMILY RECIPE METHODS
    @GetMapping("/tasty/familyrecipes/mealSearch")
    public String mealSearch(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        FamilyGroup familyGroup = user.getFamilyGroups().iterator().next(); // Get the first FamilyGroup from the Set
        model.addAttribute("familyGroupId", familyGroup.getId());

        return "familyrecipes/mealSearch";
    }

    // GET FAMILY FAVOURITE RECIPES PAGE
    @GetMapping("/tasty/familyrecipes/meals")
    public String meals(Model model, Principal principal) {
        return "familyrecipes/meals";
    }
}
