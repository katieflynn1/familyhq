package com.fam.controller;

import com.fam.model.FamilyGroup;
import com.fam.model.TodoList;
import com.fam.model.User;
import com.fam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping("/api/familygroups")
public class FamilyGroupController {
    private final FamilyGroupRepository familyGroupRepository;
    private final UserRepository userRepository;
    public FamilyGroupController( FamilyGroupRepository familyGroupRepository, UserRepository userRepository) {
        this.familyGroupRepository = familyGroupRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public RedirectView createFamilyGroup(@ModelAttribute("familyGroup") FamilyGroup familyGroup, @RequestParam("userId") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        familyGroup.setMembers(new HashSet<>());
        familyGroup.getMembers().add(user);
        FamilyGroup savedFamilyGroup = familyGroupRepository.save(familyGroup);

        return new RedirectView("/parent/familyGroup");
    }

    @PostMapping("/{id}/addMember")
    public RedirectView addMemberToFamilyGroup(@PathVariable("id") Long familyGroupId, @RequestParam("email") String email) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId)
                .orElseThrow(() -> new RuntimeException("Family Group not found"));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        familyGroup.getMembers().add(user);
        familyGroupRepository.save(familyGroup);

        return new RedirectView("/parent/familyGroup");
    }
}