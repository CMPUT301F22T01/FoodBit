package com.CMPUT301F22T01.foodbit.ui;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.RecipeBook;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class RecipeAddFragment extends DialogFragment implements RecipeAddIngredientAddFragment.OnIngredientAddListener{

    //    private static final String RECIPE_BOOK = "recipe_book";
    public final static String TAG = "AddRecipe";
    private Context context;

    // get recipe book from MainActivity
    private final RecipeBook recipeBook = MainActivity.recipeBook;

    // an ingredient list to obtain from the RecipeAddIngredientAddFragment
    ArrayList<Ingredient> ingredients = new ArrayList<>();

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
    ImageView imageView;
    MaterialToolbar ingredientsBar;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    Uri photoUri = null;

    public RecipeAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "context: "+context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        getContext().getApplicationContext().getContentResolver().takePersistableUriPermission(uri, flag);
                        photoUri = uri;
                        imageView.setImageURI(uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_add, container, false);

        // init views
        topBar = view.findViewById(R.id.recipe_add_top_bar);
        titleEditText = view.findViewById(R.id.recipe_add_edit_text_title);
        titleLayout = view.findViewById(R.id.recipe_add_text_layout_title);
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
                if (str.length() > 0 && str.charAt(0) == '0') {
                    int i = 0;
                    while (i < str.length() && str.charAt(i) == '0') {
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
                if (str.length() > 0 && str.charAt(0) == '0') {
                    int i = 0;
                    while (i < str.length() && str.charAt(i) == '0') {
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
        categoryLayout = view.findViewById(R.id.recipe_add_text_layout_category);
        commentsEditText = view.findViewById(R.id.recipe_add_edit_text_comments);
        commentsLayout = view.findViewById(R.id.recipe_add_text_layout_comments);
        imageView = view.findViewById(R.id.recipe_add_image);
        imageView.setOnClickListener(v -> {
            if (photoUri == null) {
                imageChooser(pickMedia);
            } else {
                PopupMenu popup = new PopupMenu(context, v);
                popup.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.recipe_add_photo_remove) {
                        photoUri = null;
                        imageView.setImageURI(null);
                        return true;
                    } else if (itemId == R.id.recipe_add_photo_select_new) {
                        imageChooser(pickMedia);
                        return true;
                    }
                    return false;
                });
                MenuInflater inflater1 = popup.getMenuInflater();
                inflater1.inflate(R.menu.recipe_add_image_edit, popup.getMenu());
                popup.show();
            }
        });
        ingredientsBar = view.findViewById(R.id.recipe_add_ingredients_bar);

        // set top bar behaviours
        // close button behaviour
        topBar.setNavigationOnClickListener(v -> {
            dismiss();
        });
        topBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            // done button behaviour
            if (itemId == R.id.recipe_add_done) {
                String title = Objects.requireNonNull(titleEditText.getText()).toString();
                String prepTime = Objects.requireNonNull(prepTimeEditText.getText()).toString();
                String numServings = Objects.requireNonNull(numServingsEditText.getText()).toString();
                String category = Objects.requireNonNull(categoryEditText.getText()).toString(); if (category.equals("")){category = null;}
                String comments = Objects.requireNonNull(commentsEditText.getText()).toString(); if (comments.equals("")){comments = null;}
                Uri photo = photoUri;
                // check empty fields
                boolean requiredFieldEntered = true;
                if (title.equals("")) {
                    titleLayout.setError("Required");
                    requiredFieldEntered = false;
                }
                if (prepTime.equals("")) {
                    prepTimeLayout.setError("Required");
                    requiredFieldEntered = false;
                } else if (prepTime.length() > 3 || Integer.parseInt(prepTime) > 480) {
                    requiredFieldEntered = false;
                }
                if (numServings.equals("")) {
                    numServingsLayout.setError("Required");
                    requiredFieldEntered = false;
                } else if (numServings.length() > 3 || Integer.parseInt(numServings) > 100) {
                    requiredFieldEntered = false;
                }
                if (requiredFieldEntered) {
                    Recipe recipe = new Recipe(title,
                            Integer.parseInt(prepTime),
                            Integer.parseInt(numServings),
                            category, comments, photoUri, null);
                    recipeBook.add(recipe);
                    dismiss();
                }
            }
            return false;
        });

        ingredientsBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.recipe_add_ingredient_add) {
                    new RecipeAddIngredientAddFragment().show(getChildFragmentManager(), RecipeAddIngredientAddFragment.TAG);
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


    private void imageChooser(ActivityResultLauncher<PickVisualMediaRequest> pickMedia) {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE) // False error - actually works fine
                .build());

    }

    @Override
    public void onIngredientAdd(Ingredient newIngredient) {
        ingredients.add(newIngredient);
    }
}













