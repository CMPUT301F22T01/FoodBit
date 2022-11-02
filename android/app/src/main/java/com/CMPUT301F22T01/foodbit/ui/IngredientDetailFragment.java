package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

/**
 * Class representing the details for ingredients that can be viewed
 * Description, bestBefore, location, amount, unit, and category can ve viewed on the app screen
 */
public class IngredientDetailFragment extends Fragment {

    private static final String TAG = "Ingredient Detail Fragment";

    Ingredient ingredient;

    Toolbar toolbar;
    TextView descriptionView;
    TextView bestBeforeView;
    TextView locationView;
    TextView amountView;
    TextView unitView;
    TextView categoryView;
    Button deleteButton;
    Button editButton;

    public IngredientDetailFragment() {
        // Required empty constructor
    }

    /**
     * Saved state of the activity
     * @param savedInstanceState saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIngredient();
    }

    /**
     * Inflates the view for visualizing ingredient details
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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
                MainActivity.ingredientStorage.delete(ingredient);
                Navigation.findNavController(v).popBackStack();
            }
        });

        editButton = view.findViewById(R.id.button_ingredient_detail_edit);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IngredientEditFragment(ingredient).show(getChildFragmentManager(), IngredientEditFragment.TAG);

            }
        });
        return view;
    }

    /**
     * Retrieving the ingredient as a certain position
     * Position of the ingredient being viewed
     */
    private void getIngredient() {
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        ingredient = MainActivity.ingredientStorage.getIngredientByPosition(position);
        Log.d(TAG, String.valueOf(ingredient));
    }
}
