package com.CMPUT301F22T01.foodbit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        // TODO: set default page

        final NavigationBarView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(item -> {
            // TODO: move this into a controller class
            int itemId = item.getItemId();
            if (itemId == R.id.fragment_ingredient_storage) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, ingredientStorageFragment).commit();
                return true;
            } else if (itemId == R.id.fragment_recipe_book) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, recipeBookFragment).commit();
                return true;
            } else if (itemId == R.id.fragment_meal_plan) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, mealPlanFragment).commit();
                return true;
            } else if (itemId == R.id.fragment_shopping_cart) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, shoppingCartFragment).commit();
                return true;
            }
            return false;
        });
    }






}