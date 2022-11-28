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
public class IngredientTests {

    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    @Test
    public void tests() throws InterruptedException {
        // adding in an ingredient
        addIngredientTest();
        // viewing the current ingredients
        viewListOfIngredientTest();
        // viewing details of an ingredient
        viewIngredientDetailTest();
        // add an apple ingredient
        addApple();
        // testing the sorting of ingredients
        sortIngredientTest();
        // deleting an ingredient
        deleteIngredientTest();
    }

    private void addIngredientTest() throws InterruptedException {
        Thread.sleep(5000);

        // got to ingredient page
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_ingredient_list), withContentDescription("Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        // click add button
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.ingredient_add), withContentDescription("AddIngredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        // enter description
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
    }

    private void viewListOfIngredientTest() {
        // check if ingredient is added to the ingredient storage page
        // check for description
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        withParent(hasDescendant(withText("Rice"))),
                        isDisplayed()));
        textView.check(matches(withText("Rice")));

        // check for amount
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.item_ingredient_amount), withText("2.0"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        withParent(hasDescendant(withText("Rice"))),
                        isDisplayed()));
        textView2.check(matches(withText("2.0")));

        // check for unit
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("cups"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        withParent(hasDescendant(withText("Rice"))),
                        isDisplayed()));
        textView3.check(matches(withText("cups")));
    }

    private void viewIngredientDetailTest() {
        // viewing details
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_ingredient_list),
                        hasDescendant(
                                hasDescendant(withText("Rice"))
                        )));
        recyclerView.perform(actionOnItem(hasDescendant(withText("Rice")), click()));

        // check description
        ViewInteraction textView = onView(
                allOf(withId(R.id.ingredient_detail_description), withText("Description: Rice"),
                        isDisplayed()));
        textView.check(matches(withText("Description: Rice")));

        // check best before
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.ingredient_detail_best_before), withText("Best Before Date: 2023-12-13"),
                        isDisplayed()));
        textView2.check(matches(withText("Best Before Date: 2023-12-13")));

        // check location
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.ingredient_detail_location), withText("Location: vehicle"),
                        isDisplayed()));
        textView3.check(matches(withText("Location: vehicle")));

        // check amount
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.ingredient_detail_amount), withText("Amount: 2.0"),
                        isDisplayed()));
        textView4.check(matches(withText("Amount: 2.0")));

        // check unit
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.ingredient_detail_unit), withText("Unit: cups"),
                        isDisplayed()));
        textView5.check(matches(withText("Unit: cups")));

        // check category
        ViewInteraction textView6 = onView(
                allOf(withId(R.id.ingredient_detail_category), withText("Category: grains"),
                        isDisplayed()));
        textView6.check(matches(withText("Category: grains")));

        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.ingredient_detail_toolbar),
                                        childAtPosition(
                                                allOf(withId(R.id.ingredient_detail_tool_bar), withContentDescription("Rice")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

    private void addApple() {
        // adding ingredient apple
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.ingredient_add), withContentDescription("AddIngredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        // enter description
        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_description),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), replaceText("Apple"), closeSoftKeyboard());

        // enter best before
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_best_before),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_best_before),
                                        0),
                                0)));
        textInputEditText5.perform(scrollTo(), replaceText("2024-01-01"), closeSoftKeyboard());

        // enter location
        ViewInteraction materialAutoCompleteTextView7 = onView(
                allOf(withId(R.id.location_picker),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_location),
                                        0),
                                0)));
        materialAutoCompleteTextView7.perform(scrollTo(), replaceText("pantry"), closeSoftKeyboard());

        // enter amount
        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.ingredient_add_edit_text_amount),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_amount),
                                        0),
                                0)));
        textInputEditText6.perform(scrollTo(), replaceText("3"), closeSoftKeyboard());

        // enter unit
        ViewInteraction materialAutoCompleteTextView8 = onView(
                allOf(withId(R.id.unit_picker),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_unit),
                                        0),
                                0)));
        materialAutoCompleteTextView8.perform(scrollTo(), replaceText("item"), closeSoftKeyboard());

        // enter category
        ViewInteraction materialAutoCompleteTextView9 = onView(
                allOf(withId(R.id.category_picker),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_text_layout_category),
                                        0),
                                0)));
        materialAutoCompleteTextView9.perform(scrollTo(), replaceText("fruits"), closeSoftKeyboard());

        // finish adding
        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.ingredient_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_add_top_bar),
                                        2),
                                0)));
        actionMenuItemView4.perform(scrollTo(), click());
    }

    private void sortIngredientTest() {
        // sorting ingredients
        ViewInteraction overflowMenuButton = onView(
                allOf(
                        withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        // sorting by description
        ViewInteraction materialTextView = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Description"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        // sort in ascending order
        ViewInteraction materialTextView2 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Ascending(A-Z)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        // check ordering
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView.check(matches(withText("Apple")));
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView2.check(matches(withText("Rice")));

        // sort ingredients
        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        // sort by description
        ViewInteraction materialTextView3 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Description"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        // sort by descending
        ViewInteraction materialTextView4 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Descending(Z-A)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        // check sorting order
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView3.check(matches(withText("Rice")));
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView4.check(matches(withText("Apple")));

        // ingredient sorting
        ViewInteraction overflowMenuButton3 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton3.perform(click());

        // sort by best before
        ViewInteraction materialTextView5 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Best Before Date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView5.perform(click());

        // sort in expiring soon order
        ViewInteraction materialTextView6 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Expiring Soon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView6.perform(click());

        // check sorting order
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView5.check(matches(withText("Rice")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView6.check(matches(withText("Apple")));

        // ingredient sorting
        ViewInteraction overflowMenuButton4 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton4.perform(click());

        // sort by best before
        ViewInteraction materialTextView7 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Best Before Date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView7.perform(click());

        // sort in expiring later order
        ViewInteraction materialTextView8 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Expiring Later"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView8.perform(click());

        // check sorting order
        ViewInteraction textView7 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView7.check(matches(withText("Apple")));
        ViewInteraction textView8 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView8.check(matches(withText("Rice")));

        // sorting ingredients
        ViewInteraction overflowMenuButton5 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton5.perform(click());

        // sorting by location
        ViewInteraction materialTextView9 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView9.perform(click());

        // sort ascending
        ViewInteraction materialTextView10 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Ascending(A-Z)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView10.perform(click());

        // check sort order
        ViewInteraction textView9 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView9.check(matches(withText("Apple")));
        ViewInteraction textView10 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView10.check(matches(withText("Rice")));

        // sorting ingredients
        ViewInteraction overflowMenuButton6 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton6.perform(click());

        // sort by location
        ViewInteraction materialTextView11 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView11.perform(click());

        // sort descending
        ViewInteraction materialTextView12 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Descending(Z-A)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView12.perform(click());

        // check sort order
        ViewInteraction textView11 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView11.check(matches(withText("Rice")));
        ViewInteraction textView12 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView12.check(matches(withText("Apple")));

        // ingredient sorting
        ViewInteraction overflowMenuButton7 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton7.perform(click());

        // sort by category
        ViewInteraction materialTextView13 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Category"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView13.perform(click());

        // sort ascending
        ViewInteraction materialTextView14 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Ascending(A-Z)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView14.perform(click());

        // check sort order
        ViewInteraction textView13 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView13.check(matches(withText("Apple")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView14.check(matches(withText("Rice")));

        // sorting ingredients
        ViewInteraction overflowMenuButton8 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton8.perform(click());

        // sort by category
        ViewInteraction materialTextView15 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Category"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView15.perform(click());

        // sort descending
        ViewInteraction materialTextView16 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Descending(Z-A)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView16.perform(click());

        // check sort order
        ViewInteraction textView15 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView15.check(matches(withText("Rice")));
        ViewInteraction textView16 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Apple"),
                        withParent(withParent(withId(R.id.recyclerView_ingredient_list))),
                        isDisplayed()));
        textView16.check(matches(withText("Apple")));
    }

    private void deleteIngredientTest() {
        // click on item
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_ingredient_list),
                        hasDescendant(
                                hasDescendant(withText("Rice"))
                        )
                ));
        recyclerView.perform(actionOnItem(hasDescendant(withText("Rice")), click()));

        // click of delete ingredient
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.ingredient_detail_delete), withContentDescription("DELETE"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_detail_toolbar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        // check if item no longer exists
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_ingredient_description), withText("Rice"),
                        isDisplayed()));
        textView.check(doesNotExist());
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
