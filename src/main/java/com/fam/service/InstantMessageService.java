package com.fam.service;

import com.fam.model.FamilyGroup;
import com.fam.model.InstantMessage;
import com.fam.model.User;
import com.fam.repository.FamilyGroupRepository;
import com.fam.repository.InstantMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InstantMessageService {

    private final InstantMessageRepository instantMessageRepository;
    private final FamilyGroupRepository familyGroupRepository;

    @Autowired
    public InstantMessageService(InstantMessageRepository instantMessageRepository, FamilyGroupRepository familyGroupRepository) {
        this.instantMessageRepository = instantMessageRepository;
        this.familyGroupRepository = familyGroupRepository;
    }

    public List<InstantMessage> findAllByFamilyGroupId(Long familyGroupId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId)
                .orElseThrow(() -> new RuntimeException("Family group not found"));
        return instantMessageRepository.findByFamilyGroupOrderByTimestampAsc(familyGroup);
    }

    public InstantMessage save(InstantMessage instantMessage) {
        Long familyGroupId = instantMessage.getFamilyGroup().getId();
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId)
                .orElseThrow(() -> new RuntimeException("Family group not found"));
        Set<User> members = familyGroup.getMembers();
        if (members.stream().noneMatch(u -> u.getEmail().equals(instantMessage.getSenderEmail()))) {
            throw new RuntimeException("Sender is not a member of the Family Group");
        }
        return instantMessageRepository.save(instantMessage);
    }

    public List<InstantMessage> getMessagesByFamilyGroupId(Long familyGroupId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId)
                .orElseThrow(() -> new RuntimeException("Family group not found"));
        return instantMessageRepository.findByFamilyGroupOrderByTimestampAsc(familyGroup);
    }

    public void saveInstantMessage(InstantMessage instantMessage) {
        instantMessageRepository.save(instantMessage);
    }

}