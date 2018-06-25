package com.todo.app;


import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirstTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void textButton() {
        onView(withText("Add New")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void addButton() {
        onView(allOf(withId(R.id.btn_addNew), withText("Add New"), isDisplayed()))
                .perform(click());

        onView(allOf(withText("Welcome TODO App"), isDisplayed())).check(matches(withText("Welcome TODO App")));
    }

    @Test
    public void insertToDo() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.btn_addNew), withText("Add New"), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.edtHeader), isDisplayed()))
                .perform(replaceText("Go to GYM"), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1), withText("Add")))
                .perform(scrollTo(), click());
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnHolderItem(withItemSubject("Go to GYM"),click()));


    }

    @Test
    public void deleteToDo() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.btn_addNew), withText("Add New"), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.edtHeader), isDisplayed()))
                .perform(replaceText("Badminton"), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1), withText("Add")))
                .perform(scrollTo(), click());

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnHolderItem(withItemSubject("Badminton"),
                        MyViewAction.clickChildViewWithId(R.id.imgDelete)));

    }

    public Matcher<RecyclerView.ViewHolder> withItemSubject(final String subject) {
        Checks.checkNotNull(subject);
        return new BoundedMatcher<RecyclerView.ViewHolder, ToDoAdapter.MyViewHolder>(
                ToDoAdapter.MyViewHolder.class) {

            @Override
            protected boolean matchesSafely(ToDoAdapter.MyViewHolder viewHolder) {
                ImageView deleteIcon = (ImageView) viewHolder.itemView.findViewById(R.id.imgDelete);
                TextView subjectTextView = (TextView) viewHolder.itemView.findViewById(R.id.txtheader);

                return ((subject.equals(subjectTextView.getText().toString())
                        && (subjectTextView.getVisibility() == View.VISIBLE)));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item with subject: " + subject);
            }
        };
    }

    @After
    public void testDown() {

    }
}
