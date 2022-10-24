package com.CMPUT301F22T01.foodbit.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputLayout;


public class RecipeAddFragment extends DialogFragment {

//    private static final String RECIPE_BOOK = "recipe_book";
    public final static String TAG = "AddRecipe";

    // get recipe book from MainActivity
    private RecipeBook recipeBook = MainActivity.recipeBook;

    // views
    MaterialToolbar topBar;
    TextInputEditText titleEditText;
    TextInputLayout titleLayout;
    TextInputEditText prepTimeEditText;
    TextInputLayout prepTimeLayout;
    TextInputEditText numServingsEditText;
    TextInputLayout numServingsLayout;
    TextInputEditText categoryEditText;
    TextInputLayout categoryLayout;
    TextInputEditText commentsEditText;
    TextInputLayout commentsLayout;


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
        titleEditText = view.findViewById(R.id.recipe_add_edit_text_title);
        titleLayout= view.findViewById(R.id.recipe_add_text_layout_title);
        prepTimeEditText = view.findViewById(R.id.recipe_add_edit_text_prep_time);
        prepTimeLayout = view.findViewById(R.id.recipe_add_text_layout_prep_time);
        prepTimeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // remove starting zeros
                String str = s.toString();
                Log.d(TAG, str);
                if (str.length()> 0 && str.charAt(0) == '0') {
                    int i = 0;
                    while (i< str.length() && str.charAt(i) == '0') {
                        i++;
                    }
                    prepTimeEditText.setText(str.substring(i));
                }
                // display error if max prep time exceeded
                String errorMsg = "Maximum time exceeded (480 minutes)";
                if (str.length() > 3) {
                    prepTimeLayout.setError(errorMsg);
                } else if (str.length() > 0 && Integer.parseInt(str) > 480) {
                    prepTimeLayout.setError(errorMsg);
                } else {
                    prepTimeLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        numServingsEditText = view.findViewById(R.id.recipe_add_edit_text_num_servings);
        numServingsLayout = view.findViewById(R.id.recipe_add_text_layout_num_servings);
        numServingsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // remove starting zeros
                String str = s.toString();
                Log.d(TAG, str);
                if (str.length()> 0 && str.charAt(0) == '0') {
                    int i = 0;
                    while (i< str.length() && str.charAt(i) == '0') {
                        i++;
                    }
                    numServingsEditText.setText(str.substring(i));
                }
                // display error if max prep time exceeded
                String errorMsg = "Maximum servings exceeded (100 servings)";
                if (str.length() > 3) {
                    numServingsLayout.setError(errorMsg);
                } else if (str.length() > 0 && Integer.parseInt(str) > 100) {
                    numServingsLayout.setError(errorMsg);
                } else {
                    numServingsLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        categoryEditText = view.findViewById(R.id.recipe_add_edit_text_category);
        categoryLayout= view.findViewById(R.id.recipe_add_text_layout_category);
        commentsEditText = view.findViewById(R.id.recipe_add_edit_text_comments);
        commentsLayout= view.findViewById(R.id.recipe_add_text_layout_comments);

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
                    String title = titleEditText.getText().toString();
                    String prepTime = prepTimeEditText.getText().toString();
                    String numServings = numServingsEditText.getText().toString();
                    String category = categoryEditText.getText().toString();
                    String comment = commentsEditText.getText().toString();

                    // check empty fields
                    boolean requiredFieldEntered = true;
                    if (title.equals("")) {
                        titleLayout.setError("Required");
                        requiredFieldEntered=false;
                    }
                    if (prepTime.equals("")) {
                        prepTimeLayout.setError("Required");
                        requiredFieldEntered=false;
                    } else if (prepTime.length() > 3 || Integer.parseInt(prepTime) > 480) {
                        requiredFieldEntered = false;
                    }
                    if (numServings.equals("")) {
                        numServingsLayout.setError("Required");
                        requiredFieldEntered=false;
                    } else if (numServings.length() > 3 || Integer.parseInt(numServings) > 100) {
                        requiredFieldEntered = false;
                    }
                    if (requiredFieldEntered) {
                        Recipe recipe = new Recipe(title,
                                Integer.parseInt(prepTime),
                                Integer.parseInt(numServings),
                                category,
                                comment, null, null);
                        recipeBook.add(recipe);
                        dismiss();
                    }
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













