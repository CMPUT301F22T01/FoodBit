package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * A <code>recipe detail screen</code> that displays the details of a recipe.
 */
public class RecipeDetailFragment extends Fragment implements RecipeEditFragment.OnRecipeEditedListener{

    public static final String TAG = "Recipe Detail Fragment";
    private static final RecipeController recipeController = MainActivity.recipeController;
    private Recipe recipe;
    private int position;

    // UI
    Toolbar topBar;
    TextView prepTimeView;
    TextView numServingsView;
    TextView categoryView;
    TextView commentsView;
    ImageView appBarImageView;
    RecyclerView ingredientsRecyclerView;
    TextView ingredientEmptyView;
    Button tempDeleteButton;
    IngredientAdapter ingredientAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecipe();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // set UI
        topBar = view.findViewById(R.id.recipe_detail_toolbar);
        prepTimeView = view.findViewById(R.id.recipe_detail_prep_time);
        numServingsView = view.findViewById(R.id.recipe_detail_num_servings);
        categoryView = view.findViewById(R.id.recipe_detail_category_content);
        commentsView = view.findViewById(R.id.recipe_detail_comments_content);
        appBarImageView = view.findViewById(R.id.recipe_detail_bar_image);
        ingredientsRecyclerView = view.findViewById(R.id.recipe_detail_ingredient_list);
        ingredientEmptyView = view.findViewById(R.id.recipe_detail_ingredients_empty);
        tempDeleteButton = view.findViewById(R.id.recipe_detail_temp_delete);
        tempDeleteButton.setOnClickListener(deleteButtonClicked());
        collapsingToolbarLayout = view.findViewById(R.id.recipe_detail_top_bar);

        // back button behaviour
        topBar.setNavigationOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // edit button behaviour
        topBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            // done button behaviour
            if (itemId == R.id.recipe_detail_edit) {
                editButtonClicked();
            }
            return false;

        });

        collapsingToolbarLayout.setTitle(recipe.getTitle());
        String prepTimeSuffix = " minutes"; if (recipe.getPrepTime() == 1) {prepTimeSuffix = " minute";}
        String prepTimeText = recipe.getPrepTime() + prepTimeSuffix;
        prepTimeView.setText(prepTimeText);
        String numServingsSuffix = " servings"; if (recipe.getNumServings() == 1) {numServingsSuffix = " serving";}
        String numServingsText = recipe.getNumServings() + numServingsSuffix;
        numServingsView.setText(numServingsText);
        if (recipe.getCategory() != null) {categoryView.setText(recipe.getCategory());} else {categoryView.setText("Unknown");}
        if (recipe.getComments() != null) {commentsView.setText(recipe.getComments());} else {commentsView.setText("No comments.");}

        Uri photo = recipe.getPhoto();
        if (photo == null) {
            appBarImageView.setImageResource(android.R.color.transparent);
        } else {
            appBarImageView.setImageURI(photo);
        }

        setUpRecyclerView();

        if (recipe.getIngredients().isEmpty()) {ingredientEmptyView.setVisibility(View.VISIBLE);}

        return view;
    }

    private void editButtonClicked() {
//        new RecipeEditFragment().show(getChildFragmentManager(), RecipeAddFragment.TAG);
        RecipeEditFragment.newInstance(position).show(getChildFragmentManager(), RecipeEditFragment.TAG);
    }

    private void setUpRecyclerView() {
        ingredientAdapter = new IngredientAdapter(recipe.getIngredients());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        // add borderlines between items
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(ingredientsRecyclerView.getContext(), linearLayoutManager.getOrientation()));
    }

    @NonNull
    private View.OnClickListener deleteButtonClicked() {
        return v -> {
            recipeController.remove(recipe);
            Navigation.findNavController(v).popBackStack();
        };
    }

    // get recipe from recipe book obtained from MainActivity and position given by the adapter
    private void getRecipe() {
        assert getArguments() != null;
        position = getArguments().getInt("position");
        recipe = recipeController.getRecipeByPosition(position);
        Log.d(TAG, String.valueOf(recipe));
    }

    @Override
    public void onEdited() {
        recipe = recipeController.getRecipeByPosition(position);
        collapsingToolbarLayout.setTitle(recipe.getTitle());
        String prepTimeSuffix = " minutes"; if (recipe.getPrepTime() == 1) {prepTimeSuffix = " minute";}
        String prepTimeText = recipe.getPrepTime() + prepTimeSuffix;
        prepTimeView.setText(prepTimeText);
        String numServingsSuffix = " servings"; if (recipe.getNumServings() == 1) {numServingsSuffix = " serving";}
        String numServingsText = recipe.getNumServings() + numServingsSuffix;
        numServingsView.setText(numServingsText);
        if (recipe.getCategory() != null) {categoryView.setText(recipe.getCategory());} else {categoryView.setText("Unknown");}
        if (recipe.getComments() != null) {commentsView.setText(recipe.getComments());} else {commentsView.setText("No comments.");}
        appBarImageView.setImageURI(recipe.getPhoto());
        setUpRecyclerView();
    }
}