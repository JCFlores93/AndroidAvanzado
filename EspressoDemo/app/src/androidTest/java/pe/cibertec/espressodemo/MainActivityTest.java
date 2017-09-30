package pe.cibertec.espressodemo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Android on 03/06/2017.
 */

@RunWith(AndroidJUnit4.class)

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

@Test
    public void testTextChanged(){

        onView(withId(R.id.edt_input)).perform(typeText("Juventus"),closeSoftKeyboard());

        onView(withId(R.id.btn_change_text)).perform(click());

        onView(withId(R.id.edt_input)).check(matches(withText("Real Madrid!!!")));
    }

    @Test
    public void testTextInNewActivity(){
        onView(withId(R.id.edt_input))
                .perform(typeText("Juventus"),closeSoftKeyboard());
        onView(withId(R.id.btn_switch_activity)).perform(click());

        onView(withId(R.id.txt_result)).check(matches(withText("Juventus")));

    }
}