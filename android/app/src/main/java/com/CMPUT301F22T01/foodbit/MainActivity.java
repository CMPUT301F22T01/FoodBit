package com.CMPUT301F22T01.foodbit;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.RecipeBook;
import com.CMPUT301F22T01.foodbit.ui.IngredientStorageFragment;
import com.CMPUT301F22T01.foodbit.ui.MealPlanFragment;
import com.CMPUT301F22T01.foodbit.ui.RecipeBookFragment;
import com.CMPUT301F22T01.foodbit.ui.ShoppingCartFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    // access a Cloud Firestore instance and retrieve data
    public final static String TAG = "Sample";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference user = db.collection("user");
//    RecipeBook recipeBook = getRecipeBook(db);

    // init UI
    IngredientStorageFragment ingredientStorageFragment = IngredientStorageFragment.newInstance("1","2");
//    RecipeBookFragment recipeBookFragment = RecipeBookFragment.newInstance(recipeBook);
    MealPlanFragment mealPlanFragment = MealPlanFragment.newInstance("1","2");
    ShoppingCartFragment shoppingCartFragment = ShoppingCartFragment.newInstance("1","2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        NOTICE: this is the implementation of bottom navigation view without using android navigation components
//        final NavigationBarView navView = findViewById(R.id.nav_view);
//
//        // set and init default destination
//        final Fragment defaultFragment = mealPlanFragment;
//        final int defaultFragmentLayout = R.id.fragment_meal_plan;
//        navView.setSelectedItemId(defaultFragmentLayout);
//        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, defaultFragment).commit();
//
//        navView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.fragment_ingredient_storage) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, ingredientStorageFragment).commit();
//                return true;
//            } else if (itemId == R.id.fragment_recipe_book) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, recipeBookFragment).commit();
//                return true;
//            } else if (itemId == R.id.fragment_meal_plan) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, mealPlanFragment).commit();
//                return true;
//            } else if (itemId == R.id.fragment_shopping_cart) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, shoppingCartFragment).commit();
//                return true;
//            }
//            return false;
//        });

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