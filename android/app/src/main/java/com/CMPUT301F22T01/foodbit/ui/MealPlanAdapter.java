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

import java.util.ArrayList;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.ViewHolder> {

    @NonNull
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.meal_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MealPlanAdapter.ViewHolder holder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.e("lol",items.get(position).getName());
        holder.getTextView().setText(items.get(position).getName());
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
        private final TextView textView;

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
            textView = view.findViewById(R.id.meal_plan_textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}

