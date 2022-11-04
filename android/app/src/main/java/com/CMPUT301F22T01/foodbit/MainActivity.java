package com.CMPUT301F22T01.foodbit;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
//import com.CMPUT301F22T01.foodbit.ui.IngredientStorageFragment;
//import com.CMPUT301F22T01.foodbit.ui.MealPlanFragment;
//import com.CMPUT301F22T01.foodbit.ui.RecipeBookFragment;
//import com.CMPUT301F22T01.foodbit.ui.ShoppingCartFragment;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.ui.MealPlanAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;


import java.util.ArrayList;
import java.util.List;

/**
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {


    public final static String TAG = "MainActivity";
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String FID = loadingPage.FID;

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

//        listen.setValue("empty"); //Facilitate getting User DB
//        listen.observe(this,new Observer<String>() {
//            @Override
//            public void onChanged(String changedValue) {//assignUserDB has finished getting the DB
//                FID = listen.getValue();
//                recipeBookRef = db.collection(FID).document().collection("Recipe Book");
//                ingredientStorageRef = db.collection(FID).document().collection("ingredient list");
//                mealPlanRef = db.collection(FID).document().collection("Meals");
////                mealPlan.loadAllMeals();
////                mealPlanAdapter.updateItems(mealPlan.getArrayList());
////                mealPlanAdapter.notifyDataSetChanged();
//
////                mealPlanRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
////                    @SuppressLint("NotifyDataSetChanged")
////                    @Override
////                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
////                        if (error != null) {
////                            Log.e(TAG, "Listen failed.", error);
////                            return;
////                        }
////                        ArrayList<MealPlan> newRecipes = new ArrayList<MealPlan>();
////                        assert value != null;
////                        for (QueryDocumentSnapshot doc : value) {
////                            newRecipes.add(doc.toObject(MealPlan.class));
////                        }
////                        Log.e(TAG, "Current meals before update: " + mealPlan.getArrayList());
////                        mealPlan.update(newRecipes);
////                        Log.e(TAG, "Current meals: " + mealPlan.getArrayList());
////                        mealPlanAdapter.notifyDataSetChanged();
////                    }
////                });
////                mealPlanAdapter.notifyDataSetChanged();
//                Log.e("HELLLOOOO",FID);
//            }
//        });
//        assignUserDB();
        setUpNavBar();


    }


    private void setUpNavBar(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        // grab NavHostFragment and setup up controller, and nav bar accordingly
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_container);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
////                mealPlanAdapter.notifyDataSetChanged();
//            }
//        });
        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);
        NavigationUI.setupWithNavController(bottomNav, navController);

    }

    private void assignUserDB() {
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Log.e("Installations", "Installation ID: " + task.getResult());
                            listen.setValue(task.getResult());
                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
                        }
                    }
                });
    }

}

