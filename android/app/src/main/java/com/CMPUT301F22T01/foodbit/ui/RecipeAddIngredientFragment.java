package com.CMPUT301F22T01.foodbit.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

/**
 * A pop up dialog in the <code>recipe add screen</code> that allows users to enter required and optional information to add a ingredient to the recipe.
 */
public class RecipeAddIngredientFragment extends DialogFragment {

    public static final String TAG = "RecipeAddIngredientAdd";
    public static final int MODE_ADD = 0;
    public static final int MODE_EDIT = 1;
    private int mode;

    // listeners
    public interface OnIngredientAddListener {
        void onIngredientAdd(Ingredient newIngredient);
    }
    private OnIngredientAddListener ingredientAddListener;
    public interface OnIngredientEditListener {
        void onIngredientEdit(int position, Ingredient newIngredient);
    }
    private OnIngredientEditListener ingredientEditListener;
    public interface OnIngredientDeleteListener {
        void onIngredientDelete(int position);
    }
    private OnIngredientDeleteListener ingredientDeleteListener;

    // UI
    private TextInputLayout descriptionLayout;
    private TextInputEditText descriptionEditText;
    private TextInputLayout amountLayout;
    private TextInputEditText amountEditText;
    private TextInputLayout unitLayout;
    private TextInputEditText unitEditText;
    private TextInputLayout categoryLayout;
    private TextInputEditText categoryEditText;

    private ArrayList<String> titleList;
    private Ingredient ingredient;
    private int position;

