package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.dbObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DatabaseController {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference recipeBookRef = db.collection("recipe book");
    final CollectionReference ingredientStorageRef = db.collection("ingredient list");
    final CollectionReference mealPlanRef = db.collection("Meals");
    private CollectionReference collectionReference;
    private String mode;
    private Object model;

    public DatabaseController(String mode){
        this.mode = mode;

        switch(mode) {
            case "Meals":
                collectionReference = mealPlanRef;
                this.model = new MealPlan();
                break;
            case "Ingredients":
                collectionReference = ingredientStorageRef;
                this.model  = new Ingredient();
                break;
            case "Recipes":
                collectionReference = recipeBookRef;
                this.model  = new Recipe();
                break;
            default:
        }
    }

    public void addToDB(dbObject newItem) {
        String id = collectionReference.document().getId();
        newItem.setId(id);
        collectionReference.document(id).set(newItem);
    }

    public <T> void getAllItems(ArrayList<T> items) {
        //TODO: Add optional 'order by'
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for (int i =0; i< task.getResult().size(); i++) {
                        model = task.getResult().getDocuments().get(i).toObject(model.getClass());
                        items.add((T)model);
                        Log.e("firebase response??", String.valueOf(i) + String.valueOf(task.getResult().getDocuments().get(i)));
                    }
                }
            }
        });
    }



}
