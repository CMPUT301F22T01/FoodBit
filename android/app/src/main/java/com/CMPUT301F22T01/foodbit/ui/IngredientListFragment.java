package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
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
 * The ingredient list fragment that displays the list of ingredients in the ingredient list
 */
public class IngredientListFragment extends Fragment {

    public String TAG = "IngredientController";

    private Context context;

    // getting ingredientController from main activity
    private IngredientController ingredientController = MainActivity.ingredientController;

    IngredientAdapter adapter;

    TextView missingDetailWarning;

    public IngredientListFragment() {
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
        getActivity().setTitle("Ingredients");



        // This fragment has options menu for the action bar
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflating the menu resource file for this fragment
        inflater.inflate(R.menu.ingredient_list_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Actions performed by the Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {   //Adding an Ingredient
            case R.id.ingredient_add:
                //launches IngredientAddFragment
                // allows for addition of a new ingredient when the add option is clicked on the action bar
                new IngredientAddFragment().show(getChildFragmentManager(), IngredientAddFragment.TAG);
                return true;

            // Sorting the Ingredients accordingly
            case R.id.descriptionFilter1:
                descriptionASort(getView());
                Toast.makeText(getActivity(), "Sorting(A-Z): Description", Toast.LENGTH_SHORT).show();
                break;
            case R.id.descriptionFilter2:
                descriptionDSort(getView());
                Toast.makeText(getActivity(), "Sorting(Z-A): Description", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dateFilter1:
                dateASort(getView());
                Toast.makeText(getActivity(), "Sorting(Ascending): Best Before", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dateFilter2:
                dateDSort(getView());
                Toast.makeText(getActivity(), "Sorting(Descending): Best Before", Toast.LENGTH_SHORT).show();
                break;
            case R.id.locationFilter1:
                locationASort(getView());
                Toast.makeText(getActivity(), "Sorting(A-Z): Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.locationFilter2:
                locationDSort(getView());
                Toast.makeText(getActivity(), "Sorting(Z-A): Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.categoryFilter1:
                categoryASort(getView());
                Toast.makeText(getActivity(), "Sorting(A-Z): Category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.categoryFilter2:
                categoryDSort(getView());
                Toast.makeText(getActivity(), "Sorting(Z-A): Category", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    public void descriptionASort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        Collections.sort(ingredientController.getIngredients(), Ingredient.nameAscending);
        adapter.notifyDataSetChanged();
    }
    public void descriptionDSort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(ingredientController.getIngredients(), Ingredient.nameAscending.reversed());
        }

        adapter.notifyDataSetChanged();
    }

    public void dateASort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        Collections.sort(ingredientController.getIngredients(), Ingredient.dateSort);
        adapter.notifyDataSetChanged();
    }
    public void dateDSort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(ingredientController.getIngredients(), Ingredient.dateSort.reversed());
        }
        Collections.reverseOrder();
        adapter.notifyDataSetChanged();
    }
    public void locationASort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        Collections.sort(ingredientController.getIngredients(), Ingredient.locationAscending);
        adapter.notifyDataSetChanged();
    }
    public void locationDSort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(ingredientController.getIngredients(), Ingredient.locationAscending.reversed());
        }
        Collections.reverseOrder();
        adapter.notifyDataSetChanged();
    }
    public void categoryASort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        Collections.sort(ingredientController.getIngredients(), Ingredient.categoryAscending);
        adapter.notifyDataSetChanged();
    }
    public void categoryDSort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(ingredientController.getIngredients(), Ingredient.categoryAscending.reversed());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating the view
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        // displays the ingredient list items and the add button for adding new ingredients
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_list);
        missingDetailWarning = view.findViewById(R.id.ingredient_list_missing_details_warning);
        setMissingDetailWarningVisibility();

        // Changed from addButton to adding by clicking the add icon on the Top Action Bar
        //Button addButton = view.findViewById(R.id.ingredient_list_add_button);

//        adapter = new IngredientAdapter(ingredientController.getIngredients(), IngredientAdapter.INGREDIENT_LIST);
        adapter = new IngredientListIngredientAdapter(ingredientController.getIngredients());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        getActivity().setTitle("Ingredients");
        return view;
    }

    /**
     * Will give an indicator if an ingredient has any missing details
     */
    private void setMissingDetailWarningVisibility() {
        boolean hasIngredientMissingDetail = false;
        for (Ingredient ingredient :
                ingredientController.getIngredients()) {
            if (ingredient.isMissingDetails()) {
                hasIngredientMissingDetail = true;
            }
        }
        if (hasIngredientMissingDetail) {
            missingDetailWarning.setVisibility(View.VISIBLE);
        } else {
            missingDetailWarning.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        CollectionReference ingredientListRef = MainActivity.ingredientListRef;
        ingredientListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {

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
                ingredientController.setIngredients(newIngredients);
                Log.d(TAG, "current ingredient list:" + ingredientController);
                Log.d(TAG, "Current ingredients" + ingredientController.getIngredients());
                setMissingDetailWarningVisibility();
                adapter.notifyDataSetChanged();
            }
        });
    }
}


