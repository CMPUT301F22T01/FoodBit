package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.IngredientUnit;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A pop up dialog in the <code>recipe add screen</code> that allows users to enter required and optional information to add a ingredient to the recipe.
 */
public class RecipeAddIngredientFragment extends DialogFragment {

    public static final String TAG = "RecipeAddIngredientAdd";
    public static final int MODE_ADD = 0;
    public static final int MODE_EDIT = 1;
    private int mode;

    private IngredientController ingredientStorage;

    AutoCompleteTextView autoCompleteIngredients;
    ArrayAdapter<String> adapterList;
    AutoCompleteTextView unitTextView;
    ArrayAdapter<String> unitAdapter;

    IngredientAdapter adapter;

    // listeners
    public interface OnIngredientAddListener {
        void onIngredientAdd(Ingredient newIngredient);
    }
    private OnIngredientAddListener ingredientAddListener;
    public interface OnIngredientEditListener {
        void onIngredientEdit(int position, Ingredient newIngredient);
    }
    private OnIngredientEditListener ingredientEditListener;
    public interface OnIngredientDeleteListener {
        void onIngredientDelete(int position);
    }
    private OnIngredientDeleteListener ingredientDeleteListener;

    // UI
    private TextInputLayout descriptionLayout;
    private AutoCompleteTextView descriptionEditText;
    private TextInputLayout amountLayout;
    private TextInputEditText amountEditText;
    private TextInputLayout unitLayout;
    private AutoCompleteTextView unitEditText;
    private TextInputLayout categoryLayout;
    private TextInputEditText categoryEditText;

    private ArrayList<String> titleList;
    private Ingredient ingredient;
    private int position;

    private ExtendedFloatingActionButton newUnit;
    private EditText newUnitEditText;
    private Button completeNewUnit;



