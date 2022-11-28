package com.CMPUT301F22T01.foodbit.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IngredientUnitTest {
    private IngredientUnit mockUnit1() {
        return new IngredientUnit("cups", "id1");
    }

    @Test
    void getUnitName() {
        assertEquals("cups", mockUnit1().getUnitName());
    }

    @Test
    void setUnitName() {
        IngredientUnit unit = mockUnit1();
        assertEquals("cups", unit.getUnitName());
        unit.setUnitName("new name");
        assertEquals("new name", unit.getUnitName());
    }

    @Test
    void getId() {
        assertEquals("id1", mockUnit1().getId());
    }

    @Test
    void setId() {
        IngredientUnit unit = mockUnit1();
        assertEquals("id1", unit.getId());
        unit.setId("new id");
        assertEquals("new id", unit.getId());
    }
}