package com.fam.controller;

import com.fam.model.FamilyGroup;
import com.fam.model.FamilyRecipe;
import com.fam.model.User;
import com.fam.repository.FamilyGroupRepository;
import com.fam.repository.FamilyRecipeRepository;
import com.fam.repository.UserRepository;
import com.fam.service.TastyApiService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/tasty")
public class TastyApiController {

    private final FamilyRecipeRepository familyRecipeRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final UserRepository userRepository;
    public TastyApiController(FamilyRecipeRepository familyRecipeRepository,
                              FamilyGroupRepository familyGroupRepository,UserRepository userRepository) {
        this.familyRecipeRepository = familyRecipeRepository;
        this.familyGroupRepository = familyGroupRepository;
        this.userRepository = userRepository;
    }
    @Autowired
    private TastyApiService tastyApiService;

    // GET LIST OF RECIPES FROM SEARCH
    @GetMapping("/recipes/list")
    @ResponseBody
    public ResponseEntity<String> listRecipes(@RequestParam String query, @RequestParam int from, @RequestParam int size) throws Exception {
        String responseBody = tastyApiService.callTastyApi("/recipes/list?q=" + query + "&from=" + from + "&size=" + size);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }

    // GET INFO ABOUT RECIPES
    private static final Logger LOGGER = Logger.getLogger(TastyApiController.class.getName());
    @GetMapping("/recipes/get-more-info")
    public String getRecipeDetails(@RequestParam("id") String id, Model model) {
        try {
            String recipeDetails = tastyApiService.callTastyApi("/recipes/get-more-info?id=" + id);
            System.out.println("Recipe details: " + recipeDetails);

            JSONParser parser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
            JSONObject recipeJson = (JSONObject) parser.parse(recipeDetails);

            String name = recipeJson.getAsString("name");
            String thumbnailUrl = recipeJson.getAsString("thumbnail_url");

            JSONArray sections = (JSONArray) recipeJson.get("sections");
            List<String> ingredients = new ArrayList<>();
            if (sections != null) {
                for (Object sectionObj : sections) {
                    JSONObject section = (JSONObject) sectionObj;
                    JSONArray components = (JSONArray) section.get("components");
                    if (components != null) {
                        for (Object componentObj : components) {
                            JSONObject component = (JSONObject) componentObj;
                            String rawText = component.getAsString("raw_text");
                            if (rawText != null) {
                                ingredients.add(rawText);
                            }
                        }
                    }
                }
            }

            JSONArray instructions = (JSONArray) recipeJson.get("instructions");
            List<String> rawInstructions = new ArrayList<>();
            if (instructions != null) {
                for (Object obj : instructions) {
                    JSONObject instructionJson = (JSONObject) obj;
                    String rawText = instructionJson.getAsString("display_text");
                    rawInstructions.add(rawText);
                }
            }

            model.addAttribute("name", name);
            model.addAttribute("thumbnail_url", thumbnailUrl);
            if (ingredients != null) {
                model.addAttribute("ingredients", Arrays.asList(ingredients.toArray()));
            } else {
                model.addAttribute("ingredients", Collections.emptyList());
            }
            if (instructions != null) {
                model.addAttribute("instructions", Arrays.asList(rawInstructions.toArray()));
            } else {
                model.addAttribute("instructions", Collections.emptyList());
            }

            return "familyrecipes/recipeDetails";
        } catch (Exception e) {
            // Log the exception
            LOGGER.log(Level.SEVERE, "Error retrieving recipe details: " + e.getMessage(), e);
            model.addAttribute("errorMessage", "There was an error retrieving the recipe details. Please try again later.");
            return "error";
        }
    }

    // GET FAMILY RECIPES
    @GetMapping("/familyrecipes/meals-data")
    @ResponseBody
    public List<FamilyRecipe> getFamilyRecipesData(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        FamilyGroup familyGroup = user.getFamilyGroups().iterator().next();

        return familyRecipeRepository.findByFamilyGroup(familyGroup);
    }

    // ADD RECIPE TO FAMILY FAVOURITES
    @PostMapping("/familyrecipes/add")
    public ResponseEntity<String> addFamilyRecipe(@RequestParam String recipeTitle, @RequestParam String url, @RequestParam Long familyGroupId, Principal principal) {
        Optional<FamilyGroup> familyGroupOpt = familyGroupRepository.findById(familyGroupId);
        if (familyGroupOpt.isEmpty()) {
            return new ResponseEntity<>("Family group not found", HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        FamilyGroup familyGroup = familyGroupOpt.get();
        if (!familyGroup.getMembers().contains(user)) {
            return new ResponseEntity<>("You are not a member of this family group", HttpStatus.FORBIDDEN);
        }
        FamilyRecipe familyRecipe = new FamilyRecipe();
        familyRecipe.setRecipeTitle(recipeTitle);
        familyRecipe.setUrl(url);
        familyRecipe.setFamilyGroup(familyGroup);
        familyRecipeRepository.save(familyRecipe);

        return new ResponseEntity<>("Recipe added to the family recipes", HttpStatus.OK);
    }
}