package com.CMPUT301F22T01.foodbit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.CMPUT301F22T01.foodbit.controllers.IngredientCategoryController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientLocationController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientUnitController;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
//import com.CMPUT301F22T01.foodbit.ui.IngredientStorageFragment;
//import com.CMPUT301F22T01.foodbit.ui.MealPlanFragment;
//import com.CMPUT301F22T01.foodbit.ui.RecipeBookFragment;
//import com.CMPUT301F22T01.foodbit.ui.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;

/**
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {


    public final static String TAG = "MainActivity";
    static String FID = LoadingPageActivity.FID;

    // access a Cloud Firestore instance and retrieve data
    public static CollectionReference recipeBookRef;
    public static RecipeController recipeController;
    public static CollectionReference ingredientStorageRef;
    public static IngredientController ingredientController;
    public static CollectionReference mealPlanRef;
    public static MealPlanController mealPlan;
    public static CollectionReference categoryStorageRef;
    public static IngredientCategoryController category;
    public static CollectionReference locationStorageRef;
    public static IngredientLocationController location;
    public static CollectionReference unitStorageRef;
    public static IngredientUnitController unit;

    public static MutableLiveData<String> listen = new MutableLiveData<>(); //Listener for FID from firebase


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaring and initializing the action bar
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        //changing the color to match
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ff9d3f"));
        actionBar.setBackgroundDrawable(colorDrawable);



        setUpNavBar();
    }



    private void setUpNavBar(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        // grab NavHostFragment and setup up controller, and nav bar accordingly
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_container);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);
        NavigationUI.setupWithNavController(bottomNav, navController);

    }



}