    /**
     * When adding a new ingredient, instantiate a <code>RecipeAddIngredientFragment</code> to
     * pass in the list of all titles of added ingredients (to avoid duplicate names).
     * @param titleList the list of all titles of added ingredients
     * @return an instance of <code>RecipeAddIngredientFragment</code>
     */
    public static RecipeAddIngredientFragment newInstance(ArrayList<String> titleList) {
        Bundle args = new Bundle();
        args.putStringArrayList("title list", titleList);
        args.putInt("mode", MODE_ADD);
        RecipeAddIngredientFragment fragment = new RecipeAddIngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * When editing an ingredient, instantiate a <code>RecipeAddIngredientFragment</code> to pass
     * in the ingredient to edit and its position in the adapter to update/delete the edited
     * ingredients.
     * @param oldIngredient the edited ingredient
     * @param position the position of the ingredient in the adapter
     * @return an instance of <code>RecipeAddIngredientFragment</code>
     */
    public static RecipeAddIngredientFragment newInstance(Ingredient oldIngredient, int position) {
        Bundle args = new Bundle();
        args.putSerializable("ingredient", oldIngredient);
        args.putInt("mode", MODE_EDIT);
        args.putInt("position", position);
        RecipeAddIngredientFragment fragment = new RecipeAddIngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();

        // check if the parent fragment implements all required interfaces
        if (parentFragment instanceof OnIngredientAddListener) {
            ingredientAddListener = (OnIngredientAddListener) parentFragment;
        } else {
            Log.d(TAG, String.valueOf(parentFragment));
            throw new RuntimeException(
                    parentFragment + " must implement OnIngredientAddListener"
            );
        }
        if (parentFragment instanceof OnIngredientEditListener) {
            ingredientEditListener = (OnIngredientEditListener) parentFragment;
        } else {
            Log.d(TAG, String.valueOf(parentFragment));
            throw new RuntimeException(
                    parentFragment + " must implement OnIngredientEditListener"
            );
        }
        if (parentFragment instanceof OnIngredientDeleteListener) {
            ingredientDeleteListener = (OnIngredientDeleteListener) parentFragment;
        } else {
            Log.d(TAG, String.valueOf(parentFragment));
            throw new RuntimeException(
                    parentFragment + " must implement OnIngredientDeleteListener"
            );
        }

        // get arguments base on the mode
        assert getArguments() != null;
        mode = getArguments().getInt("mode");
        if (mode == MODE_ADD) {
            titleList = getArguments().getStringArrayList("title list");
        } else if (mode == MODE_EDIT) {
            ingredient = (Ingredient) getArguments().getSerializable("ingredient");
            position = getArguments().getInt("position");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_recipe_input_ingredient_add, null);

        // set up UI
        descriptionLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_description);
        amountLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_amount);
        unitLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_unit);
        categoryLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_category);
        descriptionEditText = view.findViewById(R.id.recipe_input_ingredient_add_edit_text_description);
        amountEditText = view.findViewById(R.id.recipe_input_ingredient_add_edit_text_amount);
        unitEditText = view.findViewById(R.id.recipe_input_ingredient_add_edit_text_unit);
        categoryEditText = view.findViewById(R.id.recipe_input_ingredient_add_edit_text_category);

        // build the dialog based on the mode
        if (mode == MODE_ADD) {
            return new AlertDialog.Builder(getContext())
                    .setView(view)
                    .setTitle("Add an ingredient")
                    .setNeutralButton("Cancel", null)
                    .setPositiveButton("Add", null)
                    .create();
        } else if (mode == MODE_EDIT) {
            // fill text fields with current information of the ingredient
            descriptionEditText.setText(ingredient.getDescription());
            amountEditText.setText(String.valueOf(ingredient.getAmount()));
            unitEditText.setText(ingredient.getUnit());
            if (ingredient.getCategory() != null) {
                categoryEditText.setText(ingredient.getCategory());
            }
            return new AlertDialog.Builder(getContext())
                    .setView(view)
                    .setTitle("Edit an ingredient")
                    .setNegativeButton("Delete", null)
                    .setNeutralButton("Cancel", null)
                    .setPositiveButton("Update", null)
                    .create();
        }
        throw new IllegalArgumentException("Invalid input mode for RecipeAddIngredient.");
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogButtons();
    }

    /**
     * Specify dialog buttons' behaviour on click based on the mode.
     */
    private void setDialogButtons() {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if(dialog != null)
        {
            if (mode == MODE_ADD) {
                Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(v -> {
                    boolean canAddIngredient = true;
                    // input check
                    String description = String.valueOf(descriptionEditText.getText());
                    if (description.equals("")) {
                        canAddIngredient = false;
                        descriptionLayout.setError("Required");
                    } else if (titleList.contains(description)) {
                        canAddIngredient = false;
                        descriptionLayout.setError("Description already exists");
                    }
                    String amountStr = String.valueOf(amountEditText.getText());
                    if (amountStr.equals("")) {
                        canAddIngredient = false;
                        amountLayout.setError("Required");
                    }
                    String unit = String.valueOf(unitEditText.getText());
                    if (unit.equals("")) {
                        canAddIngredient = false;
                        unitLayout.setError("Required");
                    }
                    String category = String.valueOf(categoryEditText.getText());
                    if (category.equals("")) {
                        category = null;
                    }
                    if (canAddIngredient) {
                        ingredientAddListener.onIngredientAdd(new Ingredient(description, Float.parseFloat(amountStr), unit, category));
                        dialog.dismiss();
                    }
                });
            } else if (mode == MODE_EDIT) {
                Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(v -> {
                    boolean canUpdateIngredient = true;
                    // input check
                    String description = String.valueOf(descriptionEditText.getText());
                    if (description.equals("")) {
                        canUpdateIngredient = false;
                        descriptionLayout.setError("Required");
                    }
                    String amountStr = String.valueOf(amountEditText.getText());
                    if (amountStr.equals("")) {
                        canUpdateIngredient = false;
                        amountLayout.setError("Required");
                    }
                    String unit = String.valueOf(unitEditText.getText());
                    if (unit.equals("")) {
                        canUpdateIngredient = false;
                        unitLayout.setError("Required");
                    }
                    String category = String.valueOf(categoryEditText.getText());
                    if (category.equals("")) {
                        category = null;
                    }
                    if (canUpdateIngredient) {
                        ingredientEditListener.onIngredientEdit(position, new Ingredient(description, Float.parseFloat(amountStr), unit, category));
                        dismiss();
                    }});
                Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error, null));
                negativeButton.setOnClickListener(v -> {
                    ingredientDeleteListener.onIngredientDelete(position);
                    dismiss();
                });
            }
        }
    }
}