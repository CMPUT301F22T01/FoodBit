package com.CMPUT301F22T01.foodbit.models;

import android.net.Uri;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a recipe with an id, a title, preparation time, number of servings,
 * a category, comments, a photo, and a list of ingredients.
 */
public class Recipe implements dbObject {
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

    /**
     * Constructs a recipe with a <code>QueryDocumentSnapshot</code> object obtained from the database.
     * @param doc a <code>QueryDocumentSnapshot</code> object
     */
    public Recipe(QueryDocumentSnapshot doc) {
        this(doc.getId(),
                Objects.requireNonNull(doc.get("title")).toString(),
                (int) (long) doc.get("prepTime"),
                (int) (long) doc.get("numServings"),
                (String) doc.get("category"),
                (String) doc.get("comments"),
                doc.get("photo") != null ? Uri.parse((String) doc.get("photo")) : null,
                null);

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (HashMap map :
                (ArrayList<HashMap>) Objects.requireNonNull(doc.get("ingredients"))) {
            ingredients.add(new Ingredient(
                    (String) map.get("id"),
                    (String) map.get("description"),
                    (String) map.get("bestBefore"),
                    (String) map.get("location"),
                    (float) (double) map.get("amount"),
                    (String) map.get("unit"),
                    (String) map.get("category")));
        }
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
     * Check if this recipe contains an ingredient with the same ID.
     * @param mIngredient the ingredient to check if in the recipe
     * @return whether the ingredient is in the recipe
     */
    public boolean containsIngredient(Ingredient mIngredient) {
        for (Ingredient ingredient :
                ingredients) {
            if (Objects.equals(ingredient.getId(), mIngredient.getId())) {
                return true;
            }
        }
        return false;
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
            Integer prepTime1 = r1.getPrepTime();
            Integer prepTime2 = r2.getPrepTime();

            return prepTime1 - prepTime2;
        }
    };

    public static Comparator<Recipe> servingSort = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2)
        {
            Integer numServing1 = r1.getNumServings();
            Integer numServing2 = r2.getNumServings();

            return numServing1 - numServing2;
        }
    };

    public static Comparator<Recipe> categoryAscending = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2)
        {
            String cat1 = String.valueOf(r1.getCategory());
            String cat2 = String.valueOf(r2.getCategory());

            return String.CASE_INSENSITIVE_ORDER.compare(cat1,cat2);
        }
    };
}
