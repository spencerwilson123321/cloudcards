package com.example.cloudcards;

import static androidx.test.InstrumentationRegistry.getTargetContext;
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
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

//import androidx.appcompat.widget.MenuPopupWindow;
//import androidx.test.InstrumentationRegistry;
//import androidx.test.espresso.ViewAction;
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.contrib.RecyclerViewActions;
//import androidx.test.espresso.intent.rule.IntentsTestRule;
//import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
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

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.io.OutputStream;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityScenarioRule<Login> activityRule
            = new ActivityScenarioRule<>(Login.class);

    @Test
    public void register_user() {
        onView(withId(R.id.start_register_button)).perform(click());

        // Emulator lag messes up keyboard animation in test, using replace for now.
//        onView(withId(R.id.register_email)).perform(clearText(),typeText("test"), closeSoftKeyboard());
//        onView(withId(R.id.register_password_1)).perform(clearText(),typeText("test"), closeSoftKeyboard());
//        onView(withId(R.id.register_password_2)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.register_email)).perform(replaceText("test"));
        onView(withId(R.id.register_password_1)).perform(replaceText("test"));
        onView(withId(R.id.register_password_2)).perform(replaceText("test"));
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.register_cancel)).perform(click());
    }

    @Test
    public void homepage_add_card() {
        onView(withId(R.id.login_email)).perform(replaceText("test"));
        onView(withId(R.id.login_password)).perform(replaceText("test"));
//        onView(withId(R.id.login_email)).perform(clearText(),typeText("test"), closeSoftKeyboard());
//        onView(withId(R.id.login_password)).perform(clearText(),typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.show_dropdown_menu)).perform(click());



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
