package com.fam.repository;

public class FamilyRequestDTO {
    private Long familyId;
    private String familyRole;

    // getters and setters
    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }
    public String getFamilyRole() { return familyRole; }

    public void setFamilyRole(String familyRole) {
        this.familyRole = familyRole;
    }

}
