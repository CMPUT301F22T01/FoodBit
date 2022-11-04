package com.CMPUT301F22T01.foodbit;

import static com.CMPUT301F22T01.foodbit.MainActivity.db;
import static com.CMPUT301F22T01.foodbit.MainActivity.listen;
import com.CMPUT301F22T01.foodbit.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.ui.MealPlanAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;

public class loadingPage extends AppCompatActivity {
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
                Log.e("HELLLOOOO",listen.getValue());
                FID = listen.getValue();
                Intent myIntent = new Intent(loadingPage.this, MainActivity.class );
                loadingPage.this.startActivity(myIntent);
                MainActivity.mealPlanRef = db.collection(FID).document().collection("Meals");
                MainActivity.mealPlan = new MealPlanController();
                MainActivity.mealPlanAdapter = new MealPlanAdapter(MainActivity.mealPlan.getArrayList());
            }
        });
//        try {
//            wait(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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