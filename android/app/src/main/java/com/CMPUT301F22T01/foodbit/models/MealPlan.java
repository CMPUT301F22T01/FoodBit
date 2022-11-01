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
     *        --mealID1 (Document - Ingredient, ID#20)
     *              --name: "Apple"
     *              --servingSize: "2"
     *              --ID: "20"
     *              --isIngredient: "1"
     *              --Date: "2022-10-23"
     *        --mealID2 (Document - Recipe, ID#3)
     *              --name: "Pie"
     *              --servings: "1"
     *              --Date: "2022-10-23"
     *              --ID: "3"
     *              --isIngredient: "0"
     *              --ingredientList: {"231":3, "23":5} //Need 3 of ingredient 231s and 5 of ingredient 23
     *
     *
     * MealPlanController should be able to then query the recipe controller for the appropriate
     * ingredients for recipes and provide shopping list with the required ingredients
     *
     *
     */
    private String name;
    private int servings;
    private String id;
    private boolean isIngredient;
    private Date date;
    private Map<Integer,Integer> ingredientList;
    private String mealID = null;

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
        return mealID;
    }

    public void setId(String id) {
        this.mealID = id;
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

    public String getMealID() {
        return id;
    }

    public void setMealID(String mealID) {
        this.id = mealID;
    }

    public MealPlan(){
    };

    public MealPlan(String name, int servings, String id, boolean isIngredient, Date date, Map<Integer, Integer> ingredientList) {
        this.name = name;
        this.servings = servings;
        this.id = id;
        this.isIngredient = isIngredient;
        this.date = date;
        this.ingredientList = ingredientList;
    }

//
//    public void commit() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        final CollectionReference collectionReference = db.collection("Meals");
//        String id = collectionReference.document().getId();
//        this.mealID = id;
//        collectionReference.document(id).set(this);
//    }
//
//    public void getAllMeals(ArrayList<MealPlan> mealPlan) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        final CollectionReference collectionReference = db.collection("Meals");
//        collectionReference.orderBy("date").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    for (int i =0; i< task.getResult().size(); i++) {
//                        MealPlan meal = new MealPlan();
//                        meal = task.getResult().getDocuments().get(i).toObject(meal.getClass());
//                        mealPlan.add(meal);
//                        Log.e("firebase response??", String.valueOf(i) + String.valueOf(task.getResult().getDocuments().get(i)));
//                    }
//                }
//            }
//        });
//    }






}
