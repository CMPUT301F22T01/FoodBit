package com.CMPUT301F22T01.foodbit.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartPickedItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartPickedItemFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "Picked Ingredient";

    private Context context;
    private IngredientController ingredientController;
    public Ingredient ingredient;

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

    public ShoppingCartPickedItemFragment(Ingredient ingredient) {
        this.ingredient = ingredient;
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
        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart_edit, container, false);
    }
}