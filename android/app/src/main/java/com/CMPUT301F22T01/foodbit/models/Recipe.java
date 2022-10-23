package com.CMPUT301F22T01.foodbit.models;

//Original user story:
//        As a meal planner, I want to add a recipe, with title, preparation time, number of servings, recipe category, comments, photograph, and list of ingredients.
//        Expected Behaviour:
//
//        When the add button is clicked, the add new recipe screen should appear and prompt the user to enter the following information:
//        Mandatory: title;
//        Optional: preparation time, number of servings, recipe category, comments, photograph, and list of ingredients.
//        When the user clicks done button, the recipe will be added to the database and shown on the recipe book screen.
//        When the user clicks back button, the addition is aborted.
//        Edge Case Behaviour:
//        Mandatory field not entered -> display an error message and highlight the corresponding field prompting the user to enter

import java.util.ArrayList;

public class Recipe {
     private String title;
     private int prepTime;
     private int numServings;
     private String category;
     private String comments;
     private String photo;
     private ArrayList<Ingredient> ingredients;

     public Recipe() {}

     public Recipe(String title, int prepTime, int numServings, String category, String comments, String photo, ArrayList<Ingredient> ingredients) {
        this.title = title;
        this.prepTime = prepTime;
        this.numServings = numServings;
        this.category = category;
        this.comments = comments;
        this.photo = photo;
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getNumServings() {
        return numServings;
    }

    public void setNumServings(int numServings) {
        this.numServings = numServings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
