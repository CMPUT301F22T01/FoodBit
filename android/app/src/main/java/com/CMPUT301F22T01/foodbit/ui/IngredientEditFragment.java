package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.IngredientCategory;
import com.CMPUT301F22T01.foodbit.models.IngredientLocation;
import com.CMPUT301F22T01.foodbit.models.IngredientUnit;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Fragment for editing current ingredients
 * drop down boxes added in pt 4
 */
public class IngredientEditFragment extends DialogFragment {
    public final static String TAG = "EditIngredient";
    private Ingredient ingredient;
    private String keyword;
    private int position;
    private Context context;

    /**
     * Interface for edited ingredients
     */
    public interface OnIngredientEditedListener {
        void onEdited();
    }
    private OnIngredientEditedListener ingredientEditedListener;

    MaterialToolbar topBar;
    TextInputEditText descriptionEditText;
    TextInputLayout descriptionLayout;
    TextInputEditText bestBeforeEditText;
    TextInputLayout bestBeforeLayout;
    TextInputEditText amountEditText;
    TextInputLayout amountLayout;
    TextInputLayout locationLayout;
    TextInputLayout categoryLayout;
    TextInputLayout unitLayout;

    public IngredientEditFragment() {
        // Required empty public constructor
    }

    /**
     * sets ingredient to be edited
     * @param position position of the ingredient to be edited in the controller
     */
    public IngredientEditFragment(int position) {
        this.position = position;
        this.ingredient = MainActivity.ingredientController.getIngredientByPosition(position);
    }

    public IngredientEditFragment(int position, String keyword) {
        this.position = position;
        this.ingredient = MainActivity.ingredientController.getIngredientByPosition(position);
        this.keyword = keyword;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "context: " + context);
        ingredientEditedListener = (OnIngredientEditedListener) getParentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_add, container, false);
        topBar = view.findViewById(R.id.ingredient_add_top_bar);
        topBar.setTitle("Edit an Ingredient");
        descriptionEditText = view.findViewById(R.id.ingredient_add_edit_text_description);
        descriptionLayout = view.findViewById(R.id.ingredient_add_text_layout_description);
        bestBeforeEditText = view.findViewById(R.id.ingredient_add_edit_text_best_before);
        bestBeforeLayout = view.findViewById(R.id.ingredient_add_text_layout_best_before);
        amountEditText = view.findViewById(R.id.ingredient_add_edit_text_amount);
        amountLayout = view.findViewById(R.id.ingredient_add_text_layout_amount);
        locationLayout = view.findViewById(R.id.ingredient_add_text_layout_location);
        categoryLayout = view.findViewById(R.id.ingredient_add_text_layout_category);
        unitLayout = view.findViewById(R.id.ingredient_add_text_layout_unit);

        //Dropdown box for location
        AutoCompleteTextView locationTextView = view.findViewById(R.id.location_picker);
        // Default location options - not in database
        List<String> locations = new ArrayList<>(Arrays.asList("fridge", "pantry", "freezer"));
        // Getting all locations from the database
        locations.addAll(MainActivity.location.getLocationDescription());
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, locations);
        locationTextView.setAdapter(locationAdapter);

        //Dropdown box for units
        AutoCompleteTextView unitTextView = view.findViewById(R.id.unit_picker);
        // Default unit options - not in database
        List<String> units = new ArrayList<>(Arrays.asList("kg", "lbs", "oz", "tbs", "tsp", "g"));
        // Getting all units from the database
        units.addAll(MainActivity.unit.getUnitDescription());
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, units);
        unitTextView.setAdapter(unitAdapter);

        //Dropdown box for categories
        AutoCompleteTextView categoryTextView = view.findViewById(R.id.category_picker);
        //Defaults of categories - not in database
        List<String> categories = new ArrayList<>(Arrays.asList("vegetables", "fruits", "grains", "snacks", "dairy"));
        //Getting any categories from the database
        categories.addAll(MainActivity.category.getCategoryDescription());
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, categories);
        categoryTextView.setAdapter(categoryAdapter);

        //This section is used if the ingredient is in a recipe or meal plan
        //If so, only amount and location can be edited, the rest are restricted to viewing only
        if (Objects.equals(keyword, "restricted editing")) {
            descriptionEditText.setInputType(InputType.TYPE_NULL);
            bestBeforeEditText.setInputType(InputType.TYPE_NULL);
            unitTextView.setInputType(InputType.TYPE_NULL);
            categoryTextView.setInputType(InputType.TYPE_NULL);
            categoryTextView.setAdapter(null);
            unitTextView.setAdapter(null);
        }

        descriptionEditText.setText(ingredient.getDescription());
        bestBeforeEditText.setText(ingredient.getBestBefore());
        locationTextView.setText(ingredient.getLocation());
        amountEditText.setText(String.valueOf(ingredient.getAmount()));
        unitTextView.setText(ingredient.getUnit());
        categoryTextView.setText(ingredient.getCategory());

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
                    // if the date is before today's date, amount will default to 0
                    Date todayDate = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    //String todayString = formatter.format(todayDate);

                    boolean requiredFieldEntered = true;
                    if (description.equals("")) {
                        descriptionLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (description.length() > 150) {
                        descriptionLayout.setError("Maximum 150 characters");
                        requiredFieldEntered = false;
                    }
                    if (amount.equals("")) {
                        amount = String.valueOf(0);
                    }
                    if (bestBefore.equals("")) {
                        bestBeforeLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!bestBefore.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        bestBeforeLayout.setError("Format for date is yyyy-mm-dd");
                        requiredFieldEntered = false;
                    }
                    // This portion checks to see if date is expired
                    else {
                        try {
                            Date bestBeforeDate = formatter.parse(bestBefore);
                            if (bestBeforeDate.before(todayDate)) {
                                amount = String.valueOf(0);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (location.equals("")) {
                        locationLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!locations.contains(location)) {
                        // adding new location to adapter and database if it is not already in it
                        locationAdapter.add(location);
                        locationAdapter.notifyDataSetChanged();
                        IngredientLocation newLocation = new IngredientLocation(location);
                        MainActivity.location.add(newLocation);
                        MainActivity.location.loadAllFromDB();
                    }
                    if (unit.equals("")) {
                        unitLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!units.contains(unit)) {
                        //Adds new unit to the adapter and the database if it is not already in it
                        unitAdapter.add(unit);
                        unitAdapter.notifyDataSetChanged();
                        IngredientUnit newUnit = new IngredientUnit(unit);
                        MainActivity.unit.add(newUnit);
                        MainActivity.unit.loadAllFromDB();
                    }
                    if (category.equals("")) {
                        categoryLayout.setError("Required");
                        requiredFieldEntered = false;
                    } else if (!categories.contains(category)) {
                        // adding new category to adapter and database if it is not already in it
                        categoryAdapter.add(category);
                        categoryAdapter.notifyDataSetChanged();
                        IngredientCategory newCategory = new IngredientCategory(category);
                        MainActivity.category.add(newCategory);
                        MainActivity.category.loadAllFromDB();
                    }
                    if (requiredFieldEntered) {
                        ingredient.setDescription(description);
                        ingredient.setBestBefore(bestBefore);
                        ingredient.setLocation(location);
                        ingredient.setAmount(Float.parseFloat(amount));
                        ingredient.setUnit(unit);
                        ingredient.setCategory(category);
                        MainActivity.ingredientController.edit(ingredient);
                        ingredientEditedListener.onEdited();
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
