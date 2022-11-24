package com.CMPUT301F22T01.foodbit;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MealPlanTest {

    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    public void addIngredient() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_ingredient_storage), withContentDescription("Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.ingredient_add), withContentDescription("AddIngredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_description),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_best_before),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_best_before),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("2022-01-01"), closeSoftKeyboard());

        //ViewInteraction textInputEditText6 = onView(
        //        allOf(withId(R.id.ingredient_add_edit_text_location),
        //                childAtPosition(
        //                        childAtPosition(
        //                                withId(R.id.ingredient_add_text_layout_location),
        //                                0),
        //                        0),
        //                isDisplayed()));
        //textInputEditText6.perform(replaceText("pantry"), closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_amount),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_amount),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("1"), closeSoftKeyboard());

        //ViewInteraction textInputEditText8 = onView(
        //        allOf(withId(R.id.ingredient_add_edit_text_unit),
        //                childAtPosition(
        //                        childAtPosition(
        //                                withId(R.id.ingredient_add_text_layout_unit),
        //                                0),
        //                        0),
        //                isDisplayed()));
        //textInputEditText8.perform(replaceText("kg"), closeSoftKeyboard());

        //ViewInteraction textInputEditText9 = onView(
        //        allOf(withId(R.id.ingredient_add_edit_text_category),
        //                childAtPosition(
        //                        childAtPosition(
        //                                withId(R.id.ingredient_add_text_layout_category),
        //                                0),
        //                        0),
        //                isDisplayed()));
        //textInputEditText9.perform(replaceText("grains"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.ingredient_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());
    }

    public void addRecipe() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.recipe_add), withContentDescription("AddRecipe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.recipe_add_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_title),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.recipe_add_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_prep_time),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.recipe_add_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_num_servings),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.recipe_detail_edit), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());
    }

    @Test
    public void MealPlanTest() {
        addRecipe();
        addIngredient();



        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.fragment_meal_plan), withContentDescription("Meal Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.meal_plan_add), withContentDescription("AddMealPlan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.meal_add_serving_size),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_layout_serving_size),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.meal_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView6.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.meal_plan_title), withText("1"),
                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
                        isDisplayed()));
        textView.check(matches(withText("1")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.meal_plan_date), withText("Nov 04/22"),
                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
                        isDisplayed()));
        textView2.check(matches(withText("Nov 04/22")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.meal_plan_servings), withText("1"),
                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
                        isDisplayed()));
        textView3.check(matches(withText("1")));
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
