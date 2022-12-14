package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A <code>recipe add screen</code> that allows users to enter required and optional information to add a recipe to the recipe book.
 * <br>
 * Issues: users are not yet able to edit or remove ingredients that are added to the recipe in this screen.
 * There is a false error reported by the IDE which actually works fine.
 */
public class RecipeAddFragment extends RecipeInputFragment {

    public RecipeAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        super.ingredients = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    // Tasks to do once a recipe is confirmed by the user
    protected void doneButtonClicked() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString();
        String prepTime = Objects.requireNonNull(prepTimeEditText.getText()).toString();
        String numServings = Objects.requireNonNull(numServingsEditText.getText()).toString();
        String category = Objects.requireNonNull(categoryEditText.getText()).toString();
        if (category.equals("")){category = null;}
        String comments = Objects.requireNonNull(commentsEditText.getText()).toString();
        if (comments.equals("")){comments = null;}
        Uri photoUri = hasPhoto ? saveImage() : null;

        //Checks to make sure everything is entered and is valid
//        if (category.equals("")){category = null;}
//        String comments = Objects.requireNonNull(commentsEditText.getText()).toString();
//        if (comments.equals("")){comments = null;}
        // check empty fields
        boolean requiredFieldEntered = true;
        if (title.equals("")) {
            titleLayout.setError("Required");
            requiredFieldEntered = false;
        } else if (recipeController.getTitles().contains(title)) {
            titleLayout.setError("This title already exists");
            requiredFieldEntered = false;
        }
        if (prepTime.equals("")) {
            prepTimeLayout.setError("Required");
            requiredFieldEntered = false;
        } else if (prepTime.length() > 3 || Integer.parseInt(prepTime) > 480) {
            requiredFieldEntered = false;
        }
        if (numServings.equals("")) {
            numServingsLayout.setError("Required");
            requiredFieldEntered = false;
        } else if (numServings.length() > 3 || Integer.parseInt(numServings) > 100) {
            requiredFieldEntered = false;
        }

        IngredientController ingredientController = MainActivity.ingredientController;
        List<String> ingredientDescriptionList = ingredientController.getDescriptions();
        ArrayList<Ingredient> ingredientList = ingredientController.getIngredients();

        //Checking if it is an existing ingredient or needs to be added
        for (Ingredient ingredient : ingredients) {
            if (!ingredientDescriptionList.contains(ingredient.getDescription()))
            {
                Ingredient newIngredient = new Ingredient(ingredient.getDescription(),
                        0,
                        ingredient.getUnit(),
                        ingredient.getCategory());
                ingredientController.add(newIngredient);
                ingredient.setId(newIngredient.getId());
                Log.d(TAG, "doneButtonClicked: "+ingredient.getId());
                ingredientAdapter.notifyDataSetChanged();
            }
            if (ingredientDescriptionList.contains(ingredient.getDescription()))
            {
                for (Ingredient matchIngredient : ingredientList)
                {
                    Log.d(TAG, "doneButtonClicked: Successful");
                    if (ingredient.getDescription().equals(matchIngredient.getDescription()))
                    {
                        ingredient.setId(matchIngredient.getId());
                        Log.d(TAG, "doneButtonClicked: "+ingredient.getId());

                    }
                }
            }
        }


        if (requiredFieldEntered) {
            Recipe recipe = new Recipe(title,
                    Integer.parseInt(prepTime),
                    Integer.parseInt(numServings),
                    category, comments, photoUri, ingredients);
            super.recipeController.add(recipe);
            dismiss();
        } else {
            Toast.makeText(super.context, "Invalid input value(s) - check all fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTAG() {
        TAG = "AddRecipe";
    }

    @Override
    protected String getTitle() {
        return("Add a recipe");
    }
}











