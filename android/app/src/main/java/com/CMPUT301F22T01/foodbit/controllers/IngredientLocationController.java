package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.ui.MainActivity;
import com.CMPUT301F22T01.foodbit.models.IngredientLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores each of the locations used in ingredients
 */
public class IngredientLocationController implements Serializable {
    private final DatabaseController db = new DatabaseController("Locations");
    private final ArrayList<IngredientLocation> locations;

    /**
     * Creates a new list of IngredientLocation items
     */
    public IngredientLocationController() {
        locations = new ArrayList<IngredientLocation>();
    }

    /**
     * Creates a new array list for the ingredient location options
     * @param locations a list of IngredientLocation
     */
    public IngredientLocationController(List<IngredientLocation> locations) {
        this.locations = (ArrayList<IngredientLocation>) locations;
    }

    /**
     * Allows location to be retrieved
     * @return list of the location
     */
    public ArrayList<IngredientLocation> getLocations() {
        return locations;
    }

    /**
     * Allows locations to be set
     * @param locations the array list of locations to be set
     */
    public void setLocations(ArrayList<IngredientLocation> locations) {
        this.locations.clear();
        this.locations.addAll(locations);
    }

    /**
     * Allows retrieving of the description/name of each location
     * @return list of the location names
     */
    public List<String> getLocationDescription() {
        List<String> list = new ArrayList<>();
        for (IngredientLocation location : locations) {
            list.add(location.getLocationName());
        }
        return list;
    }

    /**
     * Check if a location is in the list
     * @param location the location to be checked
     * @return if the location is in locations or not
     */
    public boolean contains(IngredientLocation location) {
        return locations.contains(location);
    }

    /**
     * Allows a new location to be added
     * @param location location to be added
     */
    public void add(IngredientLocation location) {
        String TAG = "IngredientLocationAddLocation";
        assert !contains(location) : "This location already exists!";
        db.addItem(location);
    }

    /**
     * Allows an existing category to be deleted
     * @param location the location to be deleted
     */
    public void delete(IngredientLocation location) {
        String TAG = "IngredientLocationDeleteLocation";
        assert contains(location) : "this location does not exist!!";
        db.deleteItem(location);
    }

    /**
     * Loading all of the locations from the database
     */
    public void loadAllFromDB() {
        locations.clear();
        db.getAllItems(locations);
    }
}
