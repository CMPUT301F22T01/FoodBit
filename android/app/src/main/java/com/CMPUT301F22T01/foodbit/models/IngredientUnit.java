package com.CMPUT301F22T01.foodbit.models;

import java.io.Serializable;

/**
 * Class to represent units for ingredients
 * Units can have a name and an id
 */
public class IngredientUnit implements Serializable, dbObject {
    private String unitName;
    private String id;

    /**
     * Required empty constructor
     */
    public IngredientUnit() {
        // Empty constructor
    }

    /**
     * Creating an IngredientUnit object that can have a name and an id
     * @param unitName name of the unit
     * @param id id of the unit
     */
    public IngredientUnit(String unitName, String id) {
        this.unitName = unitName;
        this.id = id;
    }

    /**
     * Creating an UnitLocation object that can have a name
     * @param unitName name of the unit
     */
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
