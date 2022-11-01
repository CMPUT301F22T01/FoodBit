package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

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

    public IngredientDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIngredient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient_detail, container, false);

        toolbar = view.findViewById(R.id.ingredient_detail_toolbar);
        descriptionView = view.findViewById(R.id.ingredient_detail_description);
        bestBeforeView = view.findViewById(R.id.ingredient_detail_best_before);
        locationView = view.findViewById(R.id.ingredient_detail_location);
        amountView = view.findViewById(R.id.ingredient_detail_amount);
        unitView = view.findViewById(R.id.ingredient_detail_unit);
        categoryView = view.findViewById(R.id.ingredient_detail_category);

        toolbar.setTitle(ingredient.getDescription());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
        descriptionView.setText(ingredient.getDescription());
        bestBeforeView.setText(ingredient.getBestBefore());
        locationView.setText(ingredient.getLocation());
        amountView.setText(String.valueOf(ingredient.getAmount()));
        unitView.setText(ingredient.getUnit());
        categoryView.setText(ingredient.getCategory());

        deleteButton = view.findViewById(R.id.button_ingredient_detail_delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ingredientStorage.delete(ingredient);
                Navigation.findNavController(v).popBackStack();
                //new IngredientEditFragment(ingredient).show(getChildFragmentManager(), IngredientEditFragment.TAG);
            }
        });

        return view;
    }

    private void getIngredient() {
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        ingredient = MainActivity.ingredientStorage.getIngredientByPosition(position);
        Log.d(TAG, String.valueOf(ingredient));
    }
}
