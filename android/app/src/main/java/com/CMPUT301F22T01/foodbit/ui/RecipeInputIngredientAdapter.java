package com.CMPUT301F22T01.foodbit.ui;

import android.view.View;

import androidx.annotation.NonNull;

import com.CMPUT301F22T01.foodbit.models.Ingredient;

import java.util.ArrayList;

public class RecipeInputIngredientAdapter extends IngredientAdapter{
    /**
     * Adapter for the ingredient items
     *
     * @param items list of ingredient items
     */
    public RecipeInputIngredientAdapter(ArrayList<Ingredient> items) {
        super(items);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(onItemClick(holder));
    }

    private View.OnClickListener onItemClick(@NonNull ViewHolder holder) {
        return v -> itemClickListener.onIngredientItemClick(v, holder.getAdapterPosition());
    }
}
