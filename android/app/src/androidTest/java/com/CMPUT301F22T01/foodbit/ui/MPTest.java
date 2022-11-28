package com.CMPUT301F22T01.foodbit.ui;


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

/**
 * Please run this test on Pixel 6 pro.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MPTest {

    public void addRecipe() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipe Book"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.recipe_add), withContentDescription("AddRecipe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.recipe_input_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_title),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("recipe"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.recipe_input_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_prep_time),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("20"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.recipe_input_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_num_servings),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.recipe_input_done), withContentDescription("CONFIRM"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_top_bar),
                                        2),
                                0)));
        actionMenuItemView3.perform(scrollTo(), click());
    }

    public void deleteRecipe() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipe Book"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        hasDescendant(
                                hasDescendant(withText("recipe"))
                        )
                ));
        recyclerView.perform(actionOnItem(hasDescendant(withText("recipe")), click()));

        // click of delete recipe
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.recipe_detail_delete), withContentDescription("DELETE"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_detail_toolbar),
                                        2),
                                1),
                        isDisplayed()));
        actionMenuItemView3.perform(click());
    }

    public void addMealTest() throws InterruptedException {
        Thread.sleep(5000);

        // navigate to MealPlan
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_meal_plan), withContentDescription("Meal Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        // add a meal
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.meal_plan_add), withContentDescription("AddMealPlan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        // select the meal
        ViewInteraction checkableImageButton = onView(
                allOf(withId(com.google.android.material.R.id.text_input_end_icon), withContentDescription("Show dropdown menu"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.EndCompoundLayout")),
                                        1),
                                0),
                        isDisplayed()));
        checkableImageButton.perform(click());

        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.meal_picker),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_layout_meal),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("recipe"), closeSoftKeyboard());

        // choose serving size to be 4
        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.meal_add_serving_size),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_layout_serving_size),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("4"), closeSoftKeyboard());

        // add the meal
        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.meal_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());
    }

    public void viewMealListTest() {
        // check meal name displayed
        ViewInteraction textView = onView(
                allOf(withId(R.id.meal_plan_title), withText("recipe"),
                        isDisplayed()));
        textView.check(matches(withText("recipe")));

//        // check meal date displayed
//        ViewInteraction textView2 = onView(
//                allOf(withId(R.id.meal_plan_date), withText("Nov 28/22"),
//                        isDisplayed()));
//        textView2.check(matches(withText("Nov 28/22")));

        // check meal servings displayed
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.meal_plan_servings), withText("4"),
                        isDisplayed()));
        textView4.check(matches(withText("4")));
    }

    public void viewMealDetailsTest() {
        // click on the meal
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_meal_plan),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        // check meal name displayed
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.meal_detail_name), withText("recipe"),
                        isDisplayed()));
        textView5.check(matches(withText("recipe")));

        // check meal servings is displayed
        ViewInteraction textView6 = onView(
                allOf(withId(R.id.meal_detail_servings), withText("4 servings"),
                        isDisplayed()));
        textView6.check(matches(withText("4 servings")));

        // check for ingredients (none)
        ViewInteraction textView7 = onView(
                allOf(withId(R.id.meal_detail_ingredients_field), withText("Ingredients"),
                        isDisplayed()));
        textView7.check(matches(withText("Ingredients")));
    }

    public void editMealTest() {
        // edit the meal
        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.meal_detail_edit), withContentDescription("EDIT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_detail_toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView6.perform(click());

        // change servings from 4 to 8
        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.meal_add_serving_size), withText("4"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_layout_serving_size),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("8"));

        // add the edited meal
        ViewInteraction actionMenuItemView7 = onView(
                allOf(withId(R.id.meal_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView7.perform(click());

        // new servings displayed
        ViewInteraction textView11 = onView(
                allOf(withId(R.id.meal_detail_servings), withText("8 servings"), isDisplayed()));
        textView11.check(matches(withText("8 servings")));
    }

    public void discardMealTest() {
        // discard the meal
        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.button_meal_delete), withText("Discard"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                4),
                        isDisplayed()));
        materialButton6.perform(click());

        // check that meal is deleted
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.meal_plan_title), withText("recipe"),
                        isDisplayed()));
        textView2.check(doesNotExist());
    }

    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    @Test
    public void mPTest() throws InterruptedException {

        Thread.sleep(10000);

        addRecipe();

        addMealTest();

        viewMealListTest();

        viewMealDetailsTest();

        editMealTest();

        discardMealTest();

        deleteRecipe();
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
