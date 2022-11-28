package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.os.Build;
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

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.RecipeController;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;

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

        // This fragment has options menu for the action bar
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {

        // Inflating the menu resource file for this fragment
        inflater.inflate(R.menu.recipe_book_top_app_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Actions performed by the Action Bar

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();//Adding a Recipe
        if (itemId == R.id.recipe_add) {//launches RecipeAddFragment
            new RecipeAddFragment().show(getChildFragmentManager(), RecipeAddFragment.TAG);
            return true;

            // Sorting the Recipes accordingly
        } else if (itemId == R.id.titleFilter1) {//Sorting titles in ascending order
            titleASort();
            Toast.makeText(getActivity(), "Sorting(A-Z): Title", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.titleFilter2) {//Sorting titles in descending order
            titleDSort();
            Toast.makeText(getActivity(), "Sorting(Z-A): Title", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.timeFilter1) {//Sorting preparation time in ascending order
            prepTimeASort();
            Toast.makeText(getActivity(), "Sorting(Quick-Long): Preparation Time", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.timeFilter2) {//Sorting preparation time in descending order
            prepTimeDSort();
            Toast.makeText(getActivity(), "Sorting(Long-Quick): Preparation Time", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.serveFilter1) {//Sorting number of servings in ascending order
            serveASort();
            Toast.makeText(getActivity(), "Sorting(Low-High): Number of Servings", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.serveFilter2) {//Sorting number of servings in descending order
            serveDSort();
            Toast.makeText(getActivity(), "Sorting(High-Low): Number of Servings", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.categoryFilter1) {//Sorting category in ascending order
            categoryASort();
            Toast.makeText(getActivity(), "Sorting(A-Z): Category", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.categoryFilter2) {//Sorting category in descending order
            categoryDSort();
            Toast.makeText(getActivity(), "Sorting(Z-A): Category", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
        @SuppressLint("NotifyDataSetChanged")
        public void titleASort()
        {
            Collections.sort(recipeController.getRecipes(), Recipe.titleAscending);
            adapter.notifyDataSetChanged();
        }
        @SuppressLint("NotifyDataSetChanged")
        public void titleDSort()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(recipeController.getRecipes(), Recipe.titleAscending.reversed());
            }
            adapter.notifyDataSetChanged();
        }


        @SuppressLint("NotifyDataSetChanged")
        public void prepTimeASort()
        {
            Collections.sort(recipeController.getRecipes(), Recipe.prepTimeSort);
            adapter.notifyDataSetChanged();
        }
        @SuppressLint("NotifyDataSetChanged")
        public void prepTimeDSort()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(recipeController.getRecipes(), Recipe.prepTimeSort.reversed());
        }
            adapter.notifyDataSetChanged();
        }
        @SuppressLint("NotifyDataSetChanged")
        public void serveASort()
        {
            Collections.sort(recipeController.getRecipes(), Recipe.servingSort);
            adapter.notifyDataSetChanged();
        }
        @SuppressLint("NotifyDataSetChanged")
        public void serveDSort()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(recipeController.getRecipes(), Recipe.servingSort.reversed());
            }
            adapter.notifyDataSetChanged();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void categoryASort()
        {
            Collections.sort(recipeController.getRecipes(), Recipe.categoryAscending);
            adapter.notifyDataSetChanged();
        }
        @SuppressLint("NotifyDataSetChanged")
        public void categoryDSort()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(recipeController.getRecipes(), Recipe.categoryAscending.reversed());
            }
            adapter.notifyDataSetChanged();
        }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment and initialize recipeController
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        // get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recipe_book);

        // set up RecyclerView for the list of recipes
         setUpRecyclerView(recyclerView);

        requireActivity().setTitle("Recipe Book");
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

        // real time updates of the recipeController
        recipeBookUpdate();
    }



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
        CollectionReference recipeBookRef = MainActivity.recipeControllerRef;
        recipeBookRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            ArrayList<Recipe> newRecipes = new ArrayList<>();
            assert value != null;
            for (QueryDocumentSnapshot doc : value) {
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