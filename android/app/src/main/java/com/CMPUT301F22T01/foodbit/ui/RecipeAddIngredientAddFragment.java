package com.CMPUT301F22T01.foodbit.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.List;

/**
 * A pop up dialog in the <code>recipe add screen</code> that allows users to enter required and optional information to add a ingredient to the recipe.
 */
public class RecipeAddIngredientAddFragment extends DialogFragment {

    public static final String TAG = "RecipeAddIngredientAdd";

    private OnIngredientAddListener listener;

    public interface OnIngredientAddListener {
        void onIngredientAdd(Ingredient newIngredient);
    }
    // todo: input check
    private TextInputLayout descriptionLayout;
    private TextInputEditText descriptionEditText;
    private TextInputLayout amountLayout;
    private TextInputEditText amountEditText;
    private TextInputLayout unitLayout;
    private TextInputEditText unitEditText;
    private TextInputLayout categoryLayout;
    private TextInputEditText categoryEditText;

    private ArrayList<String> titleList;

    public static RecipeAddIngredientAddFragment newInstance(ArrayList<String> titleList) {
        Bundle args = new Bundle();
        args.putStringArrayList("title list", titleList);
        RecipeAddIngredientAddFragment fragment = new RecipeAddIngredientAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof OnIngredientAddListener) {
            listener = (OnIngredientAddListener) parentFragment;
        } else {
            Log.d(TAG, String.valueOf(parentFragment));
            assert parentFragment != null;
            throw new RuntimeException(
                    parentFragment + " must implement OnIngredientAddListener"
            );
        }
        assert getArguments() != null;
        titleList = getArguments().getStringArrayList("title list");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_recipe_add_ingredient_add, null);

        descriptionLayout = view.findViewById(R.id.recipe_add_ingredient_add_layout_description);
        amountLayout = view.findViewById(R.id.recipe_add_ingredient_add_layout_amount);
        unitLayout = view.findViewById(R.id.recipe_add_ingredient_add_layout_unit);
        categoryLayout = view.findViewById(R.id.recipe_add_ingredient_add_layout_category);
        descriptionEditText = view.findViewById(R.id.recipe_add_ingredient_add_edit_text_description);
        amountEditText = view.findViewById(R.id.recipe_add_ingredient_add_edit_text_amount);
        unitEditText = view.findViewById(R.id.recipe_add_ingredient_add_edit_text_unit);
        categoryEditText = view.findViewById(R.id.recipe_add_ingredient_add_edit_text_category);

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null)
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogButtons();
    }

    private void setDialogButtons() {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if(dialog != null)
        {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                // todo: complete input check
                boolean canAddIngredient = true;
                String description = String.valueOf(descriptionEditText.getText());
                if (description.equals("")) {canAddIngredient =false; descriptionLayout.setError("Required");}
                else if (titleList.contains(description)) {canAddIngredient =false; descriptionLayout.setError("Description already exists");}
                String amountStr = String.valueOf(amountEditText.getText());
                if (amountStr.equals("")) {canAddIngredient =false; amountLayout.setError("Required");}
                String unit = String.valueOf(unitEditText.getText());
                if (unit.equals("")) {canAddIngredient = false; unitLayout.setError("Required");}
                String category = String.valueOf(categoryEditText.getText());
                if (category.equals("")) {category = null;}
                if(canAddIngredient) {
                    listener.onIngredientAdd(new Ingredient(description, Float.parseFloat(amountStr), unit, category));
                    dialog.dismiss();
                }
            });
        }
    }
}