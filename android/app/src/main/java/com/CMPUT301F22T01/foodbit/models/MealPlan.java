package com.CMPUT301F22T01.foodbit.models;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MealPlan implements dbObject {
    /**
     *Looking to model MealPlans as Collections
     * MealPlan (Collection)
     *        --ID1 (Document - Ingredient, ID#20)
     *              --name: "Apple"
     *              --servingSize: "2"
     *              --recipeID: "20"
     *              --isIngredient: "1"
     *              --Date: "2022-10-23"
     *        --ID2 (Document - Recipe, ID#3)
     *              --name: "Pie"
     *              --servings: "1"
     *              --Date: "2022-10-23"
     *              --recipeID: "3"
     *              --isIngredient: "0"
     *              --ingredientList: {"231":3, "23":5} //Need 3 of ingredient 231s and 5 of ingredient 23
     *
     *
     * MealPlanController should be able to then query the recipe controller for the appropriate
     * ingredients for recipes and provide shopping list with the required ingredients
     *
     * TODO: Keep in mind ID and mealID are flipped for now. Fix by friday.
     */
    private String name;
    private int servings;
    private String id;
    private boolean isIngredient;
    private Date date;
    private Map<Integer,Integer> ingredientList;
    private String recipeID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIngredient() {
        return isIngredient;
    }

    public void setIngredient(boolean ingredient) {
        isIngredient = ingredient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<Integer, Integer> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(Map<Integer, Integer> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public MealPlan(){};

    public MealPlan(String name, int servings, String id, boolean isIngredient, Date date, Map<Integer, Integer> ingredientList) {
        this.name = name;
        this.servings = servings;
        this.id = id;
        this.isIngredient = isIngredient;
        this.date = date;
        this.ingredientList = ingredientList;
    }

}
