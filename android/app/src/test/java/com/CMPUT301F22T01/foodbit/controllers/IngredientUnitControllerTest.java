package com.CMPUT301F22T01.foodbit.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.CMPUT301F22T01.foodbit.models.IngredientUnit;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class IngredientUnitControllerTest {
    private IngredientUnit mockUnit(int choice) {
        switch (choice) {
            case 1:
                return new IngredientUnit("description1", "id1");
            case 2:
                return new IngredientUnit("description2", "id2");
            case 3:
                return new IngredientUnit("description3", "id3");
            case 4:
                return new IngredientUnit("description4", "id4");
        }
        return null;
    }

    private IngredientUnitController mockUnitList(int choice) {
        ArrayList<IngredientUnit> units = new ArrayList<>();
        switch (choice) {
            case 1:
                units.add(mockUnit(1));
                units.add(mockUnit(2));
                return new IngredientUnitController(units);
            case 2:
                units.add(mockUnit(3));
                units.add(mockUnit(4));
                return new IngredientUnitController(units);
        }
        return null;
    }

    @Test
    void getUnits() {
        IngredientUnitController ingredientUnitController1 = mockUnitList(1);
        assert ingredientUnitController1 != null;
        ArrayList<IngredientUnit> units1 = ingredientUnitController1.getUnits();
        assertEquals("id1", units1.get(0).getId());
        assertEquals("id2", units1.get(1).getId());
        IngredientUnitController ingredientUnitController2 = mockUnitList(2);
        assert ingredientUnitController2 != null;
        ArrayList<IngredientUnit> units2 = ingredientUnitController2.getUnits();
        assertEquals("id3", units2.get(0).getId());
        assertEquals("id4", units2.get(1).getId());
    }

    @Test
    public void setUnits() {
        IngredientUnitController ingredientUnitController = mockUnitList(1);
        IngredientUnit newUnit1 = mockUnit(3);
        IngredientUnit newUnit2 = mockUnit(4);
        ArrayList<IngredientUnit> newIngredientUnits = new ArrayList<>(Arrays.asList(newUnit1, newUnit2));
        assert ingredientUnitController != null;
        ingredientUnitController.setUnits(newIngredientUnits);
        assertTrue(ingredientUnitController.contains(newUnit1));
        assertTrue(ingredientUnitController.contains(newUnit2));
    }

    @Test
    void getDescriptions() {
        List<String> names = Objects.requireNonNull(mockUnitList(1)).getUnitDescription();
        assertTrue(names.contains("description1"));
        assertTrue(names.contains("description2"));
    }

    @Test
    void contains() {
        IngredientUnit ingredientUnit1 = mockUnit(1);
        IngredientUnit ingredientUnit2 = mockUnit(2);
        IngredientUnitController ingredientUnitController = new IngredientUnitController(new ArrayList<>(Arrays.asList(ingredientUnit1, ingredientUnit2)));
        assertTrue(ingredientUnitController.contains(ingredientUnit1));
        assertTrue(ingredientUnitController.contains(ingredientUnit2));
    }
}

