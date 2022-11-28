package com.CMPUT301F22T01.foodbit.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IngredientCategoryTest {
    private IngredientCategory mockCategory1() {
        return new IngredientCategory("meats", "id1");
    }

    @Test
    void getCategoryName() {
        assertEquals("meats", mockCategory1().getCategoryName());
    }

    @Test
    void setCategoryName() {
        IngredientCategory category = mockCategory1();
        assertEquals("meats", category.getCategoryName());
        category.setCategoryName("new name");
        assertEquals("new name", category.getCategoryName());
    }

    @Test
    void getId() {
        assertEquals("id1", mockCategory1().getId());
    }

    @Test
    void setId() {
        IngredientCategory category = mockCategory1();
        assertEquals("id1", category.getId());
        category.setId("new id");
        assertEquals("new id", category.getId());
    }
}
