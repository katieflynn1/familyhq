package com.fam.controller;

import com.fam.model.Family;
import com.fam.model.FamilyRequest;
import com.fam.model.FamilyStatus;
import com.fam.model.User;
import com.fam.repository.FamilyRepository;
import com.fam.repository.FamilyRequestDTO;
import com.fam.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @GetMapping("/send")
    public String sendFamilyRequest(Model model) {
        model.addAttribute("familyRequest", new FamilyRequest());
        return "family/send";
    }

    @PostMapping("/send")
    public String sendFamilyRequest(@ModelAttribute("familyRequest") FamilyRequest familyRequest,
                                    @AuthenticationPrincipal User sender,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "family/send";
        }

        Optional<User> optionalReceiver = userRepository.findByEmail(familyRequest.getEmail());

        if (optionalReceiver.isEmpty()) {
            model.addAttribute("error", "User with email " + familyRequest.getEmail() + " does not exist.");
            return "family/send";
        }

        User receiver = optionalReceiver.get();

        if (receiver.equals(sender)) {
            model.addAttribute("error", "You cannot send a family request to yourself.");
            return "family/send";
        }

        Family existingFamily = familyRepository.findBySenderAndReceiver(sender, receiver);

        if (existingFamily != null) {
            model.addAttribute("error", "You already sent a family request to this user.");
            return "family/send";
        }

        Family family = new Family(sender, receiver, FamilyStatus.PENDING, familyRequest.getFamilyRole());
        familyRepository.save(family);

        return "redirect:/family/list";
    }

    @GetMapping("/list")
    public String familyList(Model model, @AuthenticationPrincipal User currentUser) {
        List<Family> familyRequests = familyRepository.findByReceiverAndStatus(currentUser, FamilyStatus.PENDING);
        model.addAttribute("familyRequests", familyRequests);
        return "family/list";
    }

    @PostMapping("/accept")
    public String acceptFamilyRequest(@RequestParam("familyId") Long familyId,
                                      @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        Optional<Family> optionalFamily = familyRepository.findById(familyId);

        if (optionalFamily.isEmpty()) {
            throw new EntityNotFoundException("Family request with id " + familyId + " does not exist.");
        }

        Family family = optionalFamily.get();

        if (!family.getReceiver().equals(currentUser)) {
            throw new AccessDeniedException("You are not authorized to accept this family request.");
        }

        family.setStatus(FamilyStatus.ACCEPTED);
        familyRepository.save(family);

        return "redirect:/family/list";
    }

    @PostMapping("/decline")
    public ResponseEntity<?> declineFamilyRequest(@RequestBody FamilyRequestDTO familyRequestDTO,
                                                  @AuthenticationPrincipal User currentUser) {
        Optional<Family> optionalFamily = familyRepository.findById(familyRequestDTO.getFamilyId());
        if (!optionalFamily.isPresent()) {
            return ResponseEntity.badRequest().body("Family request not found");
        }

        Family family = optionalFamily.get();
        if (!family.getReceiver().equals(currentUser)) {
            return ResponseEntity.badRequest().body("You are not authorized to decline this family request");
        }

        family.setStatus(FamilyStatus.DECLINED);
        familyRepository.save(family);
        return ResponseEntity.ok("Family request declined successfully");
    }

    @PostMapping("/assign-role")
    public ResponseEntity<?> assignFamilyRole(@RequestBody FamilyRequestDTO familyRequestDTO,
                                              @AuthenticationPrincipal User currentUser) {
        Optional<Family> optionalFamily = familyRepository.findById(familyRequestDTO.getFamilyId());
        if (!optionalFamily.isPresent()) {
            return ResponseEntity.badRequest().body("Family request not found");
        }

        Family family = optionalFamily.get();
        if (!family.getSender().equals(currentUser)) {
            return ResponseEntity.badRequest().body("You are not authorized to assign family roles for this request");
        }

        family.setFamilyRole(familyRequestDTO.getFamilyRole());
        familyRepository.save(family);
        return ResponseEntity.ok("Family role assigned successfully");
    }

}