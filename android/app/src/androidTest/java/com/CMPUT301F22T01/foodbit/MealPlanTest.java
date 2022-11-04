package com.CMPUT301F22T01.foodbit;


import static androidx.test.espresso.Espresso.onData;
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
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.CMPUT301F22T01.foodbit.ui.DatePickerFragment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MealPlanTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    public void addIngredient() {
        // click on Ingredients tab
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_ingredient_storage), withContentDescription("Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        // click button to add new ingredient
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.ingredient_storage_add_button), withText("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_container),
                                        0),
                                4),
                        isDisplayed()));
        materialButton.perform(click());

        // filling in fields
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_description),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("test ingredient"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_best_before),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_best_before),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("2022-12-01"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_location),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_location),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("pantry"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_amount),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_amount),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_unit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_unit),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("lbs"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_category),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("grains"), closeSoftKeyboard());

        // adding the ingredient
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.ingredient_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());
    }

    public void addRecipe() {
        // navigating to Recipes page
        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        // click button to add a new recipe
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.recipe_book_add_button), withText("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_container),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        // filling in fields
        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.recipe_add_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_title),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("test recipe"), closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.recipe_add_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_category),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("lunch"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.recipe_add_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_prep_time),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(replaceText("20"), closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.recipe_add_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_num_servings),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("3"), closeSoftKeyboard());

        // adding the recipe
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.recipe_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());
    }

    @Test
    public void mealPlanTest() {

        addIngredient();
//        addRecipe();

        // navigating to Meal Plan page
        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.fragment_meal_plan), withContentDescription("Meal Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        // click button to add a new meal
        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_meal_plan_add), withText("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());

        // TODO: datepicker
//        int year = 2022, month = 0, day = 1;
//        ViewInteraction datePicker = onView(
//                allOf(withClassName(is("DatePickerFragment"))));
//        datePicker.perform(PickerActions.setDate(year, month, day));
//
//        ViewInteraction datePicker = onView(
//                allOf(withClassName(is("DatePickerFragment")),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));

        // this will just choose the current date
        // technically it's ok if we take the current date to check
        // but it'll probably be better to manually set the date to something so it won't change
        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        // checking if displayed properly
        ViewInteraction textView = onView(
                allOf(withText("Add a Meal"),
                        withParent(allOf(withId(R.id.meal_add_top_bar),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Add a Meal")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.meal_select_textview), withText("Select a Meal:"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView2.check(matches(withText("Select a Meal:")));

        // click on spinner: should contain both "test recipe" and "test ingredient"
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.meal_spinner), withContentDescription("meal spinner"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction checkedTextView = onView(
                allOf(withId(android.R.id.text1), withText("test ingredient"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

//        ViewInteraction checkedTextView2 = onView(
//                allOf(withId(android.R.id.text1), withText("test recipe"),
//                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
//                        isDisplayed()));
//        checkedTextView2.check(matches(isDisplayed()));

//        // TODO: click on "test ingredient"
//        DataInteraction appCompatCheckedTextView = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(0);
//        appCompatCheckedTextView.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withSpinnerText("test ingredient"),
                        0))
                .atPosition(0);
        appCompatCheckedTextView.perform(click());

        // fill in serving size
        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.meal_add_serving_size),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_layout_serving_size),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText11.perform(replaceText("2"), closeSoftKeyboard());

        // adding the meal
        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.meal_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meal_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());

        // checking to see if main page is updated and displayed properly
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.meal_plan_title), withText("test ingredient"),
                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
                        isDisplayed()));
        textView3.check(matches(withText("test ingredient")));

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("MMM dd/yy");
        String strDate = sf.format(date);
//        ViewInteraction textView4 = onView(
//                allOf(withId(R.id.meal_plan_date), withText("Nov 10/22"),
//                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
//                        isDisplayed()));
//        textView4.check(matches(withText("Nov 10/22")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.meal_plan_servings), withText("2"),
                        withParent(withParent(withId(R.id.recyclerView_meal_plan))),
                        isDisplayed()));
        textView5.check(matches(withText("2")));
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
