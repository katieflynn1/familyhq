package com.fam.controller;

import com.fam.model.Task;
import com.fam.model.TodoList;
import com.fam.model.User;
import com.fam.repository.TaskRepository;
import com.fam.repository.TodoListRepository;
import com.fam.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    // GET ALL TODO LISTS
    @GetMapping("/lists")
    @ResponseBody
    public List<TodoList> list(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<TodoList> todoLists = tr.findByCreatorIdOrAssignedUserEmail(user.getId(), user.getEmail());
        return todoLists;
    }

    // CREATE NEW TODOLIST FORM
    @GetMapping("/createForm")
    public String createForm(Model model) {
        model.addAttribute("todoList", new TodoList());
        return "admin/todolists/createForm";
    }

    // CREATE NEW TODO LIST
    @PostMapping("/create")
    public String createTodoList(@ModelAttribute("todoList") TodoList todoList, Principal principal) {
        Optional<User> creatorOptional = userRepository.findByEmail(principal.getName());
        User creator = creatorOptional.orElseThrow(() -> new RuntimeException("User not found")); // or handle the empty case differently
        todoList.setCreatorId(creator.getId());
        tr.save(todoList);
        return "redirect:/admin/todolists";
    }

    // EDIT TODO LIST FORM
    @GetMapping("/admin/todolists/edit/{id}")
    public String editTodoListForm(@PathVariable("id") Long id, Model model) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        model.addAttribute("todoList", todoList);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/todolists/editForm";
    }

    // EDIT TODO LIST
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

    // DELETE TODO LIST
    @RequestMapping(value = "/admin/todolists/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteTodoList(@PathVariable("id") Long id, Model model) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        tr.deleteById(id);
        return "redirect:/admin/todolists";
    }

    // TASK METHODS
    // GET TASKS FOR THE TODO LIST
    @GetMapping("/admin/todolists/taskPage/{id}")
    public String getTasksForTodoList(@PathVariable("id") Long id, Model model) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        List<Task> tasks = taskRepository.findByTodoList(todoList);
        model.addAttribute("tasks", tasks);
        model.addAttribute("todoList", todoList);
        return "admin/todolists/taskPage";
    }

    // ADD NEW TASK
    @PostMapping("/admin/todolists/{id}/tasks")
    public String addTask(@PathVariable("id") Long id, @RequestParam String description) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));

        Task task = new Task();
        task.setDescription(description);
        task.setCompleted(false);
        task.setTodoList(todoList);
        taskRepository.save(task);
        return "redirect:/admin/todolists/taskPage/" + id;
    }

    // EDIT TASK
    @PostMapping("/admin/todolists/{id}/tasks/{taskId}")
    public String editTask(@PathVariable("id") Long id, @PathVariable("taskId") Long taskId, @RequestParam String description, @RequestParam(required = false) boolean completed) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDescription(description);
        task.setCompleted(completed);
        taskRepository.save(task);
        return "redirect:/admin/todolists/taskPage/" + id;
    }

    // DELETE TASK
    @DeleteMapping("/admin/todolists/{id}/tasks/{taskId}")
    @ResponseBody
    public String deleteTask(@PathVariable("id") Long id, @PathVariable("taskId") Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
        return "redirect:/admin/todolists/taskPage/" + id;
    }
}