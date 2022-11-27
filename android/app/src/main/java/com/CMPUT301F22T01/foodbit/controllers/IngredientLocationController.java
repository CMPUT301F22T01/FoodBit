package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.IngredientCategory;
import com.CMPUT301F22T01.foodbit.models.IngredientLocation;
import com.CMPUT301F22T01.foodbit.ui.IngredientAddFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IngredientLocationController implements Serializable {
    private DatabaseController db = new DatabaseController("Locations");
    private final ArrayList<IngredientLocation> locations;

    public IngredientLocationController() {
        locations = new ArrayList<IngredientLocation>();
    }

    public IngredientLocationController(List<IngredientLocation> locations) {
        this.locations = (ArrayList<IngredientLocation>) locations;
    }

    public ArrayList<IngredientLocation> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<IngredientLocation> locations) {
        this.locations.clear();
        this.locations.addAll(locations);
    }

    public List<String> getLocationDescription() {
        List<String> list = new ArrayList<>();
        for (IngredientLocation location : locations) {
            list.add(location.getLocationName());
        }
        return list;
    }

    public boolean contains(IngredientLocation location) {
        return locations.contains(location);
    }

    public void add(IngredientLocation location) {
        String TAG = "IngredientLocationAddLocation";
        assert !contains(location) : "This location already exists!";
        db.addItem(location);
    }

    public void delete(IngredientLocation location) {
        String TAG = "IngredientLocationDeleteLocation";
        assert contains(location) : "this location does not exist!!";
        db.deleteItem(location);
    }

    public void loadAllFromDB() {
        locations.clear();
        CollectionReference collectionReference = MainActivity.locationStorageRef;
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.e("db is loading  !!!!!!!!!! ",  collectionReference.getPath().toString());
                    for (int i =0; i< task.getResult().size(); i++) {
                        IngredientLocation model = task.getResult().getDocuments().get(i).toObject(IngredientLocation.class);
                        locations.add(model);
                    }
                }
            }
        });
    }
}
