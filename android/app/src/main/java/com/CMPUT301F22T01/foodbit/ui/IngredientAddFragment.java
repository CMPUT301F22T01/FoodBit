package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Fragment for adding new Ingredient items
 * drop down boxes to be added in part 4
 */
public class IngredientAddFragment extends DialogFragment {
    public final static String TAG = "AddIngredient";
    private Context context;


    MaterialToolbar topBar;
    TextInputEditText descriptionEditText;
    TextInputLayout descriptionLayout;
    TextInputEditText bestBeforeEditText;
    TextInputLayout bestBeforeLayout;
    TextInputEditText amountEditText;
    TextInputLayout amountLayout;
    TextInputLayout locationLayout;
    TextInputLayout categoryLayout;


    public IngredientAddFragment() {
        // Required empty public constructor
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
        // inflating the view
        View view = inflater.inflate(R.layout.fragment_ingredient_add, container, false);

        topBar = view.findViewById(R.id.ingredient_add_top_bar);
        descriptionEditText = view.findViewById(R.id.ingredient_add_edit_text_description);
        descriptionLayout = view.findViewById(R.id.ingredient_add_text_layout_description);
        bestBeforeEditText = view.findViewById(R.id.ingredient_add_edit_text_best_before);
        bestBeforeLayout = view.findViewById(R.id.ingredient_add_text_layout_best_before);
        amountEditText = view.findViewById(R.id.ingredient_add_edit_text_amount);
        amountLayout = view.findViewById(R.id.ingredient_add_text_layout_amount);
        locationLayout = view.findViewById(R.id.ingredient_add_text_layout_location);
        categoryLayout = view.findViewById(R.id.ingredient_add_text_layout_category);

        //Dropdown box for categories
        AutoCompleteTextView categoryTextView = view.findViewById(R.id.category_picker);
        String[] categories = {"vegetables", "fruits", "grains", "snacks", "dairy"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, categories);
        categoryTextView.setAdapter(categoryAdapter);

        //Dropdown box for units
        AutoCompleteTextView unitTextView = view.findViewById(R.id.unit_picker);
        String[] units = {"kg", "lbs", "oz", "tbs", "tsp", "g"};
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, units);
        unitTextView.setAdapter(unitAdapter);

        //Dropdown box for units
        AutoCompleteTextView locationTextView = view.findViewById(R.id.location_picker);
        String[] locations = {"fridge", "pantry", "freezer"};
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, locations);
        locationTextView.setAdapter(locationAdapter);


        // back button
        topBar.setNavigationOnClickListener(v -> {
            dismiss();
        });

        // allowing for user input
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.ingredient_add_done) {
                    String description = Objects.requireNonNull(descriptionEditText.getText()).toString();
                    String bestBefore = Objects.requireNonNull(bestBeforeEditText.getText()).toString();
                    String location = Objects.requireNonNull(locationTextView.getText()).toString();
                    String amount = Objects.requireNonNull(amountEditText.getText()).toString();
                    String unit = Objects.requireNonNull(unitTextView.getText()).toString();
                    String category = Objects.requireNonNull(categoryTextView.getText()).toString();

                    // checking if inputs are valid, will display an error message if not
                    // required fields are ones that can be sorted by including description, best before, location, and category
                    // if amount is not entered it defaults to 0
                    boolean requiredFieldEntered = true;
                    if (description.equals("")) {
                        descriptionLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (description.length() > 150) {
                        descriptionLayout.setError("Maximum 150 characters");
                        requiredFieldEntered = false;
                    }
                    if (bestBefore.equals("")) {
                        bestBeforeLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!bestBefore.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        bestBeforeLayout.setError("Format for date is yyyy-mm-dd");
                        requiredFieldEntered = false;
                    }
                    if (amount.equals("")) {
                        amount = String.valueOf(0);
                    }
                    if (location.equals("")) {
                        locationLayout.setError("Required");
                        requiredFieldEntered = false;
                    }
                    if (category.equals("")) {
                        categoryLayout.setError("Required");
                        requiredFieldEntered = false;
                    }
                    if (requiredFieldEntered) {
                        Ingredient ingredient = new Ingredient(description, bestBefore, location, Float.parseFloat(amount), unit, category);
                        MainActivity.ingredientStorage.add(ingredient);
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

