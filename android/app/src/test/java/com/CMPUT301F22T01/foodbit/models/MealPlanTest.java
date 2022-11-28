package com.CMPUT301F22T01.foodbit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import android.net.Uri;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MealPlanTest {
    private Ingredient mockIngredient() {
        return new Ingredient("id_ingredient", "bread", "2022-11-05", "pantry",2F, "slice", "lunch");
    }

    private ArrayList<Ingredient> mockIngredientList() {
        ArrayList<Ingredient> mockIngredientList = new ArrayList<>();
        mockIngredientList.add(mockIngredient());
        return mockIngredientList;
    }

    private Recipe mockRecipe() {
        return new Recipe("id_recipe", "recipe", 20, 1, "category", "comments", Uri.parse("https://www.google.com"), mockIngredientList());
    }

    private Date mockDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    private MealPlan mockMealPlanIngredient() {
        return new MealPlan("meal_ingredient", 2, "id_meal_ingredient", true, mockDate(), null);
    }

    private MealPlan mockMealPlanRecipe() {
        // TODO: fix
        Map<String, Float> ingredientList = new HashMap<>();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredientList.put("id_ingredient", 2f);
        return new MealPlan("meal_recipe", 1, "id_meal_recipe", false, mockDate(), ingredients);
    }

    @Test
    void getName() {
        assertEquals("meal_ingredient", mockMealPlanIngredient().getName());
        assertEquals("meal_recipe", mockMealPlanRecipe().getName());
    }

    @Test
    void setName() {
        MealPlan mealPlanIngredient = mockMealPlanIngredient();
        assertEquals("meal_ingredient", mealPlanIngredient.getName());
        mealPlanIngredient.setName("meal_ingredient_new");
        assertEquals("meal_ingredient_new", mealPlanIngredient.getName());

        MealPlan mealPlanRecipe = mockMealPlanRecipe();
        assertEquals("meal_recipe", mealPlanRecipe.getName());
        mealPlanRecipe.setName("meal_recipe_new");
        assertEquals("meal_recipe_new", mealPlanRecipe.getName());
    }

    @Test
    void getServings() {
        assertEquals(2, mockMealPlanIngredient().getServings());
        assertEquals(1, mockMealPlanRecipe().getServings());
    }

    @Test
    void setServings() {
        MealPlan mealPlanIngredient = mockMealPlanIngredient();
        assertEquals(2, mealPlanIngredient.getServings());
        mealPlanIngredient.setServings(4);
        assertEquals(4, mealPlanIngredient.getServings());

        MealPlan mealPlanRecipe = mockMealPlanRecipe();
        assertEquals(1, mealPlanRecipe.getServings());
        mealPlanRecipe.setServings(2);
        assertEquals(2, mealPlanRecipe.getServings());
    }

    @Test
    void getID() {
        assertEquals("id_meal_ingredient", mockMealPlanIngredient().getId());
        assertEquals("id_meal_recipe", mockMealPlanRecipe().getId());
    }

    @Test
    void setID() {
        MealPlan mealPlanIngredient = mockMealPlanIngredient();
        assertEquals("id_meal_ingredient", mealPlanIngredient.getId());
        mealPlanIngredient.setId("new_id_meal_ingredient");
        assertEquals("new_id_meal_ingredient", mealPlanIngredient.getId());

        MealPlan mealPlanRecipe = mockMealPlanRecipe();
        assertEquals("id_meal_recipe", mealPlanRecipe.getId());
        mealPlanRecipe.setId("new_id_meal_recipe");
        assertEquals("new_id_meal_recipe", mealPlanRecipe.getId());
    }

    @Test
    void isIngredient() {
        assertTrue(mockMealPlanIngredient().isIngredient());
        assertFalse(mockMealPlanRecipe().isIngredient());
    }

    @Test
    void setIngredient() {
        MealPlan mealPlanIngredient = mockMealPlanIngredient();
        assertTrue(mealPlanIngredient.isIngredient());
        mealPlanIngredient.setIngredient(false);
        assertFalse(mealPlanIngredient.isIngredient());

        MealPlan mealPlanRecipe = mockMealPlanRecipe();
        assertFalse(mealPlanRecipe.isIngredient());
        mealPlanRecipe.setIngredient(true);
        assertTrue(mealPlanRecipe.isIngredient());
    }

    //@Test
    //void getDate() {
    //    assertEquals(mockDate(), mockMealPlanIngredient().getDate());
    //    assertEquals(mockDate(), mockMealPlanRecipe().getDate());
    //}

    //@Test
    //void setDate() {
    //    Date newDate = new Date();

    //    MealPlan mealPlanIngredient = mockMealPlanIngredient();
    //    assertEquals(mockDate(), mealPlanIngredient.getDate());
    //    mealPlanIngredient.setDate(newDate);
    //    assertEquals(newDate, mealPlanIngredient.getDate());

    //    MealPlan mealPlanRecipe = mockMealPlanRecipe();
    //    assertEquals(mockDate(), mealPlanRecipe.getDate());
    //    mealPlanRecipe.setDate(newDate);
    //    assertEquals(newDate, mealPlanRecipe.getDate());
    //}

    @Test
    void getIngredientList() {
        // TODO: fix
        MealPlan mealPlanIngredient = mockMealPlanIngredient();
        ArrayList<Ingredient> ingredientListIngredient = mealPlanIngredient.getIngredients();
        assertEquals(null, ingredientListIngredient);

//        MealPlan mealPlanRecipe = mockMealPlanRecipe();
//        Map<String, Float> ingredientListRecipe = mealPlanRecipe.getIngredientList();
//        Float value = ingredientListRecipe.get("id_ingredient");
//        Assertions.assertEquals(2f, (float)value);
    }

    @Test
    void setIngredientList() {
        // TODO: fix
        MealPlan mealPlanRecipe = mockMealPlanRecipe();
        ArrayList<Ingredient> ingredientListRecipe = mealPlanRecipe.getIngredients();
//        Float value = ingredientListRecipe.get("id_ingredient");
//        Assertions.assertEquals(2f, (float)value);
    }

    @Test
    void getRecipeID() {
        assertNull(mockMealPlanIngredient().getRecipeId());
        assertNull(mockMealPlanRecipe().getRecipeId());
    }

    @Test
    void setRecipeID() {
        MealPlan mealPlanIngredient = mockMealPlanIngredient();
        assertNull(mealPlanIngredient.getRecipeId());
        mealPlanIngredient.setRecipeID("recipeID1");
        assertEquals("recipeID1", mealPlanIngredient.getRecipeId());

        MealPlan mealPlanRecipe = mockMealPlanRecipe();
        assertNull(mealPlanRecipe.getRecipeId());
        mealPlanRecipe.setRecipeID("recipeID2");
        assertEquals("recipeID2", mealPlanRecipe.getRecipeId());
    }
}