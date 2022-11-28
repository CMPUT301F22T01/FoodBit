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
 * provide a set of ingredients according to the
 * ingredients storage and meal plan
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>{
    final String TAG = "ShoppingCartAdapter";
    protected final ArrayList<Ingredient> items;

    /**
     * Item click listener for ingredients
     */
    public interface OnItemClickListener {
        void onIngredientItemClick(View v, int position);
    }
    protected OnItemClickListener itemClickListener;

    /**
     * setting the item click listener
     * @param itemClickListener the listener for item clicks
     */
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * Adapter for the ingredient items
     * @param items list of ingredient items
     */
    public ShoppingCartAdapter(ArrayList<Ingredient> items) {
        this.items = items;
    }

    /**
     * set a viewHolder to provide a view for recycler view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // define UI
        private final TextView cartDescription;
        private final TextView cartAmount;
        private final TextView cartUnit;
        private final TextView cartCategory;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for items
//            view.setOnClickListener(v -> {
//                Bundle bundle = new Bundle();
//                bundle.putInt("position", getAdapterPosition());
////                Navigation.findNavController(v).navigate(R.id.action_fragment_shopping_cart_to_fragment_shopping_cart_edit, bundle);
////                ShoppingCartPickedItemFragment.newInstance(getAdapterPosition()).show();
//            });

            // init UI
            cartDescription = view.findViewById(R.id.shopping_ingredient_description);
            cartAmount = view.findViewById(R.id.shopping_ingredient_amount);
            cartUnit = view.findViewById(R.id.shopping_ingredient_unit);
            cartCategory = view.findViewById(R.id.shopping_ingredient_category);
        }

        // view holder's get view methods
        public TextView getCartDescriptionView() { return cartDescription; }

        public TextView getCartAmountView() { return cartAmount; }

        public TextView getCartUnitView() { return cartUnit; }

        public TextView getCartCategoryView() { return cartCategory; }
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_cart, parent, false);
        return new ShoppingCartAdapter.ViewHolder(view);
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
        descriptionView.setTextSize(20);
        amountView.setTextSize(18);
        unitView.setTextSize(18);
        categoryView.setTextSize(18);

        // set up UI
        descriptionView.setText(description);
        amountView.setText(String.valueOf(amount));
        unitView.setText(unit);
        categoryView.setText(category);

        // define item's on click behaviour
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onIngredientItemClick(v, holder.getAdapterPosition());
            }
        });
    }

//    private View.OnClickListener onItemClick(@NonNull IngredientAdapter.ViewHolder holder) {
//        return v -> {
//            Bundle bundle = new Bundle();
//            bundle.putInt("position", holder.getAdapterPosition());
//            Navigation.findNavController(v).navigate(R.id.action_fragment_shopping_cart_to_fragment_shopping_cart_edit, bundle);
//        };
//    }

    /**
     * provide an item count function
     * @return the size of the items
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
}