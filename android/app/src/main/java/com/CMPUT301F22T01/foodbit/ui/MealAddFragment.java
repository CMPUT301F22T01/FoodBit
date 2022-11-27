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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

    MaterialToolbar topBar;
    Spinner ingredientRecipeSpinner;
    TextInputEditText servingsEditText;
    TextInputLayout servingsLayout;
    ArrayAdapter<String> adapter;
    EditText mealDateEditText;
    EditDatePicker mealDatePicker;

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

        //Populate dropdown with ingredients and recipes
        recipeController = MainActivity.recipeController;
        ingredientController = MainActivity.ingredientController;
        mealPlanController = MainActivity.mealPlan;
        ArrayList<Ingredient> ingredientList =  ingredientController.getIngredients();
        ArrayList<Recipe> recipeList = recipeController.getRecipes();
        String[] items;
        if (ingredientList.size() + recipeList.size() == 0 ) {
            Log.e("MealAdd","Ingredient and recipe size is 0");
            items = new String [] {"test1", "test2", "test3", "test4", "test5"};
            notRealItem = true;
        } else {
            items = new String[ingredientList.size() + recipeList.size()];
            int ingredientSize = ingredientList.size();
            int j = ingredientSize;
            for (int i = 0; i<j; i++) {
                items[i] = ingredientList.get(i).getDescription();
            }
            for (int i =0; i<recipeList.size(); i++ ) {
                items[j] = recipeList.get(i).getTitle();
                j+=1;
            }
        }

        adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, items);

        // Get spinner
        ingredientRecipeSpinner = (Spinner) view.findViewById(R.id.meal_spinner);
        ingredientRecipeSpinner.setAdapter(adapter);
        ingredientRecipeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("meal selected: ", (String) parent.getItemAtPosition(position) );
                meal.setName((String) parent.getItemAtPosition(position));
                positionSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                Date mealDate = mealDatePicker.getDate();
                if (servings.equals("")) {
                    servingsLayout.setError("Required");
                } else {
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