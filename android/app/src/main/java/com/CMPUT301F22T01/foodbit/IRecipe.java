package com.CMPUT301F22T01.foodbit;

import java.util.Map;

public interface IRecipe {

    /**
     * Get ingredient list of a recipe
     * @return map of ingredient id's and the number of ingredients you need to make this recipe
     */
    // todo: rename this method
    // Firestore identifies "fields" in a custom class by looking at the getters.
    // And it identifies "getters" by looking for methods with a name that
    // follows the Java convention of getters naming (aka "getSomething").
    // This methods is originally named "getIngredientList", and Firestore grabs the methods and
    // set its returned value as the value of the "ingredientList" field.
    Map<Integer, Integer> getIngredientList();

}
