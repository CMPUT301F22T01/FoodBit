package com.CMPUT301F22T01.foodbit.ui;

import static java.lang.Float.parseFloat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.security.PrivateKey;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartPickedItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartPickedItemFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "Picked Ingredient";

    public Context context;
    public IngredientController ingredientController;
    public Ingredient ingredient;
    public int position;

    MaterialToolbar topBar;
    TextView description;
    TextView amountNeeded;
    TextView unit;
    TextView category;
    TextInputEditText pickedAmountEditText;
    TextInputLayout pickedAmountLayout;
    ArrayAdapter<String> adapter;

    public ShoppingCartPickedItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "context: " + context);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShoppingCartPickedItemFragment.
     */
    public static ShoppingCartPickedItemFragment newInstance() {
        ShoppingCartPickedItemFragment fragment = new ShoppingCartPickedItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIngredient();
        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_shopping_cart_edit, container, false);

        topBar = view.findViewById(R.id.shopping_pick_top_bar);
        description = view.findViewById(R.id.shopping_picked_description);
        amountNeeded = view.findViewById(R.id.shopping_picked_need_amount);
        unit = view.findViewById(R.id.shopping_picked_unit);
        category = view.findViewById(R.id.shopping_picked_category);
        pickedAmountEditText = view.findViewById(R.id.shopping_picked_amount);

        topBar.setTitle("Picked Ingredient");
        // close button
        topBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String inputPickedAmount = Objects.requireNonNull(pickedAmountEditText.getText().toString());
                boolean requiredFieldEntered = true;
                if (inputPickedAmount.equals("")) {
                    pickedAmountLayout.setError("Required");
                    requiredFieldEntered = false;
                }
                if (requiredFieldEntered) {
                    Log.d(TAG, "current ingredient: "+ ingredient.getDescription());
                    Log.d(TAG, "current neededAmount: "+ inputPickedAmount);
                    Log.d(TAG, "current originalAmount: "+ ingredient.getAmount());
                    float amount = parseFloat(inputPickedAmount) + ingredient.getAmount();
                    ingredient.setAmount(amount);
                    MainActivity.ingredientController.edit(ingredient);
//                    ingredientEdit(ingredient);
                    dismiss();
                }
                return false;
            }
        });

        // setting to current ingredient details
        description.setText(ingredient.getDescription());
        amountNeeded.setText(String.valueOf(ingredient.getAmount()));
        unit.setText(ingredient.getUnit());
        category.setText(ingredient.getCategory());

        return view;
    }

    public void ingredientEdit(Ingredient ingredient) {
        ingredientController.edit(ingredient);
    }

    private void getIngredient() {
        assert getArguments() != null;
        position = getArguments().getInt("position");
        ingredient = MainActivity.ingredientController.getIngredientByPosition(position);
        Log.d(TAG, String.valueOf(ingredient));
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.Theme_FoodBit_Slide);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }
}