package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Ingredient;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.controllers.RecipeBook;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The recipe book page.
 */
public class RecipeBookFragment extends Fragment {

    public String TAG = "RecipeBook";

    private Context context;

    // get recipe book from MainActivity
    private final RecipeBook recipeBook = MainActivity.recipeBook;

    RecipeAdapter adapter;

    public RecipeBookFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        // get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recipe_book);
        Button addButton = view.findViewById(R.id.recipe_book_test_add_button);

        // set RecyclerView
        adapter = new RecipeAdapter(recipeBook.getRecipes());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        // add borderlines between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // add button launches RecipeAddFragment
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RecipeAddFragment recipeAddFragment = RecipeAddFragment.newInstance(recipeBook);

                new RecipeAddFragment().show(getChildFragmentManager(), RecipeAddFragment.TAG);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // real time updates of the recipeBook
        CollectionReference recipeBookRef = FirebaseFirestore.getInstance().collection("recipe book");
        recipeBookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                ArrayList<Recipe> newRecipes = new ArrayList<Recipe>();
                assert value != null;
                for (QueryDocumentSnapshot doc : value) {
//                    newRecipes.add(doc.toObject(Recipe.class));
                    Map<String, Object> data = doc.getData();
                    String title = (String) data.get("title");
                    int prepTime = (int) (long) data.get("prepTime");
                    int numServings = (int) (long) data.get("numServings");
                    String category = (String) data.get("category");
                    String comments = (String) data.get("comments");
                    Uri photo;
                    if (data.get("photo") != null) {
                        photo = Uri.parse((String) data.get("photo"));
                    } else {photo = null;}
                    ArrayList<HashMap> ingredientsData = (ArrayList<HashMap>) data.get("ingredients");
                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    for (HashMap ingredientData : ingredientsData) {
                        ingredients.add(new Ingredient(
                                (String) ingredientData.get("description"),
                                (String) ingredientData.get("bestBefore"),
                                (String) ingredientData.get("location"),
                                ((Double) ingredientData.get("amount")).floatValue(),
                                (String) ingredientData.get("unit"),
                                (String) ingredientData.get("category")
                        ));
                    }
                    Recipe newRecipe = new Recipe(
                            doc.getId(), title, prepTime, numServings, category, comments, photo, ingredients);
                    newRecipes.add(newRecipe);
                    Log.d(TAG, "recipe id: "+newRecipe.getId());
                }
                recipeBook.setRecipes(newRecipes);
                Log.d(TAG, "current recipe book: "+recipeBook);
                Log.d(TAG, "Current recipes: " + recipeBook.getRecipes());
                adapter.notifyDataSetChanged();
            }
        });
    }
}