package com.CMPUT301F22T01.foodbit.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.IngredientCategory;
import com.CMPUT301F22T01.foodbit.models.IngredientLocation;
import com.CMPUT301F22T01.foodbit.models.IngredientUnit;
import com.CMPUT301F22T01.foodbit.ui.IngredientAddFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IngredientUnitController implements Serializable {
    private DatabaseController db = new DatabaseController("Units");
    private final ArrayList<IngredientUnit> units;

    public IngredientUnitController() {
        units = new ArrayList<IngredientUnit>();
    }

    public IngredientUnitController(List<IngredientUnit> units) {
        this.units = (ArrayList<IngredientUnit>) units;
    }

    public ArrayList<IngredientUnit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<IngredientUnit> units) {
        this.units.clear();
        this.units.addAll(units);
    }

    public List<String> getUnitDescription() {
        List<String> list = new ArrayList<>();
        for (IngredientUnit unit : units) {
            list.add(unit.getUnitName());
        }
        return list;
    }

    public boolean contains(IngredientUnit unit) {
        return units.contains(unit);
    }

    public void add(IngredientUnit unit) {
        String TAG = "IngredientUnitAddUnit";
        assert !contains(unit) : "This unit already exists!";
        db.addItem(unit);
    }

    public void delete(IngredientUnit unit) {
        String TAG = "IngredientUnitDeleteUnit";
        assert contains(unit) : "this unit does not exist!!";
        db.deleteItem(unit);
    }

    public void loadAllFromDB() {
        units.clear();
        CollectionReference collectionReference = MainActivity.unitStorageRef;
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.e("db is loading  !!!!!!!!!! ",  collectionReference.getPath().toString());
                    for (int i =0; i< task.getResult().size(); i++) {
                        IngredientUnit model = task.getResult().getDocuments().get(i).toObject(IngredientUnit.class);
                        units.add(model);
                    }
                }
            }
        });
    }
}