package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class RecipeAddEditFragment extends DialogFragment implements RecipeAddIngredientFragment.OnIngredientAddListener, RecipeAddIngredientFragment.OnIngredientEditListener, RecipeAddIngredientFragment.OnIngredientDeleteListener,  IngredientAdapter.OnItemClickListener {
    public static String TAG;
    // an ingredient list to obtain from the RecipeAddIngredientFragment
    public ArrayList<Ingredient> ingredients;
    IngredientAdapter ingredientAdapter;
    // UI
    MaterialToolbar topBar;
    TextInputEditText titleEditText;
    TextInputLayout titleLayout;
    TextInputEditText prepTimeEditText;
    TextInputLayout prepTimeLayout;
    TextInputEditText numServingsEditText;
    TextInputLayout numServingsLayout;
    TextInputEditText categoryEditText;
    TextInputLayout categoryLayout;
    TextInputEditText commentsEditText;
    TextInputLayout commentsLayout;
    MaterialToolbar ingredientsBar;
    RecyclerView ingredientsRecyclerView;
    IngredientStorage ingredientStorage;



    protected Context context;
    // get recipe book from MainActivity
    protected RecipeController recipeController = MainActivity.recipeController;

    public abstract void setTAG();

    public static RecipeAddEditFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        RecipeAddEditFragment fragment = new RecipeEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setTAG();
        this.context = context;
        Log.d(TAG, "context: "+context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);

        setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_add, container, false);

        // init views
        topBar = view.findViewById(R.id.recipe_add_top_bar);
        topBarSetText();
        titleEditText = view.findViewById(R.id.recipe_add_edit_text_title);
        titleLayout = view.findViewById(R.id.recipe_add_text_layout_title);
        prepTimeEditText = view.findViewById(R.id.recipe_add_edit_text_prep_time);
        prepTimeLayout = view.findViewById(R.id.recipe_add_text_layout_prep_time);
        prepTimeEditText.addTextChangedListener(prepTimeTextWatcher());
        numServingsEditText = view.findViewById(R.id.recipe_add_edit_text_num_servings);
        numServingsLayout = view.findViewById(R.id.recipe_add_text_layout_num_servings);
        numServingsEditText.addTextChangedListener(numServingsTextWatcher());
        categoryEditText = view.findViewById(R.id.recipe_add_edit_text_category);
        categoryLayout = view.findViewById(R.id.recipe_add_text_layout_category);
        commentsEditText = view.findViewById(R.id.recipe_add_edit_text_comments);
        commentsLayout = view.findViewById(R.id.recipe_add_text_layout_comments);
        ingredientsBar = view.findViewById(R.id.recipe_add_ingredients_bar);
        ingredientsRecyclerView = view.findViewById(R.id.recipe_add_ingredients_list);


        // preset recipe info in recipe edit screen
        presetInfo();

        // close button behaviour
        topBar.setNavigationOnClickListener(v -> dismiss());
        topBar.setOnMenuItemClickListener(item ->
        {
            int itemId = item.getItemId();
            // done button behaviour
            if (itemId == R.id.recipe_add_done) {
                doneButtonClicked();
            }
            return false;
        });

        ingredientsBar.setOnMenuItemClickListener(AddIngredientsButtonClicked());
        setUpRecyclerView();
        return view;
    }

    protected void presetInfo() {
    }


    private void topBarSetText() {
        topBar.setTitle(getTitle());
    }

    protected abstract String getTitle();

    @NonNull
    private Toolbar.OnMenuItemClickListener AddIngredientsButtonClicked() {
        return item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.recipe_add_ingredient_add) {
                ArrayList<String> titleList = new ArrayList<>();
                for (Ingredient ingredient : ingredients) {
                    titleList.add(ingredient.getDescription());

                }
                RecipeAddIngredientFragment.newInstance(titleList).show(getChildFragmentManager(), RecipeAddIngredientFragment.TAG);
            }
            return false;
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // set the style of the dialog fragment to be full screen
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.Theme_FoodBit_Slide);
        }
    }

    private void setUpRecyclerView() {
        ingredientAdapter = new IngredientAdapter(ingredients, IngredientAdapter.RECIPE_ADD);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);

        ingredientAdapter.setItemClickListener(this);
    }

    @NonNull
    private TextWatcher prepTimeTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // remove starting zeros
                String str = s.toString();
                Log.d(TAG, str);
                if (str.length() > 0 && str.charAt(0) == '0') {
                    int i = 0;
                    while (i < str.length() && str.charAt(i) == '0') {
                        i++;
                    }
                    prepTimeEditText.setText(str.substring(i));
                }
                // display error if max prep time exceeded
                String errorMsg = "Maximum time exceeded (480 minutes)";
                if (str.length() > 3) {
                    prepTimeLayout.setError(errorMsg);
                } else if (str.length() > 0 && Integer.parseInt(str) > 480) {
                    prepTimeLayout.setError(errorMsg);
                } else {
                    prepTimeLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    @NonNull
    private TextWatcher numServingsTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // remove starting zeros
                String str = s.toString();
                Log.d(TAG, str);
                if (str.length() > 0 && str.charAt(0) == '0') {
                    int i = 0;
                    while (i < str.length() && str.charAt(i) == '0') {
                        i++;
                    }
                    numServingsEditText.setText(str.substring(i));
                }
                // display error if max prep time exceeded
                String errorMsg = "Maximum servings exceeded (100 servings)";
                if (str.length() > 3) {
                    numServingsLayout.setError(errorMsg);
                } else if (str.length() > 0 && Integer.parseInt(str) > 100) {
                    numServingsLayout.setError(errorMsg);
                } else {
                    numServingsLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    protected abstract void doneButtonClicked();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onIngredientAdd(Ingredient newIngredient) {
        ingredients.add(newIngredient);

        ingredientStorage = MainActivity.ingredientStorage;
        List ingredientList = ingredientStorage.getDescriptions();

        for (Ingredient ingredient : ingredients) {
            if (!ingredientList.contains(ingredient.getDescription())) {
                Ingredient addIngredient = new Ingredient(ingredient.getDescription(), "0000-00-00", "Not Assigned", 0, "0", ingredient.getCategory());
                MainActivity.ingredientStorage.add(addIngredient);
            }
        }
        ingredientAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onIngredientEdit(int position, Ingredient newIngredient) {
        Ingredient oldIngredient = ingredients.get(position);
        oldIngredient.update(newIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onIngredientDelete(int position) {
        ingredients.remove(position);
        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onIngredientItemClick(View v, int position) {
        RecipeAddIngredientFragment.newInstance(ingredients.get(position), position).show(getChildFragmentManager(), RecipeAddIngredientFragment.TAG);
    }
}
