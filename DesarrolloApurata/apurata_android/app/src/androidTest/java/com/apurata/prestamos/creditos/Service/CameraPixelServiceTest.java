package com.apurata.prestamos.creditos.Service;

import android.content.Intent;
import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.apurata.prestamos.creditos.Activity.LoadingActivity;
import com.apurata.prestamos.creditos.Models.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by USUARIO on 31/10/2017.
 */
@RunWith(AndroidJUnit4.class)
public class CameraPixelServiceTest {
    @Test
    public void getWidthFrontCamera() throws Exception {
       // int idontknow = getWidthFrontCamera();
        assertEquals(0,0);
    }
    @Test
    public void valildationGetWitdhFrontCamera(){

    }


    @Test
    public void getWidthBackCamera() throws Exception {
    }

    @Test
    public void getHeightBackCamera() throws Exception {
    }

    @Test
    public void validateFrontAndBackCamera() throws Exception {
    }

    @Test
    public void getWidthSizeScreen() throws Exception {
    }

    @Test
    public void getHeightSizeScreen() throws Exception {
    }

    @Test
    public void getBrandCellPhone() throws Exception {
        //activityActivityTestRule.launchActivity(intent);
        System.out.println(Build.BRAND);
        assertEquals("samsung", Build.BRAND);
    }

    @Test
    public void getModelCellPhone() throws Exception {
        System.out.println(Build.MODEL);
        assertEquals("SM-J500M",Build.MODEL);
    }

    @Test
    public void getVersionCellPhone() throws Exception {
        System.out.println(Build.VERSION.RELEASE);
        assertEquals("6.0.1", Build.VERSION.RELEASE);
    }

}