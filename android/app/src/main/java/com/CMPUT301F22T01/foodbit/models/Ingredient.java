package com.CMPUT301F22T01.foodbit.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Class to represent ingredients
 * Ingredients can have an id, description, bestBefore date, location, amount, unit, and category
 */
public class Ingredient implements Serializable {
    private String id;
    private String description;
    private String bestBefore;
    private String location;
    private Float amount;
    private String unit;
    private String category;

    public Ingredient() {
        // Empty constructor
    }

    /**
     * Creates a new Ingredient type with a description, bestBefore date, location, amount, unit, and category
     * @param description the description/title of the ingredient
     * @param bestBefore the expiry/best before date of the ingredient
     * @param location where the ingredient is stored
     * @param amount how many of the ingredient
     * @param unit the units that amount is recorded in
     * @param category they type of the ingredient
     */
    public Ingredient(String description, String bestBefore, String location, float amount, String unit, String category) {
        this.description = description;
        this.bestBefore = bestBefore;
        this.location = location;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }

    /**
     * Creates a new Ingredient type with only a description, amount, unit, and category
     * @param description the description/title of the ingredient
     * @param amount how many of the ingredient
     * @param unit the units that amount is recorded in
     * @param category the type of ingredient
     */
    public Ingredient(String description, float amount, String unit, String category) {
        this.description = description;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }

    /**
     * Creates a new Ingredient type with an id, description, bestBefore date, location, amount, unit, category
     * @param id number to identity the ingredient
     * @param description the description/title of the ingredient
     * @param bestBefore the expiry/best before date of the ingredient
     * @param location where the ingredient is stored
     * @param amount how many of the ingredient
     * @param unit the units that amount is recorded in
     * @param category they type of the ingredient
     */
    public Ingredient(String id, String description, String bestBefore, String location, float amount, String unit, String category) {
        this.id = id;
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

    public String getBestBefore() {
        return bestBefore;
    }

    public String getLocation() {
        return location;
    }

    public float getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setBestBefore(String newBestBefore) {
        this.bestBefore = newBestBefore;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    public void setAmount(Float newAmount) {
        this.amount = newAmount;
    }

    public void setUnit(String newUnit) {
        this.unit = newUnit;
    }

    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    /**
     * Updates the information of this ingredient with information from another ingredient.
     * @param newIngredient the ingredient containing the new information
     */
    public void update(@NonNull Ingredient newIngredient) {
        id = newIngredient.getId();
        description = newIngredient.getDescription();
        bestBefore = newIngredient.getBestBefore();
        location = newIngredient.getLocation();
        amount = newIngredient.getAmount();
        unit = newIngredient.getUnit();
        category = newIngredient.getCategory();
    }
}
