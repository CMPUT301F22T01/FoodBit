package com.CMPUT301F22T01.foodbit.models;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Recipe item);
    }

    private ArrayList<Recipe> items;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public RecipeAdapter(ArrayList<Recipe> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView recipeTitle;
        private final TextView recipePrepTime;
        private final TextView recipeNumServings;
        private final ConstraintLayout recipePhoto;
        private final TextView recipeComments;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
//            Navigation.findNavController(view).navigate(R.id.action_fragment_recipe_book_to_fragment_recipe_detail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",getAdapterPosition());

                    Navigation.findNavController(v).navigate(R.id.action_fragment_recipe_book_to_fragment_recipe_detail, bundle);
                }
            });

            recipeTitle = (TextView) view.findViewById(R.id.item_recipe_title);
            recipePrepTime = (TextView) view.findViewById(R.id.item_recipe_prep_time);
            recipeNumServings = (TextView) view.findViewById(R.id.item_recipe_num_servings);
            recipePhoto = (ConstraintLayout) view.findViewById(R.id.item_recipe_image);
            recipeComments = (TextView) view.findViewById(R.id.item_recipe_comments);
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
        public ConstraintLayout getRecipePhotoView() {
            return recipePhoto;
        }
        public TextView getRecipeCommentsView() {
            return recipeComments;
        }
    }


    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_book, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        String title = items.get(position).getTitle();
        int prepTime = items.get(position).getPrepTime();
        int numServings = items.get(position).getNumServings();
        String comments = items.get(position).getComments();
        String photo = items.get(position).getPhoto();
        holder.getRecipePrepTimeView().setText("Preparation Time: " + prepTime);
        holder.getRecipeTitleView().setText(title);
        holder.getRecipePrepTimeView().setText("Preparation Time: " + prepTime);
        holder.getRecipeNumServingsView().setText("Number of Servings: " + numServings);
        if (comments != null) {
            holder.getRecipeCommentsView().setText(comments);
        } else {
            holder.getRecipeCommentsView().setText("No comments.");
        }
        ConstraintLayout recipePhoto = holder.getRecipePhotoView();
        if (photo != null) {
            // TODO: how to load photo?
        } else {
            TextView capLetter = (TextView) recipePhoto.getViewById(R.id.item_recipe_image_text);
            capLetter.setText(Character.toString(title.charAt(0)));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
