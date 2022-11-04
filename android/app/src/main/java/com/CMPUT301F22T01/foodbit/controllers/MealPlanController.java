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
        // sort MealPlan by date
        Collections.sort(mealPlan, new Comparator<MealPlan>() {
            public int compare(MealPlan o1, MealPlan o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return mealPlan;
    }

    public void update(ArrayList<MealPlan> newMealPlan) {
        mealPlan.clear();
        mealPlan.addAll(newMealPlan);
    }


    public String toString() {
        String t = "";
        for (int i =0; i < mealPlan.size(); i++) {
            t = t + mealPlan.get(i).getId();
        }
        return t;
    }
}
