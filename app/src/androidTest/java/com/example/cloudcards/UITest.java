package com.example.cloudcards;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.Intents.intending;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.view.View;

//import androidx.appcompat.widget.MenuPopupWindow;
//import androidx.test.InstrumentationRegistry;
//import androidx.test.espresso.ViewAction;
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.contrib.RecyclerViewActions;
//import androidx.test.espresso.intent.rule.IntentsTestRule;
//import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityScenarioRule<Login> activityRule
            = new ActivityScenarioRule<>(Login.class);

    @Test
    public void register_user() {
        onView(withId(R.id.start_register_button)).perform(click());
        onView(withId(R.id.register_email)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.register_password_1)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.register_password_2)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.register_cancel)).perform(click());

        onView(withId(R.id.login_email)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.show_dropdown_menu)).perform(click());
    }

//    @Test
//    public void homepage_test() {
//        onView(withId(R.id.login_email)).perform(clearText(),typeText("test"), closeSoftKeyboard());
//        onView(withId(R.id.login_password)).perform(clearText(),typeText("test"), closeSoftKeyboard());
//        onView(withId(R.id.login_button)).perform(click());
//        onView(withId(R.id.show_dropdown_menu)).perform(click());
//
////        onView(withId(R.menu.drop_down_menu)).perform(ViewActions.openLinkWithText("Add Card"));
//
//    }

//    @Rule
//    public ActivityScenarioRule<CollectionSearch> activityRule2
//            = new ActivityScenarioRule<>(CollectionSearch.class);
//
//    @Test
//    public void search_test() {
//        onView(withId(R.id.search_bar)).perform(clearText(),typeText("squee"), closeSoftKeyboard());
//        onView(withId(R.id.search_button)).perform(click());
////        onView(withId(R.id.collection_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//    }

/*
    @Rule
    public IntentsTestRule<Homepage> intentsTestRule =
            new IntentsTestRule<>(Homepage.class);

    @Test
    public void validateCameraScenario() {
        onView(withId(R.id.login_email)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        // Create a bitmap we can use for our simulated camera image
        Bitmap img = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.drawable.testcard);

        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", img);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage("com.android.camera")).respondWith(result);

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.show_dropdown_menu)).perform(click());
//        onData(withId(R.id.show_dropdown_menu)).inRoot(RootMatchers.isPlatformPopup())
//                .inAdapterView(CoreMatchers.<View>instanceOf(MenuPopupWindow.MenuDropDownListView.class)).atPosition(0).perform(click());

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(toPackage("com.android.camera"));

        // ... additional test steps and validation ...

    }
*/

}
