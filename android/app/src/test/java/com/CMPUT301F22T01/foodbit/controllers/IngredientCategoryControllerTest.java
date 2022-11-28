package com.CMPUT301F22T01.foodbit.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.CMPUT301F22T01.foodbit.models.IngredientCategory;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class IngredientCategoryControllerTest {
    private IngredientCategory mockCategory(int choice) {
        switch (choice) {
            case 1:
                return new IngredientCategory("description1", "id1");
            case 2:
                return new IngredientCategory("description2", "id2");
            case 3:
                return new IngredientCategory("description3", "id3");
            case 4:
                return new IngredientCategory("description4", "id4");
        }
        return null;
    }

    private IngredientCategoryController mockCategoryList(int choice) {
        ArrayList<IngredientCategory> categories = new ArrayList<>();
        switch (choice) {
            case 1:
                categories.add(mockCategory(1));
                categories.add(mockCategory(2));
                return new IngredientCategoryController(categories);
            case 2:
                categories.add(mockCategory(3));
                categories.add(mockCategory(4));
                return new IngredientCategoryController(categories);
        }
        return null;
    }

    @Test
    void getCategories() {
        IngredientCategoryController ingredientCategoryController1 = mockCategoryList(1);
        assert ingredientCategoryController1 != null;
        ArrayList<IngredientCategory> categories1 = ingredientCategoryController1.getCategories();
        assertEquals("id1", categories1.get(0).getId());
        assertEquals("id2", categories1.get(1).getId());
        IngredientCategoryController ingredientCategoryController2 = mockCategoryList(2);
        assert ingredientCategoryController2 != null;
        ArrayList<IngredientCategory> categories2 = ingredientCategoryController2.getCategories();
        assertEquals("id3", categories2.get(0).getId());
        assertEquals("id4", categories2.get(1).getId());
    }

    @Test
    public void setCategories() {
        IngredientCategoryController ingredientCategoryController = mockCategoryList(1);
        IngredientCategory newCategory1 = mockCategory(3);
        IngredientCategory newCategory2 = mockCategory(4);
        ArrayList<IngredientCategory> newIngredientCategories = new ArrayList<>(Arrays.asList(newCategory1, newCategory2));
        assert ingredientCategoryController != null;
        ingredientCategoryController.setCategories(newIngredientCategories);
        assertTrue(ingredientCategoryController.contains(newCategory1));
        assertTrue(ingredientCategoryController.contains(newCategory2));
    }

    @Test
    void getDescriptions() {
        List<String> names = Objects.requireNonNull(mockCategoryList(1)).getCategoryDescription();
        assertTrue(names.contains("description1"));
        assertTrue(names.contains("description2"));
    }

    @Test
    void contains() {
        IngredientCategory ingredientCategory1 = mockCategory(1);
        IngredientCategory ingredientCategory2 = mockCategory(2);
        IngredientCategoryController ingredientCategoryController = new IngredientCategoryController(new ArrayList<>(Arrays.asList(ingredientCategory1, ingredientCategory2)));
        assertTrue(ingredientCategoryController.contains(ingredientCategory1));
        assertTrue(ingredientCategoryController.contains(ingredientCategory2));
    }
}
