

package com.CMPUT301F22T01.foodbit.models;

import static android.icu.math.BigDecimal.ROUND_HALF_EVEN;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.math.BigDecimal;
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

    public void setIngredientsFromRecipeScaled(ArrayList<Ingredient> ingredients, int recipeServings) {
        ArrayList<Ingredient> t = new ArrayList<Ingredient>();
        BigDecimal tServings = BigDecimal.valueOf(this.servings);
        BigDecimal rServings = BigDecimal.valueOf(recipeServings);
        for (int i = 0; i<ingredients.size(); i++) {
            Ingredient copy = new Ingredient();
            copy.setId(ingredients.get(i).getId());
            copy.setDescription(ingredients.get(i).getDescription());
            copy.setUnit(ingredients.get(i).getUnit());
            BigDecimal tAmount = BigDecimal.valueOf(ingredients.get(i).getAmount());
            //scale the recipe ingredients by the need.
            // recipeIngredientAmount/Recipe Serving Size   * desired meal serving size
            // Rearranged to do the dividing at the end.
            tAmount.setScale(10);
            BigDecimal temp = tAmount.multiply(tServings).divide(rServings,10,ROUND_HALF_EVEN);
            copy.setAmount(temp.setScale(2,ROUND_HALF_EVEN).floatValue());
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

    public void setIngredientsFromRecipe(ArrayList<Ingredient> ingredients) {
        ArrayList<Ingredient> t = new ArrayList<Ingredient>();
        for (int i = 0; i<ingredients.size(); i++) {
            Ingredient copy = new Ingredient();
            copy.setId(ingredients.get(i).getId());
            copy.setDescription(ingredients.get(i).getDescription());
            copy.setUnit(ingredients.get(i).getUnit());
            copy.setAmount(ingredients.get(i).getAmount());
            t.add(copy);
        }
        this.ingredients = t;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public MealPlan(){}

    /**
     * Create meal from a firebase document.
     * @param doc
     */
    public MealPlan(QueryDocumentSnapshot doc) {
        MealPlan meal = createFromDocCustom(doc);
        this.update(meal);
    }

    /**
     * Facilitate creating new meal objects from a document for dbObjectCustom implementation.
     * @param doc
     * @return meal
     */
    public MealPlan createFromDocCustom(QueryDocumentSnapshot doc) {
        MealPlan meal2 = new MealPlan();
        meal2.setName((String) doc.get("name"));
        meal2.setServings( (int) (long) doc.get("servings"));
        meal2.setId(doc.getId());
        meal2.setIngredient((boolean) doc.get("ingredient"));
        meal2.setDate(((Timestamp) doc.get("date")).toDate());
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (HashMap map :
                (ArrayList<HashMap>) doc.get("ingredients")) {
            ingredients.add(new Ingredient(
                    (String) map.get("id"),
                    (String) map.get("description"),
                    (String) map.get("bestBefore"),
                    (String) map.get("location"),
                    (float) (double) map.get("amount"),
                    (String) map.get("unit"),
                    (String) map.get("category")));
        }
        meal2.setIngredientsFromRecipe(ingredients);
        meal2.setRecipeID(doc.get("recipeID").toString());
        return meal2;
    }

    public MealPlan(String name, int servings, String id, boolean isIngredient, Date date) {
        this.name = name;
        this.servings = servings;
        this.id = id;
        this.isIngredient = isIngredient;
        this.date = date;
    }

    public MealPlan(String name, int servings, String id, boolean isIngredient, Date date, String recipeID, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.servings = servings;
        this.id = id;
        this.isIngredient = isIngredient;
        this.date = date;
        this.recipeID = recipeID;
        this.ingredients = ingredients;
    }

    public static Comparator<MealPlan> sortByDate = new Comparator<MealPlan>() {
        @Override
        public int compare(MealPlan o1, MealPlan o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };

    public void update(MealPlan meal) {
        this.name = meal.getName();
        this.servings = meal.getServings();
        this.id = meal.getId();
        this.isIngredient = meal.isIngredient();
        this.date = meal.getDate();
        this.recipeID = meal.getRecipeID();
        this.ingredients = meal.getIngredients();
    }

}

