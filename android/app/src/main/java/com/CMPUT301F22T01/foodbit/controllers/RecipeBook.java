package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.IRecipe;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.ui.RecipeAddFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public Recipe getRecipeByPosition(int position) {
        return recipes.get(position);
    }

    public Recipe getRecipeById(String id) {
        for (Recipe recipe : recipes) {
            if (Objects.equals(id, recipe.getId())) {
                return recipe;
            }
        }
        return null;
    }

    public String add(Recipe recipe) {
        String TAG = RecipeAddFragment.TAG;
        final String[] id = {null};
        assert !recipes.contains(recipe) : "This recipe is already in the recipe book!";
        recipes.add(recipe);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference recipeBookRef = db.collection("recipe book");
        recipeBookRef.add(recipe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        id[0] = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot written with ID: " + id[0]);
                        documentReference.update("id", id[0])
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error updating document", e);
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
        return id[0];
    }

    public void update(ArrayList<Recipe> newRecipes) {
        recipes.clear();
        recipes.addAll(newRecipes);
    }

}
