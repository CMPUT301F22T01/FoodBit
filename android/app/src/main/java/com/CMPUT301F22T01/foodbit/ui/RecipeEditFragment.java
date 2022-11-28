package com.CMPUT301F22T01.foodbit.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.models.Recipe;

import java.util.Objects;

public class RecipeEditFragment extends RecipeInputFragment {
    private int position;
    private Recipe recipe;

    public interface OnRecipeEditedListener {
        void onEdited();
    }
    private OnRecipeEditedListener recipeEditedListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        assert getArguments() != null;
        position = getArguments().getInt("position");
        recipe = recipeController.getRecipeByPosition(position);
        ingredients = recipe.getIngredients();
        recipeEditedListener = (OnRecipeEditedListener) getParentFragment();
        assert recipeEditedListener != null;
        Log.d(TAG, "Parent fragment: "+ getParentFragment());
    }

    @Override
    public void onResume() {
        super.onResume();
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                topBar.setTitle("Editing "+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void displayInfo() {
        super.displayInfo();
        titleEditText.setText(recipe.getTitle());
        prepTimeEditText.setText(String.valueOf(recipe.getPrepTime()));
        numServingsEditText.setText(String.valueOf(recipe.getNumServings()));
        categoryEditText.setText(recipe.getCategory());
        commentsEditText.setText(recipe.getComments());
        if (recipe.getPhoto() != null) {
            imageView.setImageURI(recipe.getPhoto());
            hasPhoto = true;
        }
    }


    @Override
    public void setTAG() {
        TAG = "EditRecipe";
    }

    @Override
    protected String getTitle() {
        return "Editing "+recipe.getTitle();
    }

    @Override
    protected void doneButtonClicked() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString();
        String prepTime = Objects.requireNonNull(prepTimeEditText.getText()).toString();
        String numServings = Objects.requireNonNull(numServingsEditText.getText()).toString();
        String category = Objects.requireNonNull(categoryEditText.getText()).toString();
        if (category.equals("")){category = null;}
        String comments = Objects.requireNonNull(commentsEditText.getText()).toString();
        if (comments.equals("")){comments = null;}
        // check empty fields
        boolean requiredFieldEntered = true;
        if (title.equals("")) {
            titleLayout.setError("Required");
            requiredFieldEntered = false;
        } else if (recipeController.getTitles().contains(title) && !title.equals(recipe.getTitle())) {
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
        if (requiredFieldEntered) {
            recipe.setTitle(title);
            recipe.setPrepTime(Integer.parseInt(prepTime));
            recipe.setNumServings(Integer.parseInt(numServings));
            recipe.setCategory(category);
            recipe.setComments(comments);
            if (photoBitmap != null) {
                recipe.setPhoto(saveImage());
            }
            if (!hasPhoto) {
                recipe.setPhoto(null);
            }
            recipe.setIngredients(ingredients);
            recipeController.edit(recipe);
            MainActivity.mealPlanController.notifyRecipeChanged(recipe);
            recipeEditedListener.onEdited();
            dismiss();
        } else {
            Toast.makeText(context, "Invalid input value(s) - check all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
