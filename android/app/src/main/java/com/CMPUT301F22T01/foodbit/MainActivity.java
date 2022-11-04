package com.CMPUT301F22T01.foodbit;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;


import java.util.List;

/**
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {


    public final static String TAG = "MainActivity";
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    static CollectionReference recipeBookRef = db.collection("Recipe Book");
    public static RecipeBook recipeBook = new RecipeBook();
    static CollectionReference ingredientStorageRef = db.collection("ingredient list");
    public static IngredientStorage ingredientStorage = new IngredientStorage();


    // access a Cloud Firestore instance and retrieve data
    static CollectionReference mealPlanRef = db.collection("Meals");
    public static MealPlanController mealPlan = new MealPlanController();

    static String FID;

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
//        NavController.OnDestinationChangedListener(new On)
        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);
        NavigationUI.setupWithNavController(bottomNav, navController);

    }

    private void assignUserDB() {
        // [START get_installation_id]
        moveTaskToBack(true);
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Log.e("Installations", "Installation ID: " + task.getResult());
                            FID = task.getResult();
//                            dbConnected = true;
//                            notifyAll();
                            recipeBookRef = db.collection(FID).document().collection("Recipe Book");
                            ingredientStorageRef = db.collection(FID).document().collection("ingredient list");
                            mealPlanRef = db.collection(FID).document().collection("Meals");
//                            mealPlan = new MealPlanController();
                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
//                            recipeBookRef = db.collection(FID).document().collection("Recipe Book");
//                            ingredientStorageRef = db.collection(FID).document().collection("ingredient list");
//                            mealPlanRef = db.collection(FID).document().collection("Meals");
//                            mealPlan =
                        }
                    }
                });
        // [END get_installation_id]
    }

}

