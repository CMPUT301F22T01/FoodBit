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
        return view;
    }

    @Override
    public void mealEditOrAdd(MealPlan meal) {
        mealPlanController.edit(meal);
    }

    public MealPlan getUpdatedMeal() {
        return meal;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }
}
