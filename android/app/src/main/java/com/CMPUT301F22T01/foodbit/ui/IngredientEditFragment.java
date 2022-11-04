package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class IngredientEditFragment extends DialogFragment {
    public final static String TAG = "EditIngredient";
    private Ingredient ingredient;
    private Context context;


    MaterialToolbar topBar;
    TextInputEditText descriptionEditText;
    TextInputLayout descriptionLayout;
    TextInputEditText bestBeforeEditText;
    TextInputLayout bestBeforeLayout;
    TextInputEditText locationEditText;
    TextInputLayout locationLayout;
    TextInputEditText amountEditText;
    TextInputLayout amountLayout;
    TextInputEditText unitEditText;
    TextInputLayout unitLayout;
    TextInputEditText categoryEditText;
    TextInputLayout categoryLayout;

    public IngredientEditFragment() {
        // Required empty public constructor
    }

    public IngredientEditFragment(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "context: " + context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_edit, container, false);
        topBar = view.findViewById(R.id.ingredient_edit_top_bar);
        descriptionEditText = view.findViewById(R.id.ingredient_edit_edit_text_description);
        descriptionLayout = view.findViewById(R.id.ingredient_edit_text_layout_description);
        bestBeforeEditText = view.findViewById(R.id.ingredient_edit_edit_text_best_before);
        bestBeforeLayout = view.findViewById(R.id.ingredient_edit_text_layout_best_before);
        locationEditText = view.findViewById(R.id.ingredient_edit_edit_text_location);
        locationLayout = view.findViewById(R.id.ingredient_edit_text_layout_location);
        amountEditText = view.findViewById(R.id.ingredient_edit_edit_text_amount);
        amountLayout = view.findViewById(R.id.ingredient_edit_text_layout_amount);
        unitEditText = view.findViewById(R.id.ingredient_edit_edit_text_unit);
        unitLayout = view.findViewById(R.id.ingredient_edit_text_layout_unit);
        categoryEditText = view.findViewById(R.id.ingredient_edit_edit_text_category);
        categoryLayout = view.findViewById(R.id.ingredient_edit_text_layout_category);

        topBar.setNavigationOnClickListener(v -> {
            dismiss();
        });

        descriptionEditText.setText(ingredient.getDescription());
        bestBeforeEditText.setText(ingredient.getBestBefore());
        locationEditText.setText(ingredient.getLocation());
        amountEditText.setText(String.valueOf(ingredient.getAmount()));
        unitEditText.setText(ingredient.getUnit());
        categoryEditText.setText(ingredient.getCategory());

        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.ingredient_add_done) {
                    String description = Objects.requireNonNull(descriptionEditText.getText()).toString();
                    String bestBefore = Objects.requireNonNull(bestBeforeEditText.getText()).toString();
                    String location = Objects.requireNonNull(locationEditText.getText()).toString();
                    String amount = Objects.requireNonNull(amountEditText.getText()).toString();
                    String unit = Objects.requireNonNull(unitEditText.getText()).toString();
                    String category = Objects.requireNonNull(categoryEditText.getText()).toString();

                    boolean requiredFieldEntered = true;
                    if (description.equals("")) {
                        descriptionLayout.setError("Required");
                        requiredFieldEntered = false;
                    }
                    if (bestBefore.equals("")) {
                        bestBeforeLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!bestBefore.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        bestBeforeLayout.setError("Format for date is yyyy-mm-dd");
                        requiredFieldEntered = false;
                    }
                    if (location.equals("")) {
                        locationLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!location.equals("pantry") && !location.equals("freezer") && !location.equals("fridge")) {
                        locationLayout.setError("Options are pantry, freezer, or fridge!");
                        requiredFieldEntered = false;
                    }
                    if (amount.equals("")) {
                        amountLayout.setError("Required");
                        requiredFieldEntered = false;
                    }
                    if (unit.equals("")) {
                        unitLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!unit.equals("lbs") && !unit.equals("kg") && !unit.equals("oz") && !unit.equals("g")) {
                        unitLayout.setError("Options are lbs, kg, oz, or g!");
                        requiredFieldEntered = false;
                    }
                    if (category.equals("")) {
                        categoryLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!category.equals("vegetables") && !category.equals("fruits") && !category.equals("meat") && !category.equals("grains") && !category.equals("dairy") && !category.equals("snacks") && !category.equals("seasonings")) {
                        categoryLayout.setError("Options are vegetables, fruits, meat, grains, dairy, snacks, or spices!");
                        requiredFieldEntered = false;
                    }
                    if (requiredFieldEntered) {
                        ingredient.setDescription(description);
                        ingredient.setBestBefore(bestBefore);
                        ingredient.setLocation(location);
                        ingredient.setAmount(Float.parseFloat(amount));
                        ingredient.setUnit(unit);
                        ingredient.setCategory(category);
                        MainActivity.ingredientStorage.edit(ingredient);
                        dismiss();
                    }
                }
                return false;
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {

            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.Theme_FoodBit_Slide);
        }
    }
}
