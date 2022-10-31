package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.CMPUT301F22T01.foodbit.ui.IngredientAdapter;
import com.CMPUT301F22T01.foodbit.controllers.IngredientStorage;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class IngredientStorageFragment extends Fragment {

    public String TAG = "IngredientStorage";

    private Context context;

    private final IngredientStorage ingredientStorage = MainActivity.ingredientStorage;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_storage, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ingredient_storage);
        Button addButton = view.findViewById(R.id.ingredient_storage_add_button);

        int mode = 0;
        adapter = new IngredientAdapter(ingredientStorage.getIngredients(), mode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IngredientAddFragment().show(getChildFragmentManager(), IngredientAddFragment.TAG);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        CollectionReference ingredientStorageRef = FirebaseFirestore.getInstance().collection("Ingredient List");
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
                    //if (description != null){
                        //newIngredient.add(new Ingredient(description, bestBefore, location, amount, unit, category));
                    //}
                    //ingredientStorage.update(newIngredient);
                    //Log.d(TAG, "Current ingredients: " + ingredientStorage.getIngredients());
                    //adapter.notifyDataSetChanged();


