package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
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
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * The recipe book screen that displays a list of recipes in the recipe book.
 */
public class RecipeBookFragment extends Fragment {

    public String TAG = "RecipeBook";

    // get recipe book from MainActivity
    private final RecipeBook recipeBook = MainActivity.recipeBook;

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
        switch(item.getItemId())
        {   //Adding a Recipe
            case R.id.recipe_add:
                //launches RecipeAddFragment
                new RecipeAddFragment().show(getChildFragmentManager(), RecipeAddFragment.TAG);
                return true;

            // Sorting the Recipes accordingly
            case R.id.filter1:
                Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();
            case R.id.filter2:
                Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();
            case R.id.filter3:
                Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();
            case R.id.filter4:
                Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        // get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recipe_book);

        // Changed from addButton to adding by clicking the add icon on the Top Action Bar
        //Button addButton = view.findViewById(R.id.recipe_book_add_button);

        // set up RecyclerView for the list of recipes
         setUpRecyclerView(recyclerView);


        // Changed from addButton to adding by clicking the add icon on the Top Action Bar
        // add button launches RecipeAddFragment
        //addButton.setOnClickListener(addButtonClicked());


        getActivity().setTitle("Recipe Book");
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

        // real time updates of the recipeBook
        recipeBookUpdate();
    }


// Changed from addButton to adding by clicking the add icon on the Top Action Bar
//    @NonNull
//    private View.OnClickListener addButtonClicked() {
//        return v -> new RecipeAddFragment().show(getChildFragmentManager(), RecipeAddFragment.TAG);
//    }



    private void setUpRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new RecipeAdapter(recipeBook.getRecipes());
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
        CollectionReference recipeBookRef = FirebaseFirestore.getInstance().collection("Recipe Book");
        recipeBookRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            ArrayList<Recipe> newRecipes = new ArrayList<>();
            assert value != null;
            for (QueryDocumentSnapshot doc : value) {
                Recipe newRecipe = doc.toObject(Recipe.class);
                newRecipe.setId(doc.getId());
                newRecipes.add(newRecipe);
//                Map<String, Object> data = doc.getData();
//                String title = (String) data.get("title");
//                int prepTime = (int) (long) data.get("prepTime");
//                int numServings = (int) (long) data.get("numServings");
//                String category = (String) data.get("category");
//                String comments = (String) data.get("comments");
//                Uri photo;
//                if (data.get("photo") != null) {
//                    photo = Uri.parse((String) data.get("photo"));
//                } else {photo = null;}
//                ArrayList<HashMap<String, Object>> ingredientsData = (ArrayList<HashMap<String, Object>>) data.get("ingredients");
//                ArrayList<Ingredient> ingredients = new ArrayList<>();
//                assert ingredientsData != null;
//                for (HashMap<String, Object> ingredientData : ingredientsData) {
//                    ingredients.add(new Ingredient(
//                            (String) ingredientData.get("description"),
//                            ((float) (double) ingredientData.get("amount")),
//                            (String) ingredientData.get("unit"),
//                            (String) ingredientData.get("category")
//                    ));
//                }
//                Recipe newRecipe = new Recipe(
//                        doc.getId(), title, prepTime, numServings, category, comments, photo, ingredients);
//                newRecipes.add(newRecipe);
//                Log.d(TAG, "recipe id: "+newRecipe.getId());
            }
            recipeBook.setRecipes(newRecipes);
            Log.d(TAG, "current recipe book: "+recipeBook);
            Log.d(TAG, "Current recipes: " + recipeBook.getRecipes());
            adapter.notifyDataSetChanged();
        });
    }
}