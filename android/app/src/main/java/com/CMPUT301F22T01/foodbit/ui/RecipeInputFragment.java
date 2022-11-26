package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class RecipeInputFragment extends DialogFragment implements RecipeAddIngredientFragment.OnIngredientAddListener, RecipeAddIngredientFragment.OnIngredientEditListener, RecipeAddIngredientFragment.OnIngredientDeleteListener, IngredientAdapter.OnItemClickListener {
    public static String TAG;
    final int REQUEST_IMAGE_CAPTURE = 1;
    // an ingredient list to obtain from the RecipeAddIngredientFragment
    ArrayList<Ingredient> ingredients;
    IngredientAdapter ingredientAdapter;
    // UI
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
    ConstraintLayout imageLayout;
    ImageView imageView;
    MaterialToolbar ingredientsBar;
    RecyclerView ingredientsRecyclerView;
    Context context;
    // get recipe book from MainActivity
    protected RecipeController recipeController = MainActivity.recipeController;

    protected boolean hasPhoto = false;
    protected Bitmap photoBitmap;

    public abstract void setTAG();

    public static RecipeInputFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        RecipeInputFragment fragment = new RecipeEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setTAG();
        this.context = context;
        Log.d(TAG, "context: "+context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the style of the dialog fragment to be full screen
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FoodBit_FullScreenDialog);

        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_input, container, false);

        // init views
        topBar = view.findViewById(R.id.recipe_add_top_bar);
        topBarSetText();
        titleEditText = view.findViewById(R.id.recipe_add_edit_text_title);
        titleLayout = view.findViewById(R.id.recipe_add_text_layout_title);
        prepTimeEditText = view.findViewById(R.id.recipe_add_edit_text_prep_time);
        prepTimeLayout = view.findViewById(R.id.recipe_add_text_layout_prep_time);
        prepTimeEditText.addTextChangedListener(prepTimeTextWatcher());
        numServingsEditText = view.findViewById(R.id.recipe_add_edit_text_num_servings);
        numServingsLayout = view.findViewById(R.id.recipe_add_text_layout_num_servings);
        numServingsEditText.addTextChangedListener(numServingsTextWatcher());
        categoryEditText = view.findViewById(R.id.recipe_add_edit_text_category);
        categoryLayout = view.findViewById(R.id.recipe_add_text_layout_category);
        commentsEditText = view.findViewById(R.id.recipe_add_edit_text_comments);
        commentsLayout = view.findViewById(R.id.recipe_add_text_layout_comments);
        imageLayout = view.findViewById(R.id.recipe_add_image_layout);
        imageView = view.findViewById(R.id.recipe_add_image);
        ingredientsBar = view.findViewById(R.id.recipe_add_ingredients_bar);
        ingredientsRecyclerView = view.findViewById(R.id.recipe_add_ingredients_list);
        
        // display recipe info if in recipe edit screen
        displayInfo();

        imageLayout.setOnClickListener(v -> imageLayoutClicked(v));

        // close button behaviour
        topBar.setNavigationOnClickListener(v -> dismiss());
        topBar.setOnMenuItemClickListener(item ->
        {
            int itemId = item.getItemId();
            // done button behaviour
            if (itemId == R.id.recipe_add_done) {
                doneButtonClicked();
            }
            return false;
        });

        ingredientsBar.setOnMenuItemClickListener(addIngredientsButtonClicked());
        setUpRecyclerView();

        return view;
    }

    private void imageLayoutClicked(View v) {
        if (!hasPhoto) {
            dispatchTakePictureIntent();
        } else {
            PopupMenu popup = new PopupMenu(context, v);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.new_photo) {
                        dispatchTakePictureIntent();
                        return true;
                    } else if (item.getItemId() == R.id.delete_photo){
                        hasPhoto = false;
                        imageView.setImageBitmap(null);
                        return true;
                    }
                    return false;
                }
            });
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.recipe_add_photo_option, popup.getMenu());
            popup.show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            photoBitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photoBitmap);
            hasPhoto = true;
        }
    }

    /**
     * A hook method for <code>RecipeEditFragment</code> to override.
     */
    protected void displayInfo() {
    }

    private void topBarSetText() {
        topBar.setTitle(getTitle());
    }

    protected abstract String getTitle();

    @NonNull
    private Toolbar.OnMenuItemClickListener addIngredientsButtonClicked() {
        return item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.recipe_add_ingredient_add) {
                ArrayList<String> titleList = new ArrayList<>();
                for (Ingredient ingredient : ingredients) {
                    titleList.add(ingredient.getDescription());
                }
                RecipeAddIngredientFragment.newInstance(titleList).show(getChildFragmentManager(), RecipeAddIngredientFragment.TAG);
            }
            return false;
        };
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

    private void setUpRecyclerView() {
        ingredientAdapter = new IngredientAdapter(ingredients, IngredientAdapter.RECIPE_ADD);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);

        ingredientAdapter.setItemClickListener(this);
    }

    @NonNull
    private TextWatcher prepTimeTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
            public void afterTextChanged(Editable s) {}
        };
    }

    @NonNull
    private TextWatcher numServingsTextWatcher() {
        return new TextWatcher() {
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
        };
    }

    protected Uri saveImage() {
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        String title = String.valueOf(titleEditText.getText());
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, title+".jpg");
            FileOutputStream stream = new FileOutputStream(file);
            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.CMPUT301F22T01.foodbit.provider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "saveImage: "+uri);
        return uri;
    }

    protected abstract void doneButtonClicked();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onIngredientAdd(Ingredient newIngredient) {
        ingredients.add(newIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onIngredientEdit(int position, Ingredient newIngredient) {
        Ingredient oldIngredient = ingredients.get(position);
        oldIngredient.update(newIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onIngredientDelete(int position) {
        ingredients.remove(position);
        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onIngredientItemClick(View v, int position) {
        RecipeAddIngredientFragment.newInstance(ingredients.get(position), position).show(getChildFragmentManager(), RecipeAddIngredientFragment.TAG);
    }
}
