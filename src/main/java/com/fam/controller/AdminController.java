package com.fam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

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

    @RequestMapping(value = {"/admin/stats"}, method = RequestMethod.GET)
    public String adminStats(){
        return "admin/stats";
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
        return "admin/friendlist";
    }
}