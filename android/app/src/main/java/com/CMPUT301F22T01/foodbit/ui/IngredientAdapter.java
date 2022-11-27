package com.CMPUT301F22T01.foodbit.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

import java.util.ArrayList;

/**
 * Provides a binding from a set of ingredients to views that are displayed with in the
 * <code>RecyclerView</code> in either the ingredient list page, add recipe page,
 * recipe detail page, or meal detail page.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    final String TAG = "IngredientAdapter";
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
    public IngredientAdapter(ArrayList<Ingredient> items) {
        this.items = items;
    }

    /**
     * Provide a reference to the type of views that are used.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // define UI
        private final TextView ingredientDescription;
        private final TextView ingredientAmount;
        private final TextView ingredientUnit;
        private final TextView missingDetails;

        /**
         * Choosing which view to use
         * @param view the view to be used
         */
        public ViewHolder(View view) {
            super(view);
            // init UI
            ingredientDescription = view.findViewById(R.id.item_ingredient_description);
            ingredientAmount = view.findViewById(R.id.item_ingredient_amount);
            ingredientUnit = view.findViewById(R.id.item_ingredient_unit);
            missingDetails = view.findViewById(R.id.item_ingredient_missing_details);
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
        public TextView getMissingDetailsView() {return missingDetails;}
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        // get value of each fields
        Log.d(TAG, String.valueOf(items));
        Log.d(TAG, items.getClass().getName());
        String description = items.get(position).getDescription();
        String bestBefore = items.get(position).getBestBefore();
        String location = items.get(position).getLocation();
        float amount = items.get(position).getAmount();
        String unit = items.get(position).getUnit();
        String category = items.get(position).getUnit();

        // get UI
        TextView descriptionView = holder.getIngredientDescriptionView();
        TextView amountView = holder.getIngredientAmountView();
        TextView unitView = holder.getIngredientUnitView();
        TextView missingDetailsView = holder.getMissingDetailsView();

        // set up UI
        descriptionView.setText(description);
//        setDescriptionTextSize(descriptionView);
        descriptionView.setTextSize(14);
        amountView.setText(String.valueOf(amount));
        unitView.setText(unit);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
