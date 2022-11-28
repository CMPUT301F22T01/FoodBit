package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.ui.RecipeAddFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provide controls to a list of <code>Recipe</code> class objects.
 */
public class RecipeController implements Serializable {
    private DatabaseController db = new DatabaseController("Recipe Book");
    private final ArrayList<Recipe> recipes;

    /**
     * Constructs an empty <code>RecipeController</code>.
     */
    public RecipeController() {
        recipes = new ArrayList<Recipe>();
    }

    /**
     * Constructs a recipe book with an initial list of recipes.
     * @param recipes the initial list of recipes
     */
    public RecipeController(List<Recipe> recipes) {
        this.recipes = (ArrayList<Recipe>) recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
    }

    /**
     * Get a recipe by its index in the list.
     * @param position the index of the recipe
     * @return the recipe at the position
     */
    public Recipe getRecipeByPosition(int position) {
        return recipes.get(position);
    }

    /**
     * Get a recipe by its id.
     * @param id the id of the recipe
     * @return the recipe with the id
     */
    public Recipe getRecipeById(String id) {
        for (Recipe recipe : recipes) {
            if (Objects.equals(id, recipe.getId())) {
                return recipe;
            }
        }
        return null;
    }

    /**
     * Get a list of titles of all the recipes in the recipe book.
     * @return a list of titles of all the recipes in the recipe book
     */
    public List<String> getTitles() {
        List<String> list = new ArrayList<>();
        for (Recipe recipe : recipes) {
            list.add(recipe.getTitle());
        }
        return list;
    }

    /**
     * Returns true if the recipe book contains the recipe.
     * @param recipe recipe whose presence in this recipe book is to be tested
     * @return whether if the recipe book contains the recipe
     */
    public boolean contains(Recipe recipe) {
        return recipes.contains(recipe);
    }

    /**
     * Add a recipe to the recipe book and add the recipe data to the Firestore database.
     * @param recipe the recipe to be added
     * @throws AssertionError the recipe is already present in the recipe book
     */
    public void add(Recipe recipe) {
        String TAG = RecipeAddFragment.TAG;
        assert !contains(recipe) : "This recipe is already in the recipe book!";
        db.addItem(recipe);
    }

    public void edit(Recipe recipe) {
        db.editItem(recipe);
    }

    /**
     * Remove the recipe from the recipe book if it is present.
     * @param recipe the recipe to be removed from the recipe book if it is present
     * @throws AssertionError the recipe is not found in the recipe book
     */
    public void remove(Recipe recipe) {
        String TAG = "RecipeBookDeleteRecipe";
        assert contains(recipe) : "this recipe is not found in the recipe book!";
        db.deleteItem(recipe);
    }
}
