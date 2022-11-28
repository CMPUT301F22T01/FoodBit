package com.CMPUT301F22T01.foodbit.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * Class representing the details for ingredients that can be viewed
 * Description, bestBefore, location, amount, unit, and category can ve viewed on the app screen
 */
public class IngredientDetailFragment extends Fragment implements IngredientEditFragment.OnIngredientEditedListener {

    private static final String TAG = "Ingredient Detail Fragment";

    private final IngredientController ingredientController = MainActivity.ingredientController;
    private final RecipeController recipeController = MainActivity.recipeController;
    private final MealPlanController mealPlanController = MainActivity.mealPlanController;
    private Context context;

    Ingredient ingredient;
    int position;

    Toolbar toolbar;
    TextView descriptionView;
    TextView bestBeforeView;
    TextView locationView;
    TextView amountView;
    TextView unitView;
    TextView categoryView;
    Button deleteButton;
    CollapsingToolbarLayout collapsingToolbarLayout;

    /**
     * Required empty constructor
     */
    public IngredientDetailFragment() {
        // Required empty constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIngredient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflating the layout
        View view = inflater.inflate(R.layout.fragment_ingredient_detail, container, false);

        toolbar = view.findViewById(R.id.ingredient_detail_toolbar);
        descriptionView = view.findViewById(R.id.ingredient_detail_description);
        bestBeforeView = view.findViewById(R.id.ingredient_detail_best_before);
        locationView = view.findViewById(R.id.ingredient_detail_location);
        amountView = view.findViewById(R.id.ingredient_detail_amount);
        unitView = view.findViewById(R.id.ingredient_detail_unit);
        categoryView = view.findViewById(R.id.ingredient_detail_category);
        collapsingToolbarLayout = view.findViewById(R.id.ingredient_detail_tool_bar);

        toolbar.setTitle(ingredient.getDescription());

        // back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        // setting to current ingredient details
        descriptionView.setText(ingredient.getDescription());
        bestBeforeView.setText(ingredient.getBestBefore());
        locationView.setText(ingredient.getLocation());
        amountView.setText(String.valueOf(ingredient.getAmount()));
        unitView.setText(ingredient.getUnit());
        categoryView.setText(ingredient.getCategory());

        // allows for deleting of ingredient being viewed when delete button is clicked
        deleteButton = view.findViewById(R.id.button_ingredient_detail_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Removing from the database
                MainActivity.ingredientController.delete(ingredient);
                Navigation.findNavController(v).popBackStack();
            }
        });

        // allows for editing of ingredient being viewed when edit button is clicked
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.ingredient_detail_edit) {
                if (recipeController.containsIngredient(ingredient) || mealPlanController.containsIngredient(ingredient)) {
                    String toastMsg = "Edit not allowed - ingredient used in recipe(s) or meal plan(s)";
                    Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
                } else {
                    new IngredientEditFragment(position).show(getChildFragmentManager(), IngredientEditFragment.TAG);
                }
            }
            return false;
        });
        return view;
    }

    /**
     * Retrieving the ingredient as a certain position
     * Position of the ingredient being viewed
     */
    private void getIngredient() {
        assert getArguments() != null;
        position = getArguments().getInt("position");
        ingredient = ingredientController.getIngredientByPosition(position);
        Log.d(TAG, String.valueOf(ingredient));
    }

    @Override
    public void onEdited() {
        ingredient = ingredientController.getIngredientByPosition(position);
        collapsingToolbarLayout.setTitle(ingredient.getDescription());
        descriptionView.setText(ingredient.getDescription());
        bestBeforeView.setText(ingredient.getBestBefore());
        locationView.setText(ingredient.getLocation());
        amountView.setText(String.valueOf(ingredient.getAmount()));
        unitView.setText(ingredient.getUnit());
        categoryView.setText(ingredient.getCategory());
    }
}
