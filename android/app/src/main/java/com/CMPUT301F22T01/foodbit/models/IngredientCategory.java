package com.CMPUT301F22T01.foodbit.models;

import java.io.Serializable;

/**
 * Class to represent categories for ingredients
 * Categories can have a name and an id
 */
public class IngredientCategory implements Serializable, dbObject {
    private String categoryName;
    private String id;

    /**
     * Required empty constructor
     */
    public IngredientCategory() {
        // Empty constructor
    }

    /**
     * Creating an IngredientCategory object that can have a name and an id
     * @param categoryName name of the category
     * @param id id of the category
     */
    public IngredientCategory(String categoryName, String id) {
        this.categoryName = categoryName;
        this.id = id;
    }

    /**
     * Creating an IngredientCategory object that can have a name
     * @param categoryName name of the category
     */
    public IngredientCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getId() {
        return id;
    }

    public void setCategoryName(String newCategoryName) {
        this.categoryName = newCategoryName;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
