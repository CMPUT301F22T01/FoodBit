package com.CMPUT301F22T01.foodbit;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UITest {

//    @Rule
//    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
//            new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    @Test
    // The following tests are meant to be run together
    public void recipeTests() throws InterruptedException {
        // Test adding a recipe (US 02.01.01.01, US 02.07.01)
        recipeAddTest();
        // Test viewing details of a recipe (US 02.04.01.01)
        recipeDetailTest();
        // Test deleting a recipe (US 02.06.01)
        recipeDeleteTest();
    }

    public void recipeAddTest() throws InterruptedException {
        // click on the recipes icon on the bottom navigation bar
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        Thread.sleep(2000);

        // check if navigated to the recipe book screen
        ViewInteraction textView = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_large_label_view), withText("Recipes"),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                                withParent(allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipes"))))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        // click on add button on recipe book screen
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.recipe_book_add_button), withText("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_container),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        // click on close button on recipe add screen
        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.recipe_add_top_bar),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // check if addition is aborted
        ViewInteraction textView24 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView24.check(doesNotExist());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.recipe_book_add_button), withText("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_container),
                                        0),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        // click on add button (with a tick icon on top-right) on recipe add screen
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.recipe_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        // check if the correct error messages are displayed
        ViewInteraction textView2 = onView(
                allOf(withId(com.google.android.material.R.id.textinput_error), withText("Required"),
                        withParent(withParent(withParent(withId(R.id.recipe_add_text_layout_title)))),
                        isDisplayed()));
        textView2.check(matches(withText("Required")));
        ViewInteraction textView3 = onView(
                allOf(withId(com.google.android.material.R.id.textinput_error), withText("Required"),
                        withParent(withParent(withParent(withId(R.id.recipe_add_text_layout_prep_time)))),
                        isDisplayed()));
        textView3.check(matches(withText("Required")));
        ViewInteraction textView4 = onView(
                allOf(withId(com.google.android.material.R.id.textinput_error), withText("Required"),
                        withParent(withParent(withParent(withId(R.id.recipe_add_text_layout_num_servings)))),
                        isDisplayed()));
        textView4.check(matches(withText("Required")));

        // enter a title
        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.recipe_add_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_title),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText12.perform(replaceText("Sandwich"), closeSoftKeyboard());

        // enter zeros in the preparation time text field
        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.recipe_add_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_prep_time),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText13.perform(replaceText("0000"), closeSoftKeyboard());

        // check if zeros is not entered (check function of removing starting zeros)
        ViewInteraction editText = onView(
                allOf(withId(R.id.recipe_add_edit_text_prep_time),
                        withParent(withParent(withId(R.id.recipe_add_text_layout_prep_time))),
                        isDisplayed()));
        editText.check(matches(withText("")));

        // enter a oversize preparation time
        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.recipe_add_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_prep_time),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText15.perform(replaceText("481"), closeSoftKeyboard());

        //check if the correct error message is displayed
        ViewInteraction textView5 = onView(
                allOf(withId(com.google.android.material.R.id.textinput_error), withText("Maximum time exceeded (480 minutes)"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView5.check(matches(withText("Maximum time exceeded (480 minutes)")));

        // enter a category
        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.recipe_add_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_category),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText16.perform(replaceText("Lunch"), closeSoftKeyboard());

        // enter a preparation time
        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.recipe_add_edit_text_prep_time), withText("481"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_prep_time),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText17.perform(replaceText("10"));

        // enter zeros in the preparation time text field
        ViewInteraction textInputEditText19 = onView(
                allOf(withId(R.id.recipe_add_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_num_servings),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText19.perform(replaceText("00000"), closeSoftKeyboard());

        // check if zeros is not entered (check function of removing starting zeros)
        ViewInteraction editText2 = onView(
                allOf(withId(R.id.recipe_add_edit_text_num_servings),
                        withParent(withParent(withId(R.id.recipe_add_text_layout_num_servings))),
                        isDisplayed()));
        editText2.check(matches(withText("")));

        // enter a number of servings
        ViewInteraction textInputEditText21 = onView(
                allOf(withId(R.id.recipe_add_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_num_servings),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText21.perform(replaceText("1"), closeSoftKeyboard());

        // enter comments
        ViewInteraction textInputEditText22 = onView(
                allOf(withId(R.id.recipe_add_edit_text_comments),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_comments),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText22.perform(replaceText("A simple lunch."), closeSoftKeyboard());

        // click on add button in ingredient list
        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add), withContentDescription("Add an ingredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredients_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView5.perform(click());

        // click on cancel button
        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button3), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        materialButton6.perform(scrollTo(), click());

        // check if addition is aborted
        ViewInteraction textView25 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Bread"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView25.check(doesNotExist());

        // click on the add button again
        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add), withContentDescription("Add an ingredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredients_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView6.perform(click());

        ViewInteraction textInputEditText24 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_description),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText24.perform(click());

        // enter a description
        ViewInteraction textInputEditText25 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_description),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText25.perform(replaceText("Bread"), closeSoftKeyboard());

        // enter an amount
        ViewInteraction textInputEditText26 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_amount),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_amount),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText26.perform(replaceText("2"), closeSoftKeyboard());

        // enter a unit
        ViewInteraction textInputEditText27 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_unit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_unit),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText27.perform(replaceText("slice"), closeSoftKeyboard());

        // click add
        ViewInteraction materialButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton8.perform(scrollTo(), click());

        // check if ingredient is displayed
        ViewInteraction textView10 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Bread"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView10.check(matches(withText("Bread")));
        ViewInteraction textView11 = onView(
                allOf(withId(R.id.item_ingredient_amount), withText("2.0"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView11.check(matches(withText("2.0")));
        ViewInteraction textView12 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("slice"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView12.check(matches(withText("slice")));

        // click add button to add another ingredient
        ViewInteraction actionMenuItemView7 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add), withContentDescription("Add an ingredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredients_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView7.perform(click());

        // enter a description
        ViewInteraction textInputEditText28 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_description),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText28.perform(replaceText("egg"), closeSoftKeyboard());

        // enter an amount
        ViewInteraction textInputEditText29 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_amount),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_amount),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText29.perform(replaceText("1"), closeSoftKeyboard());

        // enter a unit
        ViewInteraction textInputEditText30 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_unit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_unit),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText30.perform(replaceText("item"), closeSoftKeyboard());

        // enter a category
        ViewInteraction textInputEditText31 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_category),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText31.perform(replaceText("fridge"), closeSoftKeyboard());

        // add the ingredient
        ViewInteraction materialButton9 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton9.perform(scrollTo(), click());

        // check if ingredient is displayed
        ViewInteraction textView14 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("egg"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView14.check(matches(withText("egg")));
        ViewInteraction textView15 = onView(
                allOf(withId(R.id.item_ingredient_amount), withText("1.0"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView15.check(matches(withText("1.0")));
        ViewInteraction textView16 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("item"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView16.check(matches(withText("item")));

        // click on ingredient egg
        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.recipe_add_ingredients_list),
                        childAtPosition(
                                withId(R.id.recipe_add_ingredients_layout),
                                1)));
        recyclerView4.perform(actionOnItemAtPosition(1, click()));

        // check if the correct information is in the text fields
        ViewInteraction editText3 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_description), withText("egg"),
                        withParent(withParent(withId(R.id.recipe_add_ingredient_add_layout_description))),
                        isDisplayed()));
        editText3.check(matches(withText("egg")));
        ViewInteraction editText4 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_amount), withText("1.0"),
                        withParent(withParent(withId(R.id.recipe_add_ingredient_add_layout_amount))),
                        isDisplayed()));
        editText4.check(matches(withText("1.0")));
        ViewInteraction editText5 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_unit), withText("item"),
                        withParent(withParent(withId(R.id.recipe_add_ingredient_add_layout_unit))),
                        isDisplayed()));
        editText5.check(matches(withText("item")));
        ViewInteraction editText6 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_category), withText("fridge"),
                        withParent(withParent(withId(R.id.recipe_add_ingredient_add_layout_category))),
                        isDisplayed()));
        editText6.check(matches(withText("fridge")));

        // check if the buttons are displayed correctly
        ViewInteraction button = onView(
                allOf(withId(android.R.id.button1), withText("UPDATE"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
        ViewInteraction button2 = onView(
                allOf(withId(android.R.id.button2), withText("DELETE"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));
        ViewInteraction button3 = onView(
                allOf(withId(android.R.id.button3), withText("CANCEL"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        // click on delete
        ViewInteraction materialButton10 = onView(
                allOf(withId(android.R.id.button2), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        materialButton10.perform(scrollTo(), click());

        // check if ingredient is deleted
        ViewInteraction textView18 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("egg"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView18.check(doesNotExist());

        // add the recipe
        ViewInteraction actionMenuItemView8 = onView(
                allOf(withId(R.id.recipe_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView8.perform(click());

        // check if the recipe appears on the recipe book screen
        ViewInteraction textView19 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView19.check(matches(withText("Sandwich")));
        ViewInteraction textView20 = onView(
                allOf(withId(R.id.item_recipe_prep_time), withText("10 minutes"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        withParent(hasDescendant(withText("Sandwich"))),
                        isDisplayed()));
        textView20.check(matches(withText("10 minutes")));
        ViewInteraction textView21 = onView(
                allOf(withId(R.id.item_recipe_num_servings), withText("×1"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        withParent(hasDescendant(withText("Sandwich"))),
                        isDisplayed()));
        textView21.check(matches(withText("×1")));
        ViewInteraction textView22 = onView(
                allOf(withId(R.id.item_recipe_comments), withText("A simple lunch."),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        withParent(hasDescendant(withText("Sandwich"))),
                        isDisplayed()));
        textView22.check(matches(withText("A simple lunch.")));
    }

    public void recipeDetailTest() throws InterruptedException {
        // click on the recipe
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        Thread.sleep(2000);
        recyclerView.perform(actionOnItem(hasDescendant(withText("Sandwich")),click()));

        // check title
        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.recipe_detail_toolbar),
                        withParent(allOf(withContentDescription("Sandwich"),
                                withParent(withId(R.id.appbar)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
        viewGroup.check(matches(withParent(withContentDescription("Sandwich"))));

        // check preparation time
        ViewInteraction textView = onView(
                allOf(withId(R.id.recipe_detail_prep_time), withText("10 minutes"),
                        isDisplayed()));
        textView.check(matches(withText("10 minutes")));

        // check number of servings
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.recipe_detail_num_servings), withText("1 serving"),
                        isDisplayed()));
        textView2.check(matches(withText("1 serving")));

        // check category
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.recipe_detail_category_content), withText("Lunch"),
                        isDisplayed()));
        textView3.check(matches(withText("Lunch")));

        // check comments
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.recipe_detail_comments_content), withText("A simple lunch."),
                        isDisplayed()));
        textView4.check(matches(withText("A simple lunch.")));

        // check ingredients
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Bread"),
                        withParent(withParent(withId(R.id.recipe_detail_ingredient_list))),
                        isDisplayed()));
        textView5.check(matches(withText("Bread")));
        ViewInteraction textView6 = onView(
                allOf(withId(R.id.item_ingredient_amount), withText("2.0"),
                        withParent(withParent(withId(R.id.recipe_detail_ingredient_list))),
                        isDisplayed()));
        textView6.check(matches(withText("2.0")));
        ViewInteraction textView7 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("slice"),
                        withParent(withParent(withId(R.id.recipe_detail_ingredient_list))),
                        isDisplayed()));
        textView7.check(matches(withText("slice")));

        // click back button
        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.recipe_detail_toolbar),
                                        childAtPosition(
                                                withContentDescription("Sandwich"),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

//        // check item information in the recipe book screen
//        ViewInteraction textView9 = onView(
//                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
//                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
//                        isDisplayed()));
//        textView9.check(matches(withText("Sandwich")));
//        ViewInteraction textView10 = onView(
//                allOf(withId(R.id.item_recipe_prep_time), withText("10 minutes"),
//                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
//                        isDisplayed()));
//        textView10.check(matches(withText("10 minutes")));
//        ViewInteraction textView11 = onView(
//                allOf(withId(R.id.item_recipe_num_servings), withText("×1"),
//                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
//                        isDisplayed()));
//        textView11.check(matches(withText("×1")));
//        ViewInteraction textView12 = onView(
//                allOf(withId(R.id.item_recipe_comments), withText("A simple lunch."),
//                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
//                        isDisplayed()));
//        textView12.check(matches(withText("A simple lunch.")));
    }

    public void recipeDeleteTest() {
        // check if recipe item exists
        ViewInteraction viewGroup = onView(
                allOf(withParent(allOf(withId(R.id.recyclerView_recipe_book),
                                withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))),
                        hasDescendant(withText("Sandwich")),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        // click on the recipe item
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItem(hasDescendant(withText("Sandwich")),click()));

        // click on the delete button
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.recipe_detail_temp_delete), withText("delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                8),
                        isDisplayed()));
        materialButton.perform(click());

        // check if recipe is deleted by checking if the recipe item no longer exists in the recipe book screen
        ViewInteraction viewGroup2 = onView(
                allOf(withParent(allOf(withId(R.id.recyclerView_recipe_book),
                                withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))),
                        hasDescendant(withText("Sandwich")),
                        isDisplayed()));
        viewGroup2.check(doesNotExist());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
