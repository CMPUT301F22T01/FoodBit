package com.CMPUT301F22T01.foodbit.models;

import java.io.Serializable;

public class IngredientCategory implements Serializable, dbObject {
    private String categoryName;
    private String id;

    public IngredientCategory() {
        // Empty constructor
    }

    public IngredientCategory(String categoryName, String id) {
        this.categoryName = categoryName;
        this.id = id;
    }

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
