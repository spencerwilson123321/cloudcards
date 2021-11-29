package com.example.cloudcards;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.Intents.intending;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AutoCompleteTextView;

//import androidx.appcompat.widget.MenuPopupWindow;
//import androidx.test.InstrumentationRegistry;
//import androidx.test.espresso.ViewAction;
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.contrib.RecyclerViewActions;
//import androidx.test.espresso.intent.rule.IntentsTestRule;
//import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.runner.intent.IntentCallback;
import androidx.test.runner.intent.IntentMonitorRegistry;

import com.example.cloudcards.View.LoginActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.io.OutputStream;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void register_user() {
        onView(withId(R.id.start_register_button)).perform(click());

        // Emulator lag messes up keyboard animation in test, using replace for now.
//        onView(withId(R.id.register_email)).perform(replaceText("test"));
//        onView(withId(R.id.register_password_1)).perform(replaceText("test"));
//        onView(withId(R.id.register_password_2)).perform(replaceText("test"));
        onView(withId(R.id.register_email)).perform(clearText(),typeText("test"));
        onView(withId(R.id.register_password_1)).perform(clearText(),typeText("test"));
        onView(withId(R.id.register_password_2)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.register_cancel)).perform(click());
    }

    @Test
    public void homepage_add_card() {
//        onView(withId(R.id.login_email)).perform(replaceText("test"));
//        onView(withId(R.id.login_password)).perform(replaceText("test"));
        onView(withId(R.id.login_email)).perform(clearText(),typeText("test"));
        onView(withId(R.id.login_password)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.menu)).perform(click());



        // Create a bitmap we can use for our simulated camera image
        Bitmap img = BitmapFactory.decodeResource(
                ApplicationProvider.getApplicationContext().getResources(),
                R.drawable.testcard);

        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", img);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

//        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(imgCaptureResult);
        Intents.init();
//        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(
//                result);
//        intending(hasComponent(CropImage.class.getName())).respondWith(
//                result);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(
                new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

//        intending(hasComponent(CropImage.class.getName())).respondWith(
//                new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        IntentCallback intentCallback = new IntentCallback() {
            @Override
            public void onIntentSent(Intent intent) {
                if (intent.getAction().equals("android.media.action.IMAGE_CAPTURE")) {
                    try {
                        Uri imageUri = intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                        Context context = getTargetContext();
                        Bitmap icon = BitmapFactory.decodeResource(
                                context.getResources(),
                                R.drawable.testcard);
                        OutputStream out = getTargetContext().getContentResolver().openOutputStream(imageUri);
                        icon.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
//                        GenericUtility.handleException(e);
                    }
                }
            }
        };
        IntentMonitorRegistry.getInstance().addIntentCallback(intentCallback);

        //Perform action here
        onView(withText("Add Card"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
    }

    @Test
    public void collection_search() {
//        onView(withId(R.id.login_email)).perform(replaceText("test"));
//        onView(withId(R.id.login_password)).perform(replaceText("test"));
        onView(withId(R.id.login_email)).perform(clearText(),typeText("test"));
        onView(withId(R.id.login_password)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.menu)).perform(click());
        onView(withText("View Collection"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Click the item.
//        onView(withText("World"))
//                .perform(click());
//
//        onView(withId(R.id.search_toolbar)).perform(click());
//        onView(withId(R.id.action_search))
//                .inRoot(RootMatchers.isPlatformPopup())
//                .perform(click());
//        onView(withId(R.id.action_search)).perform(replaceText("squee"));
//        onView(withId(R.id.collection_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
        try {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        } catch (Exception e) {
            //This is normal. Maybe we dont have overflow menu.
        }
        onView(anyOf(withText(R.string.filter_search), withId(R.id.action_search))).perform(click());
//        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(replaceText("squee"));
//        onView(withId(R.id.collection_recycler)).perform(click());
        onView(isAssignableFrom(AutoCompleteTextView.class))
//                .perform(clearText(), typeText("Jhessian Thief"), closeSoftKeyboard());
                    .perform(clearText(), typeText("Turret Ogre"), closeSoftKeyboard());
        onView(withId(R.id.collection_main)).perform(click());
//        onView(withId(R.id.collection_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
