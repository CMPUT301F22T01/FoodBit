package com.CMPUT301F22T01.foodbit.controllers;

import com.CMPUT301F22T01.foodbit.models.MealPlan;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MealPlanController {
    /**
     * Receive requests from UI and other Controllers to update the meal plan and pass along
     * appropriate updates to the shopping list
     */

    private ArrayList<MealPlan> mealPlan;

    public MealPlanController(){
        mealPlan = new ArrayList<MealPlan>();
    }
    public void addMeal(String name, int servings, int id, boolean isIngredient, Date date, Map<Integer, Integer> ingredientList){
        /**
         * Add a new ingredient meal to the DB from the UI
         */
        MealPlan model;
        model = new MealPlan(name,servings,id,isIngredient,date,null);
        mealPlan.add(model);
        model.commit();
    }

    public void loadAllMeals() {
//        List<MealPlan> = this.model.getALlMeals();
        MealPlan model = new MealPlan();
        model.getAllMeals(mealPlan);
    }

    public ArrayList<MealPlan> getArrayList() {
        return mealPlan;
    }


    public void update(ArrayList<MealPlan> newMealPlan) {
        mealPlan.clear();
        mealPlan.addAll(newMealPlan);
    }

}
