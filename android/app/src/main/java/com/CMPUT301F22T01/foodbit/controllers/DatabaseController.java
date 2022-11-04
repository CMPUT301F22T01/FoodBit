package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.dbObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DatabaseController {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference recipeBookRef = MainActivity.recipeBookRef;
    static CollectionReference ingredientStorageRef =  MainActivity.ingredientStorageRef;
    private CollectionReference collectionReference;
    private Object model;

    public DatabaseController(String mode){

        switch(mode) {
            case "Meals":
                collectionReference = MainActivity.mealPlanRef;
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

    public void addItem(dbObject newItem) {
        String id = collectionReference.document().getId();
        newItem.setId(id);
        Log.e("db is adding: ",  id + "   " + collectionReference.getId() + collectionReference.getPath().toString());
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
                    Log.e("db is loading  !!!!!!!!!! ",  collectionReference.getPath().toString());
                    for (int i =0; i< task.getResult().size(); i++) {
                        model = task.getResult().getDocuments().get(i).toObject(model.getClass());
                        items.add((T)model);
                        Log.e("firebase response??", String.valueOf(i) + String.valueOf(task.getResult().getDocuments().get(i)));
                    }
                }
            }
        });
    }

    public void editItem(dbObject editItem) {
        collectionReference.document(editItem.getId())
                .set(editItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Edit Database", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Edit Database", "Error updating document", e);
                    }
                });
    }

//    public void



}
