package com.CMPUT301F22T01.foodbit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.CMPUT301F22T01.foodbit.models.IngredientStorage;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.RecipeBook;
import com.CMPUT301F22T01.foodbit.ui.IngredientStorageFragment;
import com.CMPUT301F22T01.foodbit.ui.MealPlanFragment;
import com.CMPUT301F22T01.foodbit.ui.RecipeBookFragment;
import com.CMPUT301F22T01.foodbit.ui.ShoppingCartFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    // access a Cloud Firestore instance and retrieve data
    public final static String TAG = "MainActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference recipeBookRef = db.collection("recipe book");
    public static RecipeBook recipeBook = new RecipeBook();
    final CollectionReference ingredientStorageRef = db.collection("ingredient list");
    public static IngredientStorage ingredientStorage = new IngredientStorage();

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