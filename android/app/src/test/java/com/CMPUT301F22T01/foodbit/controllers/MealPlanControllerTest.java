//package com.CMPUT301F22T01.foodbit.controllers;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.CMPUT301F22T01.foodbit.models.MealPlan;
//import com.CMPUT301F22T01.foodbit.models.Recipe;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//
//public class MealPlanControllerTest {
//    private MealPlan mockMealPlan(int choice) {
//        switch (choice) {
//            case 1:
//                return new MealPlan("1", 1, "id1", true, new Date(), new HashMap<>());
//            case 2:
//                return new MealPlan("2", 2, "id2", true, new Date(), new HashMap<>());
//            case 3:
//                return new MealPlan("3", 3, "id3", false, new Date(), new HashMap<>());
//            case 4:
//                return new MealPlan("4", 4, "id4", false, new Date(), new HashMap<>());
//        }
//        return null;
//    }
//
//    private MealPlanController mockMealPlanController(int choice) {
//        ArrayList<MealPlan> mealPlans = new ArrayList<>();
//        switch (choice) {
//            case 1:
//                mealPlans.add(mockMealPlan(1));
//                mealPlans.add(mockMealPlan(2));
//                return new MealPlanController();
//            case 2:
//                mealPlans.add(mockMealPlan(3));
//                mealPlans.add(mockMealPlan(4));
//                return new MealPlanController();
//        }
//        return null;
//    }
//
//    @Test
//    void getRecipes() {
//        MealPlanController recipeBook1 = mockMealPlanController(1);
//        assert recipeBook1 != null;
//        ArrayList<Recipe> recipes1 = recipeBook1.getRecipes();
//        assertEquals("id1", recipes1.get(0).getId());
//        assertEquals("id2", recipes1.get(1).getId());
//        RecipeBook recipeBook2 = mockRecipeBook(2);
//        assert recipeBook2 != null;
//        ArrayList<Recipe> recipes2 = recipeBook2.getRecipes();
//        assertEquals("id3", recipes2.get(0).getId());
//        assertEquals("id4", recipes2.get(1).getId());
//    }
//}
