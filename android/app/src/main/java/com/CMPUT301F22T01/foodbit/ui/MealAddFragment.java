package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;


import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Fragment for adding new MealPlan items
 */
public class MealAddFragment extends DialogFragment {

    public final static String TAG = "AddMeal";
    private Context context;

    private IngredientController ingredientController;
    protected MealPlanController mealPlanController;
    private RecipeController recipeController;
    private int positionSelected;
    private Boolean notRealItem = false;
    protected MealPlan meal;
    ArrayList<String> itemsDropdown = new ArrayList<String>();
    private String mealSelected = "";


    MaterialToolbar topBar;
    TextInputEditText servingsEditText;
    TextInputLayout servingsLayout;
    ArrayAdapter<String> adapter;
    EditText mealDateEditText;
    EditDatePicker mealDatePicker;
    TextInputLayout mealAddLayout;
    AutoCompleteTextView mealAddTextView;

    public MealAddFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor with the meal as a parameter
     * @param meal
     */
    public MealAddFragment(MealPlan meal) {
        this.meal = meal;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "context: " + context);
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     * @return A new instance of fragment MealAddFragment.
     */
    public static MealAddFragment newInstance() {
        MealAddFragment fragment = new MealAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);
    }

    /**
     * Inflates the view and allows for user input for meal details to be added
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_add, container, false);

        topBar = view.findViewById(R.id.meal_add_top_bar);
        servingsEditText = view.findViewById(R.id.meal_add_serving_size);
        servingsLayout = view.findViewById(R.id.meal_add_layout_serving_size);
        mealAddLayout = view.findViewById(R.id.meal_add_layout_meal);

        //Populate dropdown with ingredients and recipes
        recipeController = MainActivity.recipeController;
        ingredientController = MainActivity.ingredientController;
        mealPlanController = MainActivity.mealPlan;
        ArrayList<Ingredient> ingredientList =  ingredientController.getIngredients();
        ArrayList<Recipe> recipeList = recipeController.getRecipes();
        String[] items = new String[ingredientList.size() + recipeList.size()];

        if (ingredientList.size() + recipeList.size() == 0 ) {
            Log.e("MealAdd","Ingredient and recipe size is 0. Should be impossible since " +
                    "we check for this before launching this fragment.");
        } else {
            int ingredientSize = ingredientList.size();
            int j = ingredientSize;
            for (int i = 0; i<j; i++) {
                itemsDropdown.add( ingredientList.get(i).getDescription());
                items[i] = ingredientList.get(i).getDescription();
            }
            for (int i =0; i<recipeList.size(); i++ ) {
                itemsDropdown.add( recipeList.get(i).getTitle());
                items[j] = recipeList.get(i).getTitle();
                j+=1;
            }
        }

        // get autocomplete text view
        mealAddTextView = view.findViewById(R.id.meal_picker);
        adapter = new ArrayAdapter<String>(context, R.layout.ingredient_dropdown_layout, items);
        mealAddTextView.setAdapter(adapter);
        mealAddTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String str = mealAddTextView.getText().toString();
                    ListAdapter listAdapter = mealAddTextView.getAdapter();
                    for(int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if(str.compareTo(temp) == 0) {
                            mealSelected = temp;
                            positionSelected = itemsDropdown.indexOf(mealSelected);
                            return;
                        } else {
                            positionSelected = i+1;
                        }
                    }
                    mealAddTextView.setText("");
                }
            }
        });

//        mealAddTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        //Date picker
        mealDateEditText = (EditText) view.findViewById(R.id.meal_add_date);
        mealDatePicker = new EditDatePicker(context,mealDateEditText);


        topBar.setNavigationOnClickListener(v -> {
            dismiss();
        });

        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) { //Check button is clicked!
                String servings = Objects.requireNonNull(servingsEditText.getText().toString());
                String mealName = Objects.requireNonNull(mealAddTextView.getText()).toString();
                Date mealDate = mealDatePicker.getDate();
                boolean requiredFieldEntered = true;
                if (servings.equals("")) {
                    servingsLayout.setError("Required");
                    requiredFieldEntered = false;
                } if (mealName.equals("")) {
                    mealAddLayout.setError("Required");
                    requiredFieldEntered = false;
                } if (requiredFieldEntered) {
                    positionSelected = itemsDropdown.indexOf(mealName);
                    meal.setName(mealName);
                    meal.setDate(mealDate);
                    meal.setServings(Integer.valueOf(servings));
                    int ingredientSize = ingredientList.size();
                    if (!notRealItem) { // Is a real item. Fixed DB Issues basically
                        if (positionSelected < ingredientSize) {
                            Ingredient ingredient = ingredientList.get(positionSelected);
                            meal.setRecipeID(ingredient.getId());
                            meal.setIngredient(true);
                            meal.setIngredients(ingredient);
                            Log.e("mealAdd Ingredient:", ingredientList.get(positionSelected).getDescription());
                        } else {
                            meal.setRecipeID(recipeList.get(positionSelected-ingredientSize).getId());
                            meal.setIngredient(false);
                            meal.setIngredientsFromRecipe(recipeList.get(positionSelected-ingredientSize).getIngredients(),
                                    recipeList.get(positionSelected-ingredientSize).getNumServings());
                            Log.e("mealAdd Recipe:", recipeList.get(positionSelected-ingredientSize).getTitle());
                        }
                    }
                    mealEditOrAdd(meal);
                    dismiss();
                }
                return false;
            }
        });

        return view;
    }

    /**
     * Sets the layout
     */
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

    public void mealEditOrAdd(MealPlan meal) {
        mealPlanController.addMeal(meal);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }
}