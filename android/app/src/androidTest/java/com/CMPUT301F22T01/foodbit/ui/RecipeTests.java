package com.CMPUT301F22T01.foodbit.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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

import com.CMPUT301F22T01.foodbit.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeTests {

    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    @Test
    public void addRecipeTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipe Book"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.fragment_meal_plan), withContentDescription("Meal Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipe Book"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        // click add button
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.recipe_add), withContentDescription("AddRecipe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        // enter title, category, preparation time, number of servings, and comments
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.recipe_input_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_title),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("Sandwich"), closeSoftKeyboard());
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.recipe_input_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_category),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("Lunch"), closeSoftKeyboard());
        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.recipe_input_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_prep_time),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("10"), closeSoftKeyboard());
        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.recipe_input_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_num_servings),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.recipe_input_edit_text_comments),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_comments),
                                        0),
                                0)));
        textInputEditText5.perform(scrollTo(), replaceText("A simple but delicious lunch."), closeSoftKeyboard());

//        // click add ingredient button
//        ViewInteraction actionMenuItemView2 = onView(
//                allOf(withId(R.id.recipe_add_ingredient_add), withContentDescription("Add an ingredient"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredients_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView2.perform(click());
//
        // enter ingredient info
//        ViewInteraction materialAutoCompleteTextView = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_ingredients),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_description),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView.perform(click());
//        ViewInteraction materialAutoCompleteTextView2 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_ingredients),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_description),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView2.perform(replaceText("Bread"), closeSoftKeyboard());
//        ViewInteraction textInputEditText6 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_edit_text_amount),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_amount),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText6.perform(replaceText("2"), closeSoftKeyboard());
//        ViewInteraction materialAutoCompleteTextView3 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_units),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_unit),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView3.perform(click());
//        ViewInteraction materialAutoCompleteTextView4 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_units),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_unit),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView4.perform(replaceText("slice"), closeSoftKeyboard());
//        ViewInteraction textInputEditText7 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_edit_text_category),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_category),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText7.perform(replaceText("grains"), closeSoftKeyboard());
//
//        // click confirm add ingredient button
//        ViewInteraction materialButton = onView(
//                allOf(withId(android.R.id.button1), withText("Add"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        materialButton.perform(scrollTo(), click());
//
//        // add another one
//        ViewInteraction actionMenuItemView3 = onView(
//                allOf(withId(R.id.recipe_add_ingredient_add), withContentDescription("Add an ingredient"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredients_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView3.perform(click());
//
//        ViewInteraction materialAutoCompleteTextView5 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_ingredients),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_description),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView5.perform(click());
//
//        ViewInteraction materialAutoCompleteTextView6 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_ingredients),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_description),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView6.perform(replaceText("egg"), closeSoftKeyboard());
//
//        ViewInteraction textInputEditText8 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_edit_text_amount),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_amount),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText8.perform(replaceText("1"), closeSoftKeyboard());
//
//        ViewInteraction materialAutoCompleteTextView7 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_units),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_unit),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView7.perform(click());
//
//        ViewInteraction materialAutoCompleteTextView8 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_auto_complete_units),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_unit),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialAutoCompleteTextView8.perform(replaceText("item"), closeSoftKeyboard());
//
//        ViewInteraction textInputEditText9 = onView(
//                allOf(withId(R.id.recipe_input_ingredient_add_edit_text_category),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.recipe_input_ingredient_add_layout_category),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText9.perform(replaceText("protein"), closeSoftKeyboard());
//
//        ViewInteraction materialButton2 = onView(
//                allOf(withId(android.R.id.button1), withText("Add"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        materialButton2.perform(scrollTo(), click());

        // confirm adding the recipe
        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.recipe_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_top_bar),
                                        2),
                                0)));
        actionMenuItemView4.perform(scrollTo(), click());

        // check if recipe is added to the recipe book page
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView.check(matches(withText("Sandwich")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.item_recipe_prep_time), withText("10 minutes"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView2.check(matches(withText("10 minutes")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_recipe_num_servings), withText("×1"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView3.check(matches(withText("×1")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.item_recipe_comments), withText("A simple but delicious lunch."),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView4.check(matches(withText("A simple but delicious lunch.")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
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
