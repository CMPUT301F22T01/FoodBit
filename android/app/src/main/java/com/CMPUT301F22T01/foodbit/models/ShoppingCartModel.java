//package com.CMPUT301F22T01.foodbit.models;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import java.util.ArrayList;
//import java.util.Set;
//import java.util.logging.Filter;
//
//public class ShoppingCartModel extends ViewModel {
//    private final MutableLiveData<Set<Filter>> filters = new MutableLiveData<>();
//
//    private final LiveData<ArrayList<Ingredient>> originalList = ...;
//    private final LiveData<ArrayList<Ingredient>> filteredList = ...;
//
//    public LiveData<ArrayList<Ingredient>> getFilteredList() {
//        return filteredList;
//    }
//
//    public LiveData<Set<Filter>> getFilters() {
//        return filters;
//    }
//
//    public void addFilter(Filter filter) { ... }
//
//    public void removeFilter(Filter filter) { ... }
//}
