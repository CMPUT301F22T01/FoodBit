

package com.CMPUT301F22T01.foodbit.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * A class to represent a MealPlan with a name, number of servings, id,
 * a boolean indicating whether it is an ingredient, a date, an a list of ingredients.
 */
public class MealPlan implements dbObject, dbObjectCustom {
    /**
     * Looking to model MealPlans as Collections
     * MealPlan (Collection)
     *        --ID1 (Document - Ingredient, ID#20)
     *              --name: "Apple"
     *              --servingSize: "2"
     *              --recipeID: "20"
     *              --isIngredient: "1"
     *              --Date: "2022-10-23"
     *        --ID2 (Document - Recipe, ID#3)
     *              --name: "Pie"
     *              --servings: "1"
     *              --Date: "2022-10-23"
     *              --recipeID: "3"
     *              --isIngredient: "0"
     *              --ingredientList: {"231":3, "23":5} // Need 3 of ingredient 231s and 5 of ingredient 23
     *
     *
     * MealPlanController should be able to then query the recipe controller for the appropriate
     * ingredients for recipes and provide shopping list with the required ingredients
     *
     */
    private String name;
    private int servings;
    private String id;
    private boolean isIngredient;
    private Date date;
    private String recipeID;
    private ArrayList<Ingredient> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIngredient() {
        return isIngredient;
    }

    public void setIngredient(boolean ingredient) {
        isIngredient = ingredient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /***
     * Return ingredient list required for this meal.
     * @return Ingredients required for this mealplan. Only ID and amount fields are guaranteed.
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredientsFromRecipe(ArrayList<Ingredient> ingredients, int recipeServings) {
        ArrayList<Ingredient> t = new ArrayList<Ingredient>();
        float scalingFactor = (float)this.servings/recipeServings;
        for (int i = 0; i<ingredients.size(); i++) {
            Ingredient copy = new Ingredient();
            copy.setId(ingredients.get(i).getId());
            copy.setDescription(ingredients.get(i).getDescription());
            copy.setUnit(ingredients.get(i).getUnit());
            copy.setAmount(ingredients.get(i).getAmount() * scalingFactor);
            t.add(copy);
        }
        this.ingredients = t;
    }

    public void setIngredients(Ingredient ingredient) {
        ArrayList<Ingredient> t = new ArrayList<Ingredient>();
        Ingredient copy = new Ingredient();
        copy.setId(ingredient.getId());
        copy.setDescription(ingredient.getDescription());
        copy.setAmount((float)this.getServings());
        copy.setUnit(ingredient.getUnit());
        t.add(copy);
        ingredients = t;
    }

    public String getRecipeId() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public MealPlan(){};

    public MealPlan(QueryDocumentSnapshot doc) {
        this((String) doc.get("name"),
                (int) (long) doc.get("servings"),
                doc.getId(),
                (boolean) doc.get("ingredient"),
                (Date) ((Timestamp) doc.get("date")).toDate());
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (HashMap map :
                (ArrayList<HashMap>) doc.get("ingredients")) {
            ingredients.add(new Ingredient(
                    (String) map.get("description"),
                    (float) (double) map.get("amount"),
                    (String) map.get("unit"),
                    (String) map.get("category")));
        }
        this.ingredients = ingredients;
        this.recipeID = doc.get("recipeID").toString();
    }

    public MealPlan createFromDocCustom(QueryDocumentSnapshot doc) {
        name = (String) doc.get("name");
        servings = (int) (long) doc.get("servings");
        id = doc.getId();
        isIngredient =  (boolean) doc.get("ingredient");
        date = (Date) ((Timestamp) doc.get("date")).toDate();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (HashMap map :
                (ArrayList<HashMap>) doc.get("ingredients")) {
            ingredients.add(new Ingredient(
                    (String) map.get("description"),
                    (float) (double) map.get("amount"),
                    (String) map.get("unit"),
                    (String) map.get("category")));
        }
        this.ingredients = ingredients;
        this.recipeID = doc.get("recipeID").toString();
        return this;
    }

    public MealPlan(String name, int servings, String id, boolean isIngredient, Date date) {
        this.name = name;
        this.servings = servings;
        this.id = id;
        this.isIngredient = isIngredient;
        this.date = date;
    }

    public MealPlan(String name, int servings, String id, boolean isIngredient, Date date, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.servings = servings;
        this.id = id;
        this.isIngredient = isIngredient;
        this.date = date;
        this.ingredients = ingredients;
    }

    public static Comparator<MealPlan> sortByDate = new Comparator<MealPlan>() {
        @Override
        public int compare(MealPlan o1, MealPlan o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };

}


