package com.CMPUT301F22T01.foodbit.models;

public class Ingredient {
    private String id;
    private String description;
    private String bestBefore;
    private String location;
    private float amount;
    private String unit;
    private String category;

    public Ingredient() {
        // Empty constructor
    }

    public Ingredient(String description, String bestBefore, String location, float amount, String unit, String category) {
        this.description = description;
        this.bestBefore = bestBefore;
        this.location = location;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }
    public Ingredient(String description, float amount, String unit, String category) {
        this.description = description;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }
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

    public void setAmount(float newAmount) {
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
}
