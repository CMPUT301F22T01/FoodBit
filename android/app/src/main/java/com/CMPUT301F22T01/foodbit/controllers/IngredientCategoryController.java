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

public class IngredientCategoryController implements Serializable {
    private DatabaseController db = new DatabaseController("Categories");
    private final ArrayList<IngredientCategory> categories;

    public IngredientCategoryController() {
        categories = new ArrayList<IngredientCategory>();
    }

    public IngredientCategoryController(List<IngredientCategory> categories) {
        this.categories = (ArrayList<IngredientCategory>) categories;
    }

    public ArrayList<IngredientCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<IngredientCategory> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public List<String> getCategoryDescription() {
        List<String> list = new ArrayList<>();
        for (IngredientCategory category : categories) {
            list.add(category.getCategoryName());
        }
        return list;
    }

    public boolean contains(IngredientCategory category) {
        return categories.contains(category);
    }

    public void add(IngredientCategory category) {
        String TAG = "IngredientCategoryAddCategory";
        assert !contains(category) : "This category already exists!";
        db.addItem(category);
    }

    public void delete(IngredientCategory category) {
        String TAG = "IngredientCategoryDeleteCategory";
        assert contains(category) : "this category does not exist!!";
        db.deleteItem(category);
    }

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
