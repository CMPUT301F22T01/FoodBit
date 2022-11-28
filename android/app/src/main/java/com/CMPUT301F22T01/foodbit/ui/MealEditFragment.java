package com.CMPUT301F22T01.foodbit.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F22T01.foodbit.models.MealPlan;

public class MealEditFragment extends MealAddFragment {

    public MealEditFragment(MealPlan meal) {
        super(meal);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        servingsEditText.setText(String.valueOf(meal.getServings()));
        mealAddTextView.setText(meal.getName());
        mealDatePicker.setDate(meal.getDate());
        if(meal.isIngredient()) {
            if (meal.getIngredients().get(0).getUnit() != null) {
                servingsEditText.setHint(meal.getIngredients().get(0).getUnit());
                servingsLayout.setHint(meal.getIngredients().get(0).getUnit());
            }
        } else {
            servingsEditText.setHint("Servings");
            servingsLayout.setHint("Servings");
        }


        return view;
    }

    /**
     * Edits a meal
     * @param meal the meal to be added or edited
     */
    @Override
    public void mealEditOrAdd(MealPlan meal) {
        mealPlanController.edit(meal);

        // Reload detail fragment
        MealDetailFragment fragment = (MealDetailFragment) this.getParentFragment();
        fragment.populateData();
    }

    /**
     * Gets the updated meal
     * @return the updated meal
     */
    public MealPlan getUpdatedMeal() {
        return meal;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }
}