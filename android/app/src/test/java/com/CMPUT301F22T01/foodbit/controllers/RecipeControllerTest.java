package com.CMPUT301F22T01.foodbit.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class RecipeControllerTest {
    private Recipe mockRecipe(int choice) {
        switch (choice) {
            case 1:
                return new Recipe("id1", "title1", 1, 1, "category1", "comments1", null, null);
            case 2:
                return new Recipe("id2", "title2", 2, 2, "category2", "comments2", null, null);
            case 3:
                return new Recipe("id3", "title3", 3, 3, "category3", "comments3", null, null);
            case 4:
                return new Recipe("id4", "title4", 4, 4, "category4", "comments4", null, null);
        }
        return null;
    }

    private RecipeController mockRecipeBook(int choice) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        switch (choice) {
            case 1:
                recipes.add(mockRecipe(1));
                recipes.add(mockRecipe(2));
                return new RecipeController(recipes);
            case 2:
                recipes.add(mockRecipe(3));
                recipes.add(mockRecipe(4));
                return new RecipeController(recipes);
        }
        return null;
    }
    private Ingredient mockIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId("ingredient1");
        return ingredient;
    }

    @Test
    void getRecipes() {
        RecipeController recipeController1 = mockRecipeBook(1);
        assert recipeController1 != null;
        ArrayList<Recipe> recipes1 = recipeController1.getRecipes();
        assertEquals("id1", recipes1.get(0).getId());
        assertEquals("id2", recipes1.get(1).getId());
        RecipeController recipeController2 = mockRecipeBook(2);
        assert recipeController2 != null;
        ArrayList<Recipe> recipes2 = recipeController2.getRecipes();
        assertEquals("id3", recipes2.get(0).getId());
        assertEquals("id4", recipes2.get(1).getId());
    }

    @Test
    public void setRecipes() {
        RecipeController recipeController = mockRecipeBook(1);
        Recipe newRecipe1 = mockRecipe(3);
        Recipe newRecipe2 = mockRecipe(4);
        ArrayList<Recipe> newRecipes = new ArrayList<>(Arrays.asList(newRecipe1, newRecipe2));
        assert recipeController != null;
        recipeController.setRecipes(newRecipes);
        assertTrue(recipeController.contains(newRecipe1));
        assertTrue(recipeController.contains(newRecipe2));
    }

    @Test
    void getRecipeByPosition() {
        RecipeController recipeController = mockRecipeBook(1);
        assert recipeController != null;
        assertEquals("id1", recipeController.getRecipeByPosition(0).getId());
        assertEquals("id2", recipeController.getRecipeByPosition(1).getId());
    }

    @Test
    void getRecipeById() {
        RecipeController recipeController = mockRecipeBook(2);
        assert recipeController != null;
        assertEquals("id3", recipeController.getRecipeById("id3").getId());
        assertEquals("id4", recipeController.getRecipeById("id4").getId());
    }

    @Test
    void getTitles() {
        List<String> names = Objects.requireNonNull(mockRecipeBook(1)).getTitles();
        assertTrue(names.contains("title1"));
        assertTrue(names.contains("title2"));
    }

    @Test
    void contains() {
        Recipe recipe1 = mockRecipe(1);
        Recipe recipe2 = mockRecipe(2);
        RecipeController recipeController = new RecipeController(new ArrayList<>(Arrays.asList(recipe1, recipe2)));
        assertTrue(recipeController.contains(recipe1));
        assertTrue(recipeController.contains(recipe2));
    }

    @Test
    void addException() {
        Recipe recipe = new Recipe("id", null, 0, 0, null, null, null, null);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        RecipeController recipeController = new RecipeController(recipes);
        assertThrows(AssertionError.class, () -> recipeController.add(recipe), "This recipe is already in the recipe book!");
    }

    @Test
    void removeException() {
        RecipeController recipeController = mockRecipeBook(1);
        assertThrows(AssertionError.class, () -> {
            assert recipeController != null;
            recipeController.remove(mockRecipe(3));
        }, "this recipe is not found in the recipe book!");
    }

    @Test
    public void containsIngredient() {
        Recipe recipe = mockRecipe(1);
        assert recipe != null;
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(mockIngredient());
        recipe.setIngredients(ingredients);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        RecipeController recipeController = new RecipeController(recipes);
        assertTrue(recipeController.containsIngredient(mockIngredient()));
    }
}