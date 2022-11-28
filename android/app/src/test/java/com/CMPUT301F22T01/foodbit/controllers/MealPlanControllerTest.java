package com.CMPUT301F22T01.foodbit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.MealPlan;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MealPlanControllerTest {
    private MealPlan mockMealPlan(int choice) {
        switch (choice) {
            case 1:
                return new MealPlan("1", 1, "id1", true, new Date(), "rid1", new ArrayList<>());
            case 2:
                return new MealPlan("2", 2, "id2", true, new Date(), "rid2", new ArrayList<>());
            case 3:
                return new MealPlan("3", 3, "id3", false, new Date(), "rid3", new ArrayList<>());
            case 4:
                ArrayList<Ingredient> ingredList = new ArrayList<>();
                Ingredient ingredient = new Ingredient("id", 2);
                ingredList.add(ingredient);
                return new MealPlan("4", 4, "id4", false, new Date(), "rid4", ingredList);
        }
        return null;
    }

    private MealPlanController mockMealPlanController(int choice) {
        ArrayList<MealPlan> mealPlans = new ArrayList<>();
        switch (choice) {
            case 1:
                mealPlans.add(mockMealPlan(1));
                mealPlans.add(mockMealPlan(2));
                return new MealPlanController(mealPlans);
            case 2:
                mealPlans.add(mockMealPlan(3));
                mealPlans.add(mockMealPlan(4));
                return new MealPlanController(mealPlans);
        }
        return null;
    }

    @Test
    void getArrayList() {
        MealPlanController mpController1 = mockMealPlanController(1);
        assert mpController1 != null;
        ArrayList<MealPlan> meals1 = mpController1.getMealPlans();
        assertEquals("id1", meals1.get(0).getId());
        assertEquals("id2", meals1.get(1).getId());
        MealPlanController mpController2 = mockMealPlanController(2);
        assert mpController2 != null;
        ArrayList<MealPlan> meals2 = mpController2.getMealPlans();
        assertEquals("id3", meals2.get(0).getId());
        assertEquals("id4", meals2.get(1).getId());
    }

    @Test
    void contains() {
        MealPlan mp1 = mockMealPlan(1);
        MealPlan mp2 = mockMealPlan(2);
        MealPlanController mpController = new MealPlanController(new ArrayList<>(Arrays.asList(mp1, mp2)));
        assertTrue(mpController.contains(mp1));
        assertTrue(mpController.contains(mp2));
    }

    @Test
    void update() {
        MealPlanController mpController = mockMealPlanController(1);
        assert mpController != null;
        MealPlan mp1 = mockMealPlan(1);
        assertTrue(mpController.contains(mp1));
        MealPlan mp2 = mockMealPlan(2);
        mpController.update(new ArrayList<>(Arrays.asList(mp2)));
        assertTrue(mpController.contains(mp2));
        assertFalse(mpController.contains(mp1));
    }

    @Test
    void testToString() {
        MealPlanController mpController = mockMealPlanController(1);
        assert mpController != null;
        assertEquals("id1id2", mpController.toString());
    }

    @Test
    void getMealByPosition() {
        MealPlanController mpController = mockMealPlanController(1);
        assert mpController != null;
        assertEquals("id1", mpController.getMealByPosition(0).getId());
        assertEquals("id2", mpController.getMealByPosition(1).getId());
    }

    @Test
    void lookUpIngredientID() {
        ArrayList<Ingredient> ingredList = new ArrayList<>();
        String id = "id";
        Ingredient ingredient = new Ingredient(id, 2);
        ingredList.add(ingredient);
        MealPlanController mpController = mockMealPlanController(2);
        assert mpController != null;
        assertEquals(0, mpController.lookUpIngredientID(id,ingredList));
    }

}
