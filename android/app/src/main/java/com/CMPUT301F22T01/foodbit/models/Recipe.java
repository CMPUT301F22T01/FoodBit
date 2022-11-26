package com.CMPUT301F22T01.foodbit.models;

import android.net.Uri;

import com.CMPUT301F22T01.foodbit.IRecipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a recipe with an id, a title, preparation time, number of servings,
 * a category, comments, a photo, and a list of ingredients.
 */
public class Recipe implements IRecipe, dbObject {
    private String id;
    private String title;
    private int prepTime;
    private int numServings;
    private String category;
    private String comments;
    private Uri photo;
    private ArrayList<Ingredient> ingredients;

    public Recipe() {
        // required empty constructor
    }

    /**
     * Constructs a recipe with a title, preparation time, number of servings, a category, comments, a photo, and a list of ingredients.
     * @param title the title of the recipe
     * @param prepTime the preparation time of the recipe in whole minutes (Max. 480 minutes)
     * @param numServings the number of servings of the recipe (Max. 100 servings)
     * @param category the category of the recipe
     * @param comments comments of the recipe
     * @param photo the uri to the photo of the recipe
     * @param ingredients a list of ingredients that the recipe needs
     */
    public Recipe(String title, int prepTime, int numServings, String category, String comments, Uri photo, ArrayList<Ingredient> ingredients) {
        this.id = null;
        this.title = title;
        this.prepTime = prepTime;
        this.numServings = numServings;
        this.category = category;
        this.comments = comments;
        this.photo = photo;
        this.ingredients = ingredients;
    }

    /**
     * Constructs a recipe with an id, a title, preparation time, number of servings, a category, comments, a photo, and a list of ingredients.
     * @param id the auto-generated id of the document in Firestore with information of the recipe
     * @param title the title of the recipe
     * @param prepTime the preparation time of the recipe in whole minutes (Max. 480 minutes)
     * @param numServings the number of servings of the recipe (Max. 100 servings)
     * @param category the category of the recipe
     * @param comments comments of the recipe
     * @param photo the uri to the photo of the recipe
     * @param ingredients a list of ingredients that the recipe needs
     */
    public Recipe(String id, String title, int prepTime, int numServings, String category, String comments, Uri photo, ArrayList<Ingredient> ingredients) {
        this.id = id;
        this.title = title;
        this.prepTime = prepTime;
        this.numServings = numServings;
        this.category = category;
        this.comments = comments;
        this.photo = photo;
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getNumServings() {
        return numServings;
    }

    public void setNumServings(int numServings) {
        this.numServings = numServings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Get ingredient list of a recipe.
     *
     * @return map of ingredient names and the number of ingredients you need to make this recipe
     */
    @Override
    // todo: return the array list
    public Map<String, Float> doGetIngredientList() {
        Map<String, Float> list = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            String key = ingredient.getDescription();
            float value = ingredient.getAmount();
            list.put(key, value);
        }
        return list;
    }


    public static Comparator<Recipe> titleAscending = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2)
        {
            String title1 = String.valueOf(r1.getTitle());
            String title2 = String.valueOf(r2.getTitle());

            return String.CASE_INSENSITIVE_ORDER.compare(title1,title2);
        }
    };

    public static Comparator<Recipe> prepTimeSort = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2)
        {
            Integer prepTime1 = Integer.valueOf(r1.getPrepTime());
            Integer prepTime2 = Integer.valueOf(r2.getPrepTime());

            return prepTime1 - prepTime2;
        }
    };

    public static Comparator<Recipe> servingSort = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2)
        {
            Integer numServing1 = Integer.valueOf(r1.getNumServings());
            Integer numServing2 = Integer.valueOf(r2.getNumServings());

            return numServing1 - numServing2;
        }
    };

}
