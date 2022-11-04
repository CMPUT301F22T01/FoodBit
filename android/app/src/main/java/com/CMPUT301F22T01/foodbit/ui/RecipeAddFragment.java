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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A <code>recipe add screen</code> that allows users to enter required and optional information to add a recipe to the recipe book.
 * <br>
 * Issues: users are not yet able to edit or remove ingredients that are added to the recipe in this screen.
 * There is a false error reported by the IDE which actually works fine.
 */
public class RecipeAddFragment extends DialogFragment
        implements RecipeAddIngredientAddFragment.OnIngredientAddListener,
        RecipeAddIngredientAddFragment.OnIngredientEditListener,
        RecipeAddIngredientAddFragment.OnIngredientDeleteListener, IngredientAdapter.OnItemClickListener{

    //    private static final String RECIPE_BOOK = "recipe_book";
    public final static String TAG = "AddRecipe";
    private Context context;

    // get recipe book from MainActivity
    private final RecipeBook recipeBook = MainActivity.recipeBook;

    // an ingredient list to obtain from the RecipeAddIngredientAddFragment
    public ArrayList<Ingredient> ingredients = new ArrayList<>();
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

    public RecipeAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "context: "+context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);

        // photo picker contract register
//        registerPhotoPicker();

        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_add, container, false);

        // init views
        topBar = view.findViewById(R.id.recipe_add_top_bar);
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


        // set top bar behaviours
        //topBar.setTitle("Recipe Book");

        //close button behaviour
        topBar.setNavigationOnClickListener(v -> dismiss());
//        topBar.setOnMenuItemClickListener(item ->
//        {
//            int itemId = item.getItemId();
//            // done button behaviour
//            if (itemId == R.id.AddRecipe) {
//                doneButtonClicked();
//            }
//            return false;
//        });





        ingredientsBar.setOnMenuItemClickListener(AddIngredientsButtonClicked());
        setUpRecyclerView();
        return view;
    }

    @NonNull
    private Toolbar.OnMenuItemClickListener AddIngredientsButtonClicked() {
        return item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.recipe_add_ingredient_add) {
                ArrayList<String> titleList = new ArrayList<>();
                for (Ingredient ingredient : ingredients) {
                    titleList.add(ingredient.getDescription());
                }
                RecipeAddIngredientAddFragment.newInstance(titleList).show(getChildFragmentManager(), RecipeAddIngredientAddFragment.TAG);
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

    private void doneButtonClicked() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString();
        String prepTime = Objects.requireNonNull(prepTimeEditText.getText()).toString();
        String numServings = Objects.requireNonNull(numServingsEditText.getText()).toString();
        String category = Objects.requireNonNull(categoryEditText.getText()).toString();
        if (category.equals("")){category = null;}
        String comments = Objects.requireNonNull(commentsEditText.getText()).toString();
        if (comments.equals("")){comments = null;}
        // check empty fields
        boolean requiredFieldEntered = true;
        if (title.equals("")) {
            titleLayout.setError("Required");
            requiredFieldEntered = false;
        } else if (recipeBook.getTitles().contains(title)) {
            titleLayout.setError("This title already exists");
            requiredFieldEntered = false;
        }
        if (prepTime.equals("")) {
            prepTimeLayout.setError("Required");
            requiredFieldEntered = false;
        } else if (prepTime.length() > 3 || Integer.parseInt(prepTime) > 480) {
            requiredFieldEntered = false;
        }
        if (numServings.equals("")) {
            numServingsLayout.setError("Required");
            requiredFieldEntered = false;
        } else if (numServings.length() > 3 || Integer.parseInt(numServings) > 100) {
            requiredFieldEntered = false;
        }
        if (requiredFieldEntered) {
            Recipe recipe = new Recipe(title,
                    Integer.parseInt(prepTime),
                    Integer.parseInt(numServings),
                    category, comments, null, ingredients);
            recipeBook.add(recipe);
            dismiss();
        } else {
            Toast.makeText(context, "Invalid input value(s) - check all fields", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onIngredientAdd(Ingredient newIngredient) {
        ingredients.add(newIngredient);
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
    public void onItemClick(View v, int position) {
        RecipeAddIngredientAddFragment.newInstance(ingredients.get(position), position).show(getChildFragmentManager(), RecipeAddIngredientAddFragment.TAG);
    }
}













