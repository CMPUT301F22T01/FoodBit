package com.CMPUT301F22T01.foodbit;

//import android.app.FragmentManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {


    public final static String TAG = "MainActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    final CollectionReference recipeBookRef = db.collection("Recipe Book");
    public static RecipeBook recipeBook = new RecipeBook();
    final CollectionReference ingredientStorageRef = db.collection("ingredient list");
    public static IngredientStorage ingredientStorage = new IngredientStorage();


    // access a Cloud Firestore instance and retrieve data
    final CollectionReference mealPlanRef = db.collection("Meals");
    public static MealPlanController mealPlan = new MealPlanController();

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