package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
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
import com.CMPUT301F22T01.foodbit.models.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final static String TAG = "RecipeAdapter";
    private final ArrayList<Recipe> items;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public RecipeAdapter(ArrayList<Recipe> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // define UI
        private final TextView recipeTitle;
        private final TextView recipePrepTime;
        private final TextView recipeNumServings;
        private final ConstraintLayout recipePhoto;
        private final TextView recipeComments;


        public ViewHolder(View view) {
            super(view);

            // Define click listener for items
            view.setOnClickListener(v -> {
                // put argument
                Bundle bundle = new Bundle();
                bundle.putInt("position",getAdapterPosition());

                Navigation.findNavController(v).navigate(R.id.action_fragment_recipe_book_to_fragment_recipe_detail, bundle);
            });

            // init UI
            recipeTitle = view.findViewById(R.id.item_recipe_title);
            recipePrepTime = view.findViewById(R.id.item_recipe_prep_time);
            recipeNumServings = view.findViewById(R.id.item_recipe_num_servings);
            recipePhoto = view.findViewById(R.id.item_recipe_image);
            recipeComments = view.findViewById(R.id.item_recipe_comments);
        }

        // view holder's get view methods
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
        // get value of each fields
        String title = items.get(position).getTitle();
        int prepTime = items.get(position).getPrepTime();
        int numServings = items.get(position).getNumServings();
        String comments = items.get(position).getComments();
        Uri photo = items.get(position).getPhoto();

        // get views
        TextView titleView = holder.getRecipeTitleView();
        TextView prepTimeView = holder.getRecipePrepTimeView();
        TextView numServingsView = holder.getRecipeNumServingsView();
        TextView commentsView = holder.getRecipeCommentsView();
        ConstraintLayout photoLayout = holder.getRecipePhotoView();

        // set each view with value
        titleView.setText(title);
        String min = " minutes"; if (prepTime <= 1) {min = " minute";}
        prepTimeView.setText(prepTime + min);
        numServingsView.setText("×" + numServings);
        if (comments != null) {
            commentsView.setText(comments);
        } else {
            commentsView.setText("No comments.");
        }
        // todo: enable photo feature before release
//        if (photo != null) {
//            ImageView photoView = (ImageView) photoLayout.getViewById(R.id.item_recipe_photo_image);
//            photoView.setImageURI(photo);
//        } else {
            TextView capLetter = (TextView) photoLayout.getViewById(R.id.item_recipe_photo_text);
            capLetter.setText(Character.toString(title.charAt(0)));
//        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
