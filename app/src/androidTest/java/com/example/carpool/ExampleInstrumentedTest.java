package com.example.carpool;

import android.content.Context;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<activity_logIn> activityRule =
            new ActivityTestRule<>(activity_logIn.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withId(R.id.txt_username_logIn));
        onView(withId(R.id.txt_password_logIn));


    }

    @Test
    public void listGoesTheFold() {
        onView(withId(R.id.btn_login));
        onView(withId(R.id.progressBar));
        onView(withId(R.id.lbl_registerNow));
        onView(withId(R.id.txt_forget_password_login));

    }
}
