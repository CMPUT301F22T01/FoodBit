package com.CMPUT301F22T01.foodbit.ui;

import static com.CMPUT301F22T01.foodbit.MainActivity.listen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.CMPUT301F22T01.foodbit.R;
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
        int selectionPosition = adapter.getPosition(meal.getName());
        ingredientRecipeSpinner.setSelection(selectionPosition);
        mealDatePicker.setDate(meal.getDate());


        return view;
    }

    @Override
    public void mealEditOrAdd(MealPlan meal) {
        mealPlanController.edit(meal);
        // Reload detail fragment
        MealDetailFragment fragment = (MealDetailFragment) this.getParentFragment();
        fragment.populateData();
    }

    public MealPlan getUpdatedMeal() {
        return meal;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }
}
