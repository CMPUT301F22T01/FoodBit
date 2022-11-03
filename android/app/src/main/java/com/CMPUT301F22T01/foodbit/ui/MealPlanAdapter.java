package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.models.MealPlan;
import com.CMPUT301F22T01.foodbit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.ViewHolder> {

    @NonNull
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_meal_list, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MealPlanAdapter.ViewHolder holder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.e("lol",items.get(position).getName());
        holder.getMealPlanTitleView().setText(items.get(position).getName());
        SimpleDateFormat sf = new SimpleDateFormat("MMM dd/yy");
        holder.getMealPlanDateView().setText(sf.format(items.get(position).getDate()));
        holder.getMealPlanServings().setText(String.valueOf(items.get(position).getServings()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(MealPlan item);
    }

    private ArrayList<MealPlan> items;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public MealPlanAdapter(ArrayList<MealPlan> items) {
        this.items = items;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mealPlanTitle;
        private final TextView mealPlanDate;
        private final TextView mealPlanServings;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's
            // Define click listener for items
            view.setOnClickListener(v -> {
                // put argument
                Bundle bundle = new Bundle();
                bundle.putInt("position",getAdapterPosition());

//                Navigation.findNavController(v).navigate(R.id.action_fragment_recipe_book_to_fragment_recipe_detail, bundle);
            });
            mealPlanTitle = view.findViewById(R.id.meal_plan_title);
            mealPlanDate = view.findViewById(R.id.meal_plan_date);
            mealPlanServings = view.findViewById(R.id.meal_plan_servings);
        }

        public TextView getMealPlanTitleView() {
            return mealPlanTitle;
        }
        public TextView getMealPlanDateView() { return mealPlanDate; }
        public TextView getMealPlanServings() { return mealPlanServings; }
    }
}

