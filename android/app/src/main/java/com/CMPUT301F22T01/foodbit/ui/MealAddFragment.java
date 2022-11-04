package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealAddFragment extends DialogFragment {

    public final static String TAG = "AddMeal";
    private Context context;

    private final IngredientStorage ingredientStorage = MainActivity.ingredientStorage;
    private final MealPlanController mealPlanController = MainActivity.mealPlan;
    private final RecipeBook recipeBook = MainActivity.recipeBook;
    private int positionSelected;
    private Boolean notRealItem = false;
    private MealPlan meal;

    MaterialToolbar topBar;
    Spinner ingredientRecipeSpinner;
    TextInputEditText servingsEditText;
    TextInputLayout servingsLayout;

    public MealAddFragment() {
        // Required empty public constructor
    }

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
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealAddFragment newInstance(String param1, String param2) {
        MealAddFragment fragment = new MealAddFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_add, container, false);

        topBar = view.findViewById(R.id.meal_add_top_bar);
        servingsEditText = view.findViewById(R.id.meal_add_serving_size);
        servingsLayout = view.findViewById(R.id.meal_add_layout_serving_size);

        //Populate dropdown with ingredients and recipes
        ArrayList<Ingredient> ingredientList =  ingredientStorage.getIngredients();
        ArrayList<Recipe> recipeList = recipeBook.getRecipes();
        String[] items;
        if (ingredientList.size() + recipeList.size() == 0 ) {
            // TODO: Ingredient and recipes arent being loaded from DB. Fix constructor for IngredientStorage and recipeBook
            Log.e("MealAdd","Ingredient and recipe size is 0 so we're not hitting DB?");
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, items);

        // Get spinner
        ingredientRecipeSpinner = (Spinner) view.findViewById(R.id.meal_spinner);
        ingredientRecipeSpinner.setAdapter(adapter);
        ingredientRecipeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Based on position we should know if this is an ingredient or recipe and how to handle
                Log.e("meal selected: ", (String) parent.getItemAtPosition(position) );
                meal.setName((String) parent.getItemAtPosition(position));
                positionSelected = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        topBar.setNavigationOnClickListener(v -> {
            dismiss();
        });

        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) { //Check button is clicked!
                String servings = Objects.requireNonNull(servingsEditText.getText().toString());
                if (servings.equals("")) {
                    servingsLayout.setError("Required");
                } else {
                    meal.setServings(Integer.valueOf(servings));
                    int ingredientSize = ingredientList.size();
                    if (!notRealItem) { // Is a real item. Fixed DB Issues basically
                        if (positionSelected < ingredientSize) {
                            meal.setRecipeID(ingredientList.get(positionSelected).getId());
                            meal.setIngredient(true);
                            Log.e("mealAdd Ingredient:", ingredientList.get(positionSelected).getDescription());
                        } else {
                            meal.setRecipeID(recipeList.get(positionSelected-ingredientSize).getId());
                            meal.setIngredient(false);
                            meal.setIngredientList(recipeList.get(positionSelected-ingredientSize).doGetIngredientList());
                            Log.e("mealAdd Recipe:", recipeList.get(positionSelected-ingredientSize).getTitle());
                        }
                    }
                    mealPlanController.addMeal(meal);
                    dismiss();
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