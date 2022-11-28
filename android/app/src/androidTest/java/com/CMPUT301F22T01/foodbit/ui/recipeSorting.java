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
public class recipeSorting {

    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    @Test
    public void recipeSorting() {
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
        textInputEditText5.perform(scrollTo(), replaceText("A simple lunch."), closeSoftKeyboard());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.recipe_input_done), withContentDescription("CONFIRM"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_top_bar),
                                        2),
                                0)));
        actionMenuItemView2.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.recipe_add), withContentDescription("AddRecipe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.recipe_input_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_title),
                                        0),
                                0)));
        textInputEditText6.perform(scrollTo(), replaceText("Burger"), closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.recipe_input_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_category),
                                        0),
                                0)));
        textInputEditText7.perform(scrollTo(), replaceText("Dinner"), closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.recipe_input_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_prep_time),
                                        0),
                                0)));
        textInputEditText8.perform(scrollTo(), replaceText("20"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.recipe_input_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_num_servings),
                                        0),
                                0)));
        textInputEditText9.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.recipe_input_edit_text_comments),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_comments),
                                        0),
                                0)));
        textInputEditText10.perform(scrollTo(), replaceText("Not a simple lunch."), closeSoftKeyboard());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.recipe_input_done), withContentDescription("CONFIRM"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_top_bar),
                                        2),
                                0)));
        actionMenuItemView4.perform(scrollTo(), click());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Title"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Ascending(A-Z)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView.check(matches(withText("Burger")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView2.check(matches(withText("Sandwich")));

        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Title"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Descending(Z-A)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView3.check(matches(withText("Sandwich")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView4.check(matches(withText("Burger")));

        ViewInteraction overflowMenuButton3 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton3.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Preparation Time"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Quick-Long"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView5.check(matches(withText("Sandwich")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView6.check(matches(withText("Burger")));

        ViewInteraction overflowMenuButton4 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton4.perform(click());

        ViewInteraction materialTextView7 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Preparation Time"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView7.perform(click());

        ViewInteraction materialTextView8 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Long-Quick"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView8.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView7.check(matches(withText("Burger")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView8.check(matches(withText("Sandwich")));

        ViewInteraction overflowMenuButton5 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton5.perform(click());

        ViewInteraction materialTextView9 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Number of Servings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView9.perform(click());

        ViewInteraction materialTextView10 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Low-High"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView10.perform(click());

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView9.check(matches(withText("Sandwich")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView10.check(matches(withText("Burger")));

        ViewInteraction overflowMenuButton6 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton6.perform(click());

        ViewInteraction materialTextView11 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Number of Servings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView11.perform(click());

        ViewInteraction materialTextView12 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("High-Low"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView12.perform(click());

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView11.check(matches(withText("Burger")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView12.check(matches(withText("Sandwich")));

        ViewInteraction overflowMenuButton7 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton7.perform(click());

        ViewInteraction materialTextView13 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Category"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView13.perform(click());

        ViewInteraction materialTextView14 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Ascending(A-Z)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView14.perform(click());

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView13.check(matches(withText("Burger")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView14.check(matches(withText("Sandwich")));

        ViewInteraction overflowMenuButton8 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        overflowMenuButton8.perform(click());

        ViewInteraction materialTextView15 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Sort by: Category"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView15.perform(click());

        ViewInteraction materialTextView16 = onView(
                allOf(withId(androidx.recyclerview.R.id.title), withText("Descending(Z-A)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.constraintlayout.widget.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView16.perform(click());

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView15.check(matches(withText("Sandwich")));

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.item_recipe_title), withText("Burger"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView16.check(matches(withText("Burger")));
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
