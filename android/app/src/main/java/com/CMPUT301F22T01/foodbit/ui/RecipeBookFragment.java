package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.net.wifi.WifiNetworkSpecifier;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * The recipe book screen that displays a list of recipes in the recipe book.
 */
public class RecipeBookFragment extends Fragment {

    public String TAG = "RecipeController";

    // get recipe book from MainActivity
    private final RecipeController recipeController = MainActivity.recipeController;

    RecipeAdapter adapter;

    public RecipeBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Recipe Book");

        // This fragment has options menu for the action bar
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflating the menu resource file for this fragment
        inflater.inflate(R.menu.recipe_book_top_app_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Actions performed by the Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int itemId = item.getItemId();//Adding a Recipe
        if (itemId == R.id.recipe_add) {//launches RecipeAddFragment
            new RecipeAddFragment().show(getChildFragmentManager(), RecipeAddFragment.TAG);
            return true;

            // Sorting the Recipes accordingly
        } else if (itemId == R.id.filter1) {
            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);
        } else if (itemId == R.id.filter2) {
            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);
        } else if (itemId == R.id.filter3) {
            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);
        } else if (itemId == R.id.filter4) {
            Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and initialize recipeController
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        // get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recipe_book);

        // set up RecyclerView for the list of recipes
         setUpRecyclerView(recyclerView);

        getActivity().setTitle("Recipe Book");
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

        // real time updates of the recipeController
        recipeBookUpdate();
    }


// Changed from addButton to adding by clicking the add icon on the Top Action Bar
//    @NonNull
//    private View.OnClickListener addButtonClicked() {
//        return v -> new RecipeAddFragment().show(getChildFragmentManager(), RecipeAddFragment.TAG);
//    }



    private void setUpRecyclerView(@NonNull RecyclerView recyclerView) {
//        adapter = new RecipeAdapter(recipeController.getRecipes());
        adapter = new RecipeAdapter(MainActivity.recipeController.getRecipes());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        // add borderlines between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void recipeBookUpdate() {
        CollectionReference recipeBookRef = MainActivity.recipeBookRef;
        recipeBookRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            ArrayList<Recipe> newRecipes = new ArrayList<>();
            assert value != null;
            for (QueryDocumentSnapshot doc : value) {
//                Recipe newRecipe = doc.toObject(Recipe.class);
//                Recipe newRecipe = new Recipe(
//                        doc.getId(),
//                        doc.get("title").toString(),
//                        (int) (long) doc.get("prepTime"),
//                        (int) (long) doc.get("numServings"),
//                        (String) doc.get("category"),
//                        (String) doc.get("comments"),
//                        doc.get("photo") != null ? Uri.parse((String) doc.get("photo")) : null,
//                        (ArrayList<Ingredient>) doc.get("ingredients"));
                Recipe newRecipe = new Recipe(doc);
                newRecipe.setId(doc.getId());
                newRecipes.add(newRecipe);
            }
            recipeController.setRecipes(newRecipes);
            Log.d(TAG, "current recipe book: "+ recipeController);
            Log.d(TAG, "Current recipes: " + recipeController.getRecipes());
            adapter.notifyDataSetChanged();
        });
    }
}