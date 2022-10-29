package com.CMPUT301F22T01.foodbit.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private final static String TAG = "IngredientAdapter";
    private final ArrayList<Ingredient> items;
    //private final Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public IngredientAdapter(ArrayList<Ingredient> items) {
        this.items = items;
        //this.context = context;

    }

    //private final ArrayList<Ingredient> items;

    //public IngredientAdapter(ArrayList<Ingredient> items) {
        //this.items = items;
    //}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ingredientDescription;
        private final TextView ingredientBestBefore;
        private final TextView ingredientAmount;

        public ViewHolder(View view) {
            super(view);

            //TODO:on click listener

            ingredientDescription = view.findViewById(R.id.item_ingredient_description);
            ingredientBestBefore = view.findViewById(R.id.item_ingredient_best_before);
            ingredientAmount = view.findViewById(R.id.item_ingredient_amount);

        }

        public TextView getIngredientDescriptionView() {
            return ingredientDescription;
        }
        public TextView getIngredientBestBeforeView() {
            return ingredientBestBefore;
        }
        public TextView getIngredientAmountView() {
            return ingredientAmount;
        }

    }


    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_storage, parent, false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {

        String description = items.get(position).getDescription();
        String bestBefore = items.get(position).getBestBefore();
        String amount = String.valueOf(items.get(position).getAmount());

        TextView descriptionView = holder.getIngredientDescriptionView();
        TextView bestBeforeView = holder.getIngredientBestBeforeView();
        TextView amountView = holder.getIngredientAmountView();

        descriptionView.setText(description);
        bestBeforeView.setText(bestBefore);
        amountView.setText(amount);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
