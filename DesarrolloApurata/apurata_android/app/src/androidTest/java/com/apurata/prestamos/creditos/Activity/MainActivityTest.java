package com.apurata.prestamos.creditos.Activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.apurata.prestamos.creditos.R;
import com.apurata.prestamos.creditos.RequestModels.Coord;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Created by JEAN on 7/09/2017.
 */
public class MainActivityTest {
    Intent intentStart = new Intent();
    Coord coord = new Coord();
    public static final String KEY_IMAGE_DATA = "data";

    @Before
    public void setUp() throws Exception {
        //Context targetContext = getInstrumentation().getTargetContext();
        //Intent intentStart = new Intent(targetContext, ValidationActivity.class);
        /*intentStart.putExtra("id", "973482382794125");
        intentStart.putExtra("email", "jeancarlo_flores93@hotmail.com");
        intentStart.putExtra("name", "jean carlo " + "flores carrasco");
        intentStart.putExtra("gpsConnected", true);
        intentStart.putExtra("TimeStapLoginFacebook",1504796102881L);
        intentStart.putExtra("GPS", true);

        coord.setAccuracy(1);
        coord.setAltitude(1);
        coord.setAltitudeAccuracy(1);
        coord.setHeading("1");
        coord.setLatitude(1);
        coord.setLongitude(1);
        coord.setSpeed(1);
        Bundle b = new Bundle();
        b.putParcelable("Coordinate", coord);
        intentStart.putExtra("bundle", b);*/
    }
//
//    @Rule
//    public ActivityTestRule<ValidationActivity> mainActivityRule =
//            new ActivityTestRule(ValidationActivity.class,true,false);

    @Rule
    public IntentsTestRule<ValidationActivity> mIntentsTestRule =
            new IntentsTestRule(ValidationActivity.class,true) {
                @Override
                protected Intent getActivityIntent() {
                    intentStart.putExtra("id", "973482382794125");
                    intentStart.putExtra("email", "jeancarlo_flores93@hotmail.com");
                    intentStart.putExtra("name", "jean carlo " + "flores carrasco");
                    intentStart.putExtra("gpsConnected", true);
                    intentStart.putExtra("TimeStapLoginFacebook",1504796102881L);
                    intentStart.putExtra("GPS", true);

                    coord.setAccuracy(1);
                    coord.setAltitude(1);
                    coord.setAltitudeAccuracy(1);
                    coord.setHeading("1");
                    coord.setLatitude(1);
                    coord.setLongitude(1);
                    coord.setSpeed(1);
                    Bundle b = new Bundle();
                    b.putParcelable("Coordinate", coord);
                    intentStart.putExtra("bundle", b);
                    return intentStart;
                }
            };

    @Test
    public void testOnMainActivity() {

        onView(withId(R.id.btnGetDni)).perform(click());
        /*Intent resultData = new Intent();
        android.content.res.Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Uri imageUri =
                Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://"
                        + resources.getResourcePackageName(R.mipmap.ic_camera_24)
                        + '/' + resources.getResourceTypeName(R.mipmap.ic_camera_24)
                        + '/' + resources.getResourceEntryName(R.mipmap.ic_camera_24));
        resultData.putExtra("file", imageUri);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);*/
        intended(toPackage("com.android.camera"));
       // menuActivityTestRule.launchActivity(null);
       // onView(withId(R.id.btnGetDni)).perform(click());
        //intended(toPackage("com.android.camera"));
//        Instrumentation.ActivityResult result = createImageCaptureStub();
//        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
        //intended(toPackage("com.android.camera"));

//        android.content.res.Resources resources = InstrumentationRegistry.getTargetContext().getResources();
//        Uri imageUri =
//                Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                        + "://"
//                        + resources.getResourcePackageName(R.mipmap.ic_camera_24)
//                        + '/' + resources.getResourceTypeName(R.mipmap.ic_camera_24)
//                        + '/' + resources.getResourceEntryName(R.mipmap.ic_camera_24));
//        Intent resultData = new Intent();
//        resultData.setData(imageUri);
//        Instrumentation.ActivityResult result1 = new Instrumentation.ActivityResult(
//                Activity.RESULT_OK, resultData);
//        Matcher<Intent> expectedIntended = allOf(hasAction(Intent.ACTION_PICK),
//                hasData(imageUri));
//        onView(withId(R.id.btnGetDni)).perform(click());
//        intending(expectedIntended).respondWith(result);
//        intended(expectedIntended);

    }
    @Before
    public void grantPhonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.CAMERA");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.WRITE_EXTERNAL_STORAGE");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.READ_EXTERNAL_STORAGE");
        }
    }
    /*@Before
    public void StubCameraIntent() {
        Instrumentation.ActivityResult result = createImageCaptureStub();
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
    }*/

    private Instrumentation.ActivityResult createImageCaptureStub() {
        //Intent resultData = new Intent();
        android.content.res.Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Uri imageUri =
                Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://"
                        + resources.getResourcePackageName(R.mipmap.ic_camera_24)
                        + '/' + resources.getResourceTypeName(R.mipmap.ic_camera_24)
                        + '/' + resources.getResourceEntryName(R.mipmap.ic_camera_24));
        Intent resultData = new Intent();
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result1 = new Instrumentation.ActivityResult(
                Activity.RESULT_OK, resultData);
        Matcher<Intent> expectedIntended = allOf(hasAction(Intent.ACTION_PICK),
                hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
       //resultData.putExtras(bundle);
        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
    }

}