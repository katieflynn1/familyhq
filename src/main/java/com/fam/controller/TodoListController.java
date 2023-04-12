package com.fam.controller;

import com.fam.model.Event;
import com.fam.model.TodoList;
import com.fam.model.User;
import com.fam.repository.TodoListRepository;
import com.fam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class TodoListController {

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/lists")
    @ResponseBody
    public List<TodoList> list(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<TodoList> todoLists = todoListRepository.findByCreatorIdOrAssignedUserEmail(user.getId(), user.getEmail());
        return todoLists;
    }

    @GetMapping("/createForm")
    public String createForm(Model model) {
        model.addAttribute("todoList", new TodoList());
        return "admin/todolists/createForm";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("todoList") TodoList todoList, Principal principal) {
        Optional<User> creatorOptional = userRepository.findByEmail(principal.getName());
        User creator = creatorOptional.orElseThrow(() -> new RuntimeException("User not found")); // or handle the empty case differently
        todoList.setCreatorId(creator.getId());
        todoListRepository.save(todoList);
        return "redirect:/admin/todolists/todolist";
    }

    @GetMapping("/admin/todolists/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        TodoList todoList = todoListRepository.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        model.addAttribute("todoList", todoList);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/todolists/editForm";
    }

    @PostMapping(value = "/admin/todolists/edit/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute TodoList todoList) {
        TodoList existingTodoList = todoListRepository.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        existingTodoList.setAssignedUserEmail(todoList.getAssignedUserEmail());
        existingTodoList.setCompleted(todoList.isCompleted());
        todoListRepository.save(existingTodoList);
        return "redirect:/admin/todolists/";
    }

}