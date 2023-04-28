package com.fam.controller;

import com.fam.model.FamilyGroup;
import com.fam.model.Goal;
import com.fam.model.User;
import com.fam.repository.FamilyGroupRepository;
import com.fam.repository.GoalRepository;
import com.fam.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ChildController {
    private final FamilyGroupRepository familyGroupRepository;
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    public ChildController( FamilyGroupRepository familyGroupRepository,
                            UserRepository userRepository, GoalRepository goalRepository) {
        this.familyGroupRepository = familyGroupRepository;
        this.userRepository = userRepository;
        this.goalRepository = goalRepository;
    }

    // CHILD DASHBOARD PAGE
    @RequestMapping(value = {"/child/dashboard"}, method = RequestMethod.GET)
    public String childDashboard(){
        return "child/dashboard";
    }

    // CHILD REWARD PAGE
    @RequestMapping(value = {"/child/reward"}, method = RequestMethod.GET)
    public String childReward(Principal principal, Model model){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        List<Goal> assignedGoals = goalRepository.findByAssignedUser(user);

        model.addAttribute("goals", assignedGoals);

        return "child/goals/list";
    }

    // CHILD FAMILY GROUP PAGE
    @RequestMapping(value = {"/child/familyGroup"}, method = RequestMethod.GET)
    public String childFamilyGroup(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is a member of any family group
        List<FamilyGroup> familyGroups = familyGroupRepository.findAllByMembers(user);
        if (!familyGroups.isEmpty()) {
            FamilyGroup familyGroup = familyGroups.get(0);
            model.addAttribute("familyGroupName", familyGroup.getGroupName());
            model.addAttribute("familyGroupMembers", familyGroup.getMembers());
            model.addAttribute("familyGroupId", familyGroup.getId());
            return "child/familygroup/familyGroup";
        } else {
            model.addAttribute("userId", user.getId());
            return "child/familygroup/noFamilyGroup";
        }
    }
}