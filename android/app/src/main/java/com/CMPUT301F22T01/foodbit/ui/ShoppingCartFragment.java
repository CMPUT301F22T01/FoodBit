package com.CMPUT301F22T01.foodbit.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Iterator;
import java.util.ListIterator;

/**
 * provide a fragment show shopping cart ingredients
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartAdapter.OnItemClickListener, ShoppingCartPickedItemFragment.OnItemPickedUpListener{

    public String TAG = "shoppingCartFragment";

    private Context context;

    // get ingredient storage and meal plan controller from MainActivity
    private final IngredientController ingredientController = MainActivity.ingredientController;
    private final MealPlanController mealPlanController = MainActivity.mealPlanController;

    private final ArrayList<Ingredient> shoppingList = new ArrayList<>();
    private ArrayList<Ingredient> need;
    private ArrayList<Ingredient> have;
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
                //Sorting titles in ascending order
                descriptionASort();
                Toast.makeText(getActivity(), "Sorting(A-Z): Description", Toast.LENGTH_SHORT).show();
                break;
            case R.id.descriptionFilter2:
                //Sorting titles in descending order
                descriptionDSort();
                Toast.makeText(getActivity(), "Sorting(Z-A): Description", Toast.LENGTH_SHORT).show();
                break;
            case R.id.categoryFilter1:
                //Sorting categories in ascending order    
                categoryASort();      
                Toast.makeText(getActivity(), "Sorting(A-Z): Category", Toast.LENGTH_SHORT).show();
                break;
            case R.id.categoryFilter2:
                //Sorting categories in descending order
                categoryDSort();
                Toast.makeText(getActivity(), "Sorting(Z-A): Category", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    public void descriptionASort()
    {
        Collections.sort(shoppingList, Ingredient.nameAscending);
        adapter.notifyDataSetChanged();
    }
  
    public void descriptionDSort()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(shoppingList, Ingredient.nameAscending.reversed());
        }

        adapter.notifyDataSetChanged();
    }

    public void categoryASort()
    {
        Collections.sort(shoppingList, Ingredient.categoryAscending);
        adapter.notifyDataSetChanged();
    }
    public void categoryDSort()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(shoppingList, Ingredient.categoryAscending.reversed());
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * get a shopping list from meal ingredient and ingredient storage
     * @param need the amount that need for a ingredient in mealPlan
     * @param have the amount that have in the ingredientList
     * @return an arrayList of Ingredient
     */
    private ArrayList<Ingredient> getShoppingList(ArrayList<Ingredient> need, ArrayList<Ingredient> have) {
        ArrayList<Ingredient> shoppingList = new ArrayList<>();
        for (int i = 0; i < need.size(); i++){
            Ingredient cartItem = new Ingredient();
            cartItem.update(need.get(i));
            shoppingList.add(cartItem);
            Log.d("GetShoppingList", String.valueOf(cartItem.getDescription()));
        }
        ListIterator<Ingredient> iter = shoppingList.listIterator();
        while (iter.hasNext()){
            Ingredient currentIngredient = iter.next();

            int index = lookUpIngredientID(currentIngredient.getId(), have);
            String id = currentIngredient.getId();
            if (currentIngredient.getAmount() <= have.get(index).getAmount()) {
                iter.remove();
            } else {
                float amountNeeded = currentIngredient.getAmount() - have.get(index).getAmount();
                currentIngredient.setAmount(amountNeeded);
                currentIngredient.setCategory(ingredientController.getIngredientById(currentIngredient.getId()).getCategory());
                if(currentIngredient.getCategory() == null) {
                    currentIngredient.setCategory("missing");
                }
            }
        }
        return shoppingList;
    }

    /**
     * get ingredient index by look up ingredientID
     * @param ID ingredient
     * @param ingredList ingredientController
     * @return an index of a given ingredient
     */
    public int lookUpIngredientID(String ID, ArrayList<Ingredient> ingredList) {
        for (Ingredient ingredient :
                ingredList) {
            Log.d(TAG, "lookUpIngredientID: "+ingredient.getId());
        }
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
        need = mealPlanController.getAllIngredients();
        have = ingredientController.getIngredients();
        shoppingList.clear();
        shoppingList.addAll(getShoppingList(need, have));

        //get views
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_shopping_cart);
        TextView bottomInfoView = view.findViewById(R.id.shopping_cart_item_info);

        //set recyclerView
        adapter = new ShoppingCartAdapter(shoppingList);
        adapter.setItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        //update bottom cart info
//        if (adapter.getItemCount() > 0) {
//            bottomInfoView.setText("Your shopping cart has " + adapter.getItemCount() + " item(s)");
//        }


        // add borderlines between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        getActivity().setTitle("Shopping Cart");
        return view;
    }

    @Override
    public void onIngredientItemClick(View v, String id) {
        ShoppingCartPickedItemFragment.newInstance(id).show(getChildFragmentManager(), TAG);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemPickedUp() {
        shoppingList.clear();
        shoppingList.addAll(getShoppingList(need, have));
        adapter.notifyDataSetChanged();
    }
}