package com.CMPUT301F22T01.foodbit.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.models.Recipe;
import com.CMPUT301F22T01.foodbit.models.RecipeAdapter;
import com.CMPUT301F22T01.foodbit.models.RecipeBook;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeBookFragment extends Fragment {

    private static final String DATA = "data";

    private RecipeBook recipeBook;

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
            // test: before integrating db
            recipeBook = new RecipeBook();
        }
        // test: before integrating db
        recipeBook = new RecipeBook();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recipe_book);

        // test data
        recipeBook.add(new Recipe("Sandwich", 10, 2, null, null, null, null));

        RecipeAdapter adapter = new RecipeAdapter(recipeBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        return view;
    }
}