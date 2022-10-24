package com.CMPUT301F22T01.foodbit.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.ui.RecipeAddFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeBook implements Serializable {
    private final ArrayList<Recipe> recipes;

    public RecipeBook() {
        recipes = new ArrayList<Recipe>();
    }

    public RecipeBook(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public Recipe get(int position) {
        return recipes.get(position);
    }

    public void add(Recipe recipe) {
        String TAG = RecipeAddFragment.TAG;
        assert !recipes.contains(recipe) : "This recipe is already in the recipe book!";
        recipes.add(recipe);
//        update();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference recipeBookRef = db.collection("recipe book");
        recipeBookRef.add(recipe)
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

    public void update(ArrayList<Recipe> newRecipes) {
        recipes.clear();
        recipes.addAll(newRecipes);
    }

}
