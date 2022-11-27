package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.ui.MainActivity;
import com.CMPUT301F22T01.foodbit.models.IngredientCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores each of the categories used in ingredients
 */
public class IngredientCategoryController implements Serializable {
    private DatabaseController db = new DatabaseController("Categories");
    private final ArrayList<IngredientCategory> categories;

    /**
     * Creates a new list of IngredientCategory items
     */
    public IngredientCategoryController() {
        categories = new ArrayList<IngredientCategory>();
    }

    /**
     * Creates a new array list for the ingredient category options
     * @param categories a list of IngredientCategory
     */
    public IngredientCategoryController(List<IngredientCategory> categories) {
        this.categories = (ArrayList<IngredientCategory>) categories;
    }

    /**
     * Allows categories to be retrieved
     * @return list of the categories
     */
    public ArrayList<IngredientCategory> getCategories() {
        return categories;
    }

    /**
     * Allows categories to be set
     * @param categories the array list of categories to be set
     */

    public void setCategories(ArrayList<IngredientCategory> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }

    /**
     * Allows retrieving of the description/name of each category
     * @return list of the category names
     */
    public List<String> getCategoryDescription() {
        List<String> list = new ArrayList<>();
        for (IngredientCategory category : categories) {
            list.add(category.getCategoryName());
        }
        return list;
    }

    /**
     * Check if a category is in the list
     * @param category the category to be checked
     * @return if the category is in categories or not
     */
    public boolean contains(IngredientCategory category) {
        return categories.contains(category);
    }

    /**
     * Allows a new category to be added
     * @param category category to be added
     */
    public void add(IngredientCategory category) {
        String TAG = "IngredientCategoryAddCategory";
        assert !contains(category) : "This category already exists!";
        db.addItem(category);
    }

    /**
     * Allows an existing category to be deleted
     * @param category the category to be deleted
     */
    public void delete(IngredientCategory category) {
        String TAG = "IngredientCategoryDeleteCategory";
        assert contains(category) : "this category does not exist!!";
        db.deleteItem(category);
    }

    /**
     * Loading all of the categories from the database
     */
    public void loadAllFromDB() {
        categories.clear();
        CollectionReference collectionReference = MainActivity.categoryListRef;
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.e("db is loading  !!!!!!!!!! ",  collectionReference.getPath().toString());
                    for (int i =0; i< task.getResult().size(); i++) {
                        IngredientCategory model = task.getResult().getDocuments().get(i).toObject(IngredientCategory.class);
                        categories.add(model);
                    }
                }
            }
        });
    }
}
