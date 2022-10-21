package com.CMPUT301F22T01.foodbit;

import android.os.Bundle;
import android.view.MenuItem;

import com.CMPUT301F22T01.foodbit.ui.IngredientStorageFragment;
import com.CMPUT301F22T01.foodbit.ui.MealPlanFragment;
import com.CMPUT301F22T01.foodbit.ui.RecipeBookFragment;
import com.CMPUT301F22T01.foodbit.ui.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.CMPUT301F22T01.foodbit.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    IngredientStorageFragment ingredientStorageFragment = IngredientStorageFragment.newInstance("1","2");
    RecipeBookFragment recipeBookFragment = RecipeBookFragment.newInstance("1","2");
    MealPlanFragment mealPlanFragment = MealPlanFragment.newInstance("1","2");
    ShoppingCartFragment shoppingCartFragment = ShoppingCartFragment.newInstance("1","2");

//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);

        final NavigationBarView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_ingredient_storage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, ingredientStorageFragment).commit();
                        return true;

                    case R.id.navigation_recipe_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, recipeBookFragment).commit();
                        return true;

                    case R.id.navigation_meal_plan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, mealPlanFragment).commit();
                        return true;

                    case R.id.navigation_shopping_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, shoppingCartFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }






}