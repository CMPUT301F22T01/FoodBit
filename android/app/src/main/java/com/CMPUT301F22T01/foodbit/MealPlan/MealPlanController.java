package com.CMPUT301F22T01.foodbit.MealPlan;

import java.util.Date;
import java.util.Map;

public class MealPlanController {
    /**
     * Receive requests from UI and other Controllers to update the meal plan and pass along
     * appropriate updates to the shopping list
     */

    private MealPlanModel model;

    public MealPlanController(){

    }
    public void addMeal(String name, int servings, int id, boolean isIngredient, Date date, Map<Integer, Integer> ingredientList){
        /**
         * Add a new ingredient meal to the DB from the UI
         */
        this.model = new MealPlanModel(name,servings,id,isIngredient,date,null);
        this.model.commit();
    }

    public void loadAllMeals() {
//        List<MealPlanModel> = this.model.getALlMeals();

        this.model.getALlMeals();
    }

//    public void loadMeal() {
//
//    }
//
//    public void editMeal() {
//
//    }


}
