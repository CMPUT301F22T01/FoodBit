package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.ui.IngredientAddFragment;
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
 * Stores each ingredient created
 */
public class IngredientStorage implements Serializable {
    private FirebaseFirestore db;
    private final ArrayList<Ingredient> ingredients;

    /**
     * Creates a new list for ingredients
     */
    public IngredientStorage() {
        ingredients = new ArrayList<Ingredient>();
    }

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
    public List<String> getDescriptions() {
        List<String> list = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            list.add(ingredient.getDescription());
        }
        return list;
    }

    public boolean contains(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    /**
     * Allows for an ingredient to be added
     * @param ingredient ingredient to be added
     */
    public void add(Ingredient ingredient) {
        String TAG = IngredientAddFragment.TAG;
        assert !ingredients.contains(ingredient) : "This ingredient is already in the list!";
        db = FirebaseFirestore.getInstance();

        CollectionReference ingredientStorageRef = MainActivity.ingredientStorageRef;
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

    /**
     * Allows for an ingredient to be deleted
     * @param ingredient ingredient to be deleted
     */
    public void delete(Ingredient ingredient) {
        String TAG = "DeleteIngredient";
        assert ingredients.contains(ingredient) : "this ingredient is not in the list";

        MainActivity.ingredientStorageRef.document(ingredient.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
    public void edit(Ingredient ingredient) {
        String TAG = "EditIngredient";
        assert ingredients.contains(ingredient) : "this ingredient is not in the list";

        MainActivity.ingredientStorageRef.document(ingredient.getId())
                .set(ingredient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
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

    public void loadAllFromDB() {
        //Load mealPlans from database into local array
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
