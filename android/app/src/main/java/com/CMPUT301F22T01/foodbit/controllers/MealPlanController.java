package com.CMPUT301F22T01.foodbit.controllers;

import com.CMPUT301F22T01.foodbit.models.MealPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Receive requests from UI and other Controllers to update the meal plan and pass along
 * appropriate updates to the shopping list
 */
public class MealPlanController {

    private ArrayList<MealPlan> mealPlan;
    private DatabaseController db;

    /**
     * Creates a new list of MealPlans
     */
    public MealPlanController(){
        mealPlan = new ArrayList<MealPlan>();
        db = new DatabaseController("Meals");
        this.loadAllMeals();
    }

    /**
     * Adds a new meal to the database from the UI
     * @param meal the meal to be added
     */
    public void addMeal(MealPlan meal){
        db.addItem(meal);
        mealPlan.add(meal);
    }

    /**
     * Loads the MealPlan array from the database into the local array
     */
    public void loadAllMeals() {
        db.getAllItems(mealPlan);
    }

    /**
     * Sorts the mealPlan array by date
     * @return the sorted array of MealPlans
     */
    public ArrayList<MealPlan> getArrayList() {
        Collections.sort(mealPlan, new Comparator<MealPlan>() {
            public int compare(MealPlan o1, MealPlan o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return mealPlan;
    }

    /**
     * Updates the array of MealPlans
     * @param newMealPlan the array of MealPlans
     */
    public void update(ArrayList<MealPlan> newMealPlan) {
        mealPlan.clear();
        mealPlan.addAll(newMealPlan);
    }

    /**
     * Converts the array of MealPlans to a string
     * @return
     */
    public String toString() {
        String t = "";
        for (int i =0; i < mealPlan.size(); i++) {
            t = t + mealPlan.get(i).getId();
        }
        return t;
    }
}
