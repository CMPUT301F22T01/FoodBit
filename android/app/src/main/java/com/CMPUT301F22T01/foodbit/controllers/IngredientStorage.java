package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.ui.IngredientAddFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class IngredientStorage implements Serializable {
    private final ArrayList<Ingredient> ingredients;

    public IngredientStorage() {
        ingredients = new ArrayList<Ingredient>();
    }

    public IngredientStorage(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public Ingredient get(int position) {
        return ingredients.get(position);
    }

    public void add(Ingredient ingredient) {
        String TAG = IngredientAddFragment.TAG;
        assert !ingredients.contains(ingredient) : "This ingredient is already in the list!";
        ingredients.add(ingredient);
        update(ingredients);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ingredientStorageRef = db.collection("ingredient list");
        ingredientStorageRef.add(ingredient)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void update(ArrayList<Ingredient> newIngredient) {
        ingredients.clear();
        ingredients.addAll(newIngredient);
    }

}
