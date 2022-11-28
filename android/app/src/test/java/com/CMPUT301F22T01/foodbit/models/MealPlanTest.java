package com.CMPUT301F22T01.foodbit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import android.net.Uri;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MealPlanTest {

    private Ingredient mockIngredient() {
        return new Ingredient("id_ingredient","desc", "bb", "loc", 4.0F, "unit", "cat");
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
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private MealPlan mockMPIngredient() {
        return new MealPlan("mp_ingredient", 20, "mp_i", true, mockDate(), "id_ingredient", mockIngredientList());
    }

    private MealPlan mockMPRecipe() {
        return new MealPlan("mp_recipe", 10, "mp_r", false, mockDate(), "id_recipe", mockIngredientList());
    }

    @Test
    void getName() {
        assertEquals("mp_ingredient", mockMPIngredient().getName());
        assertEquals("mp_recipe", mockMPRecipe().getName());
    }

    @Test
    void setName() {
        MealPlan mealPlanIngredient = mockMPIngredient();
        assertEquals("mp_ingredient", mealPlanIngredient.getName());
        mealPlanIngredient.setName("mp_ingredient_new");
        assertEquals("mp_ingredient_new", mealPlanIngredient.getName());

        MealPlan mealPlanRecipe = mockMPRecipe();
        assertEquals("mp_recipe", mealPlanRecipe.getName());
        mealPlanRecipe.setName("mp_recipe_new");
        assertEquals("mp_recipe_new", mealPlanRecipe.getName());
    }

    @Test
    void getServings() {
        assertEquals(20, mockMPIngredient().getServings());
        assertEquals(10, mockMPRecipe().getServings());
    }

    @Test
    void setServings() {
        MealPlan mealPlanIngredient = mockMPIngredient();
        assertEquals(20, mealPlanIngredient.getServings());
        mealPlanIngredient.setServings(2);
        assertEquals(2, mealPlanIngredient.getServings());

        MealPlan mealPlanRecipe = mockMPRecipe();
        assertEquals(10, mealPlanRecipe.getServings());
        mealPlanRecipe.setServings(1);
        assertEquals(1, mealPlanRecipe.getServings());
    }

    @Test
    void getID() {
        assertEquals("mp_i", mockMPIngredient().getId());
        assertEquals("mp_r", mockMPRecipe().getId());
    }

    @Test
    void setID() {
        MealPlan mealPlanIngredient = mockMPIngredient();
        assertEquals("mp_i", mealPlanIngredient.getId());
        mealPlanIngredient.setId("mp_i_new");
        assertEquals("mp_i_new", mealPlanIngredient.getId());

        MealPlan mealPlanRecipe = mockMPRecipe();
        assertEquals("mp_r", mealPlanRecipe.getId());
        mealPlanRecipe.setId("mp_r_new");
        assertEquals("mp_r_new", mealPlanRecipe.getId());
    }

    @Test
    void isIngredient() {
        assertTrue(mockMPIngredient().isIngredient());
        assertFalse(mockMPRecipe().isIngredient());
    }

    @Test
    void setIngredient() {
        MealPlan mealPlanIngredient = mockMPIngredient();
        assertTrue(mealPlanIngredient.isIngredient());
        mealPlanIngredient.setIngredient(false);
        assertFalse(mealPlanIngredient.isIngredient());

        MealPlan mealPlanRecipe = mockMPRecipe();
        assertFalse(mealPlanRecipe.isIngredient());
        mealPlanRecipe.setIngredient(true);
        assertTrue(mealPlanRecipe.isIngredient());
    }

    @Test
    void getDate() {
        assertEquals(mockDate(), mockMPIngredient().getDate());
        assertEquals(mockDate(), mockMPRecipe().getDate());
    }

    @Test
    void setDate() {
        Date newDate = new Date();

        MealPlan mealPlanIngredient = mockMPIngredient();
        assertEquals(mockDate(), mealPlanIngredient.getDate());
        mealPlanIngredient.setDate(newDate);
        assertEquals(newDate, mealPlanIngredient.getDate());

        MealPlan mealPlanRecipe = mockMPRecipe();
        assertEquals(mockDate(), mealPlanRecipe.getDate());
        mealPlanRecipe.setDate(newDate);
        assertEquals(newDate, mealPlanRecipe.getDate());
    }

    @Test
    void getIngredients() {
        assertEquals("id_ingredient", mockMPIngredient().getIngredients().get(0).getId());
        assertEquals("desc", mockMPIngredient().getIngredients().get(0).getDescription());
        assertEquals("bb", mockMPIngredient().getIngredients().get(0).getBestBefore());
        assertEquals("loc", mockMPIngredient().getIngredients().get(0).getLocation());
        assertEquals(4.0F, mockMPIngredient().getIngredients().get(0).getAmount());
        assertEquals("unit", mockMPIngredient().getIngredients().get(0).getUnit());
        assertEquals("cat", mockMPIngredient().getIngredients().get(0).getCategory());

        assertEquals("id_ingredient", mockMPRecipe().getIngredients().get(0).getId());
        assertEquals("desc", mockMPRecipe().getIngredients().get(0).getDescription());
        assertEquals("bb", mockMPRecipe().getIngredients().get(0).getBestBefore());
        assertEquals("loc", mockMPRecipe().getIngredients().get(0).getLocation());
        assertEquals(4.0F, mockMPRecipe().getIngredients().get(0).getAmount());
        assertEquals("unit", mockMPRecipe().getIngredients().get(0).getUnit());
        assertEquals("cat", mockMPRecipe().getIngredients().get(0).getCategory());
    }

    @Test
    void setIngredients() {
        Ingredient newIngredient = new Ingredient("id_ingredient_new","desc_new", "bb_new", "loc_new", 1.0F, "unit_new", "cat_new");
        MealPlan mealPlanIngredient = mockMPIngredient();
        assertEquals("id_ingredient", mealPlanIngredient.getIngredients().get(0).getId());
        mealPlanIngredient.setIngredients(newIngredient);
        assertEquals("id_ingredient_new", mealPlanIngredient.getIngredients().get(0).getId());
        assertEquals("desc_new", mealPlanIngredient.getIngredients().get(0).getDescription());
//        assertEquals("bb_new", mealPlanIngredient.getIngredients().get(0).getBestBefore());
//        assertEquals("loc_new", mealPlanIngredient.getIngredients().get(0).getLocation());
//        assertEquals(1.0F, mealPlanIngredient.getIngredients().get(0).getAmount());
        assertEquals("unit_new", mealPlanIngredient.getIngredients().get(0).getUnit());
//        assertEquals("cat_new", mealPlanIngredient.getIngredients().get(0).getCategory());
    }

    @Test
    void setIngredientsFromRecipe() {
        Ingredient newIngredient = new Ingredient("id_ingredient_new","desc_new", "bb_new", "loc_new", 1, "unit_new", "cat_new");
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(newIngredient));
        MealPlan mealPlanRecipe = mockMPRecipe();
        assertEquals("id_ingredient", mealPlanRecipe.getIngredients().get(0).getId());
        mealPlanRecipe.setIngredientsFromRecipe(ingredients, 2);
        assertEquals("id_ingredient_new", mealPlanRecipe.getIngredients().get(0).getId());
        assertEquals("desc_new", mealPlanRecipe.getIngredients().get(0).getDescription());
        assertEquals("unit_new", mealPlanRecipe.getIngredients().get(0).getUnit());
        // since the mockRecipe originally serves 10 people and we want 2 servings, 10/2 = 5
        assertEquals(5, mealPlanRecipe.getIngredients().get(0).getAmount());
    }

    @Test
    void getRecipeID() {
        assertEquals("id_ingredient", mockMPIngredient().getRecipeID());
        assertEquals("id_recipe", mockMPRecipe().getRecipeID());
    }

    @Test
    void setRecipeID() {
        MealPlan mealPlanIngredient = mockMPIngredient();
        assertEquals("id_ingredient", mockMPIngredient().getRecipeID());
        mealPlanIngredient.setRecipeID("id_ingredient_new");
        assertEquals("id_ingredient_new", mealPlanIngredient.getRecipeID());

        MealPlan mealPlanRecipe = mockMPRecipe();
        assertEquals("id_recipe", mockMPRecipe().getRecipeID());
        mealPlanRecipe.setRecipeID("id_recipe_new");
        assertEquals("id_recipe_new", mealPlanRecipe.getRecipeID());
    }

}