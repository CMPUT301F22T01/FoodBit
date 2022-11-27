package com.CMPUT301F22T01.foodbit;

import static com.CMPUT301F22T01.foodbit.MainActivity.category;
import static com.CMPUT301F22T01.foodbit.MainActivity.db;
import static com.CMPUT301F22T01.foodbit.MainActivity.ingredientStorage;
import static com.CMPUT301F22T01.foodbit.MainActivity.listen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import com.CMPUT301F22T01.foodbit.controllers.DatabaseController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientCategoryController;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;


public class LoadingPageActivity extends AppCompatActivity {
    static String FID = "empty";
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
                MainActivity.mealPlan = new MealPlanController();

                MainActivity.recipeBookRef = db.collection(FID).document(FID).collection("Recipe Book");
                MainActivity.recipeController = new RecipeController();
                // todo: test
//                MainActivity.recipeController.load();

                MainActivity.ingredientStorageRef = db.collection(FID).document(FID).collection("ingredient list");
                MainActivity.ingredientStorage = new IngredientStorage();
                ingredientStorage.loadAllFromDB();

                MainActivity.categoryStorageRef = db.collection(FID).document(FID).collection("Category List");
                MainActivity.category = new IngredientCategoryController();
                category.loadAllFromDB();

                Intent myIntent = new Intent(LoadingPageActivity.this, MainActivity.class );
                LoadingPageActivity.this.startActivity(myIntent);
            }
        });
//

    }


}