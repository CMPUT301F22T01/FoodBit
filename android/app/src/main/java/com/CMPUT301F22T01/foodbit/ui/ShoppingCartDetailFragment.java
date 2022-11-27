package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

public class ShoppingCartDetailFragment extends Fragment {
//    private static final String TAG = "Shopping Detail Fragment";
//
//    Ingredient ingredient;
//
//    Toolbar toolbar;
//    TextView descriptionView;
//    TextView bestBeforeView;
//    TextView locationView;
//    TextView amountView;
//    TextView unitView;
//    TextView categoryView;
//    Button editButton;
//
//    public ShoppingCartDetailFragment() {
//        // Required empty constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getIngredient();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        // inflating the layout
//        View view = inflater.inflate(R.layout.fragment_shopping_cart_edit, container, false);
//
//        toolbar = view.findViewById(R.id.shopping_detail_toolbar);
//        descriptionView = view.findViewById(R.id.shopping_detail_description);
//        bestBeforeView = view.findViewById(R.id.shopping_detail_best_before);
//        locationView = view.findViewById(R.id.shopping_detail_location);
//        amountView = view.findViewById(R.id.shopping_detail_amount);
//        unitView = view.findViewById(R.id.shopping_detail_unit);
//        categoryView = view.findViewById(R.id.shopping_detail_category);
//
//        toolbar.setTitle(ingredient.getDescription());
//
//        // back button
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).popBackStack();
//            }
//        });
//
//        // setting to current ingredient details
//        descriptionView.setText(ingredient.getDescription());
//        bestBeforeView.setText(ingredient.getBestBefore());
//        locationView.setText(ingredient.getLocation());
//        amountView.setText(String.valueOf(ingredient.getAmount()));
//        unitView.setText(ingredient.getUnit());
//        categoryView.setText(ingredient.getCategory());
//
//        editButton = view.findViewById(R.id.button_shopping_detail_edit);
//
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new IngredientEditFragment(ingredient).show(getChildFragmentManager(), IngredientEditFragment.TAG);
//            }
//        });
//        return view;
//    }
//
//    /**
//     * Retrieving the ingredient as a certain position
//     * Position of the ingredient being viewed
//     */
//    private void getIngredient() {
//        assert getArguments() != null;
//        int position = getArguments().getInt("position");
//        ingredient = MainActivity.ingredientController.getIngredientByPosition(position);
//        Log.d(TAG, String.valueOf(ingredient));
//    }
}
//
