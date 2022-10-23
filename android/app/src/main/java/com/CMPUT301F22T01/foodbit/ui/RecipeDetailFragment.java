package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.RecipeBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Recipe Detail Fragment";

    // Recipe Book
    RecipeBook recipeBook;
    Recipe recipe;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int position;

    // DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // UI
    Toolbar toolbar;

    public RecipeDetailFragment() {
        // Required empty public constructor
        getRecipe();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(String param1, String param2) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            position = getArguments().getInt("position");
            recipeBook = (RecipeBook) getArguments().getSerializable("recipeBook");
            Log.d("RecipeDetail", String.valueOf(position));
        }
        getRecipe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getTitle());

        return view;
    }

    private void getRecipe() {
//        DocumentReference docRef = db.collection("user").document("recipe book");
//        Log.d(TAG, "docRef created");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                recipeBook = documentSnapshot.toObject(RecipeBook.class);
//                assert recipeBook != null;
//                Log.d(TAG, recipeBook.toString());
//                recipe = recipeBook.get(position);
//                Log.d(TAG, recipe.toString());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "Data could not be retrieved!" + e.toString());
//            }
//        });
        recipe = RecipeBookFragment.recipeBook.get(position);
    }
}