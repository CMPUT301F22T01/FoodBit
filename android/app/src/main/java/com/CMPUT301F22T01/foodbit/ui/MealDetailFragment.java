package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.MealPlan;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

public class MealDetailFragment extends Fragment {

    private static final String TAG = "Meal Detail Fragment";

    private MealPlan mealPlan;

    TextView descriptionView;
    TextView mealDateView;
    TextView servingsView;
    Button deleteButton;

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

        // set text
        descriptionView.setText(mealPlan.getName());
        SimpleDateFormat sf = new SimpleDateFormat("MMM dd/yy");
        mealDateView.setText(sf.format(mealPlan.getDate()));
        servingsView.setText(String.valueOf(mealPlan.getServings()));

        // delete button functionality
        deleteButton = view.findViewById(R.id.button_meal_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mealPlan.deleteMeal(mealPlan);
                Navigation.findNavController(v).popBackStack();
            }
        });



        return view;
    }

    private void getMeal() {
        assert getArguments() != null;
        int position = getArguments().getInt("position");
        mealPlan = MainActivity.mealPlan.getMealByPosition(position);
        Log.d(TAG, String.valueOf(mealPlan));
    }
}