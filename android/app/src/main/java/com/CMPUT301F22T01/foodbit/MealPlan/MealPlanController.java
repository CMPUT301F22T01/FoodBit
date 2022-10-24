package com.CMPUT301F22T01.foodbit.MealPlan;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MealPlanController {
    /**
     * Receive requests from UI and other Controllers to update the meal plan and pass along
     * appropriate updates to the shopping list
     */

    private ArrayList<MealPlanModel> mealPlan;

    public MealPlanController(){
        mealPlan = new ArrayList<MealPlanModel>();
    }
    public void addMeal(String name, int servings, int id, boolean isIngredient, Date date, Map<Integer, Integer> ingredientList){
        /**
         * Add a new ingredient meal to the DB from the UI
         */
        MealPlanModel model;
        model = new MealPlanModel(name,servings,id,isIngredient,date,null);
        mealPlan.add(model);
        model.commit();
    }

    public void loadAllMeals() {
//        List<MealPlanModel> = this.model.getALlMeals();
        MealPlanModel model = new MealPlanModel();
        model.getALlMeals(mealPlan);
    }

//    public void loadMeal() {
//
//    }
//
//    public void editMeal() {
//
//    }


}
