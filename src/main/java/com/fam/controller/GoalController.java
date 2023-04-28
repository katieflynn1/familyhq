package com.fam.controller;

import com.fam.model.Goal;
import com.fam.model.User;
import com.fam.repository.GoalRepository;
import com.fam.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;

    // CREATE AND ASSIGN GOAL
    @PostMapping
    public String createAndAssignGoal(@RequestParam String title, @RequestParam int pointsNeeded, @RequestParam Long childId, Principal principal) {
        User creator = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        User child = userRepository.findById(childId).orElseThrow(() -> new RuntimeException("User not found"));
        Goal goal = new Goal(title, creator, child, pointsNeeded, 0, false);
        goalRepository.save(goal);
        return "redirect:/parent/reward";
    }

    // DELETE GOAL
    @PostMapping("/{goalId}/delete")
    public String deleteGoal(@PathVariable("goalId") Long goalId, HttpServletRequest request) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal not found"));
        goalRepository.delete(goal);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
