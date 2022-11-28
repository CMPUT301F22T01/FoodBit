package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.ui.MainActivity;
import com.CMPUT301F22T01.foodbit.models.IngredientUnit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores each of the units used in ingredients
 */
public class IngredientUnitController implements Serializable {
    private DatabaseController db = new DatabaseController("Units");
    private final ArrayList<IngredientUnit> units;

    /**
     * Creates a new list of IngredientUnit items
     */
    public IngredientUnitController() {
        units = new ArrayList<IngredientUnit>();
    }

    /**
     * Creates a new array list for the ingredient unit options
     * @param units a list of IngredientUnit
     */
    public IngredientUnitController(List<IngredientUnit> units) {
        this.units = (ArrayList<IngredientUnit>) units;
    }

    /**
     * Allows units to be retrieved
     * @return list of the units
     */
    public ArrayList<IngredientUnit> getUnits() {
        return units;
    }

    /**
     * Allows units to be set
     * @param units the array list of units to be set
     */
    public void setUnits(ArrayList<IngredientUnit> units) {
        this.units.clear();
        this.units.addAll(units);
    }

    /**
     * Allows retrieving of the description/name of each unit
     * @return list of the unit names/abbreviations
     */
    public List<String> getUnitDescription() {
        List<String> list = new ArrayList<>();
        for (IngredientUnit unit : units) {
            list.add(unit.getUnitName());
        }
        return list;
    }

    /**
     * Check if a unit is in the list
     * @param unit the unit to be checked
     * @return if the unit is in units or not
     */
    public boolean contains(IngredientUnit unit) {
        return units.contains(unit);
    }

    /**
     * Allows a new unit to be added
     * @param unit the unit to be added
     */
    public void add(IngredientUnit unit) {
        String TAG = "IngredientUnitAddUnit";
        assert !contains(unit) : "This unit already exists!";
        db.addItem(unit);
    }

    /**
     * Allows an existing unit to be deleted
     * @param unit the unit to be deleted
     */
    public void delete(IngredientUnit unit) {
        String TAG = "IngredientUnitDeleteUnit";
        assert contains(unit) : "this unit does not exist!!";
        db.deleteItem(unit);
    }

    /**
     * Loading all of the units from the database
     */
    public void loadAllFromDB() {
        units.clear();
        db.getAllItems(units);
    }
}