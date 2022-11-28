package com.CMPUT301F22T01.foodbit.ui;

import android.content.Context;
import android.os.Build;
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

import com.CMPUT301F22T01.foodbit.R;
import com.CMPUT301F22T01.foodbit.controllers.IngredientController;
import com.CMPUT301F22T01.foodbit.controllers.MealPlanController;
import com.CMPUT301F22T01.foodbit.models.Ingredient;

import java.util.ArrayList;
import java.util.Collections;

/**
 * provide a fragment show shopping cart ingredients
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartAdapter.OnItemClickListener{

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
            case R.id.descriptionFilter1:
                descriptionASort(getView());
                Toast.makeText(getActivity(), "Sorting(A-Z): Description", Toast.LENGTH_SHORT).show();
                break;
            case R.id.descriptionFilter2:
                descriptionDSort(getView());
                Toast.makeText(getActivity(), "Sorting(Z-A): Description", Toast.LENGTH_SHORT).show();
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
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_shopping_cart);
        Collections.sort(ingredientController.getIngredients(), Ingredient.nameAscending);
        adapter.notifyDataSetChanged();
    }
    public void descriptionDSort(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_shopping_cart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(ingredientController.getIngredients(), Ingredient.nameAscending.reversed());
        }

        adapter.notifyDataSetChanged();
    }
    public void categoryASort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_shopping_cart);
        Collections.sort(ingredientController.getIngredients(), Ingredient.categoryAscending);
        adapter.notifyDataSetChanged();
    }
    public void categoryDSort(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_shopping_cart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(ingredientController.getIngredients(), Ingredient.categoryAscending.reversed());
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Compare the Ingredient of MealPlan with Ingredient Storage
     * Store new amount into new Shopping Ingredient list for adapter use
     * @param shoppingList
     * @param mealIngredient
     * @param storage
     */
    public void shoppingCart(ArrayList<Ingredient> shoppingList, ArrayList<Ingredient> mealIngredient,
                             ArrayList<Ingredient> storage) {
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


    public ArrayList<Ingredient> shoppingList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        // Get shoppingCart after calculating between meal plan and storage
        ingredientController = MainActivity.ingredientController;
        mealPlan = MainActivity.mealPlanController;
//        ArrayList<Ingredient> mealIngredient = mealPlanController.getAllIngredients();
        ArrayList<Ingredient> storage = ingredientController.getIngredients();
//        shoppingCart(shoppingList, mealIngredient, storage);

        //get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_shopping_cart);
        TextView bottomInfoView = view.findViewById(R.id.shopping_cart_item_info);

        //set recyclerView
        adapter = new ShoppingCartAdapter(storage);
        adapter.setItemClickListener(this);
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

    @Override
    public void onIngredientItemClick(View v, int position) {
        ShoppingCartPickedItemFragment.newInstance(position).show(getChildFragmentManager(), TAG);
    }
}