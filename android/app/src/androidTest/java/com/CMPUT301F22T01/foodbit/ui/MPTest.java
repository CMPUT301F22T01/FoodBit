package com.CMPUT301F22T01.foodbit.ui;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.CMPUT301F22T01.foodbit.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MPTest {

    public void addRecipe() throws InterruptedException {

        Thread.sleep(5000);

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

    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    @Test
    public void mPTest() throws InterruptedException {


        Thread.sleep(10000);
//        addRecipe();

//        // navigate to MealPlan
//        ViewInteraction bottomNavigationItemView23 = onView(
//                allOf(withId(R.id.fragment_meal_plan), withContentDescription("Meal Plan"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.nav_bar),
//                                        0),
//                                2),
//                        isDisplayed()));
//        bottomNavigationItemView23.perform(click());
//
//        // try to add a meal
//        ViewInteraction actionMenuItemView43 = onView(
//                allOf(withId(R.id.meal_plan_add), withContentDescription("AddMealPlan"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.action_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView43.perform(click());

//        addRecipe();

        // navigate back to MealPlan
        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.fragment_meal_plan), withContentDescription("Meal Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        // now add a meal
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_meal_plan), withContentDescription("Meal Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.meal_plan_add), withContentDescription("AddMealPlan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

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

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.meal_add_serving_size),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_layout_serving_size),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.meal_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());

        // check meal name displayed
        ViewInteraction textView = onView(
                allOf(withId(R.id.meal_plan_title), withText("recipe"),
                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
                        isDisplayed()));
        textView.check(matches(withText("recipe")));

//        // check meal date displayed
//        ViewInteraction textView2 = onView(
//                allOf(withId(R.id.meal_plan_date), withText("Nov 28/22"),
//                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
//                        isDisplayed()));
//        textView2.check(matches(withText("Nov 28/22")));

        // check meal servings displayed
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.meal_plan_servings), withText("4"),
                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
                        isDisplayed()));
        textView4.check(matches(withText("4")));

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

//        ViewInteraction textView8 = onView(
//                allOf(withId(R.id.item_ingredient_description), withText("ingredient"),
//                        withParent(withParent(withId(R.id.meal_detail_ingredient_list))),
//                        isDisplayed()));
//        textView8.check(matches(withText("ingredient")));
//
//        ViewInteraction textView9 = onView(
//                allOf(withId(R.id.item_ingredient_amount), withText("8.0"),
//                        withParent(withParent(withId(R.id.meal_detail_ingredient_list))),
//                        isDisplayed()));
//        textView9.check(matches(withText("8.0")));
//
//        ViewInteraction textView10 = onView(
//                allOf(withId(R.id.item_ingredient_unit), withText("kg"),
//                        withParent(withParent(withId(R.id.meal_detail_ingredient_list))),
//                        isDisplayed()));
//        textView10.check(matches(withText("kg")));

//        // try to eat the meal
//        ViewInteraction materialButton5 = onView(
//                allOf(withId(R.id.button_meal_ate), withText("Ate"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("androidx.core.widget.NestedScrollView")),
//                                        0),
//                                5),
//                        isDisplayed()));
//        materialButton5.perform(click());

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

//        ViewInteraction textInputEditText9 = onView(
//                allOf(withId(R.id.meal_add_serving_size), withText("8"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.meal_add_layout_serving_size),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText9.perform(closeSoftKeyboard());

        // add the meal
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

//        ViewInteraction textView12 = onView(
//                allOf(withId(R.id.item_ingredient_amount), withText("16.0"),
//                        withParent(withParent(withId(R.id.meal_detail_ingredient_list))),
//                        isDisplayed()));
//        textView12.check(matches(withText("16.0")));

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

//        ViewInteraction recyclerView2 = onView(
//                allOf(withId(R.id.recyclerView_meal_plan),
//                        withParent(withParent(withId(R.id.nav_container))),
//                        isDisplayed()));
//        recyclerView2.check(doesNotExist());
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
