package com.fam.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
@Entity
@Table(name = "family_recipes")
public class FamilyRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_title")
    private String recipeTitle;

    @Column(name = "url")
    private String url;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "family_group_id")
    private FamilyGroup familyGroup;

    public FamilyRecipe(Long id, String recipeTitle, String url, FamilyGroup familyGroup) {
        this.id = id;
        this.recipeTitle = recipeTitle;
        this.url = url;
        this.familyGroup = familyGroup;
    }

    public FamilyRecipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FamilyGroup getFamilyGroup() {
        return familyGroup;
    }

    public void setFamilyGroup(FamilyGroup familyGroup) {
        this.familyGroup = familyGroup;
    }
}