package com.CMPUT301F22T01.foodbit.ui;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Recipe;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = "Recipe Detail Fragment";

    Recipe recipe;

    // UI
    Toolbar toolbar;
    TextView prepTimeView;
    TextView numServingsView;
    TextView categoryView;
    TextView commentsView;
    ImageView appBarImage;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecipe();
    }

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
        appBarImage = view.findViewById(R.id.recipe_detail_bar_image);

        toolbar.setTitle(recipe.getTitle());
        // back button behaviour
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
        String prepTimeSuffix = " minutes"; if (recipe.getPrepTime() == 1) {prepTimeSuffix = " minutes";}
        prepTimeView.setText(recipe.getPrepTime() + prepTimeSuffix);
        String numServingsSuffix = " servings"; if (recipe.getPrepTime() == 1) {numServingsSuffix = " serving";}
        numServingsView.setText(recipe.getNumServings() + numServingsSuffix);
        if (recipe.getCategory() != null) {categoryView.setText(recipe.getCategory());} else {categoryView.setText("Unknown");}
        if (recipe.getComments() != null) {commentsView.setText(recipe.getComments());} else {commentsView.setText("No comments.");}
        Uri photo = recipe.getPhoto();
        if (photo != null) {
            appBarImage.setImageURI(photo);
        } else {
            appBarImage.setImageResource(android.R.color.transparent);
        }

        return view;
    }

    // get recipe from recipe book obtained from MainActivity and position given by the adapter
    private void getRecipe() {
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        recipe = MainActivity.recipeBook.get(position);
        Log.d(TAG, String.valueOf(recipe));
    }
}