package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.IngredientCategory;
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
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;

/**
 * Receive requests from LoadingPageActivity and other Controllers to communicate with Firebase and
 * establish which user DB to use
 */
public class DatabaseController {
    private FirebaseFirestore db;
    private String mode;
    private Object model;

    public DatabaseController(String mode){
        this.mode = mode;
        switch(mode) {
            case "Categories":
                this.model = new IngredientCategory();
                break;
            case "Meals":
                this.model = new MealPlan();
                break;
            case "Ingredients":
                this.model  = new Ingredient();
                break;
            case "Recipe Book":
                this.model  = new Recipe();
                break;
            default:
        }
    }

    /**
     * Finds the correct database for the controller based on mode set in constructor
     * @return collectionReference with the appropriate database
     */
    private CollectionReference getCollectionReference() {
        db = FirebaseFirestore.getInstance();
        switch (mode) {
            case "Categories":
                return MainActivity.categoryStorageRef;
            case "Meals":
                return MainActivity.mealPlanRef;
            case "Ingredients":
                return MainActivity.ingredientStorageRef;
            case "Recipe Book":
                return MainActivity.recipeBookRef;
            default:
                return null;
        }
    }

    /**
     * Add an item to the appropriate database
     * @param newItem
     */
    public void addItem(dbObject newItem) {
        CollectionReference collectionReference = getCollectionReference();
        String id = collectionReference.document().getId();
        newItem.setId(id);
        Log.e("db is adding: ",  id + "   " + collectionReference.getId() + collectionReference.getPath().toString());
        collectionReference.document(id).set(newItem);
    }

    /**
     * Pass in an arrayList<T> to be updated with information from the database.
     * <T> should implement the dbObject interface
     * @param items
     * @param <T>
     */
    public <T extends dbObject> void getAllItems(ArrayList<T> items) {
        //TODO: Add optional 'order by'
        CollectionReference collectionReference = getCollectionReference();
        Log.e("LOAD MODE: ", getCollectionReference().getPath());
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

    /**
     * Edit dbObject within Firebase
     * @param editItem - updated object with the appropriate ID.
     */
    public void editItem(dbObject editItem) {
        CollectionReference collectionReference = getCollectionReference();
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

    /**
     * Delete dbObject item from Firebase
     * @param item
     */
    public void deleteItem(dbObject item) {
        CollectionReference collectionReference = getCollectionReference();
        collectionReference.document(item.getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("delete", "DocumentSnapshot successfully deleted!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delete", "Error deleting document", e);
                    }
                });
    }

    /**
     * Populate listener value with Firebase Instance ID
     * @param listen
     */
    public void assignUserDB(MutableLiveData<String> listen) {
        //Query firebase for our installation ID.
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Log.e("Installations", "Installation ID: " + task.getResult());
                            listen.setValue(task.getResult());
                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
                        }
                    }
                });
    }

}
