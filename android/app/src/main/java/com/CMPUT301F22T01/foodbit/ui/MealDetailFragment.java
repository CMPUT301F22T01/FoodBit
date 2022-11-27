package com.CMPUT301F22T01.foodbit.ui;

import static com.CMPUT301F22T01.foodbit.MainActivity.listen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
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
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.SimpleDateFormat;

public class MealDetailFragment extends Fragment {

    private static final String TAG = "Meal Detail Fragment";
    private static final MealPlanController mealPlanController = MainActivity.mealPlan;

    private MealPlan mealPlan;

    Toolbar topBar;
    TextView mealNameView;
    TextView servingsView;
    TextView ingredientsFieldView;
    Button deleteButton;
    RecyclerView ingredientsRecyclerView;
    IngredientAdapter ingredientAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;

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

        topBar = view.findViewById(R.id.meal_detail_toolbar);
        mealNameView = view.findViewById(R.id.meal_detail_name);
        servingsView = view.findViewById(R.id.meal_detail_servings);
        ingredientsRecyclerView = view.findViewById(R.id.meal_detail_ingredient_list);
        ingredientsFieldView = view.findViewById(R.id.meal_detail_ingredients_field);
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);

        // set text
        populateData();

        // back button functionality
        topBar.setNavigationOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // delete button functionality
        deleteButton = view.findViewById(R.id.button_meal_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mealPlan.deleteMeal(mealPlan);
                Navigation.findNavController(v).popBackStack();
            }
        });

        // edit button functionality
        Button editButton = view.findViewById(R.id.button_meal_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealEditFragment newFragment = new MealEditFragment(mealPlan);
                FragmentManager fm = getChildFragmentManager();

                fm.executePendingTransactions();
                newFragment.show(fm, "EditMeal");

                //TODO: Not working, Attempting to update details page after editing

            };

//                return true;

        });


        return view;
    }

    private void setUpRecyclerView() {
        ingredientAdapter = new IngredientAdapter(mealPlan.getIngredients(), IngredientAdapter.MEAL_DETAIL);
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


    public void populateData() {
        collapsingToolbarLayout.setTitle(mealPlan.getName());
        SimpleDateFormat sf = new SimpleDateFormat("MMM dd/yy");
        collapsingToolbarLayout.setTitle(sf.format(mealPlan.getDate()));
        mealNameView.setText(mealPlan.getName());
        String servingsSuffix = " servings";
        if (mealPlan.getServings() == 1) {
            servingsSuffix = " serving";
        }
        String servingsText = mealPlan.getServings() + servingsSuffix;
        servingsView.setText(servingsText);

        if (!mealPlan.isIngredient() && !mealPlan.getIngredients().isEmpty()) {
            setUpRecyclerView();
        } else {
            // meal is an ingredient, so ingredient list is null
            ingredientsFieldView.setVisibility(View.INVISIBLE);
        }
    }

}