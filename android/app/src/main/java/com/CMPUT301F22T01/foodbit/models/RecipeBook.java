package com.CMPUT301F22T01.foodbit.models;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeBook implements Serializable {
    private final ArrayList<Recipe> recipes;

    public RecipeBook() {
        recipes = new ArrayList<Recipe>();
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public int size() {
        return recipes.size();
    }

    public void add(Recipe recipe) {
        assert !recipes.contains(recipe): "This recipe is already in the recipe book!";
        recipes.add(recipe);
    }


}
