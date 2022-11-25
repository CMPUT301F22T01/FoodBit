package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.models.MealPlan;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

public class MealDetailFragment extends Fragment {

    private static final String TAG = "Meal Detail Fragment";
    private static final MealPlanController mealPlanController = MainActivity.mealPlan;

    private MealPlan mealPlan;

    TextView descriptionView;
    TextView mealDateView;
    TextView servingsView;
    TextView ingredientsFieldView;
    Button deleteButton;
    RecyclerView ingredientsRecyclerView;
    IngredientAdapter ingredientAdapter;

    public MealDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMeal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        descriptionView = view.findViewById(R.id.meal_detail_description);
        mealDateView = view.findViewById(R.id.meal_detail_date);
        servingsView = view.findViewById(R.id.meal_detail_servings);
        ingredientsRecyclerView = view.findViewById(R.id.meal_detail_ingredient_list);
        ingredientsFieldView = view.findViewById(R.id.meal_detail_ingredients_field);

        // set text
        descriptionView.setText(mealPlan.getName());
        SimpleDateFormat sf = new SimpleDateFormat("MMM dd/yy");
        mealDateView.setText(sf.format(mealPlan.getDate()));
        servingsView.setText(String.valueOf(mealPlan.getServings()));

//        if (!mealPlan.getIngredients().isEmpty()) {
//            Log.d(TAG, mealPlan.getIngredients().toString());
//            setUpRecyclerView();
//        }

        if (!mealPlan.isIngredient()) {
            setUpRecyclerView();
        } else {
            // meal is an ingredient, so ingredient list is null
            ingredientsFieldView.setVisibility(View.INVISIBLE);
            ingredientsRecyclerView.setVisibility(View.INVISIBLE);
        }

        // delete button functionality
        deleteButton = view.findViewById(R.id.button_meal_delete);
        deleteButton.setOnClickListener(deleteButtonClicked());
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity.mealPlan.deleteMeal(mealPlan);
//                Navigation.findNavController(v).popBackStack();
//            }
//        });

        return view;
    }

    private void setUpRecyclerView() {
        ingredientAdapter = new IngredientAdapter(mealPlan.getIngredients(), 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(ingredientsRecyclerView.getContext(), linearLayoutManager.getOrientation()));
    }

    private void getMeal() {
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        mealPlan = MainActivity.mealPlan.getMealByPosition(position);
        Log.d(TAG, String.valueOf(mealPlan));
    }

    @NonNull
    private View.OnClickListener deleteButtonClicked() {
        return v -> {
            mealPlanController.deleteMeal(mealPlan);
            Navigation.findNavController(v).popBackStack();
        };
    }
}