package com.CMPUT301F22T01.foodbit.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IngredientTest {
    private Ingredient mockIngredient1() {
        return new Ingredient("id1","rice", "2023-12-31", "pantry", 4.0F, "lbs", "grains");
    }

    private Ingredient mockIngredient2() {
        return new Ingredient("id2","rice", "2023-12-31", "pantry", 4.0F, "lbs", "grains");
    }


    @Test
    void getDescription() {
        assertEquals("rice", mockIngredient1().getDescription());
    }

    @Test
    void setDescription() {
        Ingredient ingredient = mockIngredient1();
        assertEquals("rice", ingredient.getDescription());
        ingredient.setDescription("new description");
        assertEquals("new description", ingredient.getDescription());
    }

    @Test
    void getBestBefore() {
        assertEquals("2023-12-31", mockIngredient1().getBestBefore());
    }

    @Test
    void setBestBefore() {
        Ingredient ingredient = mockIngredient1();
        assertEquals("2023-12-31", ingredient.getBestBefore());
        ingredient.setBestBefore("2022-11-17");
        assertEquals("2022-11-17", ingredient.getBestBefore());
    }

    @Test
    void getLocation() {
        assertEquals("pantry", mockIngredient1().getLocation());
    }

    @Test
    void setLocation() {
        Ingredient ingredient = mockIngredient1();
        assertEquals("pantry", ingredient.getLocation());
        ingredient.setLocation("new location");
        assertEquals("new location", ingredient.getLocation());
    }

    @Test
    void getAmount() {
        assertEquals(4.0F, mockIngredient1().getAmount());
    }

    @Test
    void setAmount() {
        Ingredient ingredient = mockIngredient1();
        assertEquals(4.0F, ingredient.getAmount());
        ingredient.setAmount(1.0F);
        assertEquals(1.0F, ingredient.getAmount());
    }

    @Test
    void getUnit() {
        assertEquals("lbs", mockIngredient1().getUnit());
    }

    @Test
    void setUnit() {
        Ingredient ingredient = mockIngredient1();
        assertEquals("lbs", ingredient.getUnit());
        ingredient.setUnit("new unit");
        assertEquals("new unit", ingredient.getUnit());
    }

    @Test
    void getCategory() {
        assertEquals("grains", mockIngredient1().getCategory());
    }

    @Test
    void setCategory() {
        Ingredient ingredient = mockIngredient1();
        assertEquals("grains", ingredient.getCategory());
        ingredient.setCategory("new category");
        assertEquals("new category", ingredient.getCategory());
    }

    @Test
    void getId() {
        assertEquals("id", mockIngredient1().getId());
    }

    @Test
    void setId() {
        Ingredient ingredient = mockIngredient1();
        assertEquals("id", ingredient.getId());
        ingredient.setId("new id");
        assertEquals("new id", ingredient.getId());
    }

    @Test
    void update() {
        Ingredient ingredient = mockIngredient1();
        assertEquals("id1", ingredient.getId());
        ingredient.update(mockIngredient2());
        assertEquals("id2", ingredient.getId());
    }

    @Test
    public void isMissingDetails() {
        Ingredient ingredient = mockIngredient1();
        assertFalse(ingredient.isMissingDetails());
        ingredient.setDescription(null);
        assertTrue(ingredient.isMissingDetails());
    }
}