    /**
     * When adding a new ingredient, instantiate a <code>RecipeAddIngredientFragment</code> to
     * pass in the list of all titles of added ingredients (to avoid duplicate names).
     * @param titleList the list of all titles of added ingredients
     * @return an instance of <code>RecipeAddIngredientFragment</code>
     */
    public static RecipeAddIngredientFragment newInstance(ArrayList<String> titleList) {
        Bundle args = new Bundle();
        args.putStringArrayList("title list", titleList);
        args.putInt("mode", MODE_ADD);
        RecipeAddIngredientFragment fragment = new RecipeAddIngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * When editing an ingredient, instantiate a <code>RecipeAddIngredientFragment</code> to pass
     * in the ingredient to edit and its position in the adapter to update/delete the edited
     * ingredients.
     * @param oldIngredient the edited ingredient
     * @param position the position of the ingredient in the adapter
     * @return an instance of <code>RecipeAddIngredientFragment</code>
     */
    public static RecipeAddIngredientFragment newInstance(Ingredient oldIngredient, int position) {
        Bundle args = new Bundle();
        args.putSerializable("ingredient", oldIngredient);
        args.putInt("mode", MODE_EDIT);
        args.putInt("position", position);
        RecipeAddIngredientFragment fragment = new RecipeAddIngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();

        // check if the parent fragment implements all required interfaces
        if (parentFragment instanceof OnIngredientAddListener) {
            ingredientAddListener = (OnIngredientAddListener) parentFragment;
        } else {
            Log.d(TAG, String.valueOf(parentFragment));
            throw new RuntimeException(
                    parentFragment + " must implement OnIngredientAddListener"
            );
        }
        if (parentFragment instanceof OnIngredientEditListener) {
            ingredientEditListener = (OnIngredientEditListener) parentFragment;
        } else {
            Log.d(TAG, String.valueOf(parentFragment));
            throw new RuntimeException(
                    parentFragment + " must implement OnIngredientEditListener"
            );
        }
        if (parentFragment instanceof OnIngredientDeleteListener) {
            ingredientDeleteListener = (OnIngredientDeleteListener) parentFragment;
        } else {
            Log.d(TAG, String.valueOf(parentFragment));
            throw new RuntimeException(
                    parentFragment + " must implement OnIngredientDeleteListener"
            );
        }

        // get arguments base on the mode
        assert getArguments() != null;
        mode = getArguments().getInt("mode");
        if (mode == MODE_ADD) {
            titleList = getArguments().getStringArrayList("title list");
        } else if (mode == MODE_EDIT) {
            ingredient = (Ingredient) getArguments().getSerializable("ingredient");
            position = getArguments().getInt("position");
        }
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_recipe_input_ingredient_add, null);

        // set up UI
        descriptionLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_description);
        amountLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_amount);
        unitLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_unit);
        categoryLayout = view.findViewById(R.id.recipe_input_ingredient_add_layout_category);
        descriptionEditText = view.findViewById(R.id.recipe_input_ingredient_add_auto_complete_ingredients);
        amountEditText = view.findViewById(R.id.recipe_input_ingredient_add_edit_text_amount);
        unitEditText = view.findViewById(R.id.recipe_input_ingredient_add_auto_complete_units);
        categoryEditText = view.findViewById(R.id.recipe_input_ingredient_add_edit_text_category);

        autoCompleteIngredients = view.findViewById(R.id.recipe_input_ingredient_add_auto_complete_ingredients);
        ingredientStorage = MainActivity.ingredientController;
        List ingredientList = ingredientStorage.getDescriptions();
        adapterList = new ArrayAdapter<String>(getActivity(),R.layout.recipe_add_dropdown_layout,ingredientList);
        autoCompleteIngredients.setAdapter(adapterList);



        autoCompleteIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                ingredientStorage = MainActivity.ingredientController;
                ArrayList<Ingredient> ingredientList = ingredientStorage.getIngredients();


                for (Ingredient findIngredient: ingredientList) {

                    if (findIngredient.getDescription().equals(item)){
                    categoryEditText.setText(findIngredient.getCategory());

                }}
            }
        });




        unitTextView = view.findViewById(R.id.recipe_input_ingredient_add_auto_complete_units);
        List<String> units = new ArrayList<>(Arrays.asList("kg", "lbs", "oz", "tbs", "tsp", "g"));
        units.addAll(MainActivity.unit.getUnitDescription());
        unitAdapter = new ArrayAdapter<>(getActivity(), R.layout.ingredient_dropdown_layout, units);
        unitTextView.setAdapter(unitAdapter);

        newUnit = (ExtendedFloatingActionButton) view.findViewById(R.id.recipe_input_ingredient_new_unit);
        //Popup window for when user wants to add a new unit
        newUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                View popupView = getLayoutInflater().inflate(R.layout.ingredient_add_dropdown_popup, null);

                newUnitEditText = popupView.findViewById(R.id.add_dropdown_edit_text);
                completeNewUnit = popupView.findViewById(R.id.add_complete);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //add button pressed in popup window
                completeNewUnit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //getting information user typed and adding it to the unit options
                        String newUnit = newUnitEditText.getText().toString();
                        if (!units.contains(newUnit)){
                            unitAdapter.add(newUnit);
                            unitAdapter.notifyDataSetChanged();
                            IngredientUnit unit = new IngredientUnit(newUnit);
                            MainActivity.unit.add(unit);
                            MainActivity.unit.loadAllFromDB();
                            popupWindow.dismiss();
                        }
                        popupWindow.dismiss();
                    }
                });

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        //int mode = 0;
        ingredientStorage = MainActivity.ingredientController;
        adapter = new IngredientAdapter(ingredientStorage.getIngredients());



        // build the dialog based on the mode
        if (mode == MODE_ADD) {

            //categoryEditText.setText(ingredient.getCategory());
            adapterList.notifyDataSetChanged();
            return new AlertDialog.Builder(getContext())
                    .setView(view)
                    .setTitle("Add an ingredient")
                    .setNeutralButton("Cancel", null)
                    .setPositiveButton("Add", null)
                    .create();
        } else if (mode == MODE_EDIT) {
            // fill text fields with current information of the ingredient
            adapterList.notifyDataSetChanged();
            descriptionEditText.setText(ingredient.getDescription());
            amountEditText.setText(String.valueOf(ingredient.getAmount()));
            unitEditText.setText(ingredient.getUnit());
            if (ingredient.getCategory() != null) {
                categoryEditText.setText(ingredient.getCategory());
            }
            return new AlertDialog.Builder(getContext())
                    .setView(view)
                    .setTitle("Edit an ingredient")
                    .setNegativeButton("Delete", null)
                    .setNeutralButton("Cancel", null)
                    .setPositiveButton("Update", null)
                    .create();
        }
        throw new IllegalArgumentException("Invalid input mode for RecipeAddIngredient.");


    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogButtons();
        adapter.notifyDataSetChanged();
        adapterList.notifyDataSetChanged();
        CollectionReference ingredientStorageRef = MainActivity.ingredientListRef;
        ingredientStorageRef.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                ArrayList<Ingredient> newIngredients = new ArrayList<Ingredient>();
                assert value != null;
                for (QueryDocumentSnapshot doc : value) {
                    Map<String, Object> data = doc.getData();
                    String description = (String) data.get("description");
                    String bestBefore = (String) data.get("bestBefore");
                    String location = (String) data.get("location");
                    float amount = (float) (double) data.get("amount");
                    String unit = (String) data.get("unit");
                    String category = (String) data.get("category");

                    Ingredient newIngredient = new Ingredient(doc.getId(), description, bestBefore, location, amount, unit, category);
                    newIngredients.add(newIngredient);
                    Log.d(TAG, "ingredient id: " + newIngredient.getId());
                }
                ingredientStorage.setIngredients(newIngredients);
                adapter.notifyDataSetChanged();
                adapterList.notifyDataSetChanged();

            }
    });


    }

    /**
     * Specify dialog buttons' behaviour on click based on the mode.
     */
    private void setDialogButtons() {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if(dialog != null)
        {
            if (mode == MODE_ADD) {
                Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(v -> {
                    boolean canAddIngredient = true;
                    // input check
                    String description = String.valueOf(descriptionEditText.getText());
                    ingredientStorage = MainActivity.ingredientController;

                    if (description.equals("")) {
                        canAddIngredient = false;
                        descriptionLayout.setError("Required");
                    }


                    else if (titleList.contains(description)) {
                        canAddIngredient = false;
                        descriptionLayout.setError("Description already exists");
                    }
                    String amountStr = String.valueOf(amountEditText.getText());
                    if (amountStr.equals("")) {
                        canAddIngredient = false;
                        amountLayout.setError("Required");
                    }

                    String unit = String.valueOf(unitEditText.getText());
                    List<IngredientUnit> units = MainActivity.unit.getUnits();
                    List<String> definedUnits = new ArrayList<>(Arrays.asList("kg", "lbs", "oz", "tbs", "tsp", "g"));
                    //units.addAll(MainActivity.unit.getUnitDescription());
                    if (unit.equals("")) {
                        canAddIngredient = false;
                        unitLayout.setError("Required");
                    }
                    else if (!unit.equals("")){
                        unitAdapter.notifyDataSetChanged();
                        boolean unitExists = false;
                        //canAddIngredient = false;



                        for (IngredientUnit findUnit:units)
                        {
                            if (findUnit.getUnitName().equals(unit))
                            {
                                unitExists = true;
                            }

                        }

                        if (!unitExists){
                            if (!definedUnits.contains(unit))
                            {
                                canAddIngredient = false;
                                unitLayout.setError("Select Existing Unit or Add new Unit");
                            }
                            //canAddIngredient = false;
                            //unitLayout.setError("Select Existing Unit or Add new Unit");
                        }


                    }

                    String category = String.valueOf(categoryEditText.getText());
                    if (category.equals("")) {
                        category = null;
                    }
                    if (canAddIngredient) {
                        ingredientAddListener.onIngredientAdd(new Ingredient(description, Float.parseFloat(amountStr), unit, category));
                        dialog.dismiss();
                    }
                });
            } else if (mode == MODE_EDIT) {
                Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(v -> {
                    boolean canUpdateIngredient = true;
                    // input check
                    String description = String.valueOf(descriptionEditText.getText());

                    if (description.equals("")) {
                        canUpdateIngredient = false;
                        descriptionLayout.setError("Required");
                    }


                    String amountStr = String.valueOf(amountEditText.getText());
                    if (amountStr.equals("")) {
                        canUpdateIngredient = false;
                        amountLayout.setError("Required");
                    }
                    String unit = String.valueOf(unitEditText.getText());
                    List<IngredientUnit> units = MainActivity.unit.getUnits();
                    List<String> definedUnits = new ArrayList<>(Arrays.asList("kg", "lbs", "oz", "tbs", "tsp", "g"));

                    if (unit.equals("")) {
                        canUpdateIngredient = false;
                        unitLayout.setError("Required");
                    }

                    else if (!unit.equals("")){
                        boolean unitExists = false;
                        //canUpdateIngredient = false;


                        for (IngredientUnit findUnit:units)
                        {
                            if (findUnit.getUnitName().equals(unit))
                            {
                                unitExists = true;
                            }
                            else if (definedUnits.contains(findUnit.getUnitName()))
                            {
                                unitExists = true;
                            }
                        }

                        if (!unitExists){
                            if (!definedUnits.contains(unit))
                            {
                                canUpdateIngredient = false;
                                unitLayout.setError("Select Existing Unit or Add new Unit");
                            }
                            //canUpdateIngredient = false;
                            //unitLayout.setError("Select Existing Unit or Add new Unit");
                        }


                    }
                    String category = String.valueOf(categoryEditText.getText());
                    if (category.equals("")) {
                        category = null;
                    }
                    if (canUpdateIngredient) {
                        ingredientEditListener.onIngredientEdit(position, new Ingredient(description, Float.parseFloat(amountStr), unit, category));
                        dismiss();
                    }});
                Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error, null));
                negativeButton.setOnClickListener(v -> {
                    ingredientDeleteListener.onIngredientDelete(position);
                    dismiss();
                });
            }
        }
    }
}