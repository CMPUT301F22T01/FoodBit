package com.CMPUT301F22T01.foodbit.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
public class IngredientTests {

    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    @Test
    public void addIngredientTest() throws InterruptedException {
//        Thread.sleep(2000);
//        ViewInteraction bottomNavigationItemView2 = onView(
//                allOf(withId(R.id.fragment_ingredient_list), withContentDescription("Ingredients"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.nav_bar),
//                                        0),
//                                0),
//                        isDisplayed()));
//        bottomNavigationItemView2.perform(click());
//
//        // click add button
//        ViewInteraction actionMenuItemView2 = onView(
//                allOf(withId(R.id.ingredient_add), withContentDescription("AddIngredient"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.action_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView2.perform(click());
//
//        // enter description, best before, location, amount, unit, and category
//        // enter description
//        ViewInteraction textInputEditText = onView(
//                allOf(withId(R.id.ingredient_add_edit_text_description),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.ingredient_add_text_layout_location),
//                                        0),
//                                0)));
//        textInputEditText.perform(scrollTo(), replaceText("Rice"), closeSoftKeyboard());
        Thread.sleep(5000);
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_ingredient_list), withContentDescription("Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.ingredient_add), withContentDescription("AddIngredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_description),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("Rice"), closeSoftKeyboard());

        // enter best before
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_best_before),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_best_before),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("2023-12-13"), closeSoftKeyboard());

        // enter location
        ViewInteraction materialAutoCompleteTextView1 = onView(
                allOf(withId(R.id.location_picker),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_location),
                                        0),
                                0)));
        materialAutoCompleteTextView1.perform(scrollTo(), replaceText("vehicle"), closeSoftKeyboard());

        // enter amount
        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_amount),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_amount),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        // enter unit
        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.unit_picker),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_unit),
                                        0),
                                0)));
        materialAutoCompleteTextView2.perform(scrollTo(), replaceText("cups"), closeSoftKeyboard());

        // enter category
        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.category_picker),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_category),
                                        0),
                                0)));
        materialAutoCompleteTextView3.perform(scrollTo(), replaceText("grains"), closeSoftKeyboard());


        // confirm adding the ingredient
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.ingredient_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_top_bar),
                                        2),
                                0)));
        actionMenuItemView3.perform(scrollTo(), click());

        // check if ingredient is added to the ingredient storage page
        // check for description
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView.check(matches(withText("Rice")));

        // check for amount
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.item_ingredient_amount), withText("2.0"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView2.check(matches(withText("2.0")));

        // check for unit
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("cups"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView3.check(matches(withText("cups")));

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
