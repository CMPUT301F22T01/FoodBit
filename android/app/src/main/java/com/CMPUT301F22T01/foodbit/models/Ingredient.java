package com.CMPUT301F22T01.foodbit.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class to represent ingredients
 * Ingredients can have an id, description, bestBefore date, location, amount, unit, and category
 */
public class Ingredient implements Serializable, dbObject {
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
     * Creates a new Ingredient type with only a id and amount
     * @param id id of the ingredient
     * @param amount how many of the ingredient
     */
    public Ingredient(String id, float amount) {
        this.id = id;
        this.amount = amount;

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

    public static Comparator<Ingredient> nameAscending = new Comparator<Ingredient>() {
        @Override
        public int compare(Ingredient o1, Ingredient o2)
        {
            String desc1 = String.valueOf(o1.getDescription());
            String desc2 = String.valueOf(o2.getDescription());

            return String.CASE_INSENSITIVE_ORDER.compare(desc1,desc2);
        }
    };

    public static Comparator<Ingredient> dateSort = new Comparator<Ingredient>() {
        @Override
        public int compare(Ingredient o1, Ingredient o2)
        {
            return o1.getBestBefore().compareTo(o2.bestBefore);
        }
    };
}
