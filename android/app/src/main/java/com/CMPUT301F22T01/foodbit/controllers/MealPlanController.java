package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class MealPlanController {
    /**
     * Receive requests from UI and other Controllers to update the meal plan and pass along
     * appropriate updates to the shopping list
     */

    private ArrayList<MealPlan> mealPlan;
    private DatabaseController db;

    public MealPlanController(){
        mealPlan = new ArrayList<MealPlan>();
        db = new DatabaseController("Meals");
        this.loadAllMeals();
    }

    /**
     * Add meal to the database
     * @param meal
     */
    public void addMeal(MealPlan meal){
        db.addItem(meal);
        mealPlan.add(meal);
    }

    /**
     * Update local cache
     */
    public void loadAllMeals() {
        db.getAllItems(mealPlan);
    }

    /**
     * Return cache of meals
     * @return ArrayList<MealPlan>
     */
    public ArrayList<MealPlan> getArrayList() {
        // sort MealPlan by date
        Collections.sort(mealPlan, new Comparator<MealPlan>() {
            public int compare(MealPlan o1, MealPlan o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return mealPlan;
    }

    /**
     * Update cache with new meals
     * @param newMealPlan
     */
    public void update(ArrayList<MealPlan> newMealPlan) {
        mealPlan.clear();
        mealPlan.addAll(newMealPlan);
    }

    /**
     * Generate string of ID's of meals in cache
     * @return String strings
     */
    public String toString() {
        String t = "";
        for (int i =0; i < mealPlan.size(); i++) {
            t = t + mealPlan.get(i).getId();
        }
        return t;
    }
}
