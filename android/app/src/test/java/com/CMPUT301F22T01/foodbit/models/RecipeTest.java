package com.CMPUT301F22T01.foodbit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import android.net.Uri;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RecipeTest {
    private Ingredient mockIngredient() {
        return new Ingredient("bread", 2F, "slice", "pantry");
    }

    private ArrayList<Ingredient> mockIngredientList() {
        ArrayList<Ingredient> mockIngredientList = new ArrayList<>();
        mockIngredientList.add(mockIngredient());
        return mockIngredientList;
    }

    private Recipe mockRecipe() {
        return new Recipe("id", "title", 0, 0, "category", "comments", Uri.parse("https://www.google.com"), mockIngredientList());
    }

    @Test
    void getTitle() {
        assertEquals("title", mockRecipe().getTitle());
    }

    @Test
    void setTitle() {
        Recipe recipe = mockRecipe();
        assertEquals("title", recipe.getTitle());
        recipe.setTitle("new title");
        assertEquals("new title", recipe.getTitle());
    }

    @Test
    void getPrepTime() {
        assertEquals(0, mockRecipe().getPrepTime());
    }

    @Test
    void setPrepTime() {
        Recipe recipe = mockRecipe();
        assertEquals(0, recipe.getPrepTime());
        recipe.setPrepTime(1);
        assertEquals(1, recipe.getPrepTime());
    }

    @Test
    void getNumServings() {
        assertEquals(0, mockRecipe().getNumServings());
    }

    @Test
    void setNumServings() {
        Recipe recipe = mockRecipe();
        assertEquals(0, recipe.getNumServings());
        recipe.setNumServings(1);
        assertEquals(1, recipe.getNumServings());
    }

    @Test
    void getCategory() {
        assertEquals("category", mockRecipe().getCategory());
    }

    @Test
    void setCategory() {
        Recipe recipe = mockRecipe();
        assertEquals("category", recipe.getCategory());
        recipe.setCategory("new category");
        assertEquals("new category", recipe.getCategory());
    }

    @Test
    void getComments() {
        assertEquals("comments", mockRecipe().getComments());
    }

    @Test
    void setComments() {
        Recipe recipe = mockRecipe();
        assertEquals("comments", recipe.getComments());
        recipe.setComments("new comments");
        assertEquals("new comments", recipe.getComments());
    }

    @Test
    void getPhoto() {
        assertEquals(Uri.parse("https://www.google.com"), mockRecipe().getPhoto());
    }

    @Test
    void setPhoto() {
        Recipe recipe = mockRecipe();
        assertEquals(Uri.parse("https://www.google.com"), recipe.getPhoto());
        recipe.setPhoto(Uri.parse("https://github.com"));
        assertSame(Uri.parse("https://github.com"), recipe.getPhoto());
    }

    @Test
    void getId() {
        assertEquals("id", mockRecipe().getId());
    }

    @Test
    void setId() {
        Recipe recipe = mockRecipe();
        assertEquals("id", recipe.getId());
        recipe.setId("new id");
        assertEquals("new id", recipe.getId());
    }

    @Test
    void getIngredients() {
        assertEquals("bread", mockRecipe().getIngredients().get(0).getDescription());
    }

    @Test
    void setIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(new Ingredient("new ingredient", (float) 0, null, null)));
        Recipe recipe = mockRecipe();
        assertEquals("bread", recipe.getIngredients().get(0).getDescription());
        recipe.setIngredients(ingredients);
        assertEquals("new ingredient", recipe.getIngredients().get(0).getDescription());
    }

    @Test
    void containsIngredient() {
        assertTrue(mockRecipe().containsIngredient(mockIngredient()));
    }
}