package com.fam.controller;

import com.fam.model.FamilyGroup;
import com.fam.model.User;
import com.fam.repository.FamilyGroupRepository;
import com.fam.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
public class ParentController {
    private final FamilyGroupRepository familyGroupRepository;
    private final UserRepository userRepository;
    public ParentController( FamilyGroupRepository familyGroupRepository, UserRepository userRepository) {
        this.familyGroupRepository = familyGroupRepository;
        this.userRepository = userRepository;
    }

    // PARENT DASHBOARD PAGE
    @RequestMapping(value = {"/parent/dashboard"}, method = RequestMethod.GET)
    public String parentDasboard(){ return "parent/dashboard";}

    // PARENT REWARDS PAGE
    @RequestMapping(value = {"/parent/reward"}, method = RequestMethod.GET)
    public String parentReward(){
        return "parent/reward";
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