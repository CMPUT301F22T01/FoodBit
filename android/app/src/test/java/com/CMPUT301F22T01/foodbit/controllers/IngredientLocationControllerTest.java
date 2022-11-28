package com.CMPUT301F22T01.foodbit.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.CMPUT301F22T01.foodbit.models.IngredientLocation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class IngredientLocationControllerTest {
    private IngredientLocation mockLocation(int choice) {
        switch (choice) {
            case 1:
                return new IngredientLocation("description1", "id1");
            case 2:
                return new IngredientLocation("description2", "id2");
            case 3:
                return new IngredientLocation("description3", "id3");
            case 4:
                return new IngredientLocation("description4", "id4");
        }
        return null;
    }

    private IngredientLocationController mockLocationList(int choice) {
        ArrayList<IngredientLocation> locations = new ArrayList<>();
        switch (choice) {
            case 1:
                locations.add(mockLocation(1));
                locations.add(mockLocation(2));
                return new IngredientLocationController(locations);
            case 2:
                locations.add(mockLocation(3));
                locations.add(mockLocation(4));
                return new IngredientLocationController(locations);
        }
        return null;
    }

    @Test
    void getLocations() {
        IngredientLocationController ingredientLocationController1 = mockLocationList(1);
        assert ingredientLocationController1 != null;
        ArrayList<IngredientLocation> locations1 = ingredientLocationController1.getLocations();
        assertEquals("id1", locations1.get(0).getId());
        assertEquals("id2", locations1.get(1).getId());
        IngredientLocationController ingredientLocationController2 = mockLocationList(2);
        assert ingredientLocationController2 != null;
        ArrayList<IngredientLocation> locations2 = ingredientLocationController2.getLocations();
        assertEquals("id3", locations2.get(0).getId());
        assertEquals("id4", locations2.get(1).getId());
    }

    @Test
    public void setLocations() {
        IngredientLocationController ingredientLocationController = mockLocationList(1);
        IngredientLocation newLocation1 = mockLocation(3);
        IngredientLocation newLocation2 = mockLocation(4);
        ArrayList<IngredientLocation> newIngredientLocations = new ArrayList<>(Arrays.asList(newLocation1, newLocation2));
        assert ingredientLocationController != null;
        ingredientLocationController.setLocations(newIngredientLocations);
        assertTrue(ingredientLocationController.contains(newLocation1));
        assertTrue(ingredientLocationController.contains(newLocation2));
    }

    @Test
    void getDescriptions() {
        List<String> names = Objects.requireNonNull(mockLocationList(1)).getLocationDescription();
        assertTrue(names.contains("description1"));
        assertTrue(names.contains("description2"));
    }

    @Test
    void contains() {
        IngredientLocation ingredientLocation1 = mockLocation(1);
        IngredientLocation ingredientLocation2 = mockLocation(2);
        IngredientLocationController ingredientLocationController = new IngredientLocationController(new ArrayList<>(Arrays.asList(ingredientLocation1, ingredientLocation2)));
        assertTrue(ingredientLocationController.contains(ingredientLocation1));
        assertTrue(ingredientLocationController.contains(ingredientLocation2));
    }
}

