package com.apurata.prestamos.creditos.Service;

import android.hardware.Camera;
import android.os.Build;
import java.util.*;

/**
 * Created by USUARIO on 20/07/2017.
 */
public class CameraPixelService {

    List<Integer> arrayListForWidth = new ArrayList<Integer>();
    List<Integer> arrayListForHeight =new ArrayList<Integer>();
    int widthSizeScreen = 0;
    int heightSizeScreen = 0;


    public int getWidthFrontCamera() {

        boolean cameraExist = validateFrontAndBackCamera(1);
        Camera camera;
        int  widthFrontCamera = 0;
        if (cameraExist) {
            camera = Camera.open(1) ;       //  For Front Camera
            android.hardware.Camera.Parameters params1 = camera.getParameters();
            List sizes1 = params1.getSupportedPictureSizes();
            Camera.Size result1 = null;
            camera.release();
            for (int i = 0; i < sizes1.size(); i++) {
                result1 = (Camera.Size) sizes1.get(i);
                arrayListForWidth.add(result1.width);
            }
            if (arrayListForWidth.size() != 0) {
                widthFrontCamera = Collections.max(arrayListForWidth);
            }
        }
        return widthFrontCamera;
    }

    public int getHeightFrontCamera() {
        boolean cameraExist = validateFrontAndBackCamera(1);
        Camera camera;
        int heightFrontCamera = 0;
        if (cameraExist) {
            camera = Camera.open(1);        //  For Front Camera
            android.hardware.Camera.Parameters params1 = camera.getParameters();
            List sizes1 = params1.getSupportedPictureSizes();
            Camera.Size result1 = null;
            for (int i = 0; i < sizes1.size(); i++) {
                result1 = (Camera.Size) sizes1.get(i);
                arrayListForHeight.add(result1.height);}
            if (arrayListForWidth.size() != 0 && arrayListForHeight.size() != 0) {
                heightFrontCamera = Collections.max(arrayListForHeight);
            }
            camera.release();
        }
        return heightFrontCamera;
    }

    public int getWidthBackCamera() {
        boolean cameraExist = validateFrontAndBackCamera(0);
        Camera camera;
        int widthBackCamera = 0;
        if (cameraExist) {
            camera = Camera.open(0);    // For Back Camera
            android.hardware.Camera.Parameters params = camera.getParameters();
            List sizes = params.getSupportedPictureSizes();
            Camera.Size result = null;
            for (int i = 0; i < sizes.size(); i++) {
                result = (Camera.Size) sizes.get(i);
                arrayListForWidth.add(result.width);
            }
            if (arrayListForWidth.size() != 0) {
                widthBackCamera = Collections.max(arrayListForWidth);              // Gives Maximum Width
            }
            camera.release();
        }
        return widthBackCamera;
    }

    public int getHeightBackCamera() {
        boolean cameraExist = validateFrontAndBackCamera(0);
        Camera camera;
        int heightBackCamera = 0;
        if (cameraExist) {
            camera = Camera.open(0);        //  For BackCamera
            android.hardware.Camera.Parameters params1 = camera.getParameters();
            List sizes1 = params1.getSupportedPictureSizes();
            Camera.Size result1 = null;
            for (int i = 0; i < sizes1.size(); i++) {
                result1 = (Camera.Size) sizes1.get(i);
                arrayListForHeight.add(result1.height);
            }
            if (arrayListForHeight.size() != 0) {
                heightBackCamera = Collections.max(arrayListForHeight);
            }
            camera.release();
        }
        return heightBackCamera;
    }

    public Boolean validateFrontAndBackCamera(int id) {
        Camera mCamera = null;
        boolean cOpened = false;

        try {
            //releaseCameraAndPreview();
            mCamera = Camera.open(id);
            cOpened = (mCamera != null);
        } catch (Exception e) {
            cOpened = false;
            e.printStackTrace();
        }
        if (cOpened) {
            mCamera.release();
        }

        return cOpened;
    }

    public String getBrandCellPhone() {
        return Build.BRAND;
    }

    public String getModelCellPhone() {
        return Build.MODEL;
    }

    public String getVersionCellPhone() {
        return Build.VERSION.RELEASE;
    }

    public int getWidthSizeScreen() {
        return widthSizeScreen;
    }

    public int getHeightSizeScreen() {
        return heightSizeScreen;
    }
}