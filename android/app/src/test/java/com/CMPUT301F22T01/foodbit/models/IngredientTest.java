package com.CMPUT301F22T01.foodbit.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class IngredientTest {
    private Ingredient mockIngredient() {
        return new Ingredient("id","rice", "2023-12-31", "pantry", 4.0F, "lbs", "grains");
    }

    @Test
    void getDescription() {
        assertEquals("rice", mockIngredient().getDescription());
    }

    @Test
    void setDescription() {
        Ingredient ingredient = mockIngredient();
        assertEquals("rice", ingredient.getDescription());
        ingredient.setDescription("new description");
        assertEquals("new description", ingredient.getDescription());
    }

    @Test
    void getBestBefore() {
        assertEquals("2023-12-31", mockIngredient().getBestBefore());
    }

    @Test
    void setBestBefore() {
        Ingredient ingredient = mockIngredient();
        assertEquals("2023-12-31", ingredient.getBestBefore());
        ingredient.setBestBefore("2022-11-17");
        assertEquals("2022-11-17", ingredient.getBestBefore());
    }

    @Test
    void getLocation() {
        assertEquals("pantry", mockIngredient().getLocation());
    }

    @Test
    void setLocation() {
        Ingredient ingredient = mockIngredient();
        assertEquals("pantry", ingredient.getLocation());
        ingredient.setLocation("new location");
        assertEquals("new location", ingredient.getLocation());
    }

    @Test
    void getAmount() {
        assertEquals(4.0F, mockIngredient().getAmount());
    }

    @Test
    void setAmount() {
        Ingredient ingredient = mockIngredient();
        assertEquals(4.0F, ingredient.getAmount());
        ingredient.setAmount(1.0F);
        assertEquals(1.0F, ingredient.getAmount());
    }

    @Test
    void getUnit() {
        assertEquals("lbs", mockIngredient().getUnit());
    }

    @Test
    void setUnit() {
        Ingredient ingredient = mockIngredient();
        assertEquals("lbs", ingredient.getUnit());
        ingredient.setUnit("new unit");
        assertEquals("new unit", ingredient.getUnit());
    }

    @Test
    void getCategory() {
        assertEquals("grains", mockIngredient().getCategory());
    }

    @Test
    void setCategory() {
        Ingredient ingredient = mockIngredient();
        assertEquals("grains", ingredient.getCategory());
        ingredient.setUnit("new category");
        assertEquals("new category", ingredient.getCategory());
    }

    @Test
    void getId() {
        assertEquals("id", mockIngredient().getId());
    }

    @Test
    void setId() {
        Ingredient ingredient = mockIngredient();
        assertEquals("id", ingredient.getId());
        ingredient.setUnit("new id");
        assertEquals("new id", ingredient.getId());
    }
}
