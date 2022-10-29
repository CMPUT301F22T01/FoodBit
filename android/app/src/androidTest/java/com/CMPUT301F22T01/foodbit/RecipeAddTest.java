package com.CMPUT301F22T01.foodbit;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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

import java.lang.annotation.Repeatable;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeAddTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void repeat() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            recipeAddTest();
        }
    }

//    @Test
    public void recipeAddTest() throws InterruptedException {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_bar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_large_label_view), withText("Recipes"),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                                withParent(allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipes"))))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.recipe_book_test_add_button), withText("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_container),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        Thread.sleep(200);

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.recipe_add_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_title),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.recipe_add_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_title),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.recipe_add_edit_text_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_title),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("test title"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.recipe_add_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_category),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("test category"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.recipe_add_edit_text_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_prep_time),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("240"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.recipe_add_edit_text_num_servings),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_num_servings),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("50"), closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.recipe_add_edit_text_comments),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_text_layout_comments),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("test comment"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.recipe_add_ingredient_add), withContentDescription("Add an ingredient"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredients_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_description),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_description),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("test description"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_amount),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_amount),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(replaceText("1.5"), closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_unit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_unit),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("test unit"), closeSoftKeyboard());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.recipe_add_ingredient_add_edit_text_category),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_ingredient_add_layout_category),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText11.perform(replaceText("test category"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("test description"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView2.check(matches(withText("test description")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_ingredient_amount), withText("1.5"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView3.check(matches(withText("1.5")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("test unit"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView4.check(matches(withText("test unit")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("test unit"),
                        withParent(withParent(withId(R.id.recipe_add_ingredients_list))),
                        isDisplayed()));
        textView5.check(matches(withText("test unit")));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.recipe_add_done), withContentDescription("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_add_top_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        Thread.sleep(500);

        ViewInteraction textView6 = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_large_label_view), withText("Recipes"),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                                withParent(allOf(withId(R.id.fragment_recipe_book), withContentDescription("Recipes"))))),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.item_recipe_title), withText("test title"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView7.check(matches(withText("test title")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.item_recipe_prep_time), withText("240 minutes"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView8.check(matches(withText("240 minutes")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.item_recipe_num_servings), withText("×50"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView9.check(matches(withText("×50")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.item_recipe_comments), withText("test comment"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView10.check(matches(withText("test comment")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        Thread.sleep(200);

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.recipe_detail_prep_time), withText("240 minutes"),
                        isDisplayed()));
        textView11.check(matches(withText("240 minutes")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.recipe_detail_num_servings), withText("50 servings"),
                        isDisplayed()));
        textView12.check(matches(withText("50 servings")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.recipe_detail_category_content), withText("test category"),
                        isDisplayed()));
        textView13.check(matches(withText("test category")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.recipe_detail_comments_content), withText("test comment"),
                        isDisplayed()));
        textView14.check(matches(withText("test comment")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.item_ingredient_description), withText("test description"),
                        withParent(withParent(withId(R.id.recipe_detail_ingredient_list))),
                        isDisplayed()));
        textView15.check(matches(withText("test description")));

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.item_ingredient_amount), withText("1.5"),
                        withParent(withParent(withId(R.id.recipe_detail_ingredient_list))),
                        isDisplayed()));
        textView16.check(matches(withText("1.5")));

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.item_ingredient_unit), withText("test unit"),
                        withParent(withParent(withId(R.id.recipe_detail_ingredient_list))),
                        isDisplayed()));
        textView17.check(matches(withText("test unit")));

        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.recipe_detail_toolbar),
                                        childAtPosition(
                                                withContentDescription("test title"),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        Thread.sleep(200);

        ViewInteraction textView19 = onView(
                allOf(withId(R.id.item_recipe_photo_text), withText("T"),
                        withParent(allOf(withId(R.id.item_recipe_image),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView19.check(matches(withText("T")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerView_recipe_book),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        Thread.sleep(200);

        ViewInteraction button = onView(
                allOf(withId(R.id.recipe_detail_temp_delete), withText("DELETE"),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.recipe_detail_temp_delete), withText("delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                8),
                        isDisplayed()));
        materialButton3.perform(click());

        Thread.sleep(500);

        ViewInteraction textView20 = onView(
                allOf(withId(R.id.item_recipe_title), withText("test title"),
                        withParent(withParent(withId(R.id.recyclerView_recipe_book))),
                        isDisplayed()));
        textView20.check(doesNotExist());


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
