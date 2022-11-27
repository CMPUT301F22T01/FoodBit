package com.CMPUT301F22T01.foodbit.models;

import java.io.Serializable;

public class IngredientLocation implements Serializable, dbObject {
    private String locationName;
    private String id;

    public IngredientLocation() {
        // Empty constructor
    }

    public IngredientLocation(String locationName, String id) {
        this.locationName = locationName;
        this.id = id;
    }

    public IngredientLocation(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getId() {
        return id;
    }

    public void setLocationName(String newLocationName) {
        this.locationName = newLocationName;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
