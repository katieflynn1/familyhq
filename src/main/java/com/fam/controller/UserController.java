package com.fam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping(value = {"/user/dashboard"}, method = RequestMethod.GET)
    public String userDashboard(){
        return "user/dashboard";
    }

    @RequestMapping(value = {"/user/calendar"}, method = RequestMethod.GET)
    public String userCalendar(){
        return "user/calendar";
    }

    @RequestMapping(value = {"/user/todolist"}, method = RequestMethod.GET)
    public String userTodolist(){
        return "user/todolists/todolist";
    }

    @RequestMapping(value = {"/user/instantmessage"}, method = RequestMethod.GET)
    public String userInstantMessage(){
        return "user/instantmessage";
    }

    @RequestMapping(value = {"/user/stats"}, method = RequestMethod.GET)
    public String userStats(){
        return "user/stats";
    }

    @RequestMapping(value = {"/user/budget"}, method = RequestMethod.GET)
    public String userBudget(){
        return "user/budget";
    }

    @RequestMapping(value = {"/user/mealplan"}, method = RequestMethod.GET)
    public String userMealPlan(){
        return "user/mealplan";
    }

    @RequestMapping(value = {"/user/friendlist"}, method = RequestMethod.GET)
    public String userFriendList(){
        return "user/friendlist/friendlist";
    }
}