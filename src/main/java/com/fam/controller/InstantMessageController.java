package com.fam.controller;

import com.fam.model.FamilyGroup;
import com.fam.model.InstantMessage;
import com.fam.model.User;
import com.fam.repository.FamilyGroupRepository;
import com.fam.repository.UserRepository;
import com.fam.service.FamilyGroupService;
import com.fam.service.InstantMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class InstantMessageController {

    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final InstantMessageService instantMessageService;

    @Autowired
    public InstantMessageController(UserRepository userRepository, FamilyGroupRepository familyGroupRepository, InstantMessageService instantMessageService) {
        this.userRepository = userRepository;
        this.familyGroupRepository = familyGroupRepository;
        this.instantMessageService = instantMessageService;
    }

    @GetMapping("/instantmessage")
    public String showInstantMessagePage(Model model, Principal principal) {
        // Get the user
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get the family group of the current user
        FamilyGroup familyGroup = familyGroupRepository.findByMembersContaining(user)
                .orElseThrow(() -> new RuntimeException("User is not a member of any family group"));

        // Get all the messages for the family group chat
        List<InstantMessage> messages = instantMessageService.getMessagesByFamilyGroupId(familyGroup.getId());

        model.addAttribute("messages", messages);
        model.addAttribute("message", new InstantMessage());

        return "instantmessage";
    }

    @PostMapping("/instantmessage")
    public String sendMessage(Model model, Principal principal, @ModelAttribute InstantMessage instantMessage, CsrfToken csrfToken) {
        // Get the user
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get the family group of the current user
        Optional<FamilyGroup> familyGroup = familyGroupRepository.findByMembersContaining(user);
        if (familyGroup.isEmpty()) {
            // Redirect to create family group page
            return "redirect:/createfamilygroup";
        }

        // Set the sender's email
        instantMessage.setSenderEmail(user.getEmail());

        // Set the family group and timestamp
        instantMessage.setFamilyGroup(familyGroup.get());
        instantMessage.setTimestamp(LocalDateTime.now());

        instantMessageService.save(instantMessage);
        return "redirect:/instantmessage";
    }
}
