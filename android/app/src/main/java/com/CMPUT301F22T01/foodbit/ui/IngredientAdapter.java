package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

import java.util.ArrayList;

/**
 * Provides a binding from a set of ingredients to views that are displayed with in the
 * <code>RecyclerView</code> in either the ingredient storage page, add recipe page or
 * recipe detail page.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    public final static int INGREDIENT_STORAGE = 0;
    public final static int RECIPE_ADD = 1;
    public final static int RECIPE_DETAIL = 2;
    private final ArrayList<Ingredient> items;
    private final int mode;

    public IngredientAdapter(ArrayList<Ingredient> items, int mode) {
        this.items = items;
        this.mode = mode;
    }

    /**
     * Provide a reference to the type of views that are used.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final static int INGREDIENT_STORAGE = 0;
        private final static int RECIPE_ADD = 1;
        private final static int RECIPE_DETAIL = 2;
        // define UI
        private final TextView ingredientDescription;
        private final TextView ingredientAmount;
        private final TextView ingredientUnit;

        public ViewHolder(View view, int mode) {
            super(view);
            // todo: Define click listener for the ViewHolder's View
            //view.setOnClickListener();
            switch(mode) {
                case INGREDIENT_STORAGE: {
                    view.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", getAdapterPosition());
                        Navigation.findNavController(v).navigate(R.id.action_fragment_ingredient_storage_to_fragment_ingredient_detail, bundle);
                    });

                }
                case RECIPE_ADD: {
                    // todo: recipe_add_ingredient_edit
//                    view.setOnClickListener(v -> {
//                        // put argument
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("position", getAdapterPosition());
//                    });
                }
                case RECIPE_DETAIL: {

                }
            }

            // init UI
            ingredientDescription = view.findViewById(R.id.item_ingredient_description);
            ingredientAmount = view.findViewById(R.id.item_ingredient_amount);
            ingredientUnit = view.findViewById(R.id.item_ingredient_unit);
        }

        // view holder's get view methods
        public TextView getIngredientDescriptionView() {
            return ingredientDescription;
        }
        public TextView getIngredientAmountView() {
            return ingredientAmount;
        }
        public TextView getIngredientUnitView() {
            return ingredientUnit;
        }
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientAdapter.ViewHolder(view, mode);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        // get value of each fields
        Log.d("IngredientAdapter", String.valueOf(items));
        Log.d("IngredientAdapter", items.getClass().getName());
        String description = items.get(position).getDescription();
        float amount = items.get(position).getAmount();
        String unit = items.get(position).getUnit();

        // get UI
        TextView descriptionView = holder.getIngredientDescriptionView();
        TextView amountView = holder.getIngredientAmountView();
        TextView unitView = holder.getIngredientUnitView();

        // set up UI
        descriptionView.setText(description);
        amountView.setText(String.valueOf(amount));
        unitView.setText(unit);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
