package com.CMPUT301F22T01.foodbit.models;

import java.io.Serializable;

public class IngredientUnit implements Serializable, dbObject {
    private String unitName;
    private String id;

    public IngredientUnit() {
        // Empty constructor
    }

    public IngredientUnit(String unitName, String id) {
        this.unitName = unitName;
        this.id = id;
    }

    public IngredientUnit(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getId() {
        return id;
    }

    public void setUnitName(String newUnitName) {
        this.unitName = newUnitName;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
