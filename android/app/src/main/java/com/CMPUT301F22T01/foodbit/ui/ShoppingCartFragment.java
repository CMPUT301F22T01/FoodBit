package com.CMPUT301F22T01.foodbit.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F22T01.foodbit.MainActivity;
import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * provide a fragment show shopping cart ingredients
 */
public class ShoppingCartFragment extends Fragment {

    public String TAG = "shoppingCartFragment";

    private Context context;

    // get ingredient storage and meal plan controller from MainActivity
    private IngredientController ingredientController;
    private MealPlanController mealPlan;

    ShoppingCartAdapter adapter;

    public ShoppingCartFragment() {
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
        getActivity().setTitle("Shopping Cart");

        // This fragment has options menu for the action bar
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflating the menu resource file for this fragment
        inflater.inflate(R.menu.shoppingcart_actionbar, menu);
        menu.findItem(R.id.cart_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Actions performed by the Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            // Sorting the Shopping List accordingly
            case R.id.filter1:
                Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();
            case R.id.filter2:
                Toast.makeText(getActivity(), "Sorting Functionality Coming Soon", Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Compare the Ingredient of MealPlan with Ingredient Storage
     * Store new amount into new Shopping Ingredient list for adapter use
     * @param shoppingList
     * @param mealIngredient
     * @param storage
     * @param descriptionList
     */
    public void shoppingCart(ArrayList<Ingredient> shoppingList, ArrayList<Ingredient> mealIngredient,
                             ArrayList<Ingredient> storage, List descriptionList) {
        for (Ingredient ingredient: mealIngredient
             ) {
            shoppingList.add(ingredient);
        }
        for (Ingredient ingredient: shoppingList
        ) {
            int index = lookUpIngredientID(ingredient.getId(), storage);
            if (ingredient.getAmount() > storage.get(index).getAmount()){
                float amountNeed = ingredient.getAmount() - storage.get(index).getAmount();
                ingredient.update(storage.get(index));
                ingredient.setAmount(amountNeed);
            }
            else{
                shoppingList.remove(ingredient);
            }
        }
    }

    public int lookUpIngredientID(String ID, ArrayList<Ingredient> ingredList) {
        for (int i = 0; i< ingredList.size(); i++) {
            if (ID.equals(ingredList.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        // Get shoppingCart after calculating between meal plan and storage
        ingredientController = MainActivity.ingredientController;
        mealPlan = MainActivity.mealPlan;
        ArrayList<Ingredient> shoppingList = new ArrayList<>();
        ArrayList<Ingredient> mealIngredient = mealPlan.getAllIngredients();
        ArrayList<Ingredient> storage = ingredientController.getIngredients();
        List<String> descriptionList = ingredientController.getDescriptions();
        shoppingCart(shoppingList, mealIngredient, storage, descriptionList);


        //get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_shopping_cart);
        TextView bottomInfoView = view.findViewById(R.id.shopping_cart_item_info);

        //set recyclerView
        int mode = 0;
        adapter = new ShoppingCartAdapter(shoppingList, mode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        //update bottom cart info
        if (adapter.getItemCount() > 0) {
            bottomInfoView.setText("Your shopping cart has " + adapter.getItemCount() + " item(s)");
        }

        // add borderlines between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        getActivity().setTitle("Shopping Cart");
        return view;
    }

}