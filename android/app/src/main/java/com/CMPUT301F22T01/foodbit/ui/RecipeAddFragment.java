package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.RecipeBook;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;


public class RecipeAddFragment extends DialogFragment {

//    private static final String RECIPE_BOOK = "recipe_book";
    public final static String TAG = "AddRecipe";

    // get recipe book from MainActivity
    private RecipeBook recipeBook = MainActivity.recipeBook;

    // views
    MaterialToolbar topBar;
    TextInputEditText titleView;
    TextInputEditText prepTimeView;
    TextInputEditText numServingsView;
    TextInputEditText categoryView;
    TextInputEditText commentView;


    public RecipeAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_add, container, false);

        // init views
        topBar = view.findViewById(R.id.recipe_add_top_bar);
        titleView = view.findViewById(R.id.recipe_add_edit_text_title);
        prepTimeView = view.findViewById(R.id.recipe_add_edit_text_prep_time);
        numServingsView = view.findViewById(R.id.recipe_add_edit_text_num_servings);
        categoryView = view.findViewById(R.id.recipe_add_edit_text_category);
        commentView = view.findViewById(R.id.recipe_add_edit_text_comments);

        // set top bar behaviours
        // close button behaviour
        topBar.setNavigationOnClickListener(v -> {
            dismiss();
        });
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            // TODO: input check!!!
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                // done button behaviour
                if (itemId == R.id.recipe_add_done) {
                    String title = titleView.getText().toString();
                    int prepTime = Integer.parseInt(prepTimeView.getText().toString());
                    int numServings = Integer.parseInt(numServingsView.getText().toString());
                    String category = categoryView.getText().toString();
                    String comment = commentView.getText().toString();
                    Recipe recipe = new Recipe(title, prepTime, numServings, category, comment, null, null);
                    recipeBook.add(recipe);
                    dismiss();
                }
                return false;
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // set the style of the dialog fragment to be full screen
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.Theme_FoodBit_Slide);
        }
    }
}

//    TODO: photo support
//    private void imageChooser() {
//        // create an instance of the
//        // intent of the type image
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//
//        // pass the constant to compare it
//        // with the returned requestCode
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
//    }
//
//    // this function is triggered when user
//    // selects the image from the imageChooser
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == 200) {
//                // Get the url of the image from data
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    // update the preview image in the layout
//                    imageButton.setImageURI(selectedImageUri);
//                }
//            }
//        }
//    }













