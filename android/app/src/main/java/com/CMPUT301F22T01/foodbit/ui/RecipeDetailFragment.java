package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Recipe;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = "Recipe Detail Fragment";

    Recipe recipe;

    // UI
    Toolbar toolbar;

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

        // set toolbar
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getTitle());

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