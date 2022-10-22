package com.CMPUT301F22T01.foodbit.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private RecipeBook recipeBook;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView recipeTitle;
        private final TextView recipePrepTime;
        private final TextView recipeNumServings;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            recipeTitle = (TextView) view.findViewById(R.id.item_recipe_title);
            recipePrepTime = (TextView) view.findViewById(R.id.item_recipe_prep_time);
            recipeNumServings = (TextView) view.findViewById(R.id.item_recipe_num_servings);
        }

        public TextView getRecipeTitleView() {
            return recipeTitle;
        }
        public TextView getRecipePrepTimeView() {
            return recipePrepTime;
        }
        public TextView getRecipeNumServingsView() {
            return recipeNumServings;
        }
    }

    public RecipeAdapter(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_book_item, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        holder.getRecipeTitleView().setText(recipeBook.getRecipes().get(position).getTitle());
        holder.getRecipePrepTimeView().setText(String.valueOf(recipeBook.getRecipes().get(position).getPrepTime()));
        holder.getRecipeNumServingsView().setText(String.valueOf(recipeBook.getRecipes().get(position).getNumServings()));
    }

    @Override
    public int getItemCount() {
        return recipeBook.size();
    }
}
