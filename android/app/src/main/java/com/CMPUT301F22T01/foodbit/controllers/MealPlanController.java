package com.CMPUT301F22T01.foodbit.controllers;

import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MealPlanController {
    /**
     * Receive requests from UI and other Controllers to update the meal plan and pass along
     * appropriate updates to the shopping list
     */

    private ArrayList<MealPlan> mealPlan;
    private DatabaseController db = new DatabaseController("Meals");
    public MealPlanController(){

        mealPlan = new ArrayList<MealPlan>();
        this.loadAllMeals();
    }
    public void addMeal(MealPlan meal){
        /**
         * Add a new ingredient meal to the DB from the UI
         */
        db.addItem(meal);
        mealPlan.add(meal);
    }

    public void loadAllMeals() {
        //Load mealPlans from database into local array
        db.getAllItems(mealPlan);

    }

    public ArrayList<MealPlan> getArrayList() {
        return mealPlan;
    }


    public void update(ArrayList<MealPlan> newMealPlan) {
        mealPlan.clear();
        mealPlan.addAll(newMealPlan);
    }


}
