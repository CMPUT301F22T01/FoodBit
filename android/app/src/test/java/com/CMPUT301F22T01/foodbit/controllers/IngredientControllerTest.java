package com.CMPUT301F22T01.foodbit.controllers;


import static org.junit.jupiter.api.Assertions.*;

import com.CMPUT301F22T01.foodbit.models.Ingredient;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class IngredientControllerTest {
    private Ingredient mockIngredient(int choice) {
        switch (choice) {
            case 1:
                return new Ingredient("id1", "description1", "best before1", "location1", 1.0F, "unit1", "category1");
            case 2:
                return new Ingredient("id2", "description2", "best before2", "location2", 2.0F, "unit2", "category2");
            case 3:
                return new Ingredient("id3", "description3", "best before3", "location3", 3.0F, "unit3", "category3");
            case 4:
                return new Ingredient("id4", "description4", "best before4", "location4", 4.0F, "unit4", "category4");
        }
        return null;
    }

    private IngredientController mockIngredientStorage(int choice) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        switch (choice) {
            case 1:
                ingredients.add(mockIngredient(1));
                ingredients.add(mockIngredient(2));
                return new IngredientController(ingredients);
            case 2:
                ingredients.add(mockIngredient(3));
                ingredients.add(mockIngredient(4));
                return new IngredientController(ingredients);
        }
        return null;
    }

    @Test
    void getIngredients() {
        IngredientController ingredientController1 = mockIngredientStorage(1);
        assert ingredientController1 != null;
        ArrayList<Ingredient> ingredients1 = ingredientController1.getIngredients();
        assertEquals("id1", ingredients1.get(0).getId());
        assertEquals("id2", ingredients1.get(1).getId());
        IngredientController ingredientController2 = mockIngredientStorage(2);
        assert ingredientController2 != null;
        ArrayList<Ingredient> ingredients2 = ingredientController2.getIngredients();
        assertEquals("id3", ingredients2.get(0).getId());
        assertEquals("id4", ingredients2.get(1).getId());
    }

    @Test
    public void setIngredients() {
        IngredientController ingredientController = mockIngredientStorage(1);
        Ingredient newIngredient1 = mockIngredient(3);
        Ingredient newIngredient2 = mockIngredient(4);
        ArrayList<Ingredient> newIngredients = new ArrayList<>(Arrays.asList(newIngredient1, newIngredient2));
        assert ingredientController != null;
        ingredientController.setIngredients(newIngredients);
        assertTrue(ingredientController.contains(newIngredient1));
        assertTrue(ingredientController.contains(newIngredient2));
    }

    @Test
    void getIngredientByPosition() {
        IngredientController ingredientController = mockIngredientStorage(1);
        assert ingredientController != null;
        assertEquals("id1", ingredientController.getIngredientByPosition(0).getId());
        assertEquals("id2", ingredientController.getIngredientByPosition(1).getId());
    }

    @Test
    void getIngredientById() {
        IngredientController ingredientController = mockIngredientStorage(2);
        assert ingredientController != null;
        assertEquals("id3", ingredientController.getIngredientById("id3").getId());
        assertEquals("id4", ingredientController.getIngredientById("id4").getId());
    }

    @Test
    void getDescriptions() {
        List<String> names = Objects.requireNonNull(mockIngredientStorage(1)).getDescriptions();
        assertTrue(names.contains("description1"));
        assertTrue(names.contains("description2"));
    }

    @Test
    void contains() {
        Ingredient ingredient1 = mockIngredient(1);
        Ingredient ingredient2 = mockIngredient(2);
        IngredientController ingredientController = new IngredientController(new ArrayList<>(Arrays.asList(ingredient1, ingredient2)));
        assertTrue(ingredientController.contains(ingredient1));
        assertTrue(ingredientController.contains(ingredient2));
    }
}
