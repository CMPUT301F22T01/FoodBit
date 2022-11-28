package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A <code>meal detail screen</code> that displays the details of a meal.
 */
public class MealDetailFragment extends Fragment {

    private static final String TAG = "Meal Detail Fragment";
    private static final MealPlanController mealPlanController = MainActivity.mealPlanController;

    private MealPlan mealPlan;
    private int position = -1;

    Toolbar topBar;
    TextView mealNameView;
    TextView servingsView;
    TextView ingredientsFieldView;
    Button deleteButton;
    RecyclerView ingredientsRecyclerView;
    TextView ingredientEmptyView;
    IngredientAdapter ingredientAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;

    public MealDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMeal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        topBar = view.findViewById(R.id.meal_detail_toolbar);
        mealNameView = view.findViewById(R.id.meal_detail_name);
        servingsView = view.findViewById(R.id.meal_detail_servings);
        ingredientsRecyclerView = view.findViewById(R.id.meal_detail_ingredient_list);
        ingredientsFieldView = view.findViewById(R.id.meal_detail_ingredients_field);
        ingredientEmptyView = view.findViewById(R.id.meal_detail_ingredients_empty);
        collapsingToolbarLayout = view.findViewById(R.id.meal_detail_top_bar);

        // back button functionality
        topBar.setNavigationOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // delete button functionality
        deleteButton = view.findViewById(R.id.button_meal_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mealPlanController.deleteMeal(mealPlan);
                Navigation.findNavController(v).popBackStack();
            }
        });

        // edit button functionality
        topBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.meal_detail_edit) {
                editButtonClicked();
            }
            return false;
        });

        // ate button
        Button ateButton = view.findViewById(R.id.button_meal_ate);
        ateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(consumeIngredients(mealPlan)) {
                    MainActivity.mealPlanController.deleteMeal(mealPlan);
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.nav_container),
                            "Meal consumed! Corresponding ingredients will be removed from your" +
                                    "ingredient list!", Snackbar.LENGTH_SHORT);
                    snackbar.setAnchorView(R.id.nav_bar).show();
                    Navigation.findNavController(v).popBackStack();
                } else{
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.nav_container),
                            "Not enough ingredients within storage!", Snackbar.LENGTH_SHORT);
                    snackbar.setAnchorView(R.id.nav_bar).show();
                }
            }
        });

        // set text
        populateData();

        return view;
    }

    /**
     * Sets up the recyclerView to display the list of meals
     */
    private void setUpRecyclerView() {
        ingredientAdapter = new IngredientAdapter(mealPlan.getIngredients());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(ingredientsRecyclerView.getContext(), linearLayoutManager.getOrientation()));
    }

    /**
     * Gets the meal
     */
    private void getMeal() {
        assert getArguments() != null;
        position = getArguments().getInt("position");
        mealPlan = MainActivity.mealPlanController.getMealByPosition(position);
        Log.d(TAG, String.valueOf(mealPlan));
    }

    /**
     * Check if we have enough ingredients to consume the meal
     * @return true if enough, false if not.
     */
    public boolean checkIngredients(MealPlan meal) {
        ArrayList<Ingredient> mealIngredients = meal.getIngredients();
        for (int i = 0; i <mealIngredients.size(); i++) {//check all ingredients in this meal
            Ingredient temp = MainActivity.ingredientController.getIngredientById(mealIngredients.get(i).getId());
            if (temp!=null) {//Ingredient exists
                if (temp.getAmount() < mealIngredients.get(i).getAmount()) {//Not enough.
                    return false;
                }
            } else {//Ingredient doesn't exist
                return false;
            }
        }
        return true;
    }

    /**
     * Consume ingredients if we have enough ingredients.
     * @return true if enough and consumed, false if not.
     */
    public boolean consumeIngredients(MealPlan meal) {
        if(checkIngredients(meal)){ //we have enough so consume the ingredients!
            ArrayList<Ingredient> mealIngredients = meal.getIngredients();
            for (int i = 0; i <mealIngredients.size(); i++) { //reduce the amount within each ingredient
                Ingredient temp = MainActivity.ingredientController.getIngredientById(mealIngredients.get(i).getId());
                temp.setAmount(temp.getAmount() - mealIngredients.get(i).getAmount());
                MainActivity.ingredientController.edit(temp);
            }
            return true;
        }
        return false; //checkIngredients false therefore we don't have enough
    }

    /**
     * Button to allow for deleting a meal
     * @return
     */
    @NonNull
    private View.OnClickListener deleteButtonClicked() {
        return v -> {
            mealPlanController.deleteMeal(mealPlan);
            Navigation.findNavController(v).popBackStack();
        };
    }

    /**
     * Button to allow for editing a meal
     */
    private void editButtonClicked() {
        MealPlan editingMeal = new MealPlan();
        editingMeal.update(mealPlan);
        MealEditFragment newFragment = new MealEditFragment(editingMeal);
        FragmentManager fm = getChildFragmentManager();

        fm.executePendingTransactions();
        newFragment.show(fm, "EditMeal");
    }

    /**
     * Populates the detail screen with the information regarding the meal, including
     * the name, date, number of servings/units, and ingredients if they exist
     */
    public void populateData() {
        mealPlan = mealPlanController.getMealByPosition(position); //Update with edited meal.
        collapsingToolbarLayout.setTitle(mealPlan.getName());
        SimpleDateFormat sf = new SimpleDateFormat("MMM dd/yy");
        collapsingToolbarLayout.setTitle(sf.format(mealPlan.getDate()));
        mealNameView.setText(mealPlan.getName());
        String servingsSuffix = " servings";
        if(mealPlan.isIngredient()) {
            if (mealPlan.getIngredients().get(0).getUnit() != null) {
                servingsSuffix = " " + mealPlan.getIngredients().get(0).getUnit();
            } else {
                servingsSuffix = "";
            }
        } else {
            if (mealPlan.getServings() == 1) {
                servingsSuffix = " serving";
            }
        }

        String servingsText = mealPlan.getServings() + servingsSuffix;
        servingsView.setText(servingsText);

        if(mealPlan.isIngredient()) { //Ingredient so show nothing
            ingredientEmptyView.setVisibility(View.INVISIBLE);
            ingredientsFieldView.setVisibility(View.INVISIBLE);
            ingredientsRecyclerView.setVisibility(View.INVISIBLE);
        } else if (!mealPlan.getIngredients().isEmpty()) { //Recipe with ingredients
            ingredientEmptyView.setVisibility(View.INVISIBLE);
            ingredientsFieldView.setVisibility(View.VISIBLE);
            ingredientsRecyclerView.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        } else { //recipe with no ingredients
            ingredientEmptyView.setVisibility(View.VISIBLE);
            ingredientsFieldView.setVisibility(View.VISIBLE);
            ingredientsRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

}