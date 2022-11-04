package com.CMPUT301F22T01.foodbit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
//import com.CMPUT301F22T01.foodbit.ui.IngredientStorageFragment;
//import com.CMPUT301F22T01.foodbit.ui.MealPlanFragment;
//import com.CMPUT301F22T01.foodbit.ui.RecipeBookFragment;
//import com.CMPUT301F22T01.foodbit.ui.ShoppingCartFragment;
import com.CMPUT301F22T01.foodbit.ui.MealPlanAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {


    public final static String TAG = "MainActivity";
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String FID = LoadingPageActivity.FID;

//    public static CollectionReference recipeBookRef;
    public static CollectionReference recipeBookRef = db.collection(FID).document().collection("Recipe Book");
    public static RecipeBook recipeBook = new RecipeBook();
    public static CollectionReference ingredientStorageRef = db.collection(FID).document().collection("ingredient list");
    public static IngredientStorage ingredientStorage = new IngredientStorage();


    // access a Cloud Firestore instance and retrieve data
    public static CollectionReference mealPlanRef;
//    public static CollectionReference mealPlanRef = db.collection(FID).document().collection("Meals");
    public static MealPlanController mealPlan;
//    public static MealPlanController mealPlan = new MealPlanController();
    public static MealPlanAdapter mealPlanAdapter;
//    public static MealPlanAdapter mealPlanAdapter = new MealPlanAdapter(mealPlan.getArrayList());

    public static MutableLiveData<String> listen = new MutableLiveData<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

