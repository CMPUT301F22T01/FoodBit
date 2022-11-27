package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The main Meal Plan page that displays the list of MealPlans
 */
public class MealPlanFragment extends Fragment {

    public String TAG = "MealPlan";

    // get meal plan from MainActivity
    private final MealPlanController mealPlan = MainActivity.mealPlan;

    MealPlanAdapter adapter;

    public MealPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Meal Plan");

        //This fragment has options menu for the action bar
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflating the menu resource file for this fragment
        inflater.inflate(R.menu.mealplan_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Actions performed by the Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {   //Adding a Meal Plan
            case R.id.meal_plan_add:
                //launches newFragment if there are ingredients/recipes
                RecipeController recipeController = MainActivity.recipeController;
                IngredientStorage ingredientStorage = MainActivity.ingredientStorage;
//                ingredientStorage.loadAllFromDB();

                if (ingredientStorage.getIngredients().size() + recipeController.getRecipes().size() == 0 ) {
                    Snackbar snackbar = Snackbar.make(this.getActivity().findViewById(R.id.nav_container),
                            "Add an ingredient or recipe first!", Snackbar.LENGTH_SHORT);
                    snackbar.setAnchorView(R.id.nav_bar).show();

                } else {
                    //Go straight to meal edit page
                    MealPlan newMeal = new MealPlan();
                    MealAddFragment newFragment = new MealAddFragment(newMeal);
                    newFragment.show(getChildFragmentManager(), "AddMeal");
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);

        // get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_meal_plan);

        // sort
        Collections.sort(mealPlan.getArrayList(), MealPlan.sortByDate);

        // set RecyclerView
        adapter = new MealPlanAdapter(mealPlan.getArrayList());
        Log.e(TAG,"init = " + mealPlan.getArrayList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // add borderlines between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        getActivity().setTitle("Meal Plan");
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        // real time updates of the recipeController
        CollectionReference recipeBookRef = MainActivity.mealPlanRef;
        recipeBookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(TAG, "Listen failed.", error);
                    return;
                }
                ArrayList<MealPlan> newMealPlans = new ArrayList<MealPlan>();
                assert value != null;
                for (QueryDocumentSnapshot doc : value) {
                    newMealPlans.add(doc.toObject(MealPlan.class));
                }
                mealPlan.update(newMealPlans);
                Log.e(TAG, "Current meal plans: " + mealPlan.toString() + MainActivity.mealPlanRef.getPath());
                adapter.notifyDataSetChanged();
            }
        });
    }
}