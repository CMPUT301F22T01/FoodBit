package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

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

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Recipe;

/**
 * A <code>recipe detail screen</code> that displays the details of a recipe.
 */
public class RecipeDetailFragment extends Fragment {

    private static final String TAG = "Recipe Detail Fragment";

    Recipe recipe;

    // UI
    Toolbar toolbar;
    TextView prepTimeView;
    TextView numServingsView;
    TextView categoryView;
    TextView commentsView;
    ImageView appBarImageView;
    RecyclerView ingredientsRecyclerView;
    Button tempDeleteButton;

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
        toolbar = view.findViewById(R.id.recipe_detail_toolbar);
        prepTimeView = view.findViewById(R.id.recipe_detail_prep_time);
        numServingsView = view.findViewById(R.id.recipe_detail_num_servings);
        categoryView = view.findViewById(R.id.recipe_detail_category_content);
        commentsView = view.findViewById(R.id.recipe_detail_comments_content);
        appBarImageView = view.findViewById(R.id.recipe_detail_bar_image);
        ingredientsRecyclerView = view.findViewById(R.id.recipe_detail_ingredient_list);
        tempDeleteButton = view.findViewById(R.id.recipe_detail_temp_delete);
        // todo: Temporary delete button. Will be replaced by a delete button in the action bar in the recipe book screen.
        tempDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.recipeBook.remove(recipe);
                Navigation.findNavController(v).popBackStack();
            }
        });

        toolbar.setTitle(recipe.getTitle());
        // back button behaviour
        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        String prepTimeSuffix = " minutes"; if (recipe.getPrepTime() == 1) {prepTimeSuffix = " minute";}
        String prepTimeText = recipe.getPrepTime() + prepTimeSuffix;
        prepTimeView.setText(prepTimeText);
        String numServingsSuffix = " servings"; if (recipe.getNumServings() == 1) {numServingsSuffix = " serving";}
        String numServingsText = recipe.getNumServings() + numServingsSuffix;
        numServingsView.setText(numServingsText);
        if (recipe.getCategory() != null) {categoryView.setText(recipe.getCategory());} else {categoryView.setText("Unknown");}
        if (recipe.getComments() != null) {commentsView.setText(recipe.getComments());} else {commentsView.setText("No comments.");}

        // todo: enable photo feature before release
        Uri photo = recipe.getPhoto();
        appBarImageView.setImageResource(android.R.color.transparent);
//        if (photo != null) {
//            appBarImage.setImageURI(photo);
//        } else {
//            appBarImage.setImageResource(android.R.color.transparent);
//        }

        IngredientAdapter ingredientAdapter = new IngredientAdapter(recipe.getIngredients(), IngredientAdapter.RECIPE_DETAIL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        // add borderlines between items
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(ingredientsRecyclerView.getContext(), linearLayoutManager.getOrientation()));

        return view;
    }

    // get recipe from recipe book obtained from MainActivity and position given by the adapter
    private void getRecipe() {
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        recipe = MainActivity.recipeBook.getRecipeByPosition(position);
        Log.d(TAG, String.valueOf(recipe));
    }
}