package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class MealPlanFragment extends Fragment implements DatePickerFragment.NoticeDialogListener {

    public String TAG = "MealPlan";

    // get recipe book from MainActivity
    private final MealPlanController mealPlan = MainActivity.mealPlan;

    MealPlanAdapter adapter;

    public MealPlanFragment() {
        // Required empty public constructor
//        mealPlan.loadAllMeals();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);

        // get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_meal_plan);
        Button addButton = view.findViewById(R.id.btn_meal_plan_add);

        // set RecyclerView
        mealPlan.loadAllMeals();
        adapter = new MealPlanAdapter(mealPlan.getArrayList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // add borderlines between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        boolean test = false;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a DatePicker. When User clicks OK we move to onDialogPositiveClick
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.show(getChildFragmentManager(), "datePicker"); //Goes to onDialogPositiveClick when done
            }
        });

        return view;
    }

    // User has clicked the ok from the date picker
    @Override
    public void onDialogPositiveClick(DatePickerFragment dialog) {
        MealPlan newMeal = new MealPlan();
        newMeal.setDate(dialog.getDate());
        MealAddFragment newFragment = new MealAddFragment(newMeal);
        newFragment.show(getChildFragmentManager(), "AddMeal");


    }

    @Override
    public void onResume() {
        super.onResume();

        // real time updates of the recipeBook
        CollectionReference recipeBookRef = FirebaseFirestore.getInstance().collection("Meals");
        recipeBookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(TAG, "Listen failed.", error);
                    return;
                }
                ArrayList<MealPlan> newRecipes = new ArrayList<MealPlan>();
                assert value != null;
                for (QueryDocumentSnapshot doc : value) {
                    newRecipes.add(doc.toObject(MealPlan.class));
                }
                mealPlan.update(newRecipes);
                Log.e(TAG, "Current recipes: " + mealPlan.getArrayList());
                adapter.notifyDataSetChanged();
            }
        });
    }
}