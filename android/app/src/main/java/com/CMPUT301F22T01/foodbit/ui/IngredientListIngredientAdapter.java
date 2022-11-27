package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

import java.util.ArrayList;

public class IngredientListIngredientAdapter extends IngredientAdapter{
    /**
     * Adapter for the ingredient items
     *
     * @param items list of ingredient items
     */
    public IngredientListIngredientAdapter(ArrayList<Ingredient> items) {
        super(items);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String description = items.get(position).getDescription();
        String bestBefore = items.get(position).getBestBefore();
        String location = items.get(position).getLocation();
        String unit = items.get(position).getUnit();

        TextView descriptionView = holder.getIngredientDescriptionView();
        TextView amountView = holder.getIngredientAmountView();
        TextView unitView = holder.getIngredientUnitView();
        ImageView missingDetailsView = holder.getMissingDetailsView();
        descriptionView.setTextSize(20);
        amountView.setTextSize(18);
        unitView.setTextSize(18);
        if (((description == null) || (bestBefore == null) || (location == null) || (unit == null))) {
            missingDetailsView.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(onItemClick(holder));
    }

    private View.OnClickListener onItemClick(@NonNull ViewHolder holder) {
        return v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("position", holder.getAdapterPosition());
            Navigation.findNavController(v).navigate(R.id.action_fragment_ingredient_list_to_fragment_ingredient_detail, bundle);
        };
    }
}
