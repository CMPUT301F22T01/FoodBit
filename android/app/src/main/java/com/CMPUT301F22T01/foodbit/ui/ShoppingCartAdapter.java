package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;

import java.util.ArrayList;

/**
 * provide a set of ingredients according to the
 * ingredients storage and meal plan
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private final static int ShoppingIngredientDetail = 0;
    private final static String TAG = "ShoppingCartAdapter";
    private final ArrayList<Ingredient> items;
    private final int mode;

    public ShoppingCartAdapter(ArrayList<Ingredient> items, int mode) {
        this.items = items;
        this.mode = mode;
    }

    /**
     * set a viewHolder to provide a view for recycler view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final static int ShoppingIngredientDetail = 0;
        // define UI
        private final TextView cartDescription;
        private final TextView cartAmount;
        private final TextView cartUnit;
        private final TextView cartCategory;

        public ViewHolder(View view, int mode) {
            super(view);

            // Define click listener for items
            if (mode == ShoppingIngredientDetail) {
                view.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", getAdapterPosition());
                    Navigation.findNavController(v).navigate(R.id.action_fragment_shopping_cart_to_fragment_shopping_cart_edit, bundle);
                });
            }

            // init UI
            cartDescription = view.findViewById(R.id.shopping_ingredient_description);
            cartAmount = view.findViewById(R.id.shopping_ingredient_amount);
            cartUnit = view.findViewById(R.id.shopping_ingredient_unit);
            cartCategory = view.findViewById(R.id.shopping_ingredient_category);
        }

        // view holder's get view methods
        public TextView getCartDescriptionView() {
            return cartDescription;
        }

        public TextView getCartAmountView() { return cartAmount; }

        public TextView getCartUnitView() {
            return cartUnit;
        }

        public TextView getCartCategoryView() { return cartCategory; }
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_cart, parent, false);
        return new ShoppingCartAdapter.ViewHolder(view, mode);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.ViewHolder holder, int position) {
        // get value of each fields
        Log.d("ShoppingCartAdapter", String.valueOf(items));
        Log.d("ShoppingCartAdapter", items.getClass().getName());
        String description = items.get(position).getDescription();
        float amount = items.get(position).getAmount();
        String unit = items.get(position).getUnit();
        String category = items.get(position).getCategory();


        // get UI
        TextView descriptionView = holder.getCartDescriptionView();
        TextView amountView = holder.getCartAmountView();
        TextView unitView = holder.getCartUnitView();
        TextView categoryView = holder.getCartCategoryView();

        // set up UI
        descriptionView.setText(description);
        amountView.setText(String.valueOf(amount));
        unitView.setText(unit);
        categoryView.setText(category);
    }

    /**
     * provide an item count function
     * @return the size of the items
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
}
