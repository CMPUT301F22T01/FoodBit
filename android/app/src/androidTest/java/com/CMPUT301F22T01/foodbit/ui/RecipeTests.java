package com.CMPUT301F22T01.foodbit.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

/**
 * These are the test regarding user stories of recipes. Unfortunately, due to the nature of Android Espresso tests,
 * the instrumented test gets stuck when auto complete text fields are present in a dialog fragment (presumably with multiple fragments on the backstack).
 * More specifically, after this dialog fragment is "drawn", the test will fail to go back to idle state, which prevents the test to move onto the next step.
 * Therefore, requirements that involves manipulating ingredients in a recipe (adding ingredients to, editing ingredients in, and deleting ingredients from a recipe)
 * will not be possible to include in the tests.
 * <p>
 * Similarly, since Android espresso is not entitled to operate in the camera app, adding a photo to, editing a photo in, and deleting a photo from a recipe
 * will not be possible to include in the test.
 * </p><p>
 * However, those requirements are guaranteed to work properly.
 * </p>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeTests {
    @Rule
    public ActivityScenarioRule<LoadingPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoadingPageActivity.class);

    /**
     * This set of tests include five tests, set to run in this particular order.
     */
    @Test
    public void tests() throws InterruptedException {
        // add a recipe
        addRecipeTest();

        // view the recipe's detail
        viewRecipeDetailTest();

        // edit the recipe
        editRecipeTest();

        // see a list of recipes
        seeListOfRecipesTest();

        // delete the recipe
        deleteRecipeTest();
    }

    private void addRecipeTest() throws InterruptedException {

        Thread.sleep(10000);
        // go to recipe book page
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipe Book"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        // click add recipe button
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.recipe_add), withContentDescription("AddRecipe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        // enter recipe title
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.recipe_input_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_title),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("Sandwich"), closeSoftKeyboard());

        // enter recipe category
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.recipe_input_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_category),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("Lunch"), closeSoftKeyboard());

        // enter recipe preparation time
        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.recipe_input_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_prep_time),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("10"), closeSoftKeyboard());

        // enter recipe number of servings
        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.recipe_input_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_num_servings),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        // enter recipe comments
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.recipe_input_edit_text_comments),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_comments),
                                        0),
                                0)));
        textInputEditText5.perform(scrollTo(), replaceText("A simple lunch."), closeSoftKeyboard());

        // confirm adding the recipe
        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.recipe_input_done), withContentDescription("CONFIRM"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_top_bar),
                                        2),
                                0)));
        actionMenuItemView2.perform(scrollTo(), click());
    }

    private void viewRecipeDetailTest() {
        // click on item Sandwich in the recipe book
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        hasDescendant(
                                hasDescendant(withText("Sandwich"))
                        )
                ));
        recyclerView.perform(actionOnItem(hasDescendant(withText("Sandwich")), click()));

        // check recipe preparation time
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.recipe_detail_prep_time), withText("10 minutes"),
                        isDisplayed()));
        textView5.check(matches(withText("10 minutes")));

        // check recipe number of servings
        ViewInteraction textView = onView(
                allOf(withId(R.id.recipe_detail_num_servings), withText("1 serving"),
                        isDisplayed()));
        textView.check(matches(withText("1 serving")));

        // check recipe category
        ViewInteraction textView6 = onView(
                allOf(withId(R.id.recipe_detail_category_content), withText("Lunch"),
                        isDisplayed()));
        textView6.check(matches(withText("Lunch")));

        // check recipe comments
        ViewInteraction textView7 = onView(
                allOf(withId(R.id.recipe_detail_comments_content), withText("A simple lunch."),
                        isDisplayed()));
        textView7.check(matches(withText("A simple lunch.")));

        // go back to recipe book screen
        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.recipe_detail_toolbar),
                                        childAtPosition(
                                                allOf(withId(R.id.recipe_detail_top_bar)
                                                ),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

    }

    private void editRecipeTest() {
        // click on item Sandwich in the recipe book
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        hasDescendant(
                                hasDescendant(withText("Sandwich"))
                        )
                ));
        recyclerView.perform(actionOnItem(hasDescendant(withText("Sandwich")), click()));

        // click on edit recipe button
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.recipe_detail_edit), withContentDescription("EDIT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_detail_toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        // edit recipe title
        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.recipe_input_edit_text_title), withText("Sandwich"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_title),
                                        0),
                                0)));
        textInputEditText6.perform(scrollTo(), replaceText("Burger"));

        // edit recipe category
        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.recipe_input_edit_text_category), withText("Lunch"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_category),
                                        0),
                                0)));
        textInputEditText10.perform(scrollTo(), replaceText("Dinner"));

        // edit recipe preparation time
        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.recipe_input_edit_text_prep_time), withText("10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_prep_time),
                                        0),
                                0)));
        textInputEditText13.perform(scrollTo(), replaceText("20"));

        // edit recipe number of servings
        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.recipe_input_edit_text_num_servings), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_num_servings),
                                        0),
                                0)));
        textInputEditText15.perform(scrollTo(), replaceText("2"));

        // edit recipe comments
        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.recipe_input_edit_text_comments), withText("A simple lunch."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_comments),
                                        0),
                                0)));
        textInputEditText17.perform(scrollTo(), replaceText("A not so simple lunch."));

        // confirm editing recipe
        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.recipe_input_done), withContentDescription("CONFIRM"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_top_bar),
                                        2),
                                0)));
        actionMenuItemView4.perform(scrollTo(), click());

        // check preparation time
        ViewInteraction textView = onView(
                allOf(withId(R.id.recipe_detail_prep_time), withText("20 minutes"),
                        isDisplayed()));
        textView.check(matches(withText("20 minutes")));

        // check number of servings
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.recipe_detail_num_servings), withText("2 servings"),
                        isDisplayed()));
        textView2.check(matches(withText("2 servings")));

        // check category
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.recipe_detail_category_content), withText("Dinner"),
                        isDisplayed()));
        textView3.check(matches(withText("Dinner")));

        // check comments
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.recipe_detail_comments_content), withText("A not so simple lunch."),
                        isDisplayed()));
        textView4.check(matches(withText("A not so simple lunch.")));

        // revert changes
        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.recipe_detail_edit), withContentDescription("EDIT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_detail_toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView5.perform(click());
        ViewInteraction textInputEditText23 = onView(
                allOf(withId(R.id.recipe_input_edit_text_title), withText("Burger"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_title),
                                        0),
                                0)));
        textInputEditText23.perform(scrollTo(), replaceText("Sandwich"));
        ViewInteraction textInputEditText25 = onView(
                allOf(withId(R.id.recipe_input_edit_text_category), withText("Dinner"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_category),
                                        0),
                                0)));
        textInputEditText25.perform(scrollTo(), replaceText("Lunch"));
        ViewInteraction textInputEditText27 = onView(
                allOf(withId(R.id.recipe_input_edit_text_prep_time), withText("20"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_prep_time),
                                        0),
                                0)));
        textInputEditText27.perform(scrollTo(), replaceText("10"));
        ViewInteraction textInputEditText29 = onView(
                allOf(withId(R.id.recipe_input_edit_text_num_servings), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_num_servings),
                                        0),
                                0)));
        textInputEditText29.perform(scrollTo(), replaceText("1"));
        ViewInteraction textInputEditText31 = onView(
                allOf(withId(R.id.recipe_input_edit_text_comments), withText("A not so simple lunch."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_text_layout_comments),
                                        0),
                                0)));
        textInputEditText31.perform(scrollTo(), replaceText("A simple lunch."));
        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.recipe_input_done), withContentDescription("CONFIRM"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_input_top_bar),
                                        2),
                                0)));
        actionMenuItemView6.perform(scrollTo(), click());
        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.recipe_detail_toolbar),
                                        childAtPosition(
                                                allOf(withId(R.id.recipe_detail_top_bar)
                                                ),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

    private void seeListOfRecipesTest() {
        // check title
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
                        withParent(hasDescendant(withText("Sandwich"))),
                        isDisplayed()));
        textView.check(matches(withText("Sandwich")));

        //check preparation time
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.item_recipe_prep_time), withText("10 minutes"),
                        withParent(hasDescendant(withText("Sandwich"))),
                        isDisplayed()));
        textView2.check(matches(withText("10 minutes")));

        // check number of servings
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_recipe_num_servings), withText("×1"),
                        withParent(hasDescendant(withText("Sandwich"))),
                        isDisplayed()));
        textView3.check(matches(withText("×1")));

        // check comments
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.item_recipe_comments), withText("A simple lunch."),
                        withParent(hasDescendant(withText("Sandwich"))),
                        isDisplayed()));
        textView4.check(matches(withText("A simple lunch.")));

    }

    private void deleteRecipeTest() {
        // click on item Sandwich in recipe book
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        hasDescendant(
                                hasDescendant(withText("Sandwich"))
                        )
                ));
        recyclerView.perform(actionOnItem(hasDescendant(withText("Sandwich")), click()));

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

        // check if item no longer exists
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_recipe_title), withText("Sandwich"),
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
