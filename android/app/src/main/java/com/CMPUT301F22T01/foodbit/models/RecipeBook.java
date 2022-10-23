package com.CMPUT301F22T01.foodbit.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.ui.RecipeAddFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeBook implements Serializable {
    private final ArrayList<Recipe> recipes;

    public RecipeBook() {
        recipes = new ArrayList<Recipe>();
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void add(Recipe recipe) {
        assert !recipes.contains(recipe) : "This recipe is already in the recipe book!";
        recipes.add(recipe);
        update();
    }

    private void update() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document("recipe book");
        docRef.set(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(RecipeAddFragment.TAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(RecipeAddFragment.TAG, "Data could not be added!" + e.toString());
                    }
                });
    }
}
