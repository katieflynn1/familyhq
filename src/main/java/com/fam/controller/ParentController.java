package com.fam.controller;

import com.fam.model.FamilyGroup;
import com.fam.model.Goal;
import com.fam.model.Role;
import com.fam.model.User;
import com.fam.repository.FamilyGroupRepository;
import com.fam.repository.GoalRepository;
import com.fam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ParentController {
    private final FamilyGroupRepository familyGroupRepository;
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    public ParentController( FamilyGroupRepository familyGroupRepository,
                             UserRepository userRepository, GoalRepository goalRepository) {
        this.familyGroupRepository = familyGroupRepository;
        this.userRepository = userRepository;
        this.goalRepository = goalRepository;
    }

    // PARENT DASHBOARD PAGE
    @RequestMapping(value = {"/parent/dashboard"}, method = RequestMethod.GET)
    public String parentDasboard(){ return "parent/dashboard";}

    // PARENT REWARDS PAGE
    @RequestMapping(value = {"/parent/reward"}, method = RequestMethod.GET)
    public String parentReward(Principal principal, Model model) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<Goal> createdGoals = goalRepository.findByCreator(user);

        // Fetch family members with the role CHILD
        Role childRole = Role.valueOf(Role.CHILD.name());
        Set<FamilyGroup> familyGroups = user.getFamilyGroups();
        List<User> children = new ArrayList<>();
        for (FamilyGroup familyGroup : familyGroups) {
            List<User> familyGroupChildren = userRepository.findByRoleAndFamilyGroups(childRole, familyGroup);
            children.addAll(familyGroupChildren);
        }

        model.addAttribute("goals", createdGoals);
        model.addAttribute("children", children); // Add children to the model
        return "parent/goals/list";
    }

    // PARENT FAMILY GROUP PAGE
    @RequestMapping(value = {"/parent/familyGroup"}, method = RequestMethod.GET)
    public String parentFamilyGroup(Model model) {
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
            return "parent/familygroup/familyGroup";
        } else {
            model.addAttribute("userId", user.getId());
            return "parent/familygroup/noFamilyGroup";
        }
    }
}