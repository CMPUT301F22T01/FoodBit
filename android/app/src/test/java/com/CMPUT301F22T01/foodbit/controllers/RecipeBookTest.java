package com.CMPUT301F22T01.foodbit.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.CMPUT301F22T01.foodbit.models.Recipe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class RecipeBookTest {
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

    private RecipeBook mockRecipeBook(int choice) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        switch (choice) {
            case 1:
                recipes.add(mockRecipe(1));
                recipes.add(mockRecipe(2));
                return new RecipeBook(recipes);
            case 2:
                recipes.add(mockRecipe(3));
                recipes.add(mockRecipe(4));
                return new RecipeBook(recipes);
        }
        return null;
    }

    @Test
    void getRecipes() {
        RecipeBook recipeBook1 = mockRecipeBook(1);
        assert recipeBook1 != null;
        ArrayList<Recipe> recipes1 = recipeBook1.getRecipes();
        assertEquals("id1", recipes1.get(0).getId());
        assertEquals("id2", recipes1.get(1).getId());
        RecipeBook recipeBook2 = mockRecipeBook(2);
        assert recipeBook2 != null;
        ArrayList<Recipe> recipes2 = recipeBook2.getRecipes();
        assertEquals("id3", recipes2.get(0).getId());
        assertEquals("id4", recipes2.get(1).getId());
    }

    @Test
    void getRecipeByPosition() {
        RecipeBook recipeBook = mockRecipeBook(1);
        assert recipeBook != null;
        assertEquals("id1", recipeBook.getRecipeByPosition(0).getId());
        assertEquals("id2", recipeBook.getRecipeByPosition(1).getId());
    }

    @Test
    void getRecipeById() {
        RecipeBook recipeBook = mockRecipeBook(2);
        assert recipeBook != null;
        assertEquals("id3", recipeBook.getRecipeById("id3").getId());
        assertEquals("id4", recipeBook.getRecipeById("id4").getId());
    }

    @Test
    void addException() {
        Recipe recipe = new Recipe("id", null, 0, 0, null, null, null, null);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        RecipeBook recipeBook = new RecipeBook(recipes);
        assertThrows(AssertionError.class, () -> recipeBook.add(recipe), "This recipe is already in the recipe book!");
    }

    @Test
    void update() {
        RecipeBook recipeBook = mockRecipeBook(1);
        assert recipeBook != null;
        recipeBook.setRecipes(new ArrayList<>(Arrays.asList(mockRecipe(3), mockRecipe(4))));
        assertEquals("id3", recipeBook.getRecipeByPosition(0).getId());
    }

    @Test
    void getTitles() {
        List<String> names = Objects.requireNonNull(mockRecipeBook(1)).getTitles();
        assertTrue(names.contains("title1"));
        assertTrue(names.contains("title2"));
    }
}