



package com.CMPUT301F22T01.foodbit.ui;

import static com.CMPUT301F22T01.foodbit.ui.MainActivity.category;
import static com.CMPUT301F22T01.foodbit.ui.MainActivity.ingredientController;
import static com.CMPUT301F22T01.foodbit.ui.MainActivity.listen;
import static com.CMPUT301F22T01.foodbit.ui.MainActivity.location;
import static com.CMPUT301F22T01.foodbit.ui.MainActivity.unit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.DatabaseController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientCategoryController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientLocationController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientUnitController;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoadingPageActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String FID = "empty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        listen.setValue("empty"); //Facilitate getting User DB

        //Get Firebase Instance ID
        DatabaseController dbControl = new DatabaseController("Meals");
        dbControl.assignUserDB(listen);

        listen.observe(this,new Observer<String>() {
            @Override
            public void onChanged(String changedValue) {
                //FID has been updated. Grab data and move to main activity!
                FID = listen.getValue();
                MainActivity.mealPlanRef = db.collection(FID).document(FID).collection("Meals");
                MainActivity.mealPlanController = new MealPlanController();
                MainActivity.mealPlanController.load();

                MainActivity.recipeControllerRef = db.collection(FID).document(FID).collection("Recipe Book");
                MainActivity.recipeController = new RecipeController();

                MainActivity.ingredientListRef = db.collection(FID).document(FID).collection("ingredient list");
                MainActivity.ingredientController = new IngredientController();
                ingredientController.loadAllFromDB();

                MainActivity.categoryListRef = db.collection(FID).document(FID).collection("Category List");
                MainActivity.category = new IngredientCategoryController();
                category.loadAllFromDB();

                MainActivity.locationListRef = db.collection(FID).document(FID).collection("Location List");
                MainActivity.location = new IngredientLocationController();
                location.loadAllFromDB();

                MainActivity.unitListRef = db.collection(FID).document(FID).collection("Unit List");
                MainActivity.unit = new IngredientUnitController();
                unit.loadAllFromDB();

                Intent myIntent = new Intent(LoadingPageActivity.this, MainActivity.class );
                LoadingPageActivity.this.startActivity(myIntent);
            }
        });
//

    }


}