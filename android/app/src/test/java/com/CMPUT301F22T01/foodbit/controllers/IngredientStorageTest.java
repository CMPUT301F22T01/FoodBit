package com.CMPUT301F22T01.foodbit.controllers;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.CMPUT301F22T01.foodbit.models.Ingredient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class IngredientStorageTest {
    private Ingredient mockIngredient(int choice) {
        switch (choice) {
            case 1:
                return new Ingredient("id", "description", "best before", "location", 1.0F, "unit", "category");
            case 2:
                return new Ingredient("id2", "description2", "best before2", "location2", 2.0F, "unit2", "category2");
            case 3:
                return new Ingredient("id3", "description3", "best before3", "location3", 3.0F, "unit3", "category3");
            case 4:
                return new Ingredient("id4", "description4", "best before4", "location4", 4.0F, "unit4", "category4");
        }
        return null;
    }

    private IngredientStorage mockIngredientStorage(int choice) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        switch (choice) {
            case 1:
                ingredients.add(mockIngredient(1));
                ingredients.add(mockIngredient(2));
                return new IngredientStorage(ingredients);
            case 2:
                ingredients.add(mockIngredient(3));
                ingredients.add(mockIngredient(4));
                return new IngredientStorage(ingredients);
        }
        return null;
    }

    @Test
    void getIngredients() {
        IngredientStorage ingredientStorage1 = mockIngredientStorage(1);
        assert ingredientStorage1 != null;
        ArrayList<Ingredient> ingredients1 = ingredientStorage1.getIngredients();
        assertEquals("id1", ingredients1.get(0).getId());
        assertEquals("id2", ingredients1.get(1).getId());
        IngredientStorage ingredientStorage2 = mockIngredientStorage(2);
        assert ingredientStorage2 != null;
        ArrayList<Ingredient> ingredients2 = ingredientStorage2.getIngredients();
        assertEquals("id3", ingredients2.get(0).getId());
        assertEquals("id4", ingredients2.get(1).getId());
    }

    @Test
    public void setIngredients() {
        IngredientStorage ingredientStorage = mockIngredientStorage(1);
        Ingredient newIngredient1 = mockIngredient(3);
        Ingredient newIngredient2 = mockIngredient(4);
        ArrayList<Ingredient> newIngredients = new ArrayList<>(Arrays.asList(newIngredient1, newIngredient2));
        assert ingredientStorage != null;
        ingredientStorage.setIngredients(newIngredients);
        assertTrue(ingredientStorage.contains(newIngredient1));
        assertTrue(ingredientStorage.contains(newIngredient2));
    }

    @Test
    void getRecipeByPosition() {
        IngredientStorage ingredientStorage = mockIngredientStorage(1);
        assert ingredientStorage != null;
        assertEquals("id1", ingredientStorage.getIngredientByPosition(0).getId());
        assertEquals("id2", ingredientStorage.getIngredientByPosition(1).getId());
    }

    @Test
    void getIngredientById() {
        IngredientStorage ingredientStorage = mockIngredientStorage(2);
        assert ingredientStorage != null;
        assertEquals("id3", ingredientStorage.getIngredientById("id3").getId());
        assertEquals("id4", ingredientStorage.getIngredientById("id4").getId());
    }

    @Test
    void getDescriptions() {
        List<String> names = Objects.requireNonNull(mockIngredientStorage(1)).getDescriptions();
        assertTrue(names.contains("title1"));
        assertTrue(names.contains("title2"));
    }

    @Test
    void contains() {
        Ingredient ingredient1 = mockIngredient(1);
        Ingredient ingredient2 = mockIngredient(2);
        IngredientStorage ingredientStorage = new IngredientStorage(new ArrayList<>(Arrays.asList(ingredient1, ingredient2)));
        assertTrue(ingredientStorage.contains(ingredient1));
        assertTrue(ingredientStorage.contains(ingredient2));
    }
}
