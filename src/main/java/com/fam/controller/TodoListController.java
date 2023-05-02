package com.fam.controller;

import com.fam.model.Goal;
import com.fam.model.Task;
import com.fam.model.TodoList;
import com.fam.model.User;
import com.fam.repository.GoalRepository;
import com.fam.repository.TaskRepository;
import com.fam.repository.TodoListRepository;
import com.fam.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private GoalRepository goalRepository;

    // GET ALL TODO LISTS
    @GetMapping("/api/lists")
    @ResponseBody
    public List<TodoList> list(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<TodoList> todoLists = tr.findByCreatorIdOrAssignedUserEmail(user.getId(), user.getEmail());
        return todoLists;
    }

    // CREATE NEW TODOLIST FORM
    @GetMapping("/api/createForm")
    public String createForm(Model model) {
        model.addAttribute("todoList", new TodoList());
        return "todolists/createForm";
    }

    // CREATE NEW TODO LIST
    @PostMapping("/api/create")
    public String createTodoList(@ModelAttribute("todoList") TodoList todoList, Principal principal, Model model) {
        if (todoList.getAssignedUserEmail() == null || todoList.getAssignedUserEmail().isEmpty()) {
            model.addAttribute("errorMessage", "You must assign a user, even if it's yourself.");
            return "todolists/createForm";
        }

        Optional<User> creatorOptional = userRepository.findByEmail(principal.getName());
        User creator = creatorOptional.orElseThrow(() -> new RuntimeException("User not found")); // or handle the empty case differently
        todoList.setCreatorId(creator.getId());
        tr.save(todoList);
        return "redirect:/todolists";
    }

    // EDIT TODO LIST FORM
    @GetMapping("/api/todolists/edit/{id}")
    public String editTodoListForm(@PathVariable("id") Long id, Model model) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        model.addAttribute("todoList", todoList);
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "todolists/editForm";
    }

    // EDIT TODO LIST
    @PostMapping("/api/todolists/edit")
    @Transactional
    public String editTodoList(@ModelAttribute TodoList todoList) {
        TodoList t = tr.findById(todoList.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + todoList.getId()));
        t.setTitle(todoList.getTitle());
        t.setCompleted(todoList.isCompleted());
        if (todoList.getAssignedUserEmail() != null) {
            t.setAssignedUserEmail(todoList.getAssignedUserEmail());
        }
        tr.save(t);
        return "redirect:/todolists";
    }

    // DELETE TODO LIST
    @RequestMapping(value = "/api/todolists/delete/{id}", method = RequestMethod.POST)
    public String deleteTodoList(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        tr.deleteById(id);
        //String referer = request.getHeader("Referer");
        return null;
    }

    // TASK METHODS
    // GET TASKS FOR THE TODO LIST
    @GetMapping("/api/todolists/taskPage/{id}")
    public String getTasksForTodoList(@PathVariable("id") Long id, Model model) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));
        List<Task> tasks = taskRepository.findByTodoList(todoList);
        model.addAttribute("tasks", tasks);
        model.addAttribute("todoList", todoList);
        return "todolists/taskPage";
    }

    // ADD NEW TASK
    @PostMapping("/api/todolists/{id}/tasks")
    public String addTask(@PathVariable("id") Long id, @RequestParam String description, HttpServletRequest request) {
        TodoList todoList = tr.findById(id).orElseThrow(() -> new RuntimeException("TodoList not found"));

        Task task = new Task();
        task.setDescription(description);
        task.setCompleted(false);
        task.setTodoList(todoList);
        taskRepository.save(task);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // EDIT TASK
    @PostMapping("/api/todolists/{id}/tasks/{taskId}")
    public String editTask(@PathVariable("id") Long id, @PathVariable("taskId") Long taskId,
                           @RequestParam String description, @RequestParam(required = false) boolean completed,
                           HttpServletRequest request, Principal principal) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDescription(description);

        boolean wasCompleted = task.isCompleted();
        task.setCompleted(completed);

        if (!wasCompleted && completed) {
            TodoList todoList = task.getTodoList();
            User assignedUser = userRepository.findByEmail(todoList.getAssignedUserEmail())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));

            List<Goal> childGoals = goalRepository.findByAssignedUser(assignedUser);
            for (Goal goal : childGoals) {
                goal.setPointsEarned(goal.getPointsEarned() + 10);
                if (goal.getPointsEarned() >= goal.getPointsNeeded()) {
                    goal.setCompleted(true);
                }
                goalRepository.save(goal);
            }
        }

        taskRepository.save(task);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // DELETE TASK
    @DeleteMapping("/api/todolists/{id}/tasks/{taskId}")
    @ResponseBody
    public String deleteTask(@PathVariable("id") Long id, @PathVariable("taskId") Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
        return "redirect:/todolists/taskPage/" + id;
    }
}