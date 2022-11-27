package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.IngredientCategory;
import com.CMPUT301F22T01.foodbit.models.IngredientLocation;
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
    Button addLocation;
    Button addUnit;
    Button addCategory;
    EditText editTextLocation;
    EditText editTextUnit;
    EditText editTextCategory;
    Button completeNewLocation;
    Button completeNewUnit;
    Button completeNewCategory;

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
        addCategory = view.findViewById(R.id.add_category_button);
        addLocation = view.findViewById(R.id.add_location_button);
        addUnit = view.findViewById(R.id.add_unit_button);

        //Dropdown box for location
        AutoCompleteTextView locationTextView = view.findViewById(R.id.location_picker);
        List<String> locations = new ArrayList<>(Arrays.asList("fridge", "pantry", "freezer"));
        locations.addAll(MainActivity.location.getLocationDescription());
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, locations);
        locationTextView.setAdapter(locationAdapter);

        //Dropdown box for units
        AutoCompleteTextView unitTextView = view.findViewById(R.id.unit_picker);
        List<String> units = new ArrayList<>(Arrays.asList("kg", "lbs", "oz", "tbs", "tsp", "g"));
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

        //Popup window for when user wants to add a new location
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                View popupView = inflater.inflate(R.layout.ingredient_add_dropdown_popup, null);

                editTextLocation = popupView.findViewById(R.id.add_dropdown_edit_text);
                completeNewLocation = popupView.findViewById(R.id.add_complete);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //add button pressed in popup window
                completeNewLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //getting information user typed and adding it to the location options
                        String newLocation = editTextLocation.getText().toString();
                        if (!locations.contains(newLocation)) {
                            locationAdapter.add(newLocation);
                            locationAdapter.notifyDataSetChanged();
                            IngredientLocation location = new IngredientLocation(newLocation);
                            MainActivity.location.add(location);
                            MainActivity.location.loadAllFromDB();

                            popupWindow.dismiss();
                        }
                        popupWindow.dismiss();
                    }
                });

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        //Popup window for when user wants to add a new unit
        addUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                View popupView = inflater.inflate(R.layout.ingredient_add_dropdown_popup, null);

                editTextUnit = popupView.findViewById(R.id.add_dropdown_edit_text);
                completeNewUnit = popupView.findViewById(R.id.add_complete);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //add button pressed in popup window
                completeNewUnit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //getting information user typed and adding it to the unit options
                        String newUnit = editTextUnit.getText().toString();
                        if (!units.contains(newUnit)){
                            unitAdapter.add(newUnit);
                            unitAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                        }
                        popupWindow.dismiss();
                    }
                });

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        //Popup window for when user wants to add a new category
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                View popupView = inflater.inflate(R.layout.ingredient_add_dropdown_popup, null);

                editTextCategory = popupView.findViewById(R.id.add_dropdown_edit_text);
                completeNewCategory = popupView.findViewById(R.id.add_complete);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //add button pressed in popup window
                completeNewCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //getting information user typed and adding it to the category options
                        String newCategory = editTextCategory.getText().toString();
                        if (!categories.contains(newCategory)) {
                            categoryAdapter.add(newCategory);
                            categoryAdapter.notifyDataSetChanged();
                            IngredientCategory category = new IngredientCategory(newCategory);
                            MainActivity.category.add(category);
                            MainActivity.category.loadAllFromDB();

                            popupWindow.dismiss();
                        }
                        popupWindow.dismiss();
                    }
                });

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

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

