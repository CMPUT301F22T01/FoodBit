package com.CMPUT301F22T01.foodbit.models;

// User Story 01.01.01
// As a meal planner, I want to add an ingredient, with a brief description, best before date, location, amount, unit, and ingredient category
//
// Expected behaviour;
//  - an ingredient can be added with the use of the add button
//  - when button is pressed a screen will pop up asking the user to input the details of the ingredient being added, including the description, best before date, location, amount, unit, and ingredient category
//  - the cancel button can be pressed on the pop up to cancel the addition of the new ingredient
//  - finalization of the addition is done by pressing the add button on the pop up which will then add the new ingredient to the list

import java.util.Date;

public class Ingredient {
    private String description;
    private Date bestBefore;
    private String location;
    private Integer amount;
    private String unit;
    private String category;

    public Ingredient(){
        // Empty constructor
    }

    public Ingredient(String description, Integer amount, String unit, String category) {
        this.description = description;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }

    public Ingredient(String description, Date bestBefore, String location, Integer amount, String unit, String category) {
        this.description = description;
        this.bestBefore = bestBefore;
        this.location = location;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public String getLocation() {
        return location;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        return category;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setBestBefore(Date newBestBefore) {
        this.bestBefore = newBestBefore;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    public void setAmount(Integer newAmount) {
        this.amount = newAmount;
    }

    public void setUnit(String newUnit) {
        this.unit = newUnit;
    }

    public void setCategory(String newCategory) {
        this.category = newCategory;
    }
}
