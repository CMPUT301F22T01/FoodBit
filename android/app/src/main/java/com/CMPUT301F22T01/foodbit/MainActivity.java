package com.CMPUT301F22T01.foodbit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.CMPUT301F22T01.foodbit.ui.IngredientStorageFragment;
import com.CMPUT301F22T01.foodbit.ui.MealPlanFragment;
import com.CMPUT301F22T01.foodbit.ui.RecipeBookFragment;
import com.CMPUT301F22T01.foodbit.ui.ShoppingCartFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    IngredientStorageFragment ingredientStorageFragment = IngredientStorageFragment.newInstance("1","2");
    RecipeBookFragment recipeBookFragment = RecipeBookFragment.newInstance("1","2");
    MealPlanFragment mealPlanFragment = MealPlanFragment.newInstance("1","2");
    ShoppingCartFragment shoppingCartFragment = ShoppingCartFragment.newInstance("1","2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final NavigationBarView navView = findViewById(R.id.nav_view);

        // set and init default destination
        final Fragment defaultFragment = mealPlanFragment;
        final int defaultFragmentLayout = R.id.fragment_meal_plan;
        navView.setSelectedItemId(defaultFragmentLayout);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, defaultFragment).commit();

        navView.setOnItemSelectedListener(item -> {
            // TODO: move this into a controller class
            int itemId = item.getItemId();
            if (itemId == R.id.fragment_ingredient_storage) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, ingredientStorageFragment).commit();
                return true;
            } else if (itemId == R.id.fragment_recipe_book) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, recipeBookFragment).commit();
                return true;
            } else if (itemId == R.id.fragment_meal_plan) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, mealPlanFragment).commit();
                return true;
            } else if (itemId == R.id.fragment_shopping_cart) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, shoppingCartFragment).commit();
                return true;
            }
            return false;
        });
    }






}