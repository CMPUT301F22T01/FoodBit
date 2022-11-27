package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.ui.IngredientAddFragment;
import com.CMPUT301F22T01.foodbit.ui.IngredientEditFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Stores each ingredient created
 */
public class IngredientStorage implements Serializable {
    //private FirebaseFirestore db;
    private DatabaseController db = new DatabaseController("Ingredients");
    private final ArrayList<Ingredient> ingredients;

    /**
     * Creates a new list for ingredients
     */
    public IngredientStorage() {
        ingredients = new ArrayList<Ingredient>();
    }

    /**
     * Creates new array list of ingredients
     * @param ingredients an array list of ingredients
     */
    public IngredientStorage(List<Ingredient> ingredients) {
        this.ingredients = (ArrayList<Ingredient>) ingredients;
    }

    /**
     * Allows ingredients in the list to be retrieved
     * @return the ingredients in the list of ingredients
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Adding into the ingredient list
     * @param ingredients ingredient to be added to the list
     */
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
    }

    /**
     * Allows ingredient at a certain position to be retrieved
     * @param position place where the ingredient is located
     * @return ingredient at the indicated position
     */
    public Ingredient getIngredientByPosition(int position) {
        return ingredients.get(position);
    }

    /**
     * Allows ingredient of a certain id to be retrieved
     * @param id identification number of the ingredient
     * @return ingredient associated with particular id
     */
    public Ingredient getIngredientById(String id) {
        for (Ingredient ingredient : ingredients) {
            if (Objects.equals(id, ingredient.getId())) {
                return ingredient;
            }
        }
        return null;
    }

    /**
     * Gets all the descriptions from the ingredients in the list
     * @return list of the descriptions
     */
    public List<String> getDescriptions() {
        List<String> list = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            list.add(ingredient.getDescription());
        }
        return list;
    }

    /**
     * Checking if an ingredient is in the list
     * @param ingredient the ingredient to be checked
     * @return true or false, if the container is or is not in the list respectively
     */
    public boolean contains(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    /**
     * Adds an ingredient to the ingredient storage
     * Makes sure there are no duplicates
     * @param ingredient the ingredient to be added
     */
    public void add(Ingredient ingredient) {
        String TAG = IngredientAddFragment.TAG;
        assert !contains(ingredient) : "This ingredient is already in the ingredient list!";
        db.addItem(ingredient);
    }

    /**
     * Deletes an ingredient from ingredient storage
     * Makes sure the ingredient is in the ingredient storage first
     * @param ingredient the ingredient to be deleted
     */
    public void delete(Ingredient ingredient) {
        String TAG = "IngredientStorageDeleteIngredient";
        assert contains(ingredient) : "this ingredient is not found in the ingredient list!";
        db.deleteItem(ingredient);
    }

    /**
     * Edits an ingredient in ingredient storage
     * Makes sure the ingredient is in the ingredient storage first
     * @param ingredient the ingredient to be edited
     */
    public void edit(Ingredient ingredient) {
        String TAG = IngredientEditFragment.TAG;
        assert contains(ingredient) : "This ingredient is not found int the ingredient list";
        db.editItem(ingredient);
    }

    /**
     * Loads ingredients from the database
     */
    public void loadAllFromDB() {
        ingredients.clear();
        CollectionReference collectionReference = MainActivity.ingredientStorageRef;
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.e("db is loading  !!!!!!!!!! ",  collectionReference.getPath().toString());
                    for (int i =0; i< task.getResult().size(); i++) {
                        Ingredient model = task.getResult().getDocuments().get(i).toObject(Ingredient.class);
                        ingredients.add(model);
                    }
                }
            }
        });
    }
}
