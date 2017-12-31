package com.apurata.prestamos.creditos.Activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.apurata.prestamos.creditos.Models.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jeancarlo on 8/4/17.
 */
public class LoadingActivityTest {

    Intent intent = new Intent();

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setId("973482382794125");
        user.setFirst_name("jean carlo");
        user.setLast_name("flores carrasco");
        user.setEmail("jeancarlo_flores93@hotmail.com");
        intent.putExtra("STATUS", true);
        intent.putExtra("id", "973482382794125");
        intent.putExtra("email", user.getEmail());
        intent.putExtra("name", user.getFirst_name() + user.getLast_name());
        intent.putExtra("gpsConnected", true);
    }

    @Rule
    public ActivityTestRule<LoadingActivity> activityActivityTestRule =
            new ActivityTestRule(LoadingActivity.class,true,false);

    @Test
    public void demostrateIntent() {

        activityActivityTestRule.launchActivity(intent);
    }

}