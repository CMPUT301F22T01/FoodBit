package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * The ingredient storage that displays the list of ingredients in the ingredient storage
 */
public class IngredientStorageFragment extends Fragment {

    public String TAG = "IngredientStorage";

    private Context context;

    // getting ingredientStorage from main activity
    private  IngredientStorage ingredientStorage;

    IngredientAdapter adapter;


    public IngredientStorageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ingredient List");



        // This fragment has options menu for the action bar
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating the view
        View view = inflater.inflate(R.layout.fragment_ingredient_storage, container, false);

        // displays the ingredient storage items and the add button for adding new ingredients
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_storage);

        // Changed from addButton to adding by clicking the add icon on the Top Action Bar
        //Button addButton = view.findViewById(R.id.ingredient_storage_add_button);

        int mode = 0;
        ingredientStorage = MainActivity.ingredientStorage;
        adapter = new IngredientAdapter(ingredientStorage.getIngredients(), mode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);



        getActivity().setTitle("Ingredient List");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflating the menu resource file for this fragment
        inflater.inflate(R.menu.ingredient_storage_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Actions performed by the Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {   //Adding an Ingredient
            case R.id.ingredient_add:
                //launches IngredientAddFragment
                // allows for addition of a new ingredient when the add option is clicked on the action bar
                new IngredientAddFragment().show(getChildFragmentManager(), IngredientAddFragment.TAG);
                return true;

            // Sorting the Ingredients accordingly
            case R.id.filter1:
                //descriptionSort(getView());
                Toast.makeText(getActivity(), "Sorting: Description", Toast.LENGTH_SHORT).show();
            case R.id.filter2:
                //dateSort(getView());
                Toast.makeText(getActivity(), "Sorting: Best Before", Toast.LENGTH_SHORT).show();
            case R.id.filter3:
                Toast.makeText(getActivity(), "Sorting: Location", Toast.LENGTH_SHORT).show();
            case R.id.filter4:
                Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void descriptionSort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_storage);
        Collections.sort(ingredientStorage.getIngredients(), Ingredient.nameAscending);

        ingredientStorage = MainActivity.ingredientStorage;
        adapter = new IngredientAdapter(ingredientStorage.getIngredients(), IngredientAdapter.INGREDIENT_STORAGE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    public void dateSort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_storage);
        Collections.sort(ingredientStorage.getIngredients(), Ingredient.dateSort);

        int mode = 0;
        ingredientStorage = MainActivity.ingredientStorage;
        adapter = new IngredientAdapter(ingredientStorage.getIngredients(), mode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();

        CollectionReference ingredientStorageRef = MainActivity.ingredientStorageRef;
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
                Log.d(TAG, "current ingredient storage:" + ingredientStorage);
                Log.d(TAG, "Current ingredients" + ingredientStorage.getIngredients());
                adapter.notifyDataSetChanged();
            }
        });
    }
}


