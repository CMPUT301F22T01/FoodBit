package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.RecipeAdapter;
import com.CMPUT301F22T01.foodbit.models.RecipeBook;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeBookFragment extends Fragment {

    private static final String DATA = "data";
    String TAG = MainActivity.TAG;

    public static RecipeBook recipeBook;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    final DocumentReference recipeBookRef = FirebaseFirestore.getInstance().collection("user").document("recipe book");
    RecipeAdapter adapter;

    public RecipeBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeBook Parameter 1.
     * @return A new instance of fragment RecipeBookFragment.
     */
    public static RecipeBookFragment newInstance(RecipeBook recipeBook) {
        RecipeBookFragment fragment = new RecipeBookFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA, recipeBook);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            recipeBook = (RecipeBook) getArguments().getSerializable(DATA);
        }
        recipeBook = new RecipeBook();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recipe_book);
        Button testButton = view.findViewById(R.id.recipe_book_test_add_button);

        recipes.addAll(recipeBook.getRecipes());
        adapter = new RecipeAdapter(recipes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // add borderlines between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Recipe recipe = new Recipe("Burger", 20, 3, null, null, null, null);
//                recipeBook.add(recipe);
//                recipeBookRef.set(recipeBook).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "Data has been added successfully!");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // These are a method which gets executed if there’s any problem
//                        Log.d(TAG, "Data could not be added!" + e.toString());
//                    }
//                });
//            }
//        });
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Navigation.findNavController(view).navigate(R.id.action_fragment_recipe_book_to_recipeAddFragment);

                RecipeAddFragment recipeAddFragment = RecipeAddFragment.newInstance(recipeBook);
                recipeAddFragment.show(getChildFragmentManager(), RecipeAddFragment.TAG);
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                // The device is smaller, so show the fragment fullscreen
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                // For a little polish, specify a transition animation
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                // To make it fullscreen, use the 'content' root view as the container
//                // for the fragment, which is always the root view for the activity
//                transaction.replace(android.R.id.content, recipeAddFragment)
//                        .addToBackStack(null)
//                        .commit();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        recipeBookRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    recipes.clear();
                    recipeBook = snapshot.toObject(RecipeBook.class);
                    assert recipeBook != null;
                    recipes.addAll(recipeBook.getRecipes());
                    Log.d(TAG, String.valueOf(recipes));
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }});
    }
}