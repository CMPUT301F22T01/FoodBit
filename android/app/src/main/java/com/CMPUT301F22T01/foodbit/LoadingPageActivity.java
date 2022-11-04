package com.CMPUT301F22T01.foodbit;

import static com.CMPUT301F22T01.foodbit.MainActivity.db;
import static com.CMPUT301F22T01.foodbit.MainActivity.listen;
import static com.CMPUT301F22T01.foodbit.MainActivity.mealPlan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.CMPUT301F22T01.foodbit.ui.MealPlanAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;

public class LoadingPageActivity extends AppCompatActivity {
    static String FID = "empty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        listen.setValue("empty"); //Facilitate getting User DB

        assignUserDB();
        listen.observe(this,new Observer<String>() {
            @Override
            public void onChanged(String changedValue) {//assignUserDB has finished getting the DB
                FID = listen.getValue();
                MainActivity.mealPlanRef = db.collection(FID).document(FID).collection("Meals");
                MainActivity.mealPlan = new MealPlanController();

                MainActivity.recipeBookRef = db.collection(FID).document(FID).collection("Recipe Book");
                MainActivity.recipeBook = new RecipeBook();

                MainActivity.ingredientStorageRef = db.collection(FID).document(FID).collection("ingredient list");
                MainActivity.ingredientStorage = new IngredientStorage();

                Intent myIntent = new Intent(LoadingPageActivity.this, MainActivity.class );
                LoadingPageActivity.this.startActivity(myIntent);
            }
        });
//

    }


    private void assignUserDB() {
        //Query firebase for our installation ID.
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