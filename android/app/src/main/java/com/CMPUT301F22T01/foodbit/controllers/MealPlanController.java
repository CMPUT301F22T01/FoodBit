

package com.CMPUT301F22T01.foodbit.controllers;

import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.models.Recipe;

import java.util.ArrayList;

/**
 * Receive requests from UI and other Controllers to update the meal plan and pass along
 * appropriate updates to the shopping list
 */
public class MealPlanController {

    private ArrayList<MealPlan> mealPlan;
    private DatabaseController db;
//    private ArrayList<Ingredient> allIngredients;

    /**
     * Creates a new list of MealPlans
     */
    public MealPlanController(){
        mealPlan = new ArrayList<MealPlan>();
        db = new DatabaseController("Meals");
//        this.loadAllMeals();
    }

    /**
     * Adds a new meal to the database from the UI
     * @param meal the meal to be added
     */
    public void addMeal(MealPlan meal){
        db.addItem(meal);
        mealPlan.add(meal);
    }

    public void edit(MealPlan meal) {
        assert contains(meal) : "This meal is not found int the meal list";
        db.editItem(meal);
    }

    /**
     * Deletes a meal from the meal plan
     * @param meal the meal to be deleted
     */
    public void deleteMeal(MealPlan meal) {
        assert contains(meal) : "this meal is not found in the meal plan!";
        db.deleteItem(meal);
        mealPlan.remove(meal);
    }


    /**
     * Sort and return a cache of the mealPlan by date
     * @return the sorted array of MealPlans
     */
    public ArrayList<MealPlan> getArrayList() {
        return mealPlan;
    }

    /**
     * Returns true if the meal plan contains the meal.
     * @param meal meal whose presence in this meal plan is to be tested
     * @return whether the meal plan contains the meal
     */
    public boolean contains(MealPlan meal) {
        for (int i =0; i < mealPlan.size(); i++) {
            if (mealPlan.get(i).getId().equals(meal.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update cache with new meals
     * @param newMealPlan
     */
    public void update(ArrayList<MealPlan> newMealPlan) {
        mealPlan.clear();
        mealPlan.addAll(newMealPlan);
    }

    /**
     * Converts the array of MealPlans to a string of their IDs
     * @return
     */
    public String toString() {
        String t = "";
        for (int i =0; i < mealPlan.size(); i++) {
            t = t + mealPlan.get(i).getId();
        }
        return t;
    }

    /**
     * Get a meal by its index in the list.
     * @param position the index of the meal
     * @return the meal at the position
     */
    public MealPlan getMealByPosition(int position) {
        return mealPlan.get(position);
    }

    /**
     *
     * @param ID
     * @return -1 if ingredient ID doesnt exist within the ingredient list. Otherwise return index
     */
    public int lookUpIngredientID(String ID, ArrayList<Ingredient> ingredList) {
        for (int i = 0; i< ingredList.size(); i++) {
            if (ID.equals(ingredList.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns all the ingredients required for this meal plan. Only ID and Amount are guaranteed
     * to be accurate.
     * @return the arraylist of ingredients
     */
    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> allIngredients = new ArrayList<Ingredient>();
        for (int i =0; i < mealPlan.size(); i++) { //iterate through all meals
            MealPlan meal = mealPlan.get(i);
            for (int j = 0; j<meal.getIngredients().size();j++) {//Iterate through all ingreds within meal
                Ingredient currentIngred = meal.getIngredients().get(j);
                int index = lookUpIngredientID(currentIngred.getId(),allIngredients);
                if(index != -1) {
                    allIngredients.get(index).setAmount(currentIngred.getAmount() +
                            allIngredients.get(index).getAmount());
                } else {
                    allIngredients.add(currentIngred);
                }
            }
        }
        return allIngredients;
    }

    public void load() {
        mealPlan.clear();
        db.getAllItemsCustom(mealPlan, new MealPlan());
    }

    /**
     * Update the Mealplan with recipe changes
     * @param recipe
     */
    public void notifyRecipeChanged(Recipe recipe) {
        for (int i =0; i < mealPlan.size(); i++) {
            if (mealPlan.get(i).getRecipeID().equals(recipe.getId())) {
                //Recipe exists within meal, edit the meal
                MealPlan meal = mealPlan.get(i);
                meal.setName(recipe.getTitle());
                meal.setIngredientsFromRecipe(recipe.getIngredients(),recipe.getNumServings());
                this.edit(meal);
            }
        }
    }
}

