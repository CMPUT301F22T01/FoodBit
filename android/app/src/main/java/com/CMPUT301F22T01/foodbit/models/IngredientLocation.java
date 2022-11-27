package com.CMPUT301F22T01.foodbit.models;

import java.io.Serializable;

/**
 * Class to represent location for ingredients
 * Categories can have a name and an id
 */
public class IngredientLocation implements Serializable, dbObject {
    private String locationName;
    private String id;

    /**
     * Required empty constructor
     */
    public IngredientLocation() {
        // Empty constructor
    }

    /**
     * Creating an IngredientLocation object that can have a name and an id
     * @param locationName name of the location
     * @param id id of the location
     */
    public IngredientLocation(String locationName, String id) {
        this.locationName = locationName;
        this.id = id;
    }

    /**
     * Creating an IngredientLocation object that can have a name
     * @param locationName name of the location
     */
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
