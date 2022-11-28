package com.CMPUT301F22T01.foodbit.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IngredientLocationTest {
    private IngredientLocation mockLocation1() {
        return new IngredientLocation("store", "id1");
    }

    @Test
    void getLocationName() {
        assertEquals("store", mockLocation1().getLocationName());
    }

    @Test
    void setLocationName() {
        IngredientLocation location = mockLocation1();
        assertEquals("store", location.getLocationName());
        location.setLocationName("new name");
        assertEquals("new name", location.getLocationName());
    }

    @Test
    void getId() {
        assertEquals("id1", mockLocation1().getId());
    }

    @Test
    void setId() {
        IngredientLocation location = mockLocation1();
        assertEquals("id1", location.getId());
        location.setId("new id");
        assertEquals("new id", location.getId());
    }
}
