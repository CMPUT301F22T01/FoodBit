package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.ui.IngredientAddFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class IngredientStorage implements Serializable {
    private FirebaseFirestore db;
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

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
    }

    public Ingredient getIngredientByPosition(int position) {
        return ingredients.get(position);
    }

    public Ingredient getIngredientById(String id) {
        for (Ingredient ingredient : ingredients) {
            if (Objects.equals(id, ingredient.getId())) {
                return ingredient;
            }
        }
        return null;
    }

    public void add(Ingredient ingredient) {
        String TAG = IngredientAddFragment.TAG;
        assert !ingredients.contains(ingredient) : "This ingredient is already in the list!";
        //ingredients.add(ingredient);
        //update(ingredients);
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        CollectionReference ingredientStorageRef = db.collection("Ingredient List");
        ingredientStorageRef.add(ingredient)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        documentReference.update("id", documentReference.getId())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    //public void update(ArrayList<Ingredient> newIngredient) {
    //    ingredients.clear();
    //    ingredients.addAll(newIngredient);
    //}

}
