package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.ui.RecipeAddFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Provide controls to a list of <code>Recipe</code> class objects.
 */
public class RecipeBook implements Serializable {
    private final ArrayList<Recipe> recipes;

    /**
     * Constructs an empty <code>RecipeBook</code>.
     */
    public RecipeBook() {
        recipes = new ArrayList<Recipe>();
    }

    /**
     * Constructs a recipe book with an initial list of recipes.
     * @param recipes the initial list of recipes
     */
    public RecipeBook(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
    }

    /**
     * Get a recipe by its index in the list.
     * @param position the index of the recipe
     * @return the recipe at the position
     */
    public Recipe getRecipeByPosition(int position) {
        return recipes.get(position);
    }

    /**
     * Get a recipe by its id.
     * @param id the id of the recipe
     * @return the recipe with the id
     */
    public Recipe getRecipeById(String id) {
        for (Recipe recipe : recipes) {
            if (Objects.equals(id, recipe.getId())) {
                return recipe;
            }
        }
        return null;
    }

    public List<String> getTitles() {
        List<String> list = new ArrayList<>();
        for (Recipe recipe : recipes) {
            list.add(recipe.getTitle());
        }
        return list;
    }

    /**
     * Add a recipe to the recipe book and add the recipe data to the Firestore database.
     * @param recipe the recipe to be added
     */
    public void add(Recipe recipe) {
        String TAG = RecipeAddFragment.TAG;
        assert !recipes.contains(recipe) : "This recipe is already in the recipe book!";
        recipes.add(recipe);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference recipeBookRef = db.collection("recipe book");
        recipeBookRef.add(recipe)
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
    }

}
