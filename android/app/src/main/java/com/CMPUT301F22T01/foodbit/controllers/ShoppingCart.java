package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.ui.IngredientAddFragment;
import com.CMPUT301F22T01.foodbit.ui.IngredientEditFragment;
import com.CMPUT301F22T01.foodbit.ui.RecipeAddFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Stores each ingredient by compared ingredients in
 * Meal Plan and Ingredient Storage
 */
public class ShoppingCart {
    private final ArrayList<Ingredient> shoppingCart;

    /**
     * Creates a new list for ShoppingCart
     */
    public ShoppingCart() {
        shoppingCart = new ArrayList<Ingredient>();
    }

    /**
     * Creates new array list of ingredients
     * @param shoppingCart an array list of ingredients
     */
    public ShoppingCart(List<Ingredient> shoppingCart) {
        this.shoppingCart = (ArrayList<Ingredient>) shoppingCart;
    }

    /**
     * Allows ingredients in the list to be retrieved
     * @return the ingredients in the list of ingredients
     */
    public ArrayList<Ingredient> getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Adding into the ingredient list
     * @param shoppingCart ingredient to be added to the list
     */
    public void setIngredients(ArrayList<Ingredient> shoppingCart) {
        this.shoppingCart.clear();
        this.shoppingCart.addAll(shoppingCart);
    }




}
