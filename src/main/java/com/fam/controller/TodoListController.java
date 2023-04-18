package com.fam.controller;

import com.fam.model.Event;
import com.fam.model.TodoList;
import com.fam.model.User;
import com.fam.repository.TodoListRepository;
import com.fam.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    private TodoListRepository tr;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/lists")
    @ResponseBody
    public List<TodoList> list(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<TodoList> todoLists = tr.findByCreatorIdOrAssignedUserEmail(user.getId(), user.getEmail());
        return todoLists;
    }

    @GetMapping("/createForm")
    public String createForm(Model model) {
        model.addAttribute("todoList", new TodoList());
        return "admin/todolists/createForm";
    }

    @PostMapping("/create")
    public String createTodoList(@ModelAttribute("todoList") TodoList todoList, Principal principal) {
        Optional<User> creatorOptional = userRepository.findByEmail(principal.getName());
        User creator = creatorOptional.orElseThrow(() -> new RuntimeException("User not found")); // or handle the empty case differently
        todoList.setCreatorId(creator.getId());
        tr.save(todoList);
        return "redirect:/admin/todolists";
    }

    @GetMapping("/admin/todolists/edit/{id}")
    public String editTodoListForm(@PathVariable("id") Long id, Model model) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        model.addAttribute("todoList", todoList);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/todolists/editForm";
    }
    @PostMapping("/admin/todolists/edit")
    @Transactional
    public String editTodoList(@ModelAttribute TodoList todoList) {
        TodoList t = tr.findById(todoList.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + todoList.getId()));
        t.setTitle(todoList.getTitle());
        t.setCompleted(todoList.isCompleted());
        if (todoList.getAssignedUserEmail() != null) {
            t.setAssignedUserEmail(todoList.getAssignedUserEmail());
        }
        tr.save(t);
        return "redirect:/admin/todolists";
    }

    @RequestMapping(value = "/admin/todolists/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteTodoList(@PathVariable("id") Long id, Model model) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        tr.deleteById(id);
        return "redirect:/admin/todolists";
    }
}