package com.CMPUT301F22T01.foodbit;

import java.util.Map;

public interface IRecipe {

    /**
     * Get ingredient list of a recipe
     * @return map of ingredient id's and the number of ingredients you need to make this recipe
     */
    Map<String, Float> getIngredientList();

}
