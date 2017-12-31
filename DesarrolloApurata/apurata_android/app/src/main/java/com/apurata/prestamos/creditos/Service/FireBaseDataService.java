package com.apurata.prestamos.creditos.Service;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JEAN on 11/10/2017.
 */

public class FireBaseDataService {

    DateFormat df;
    String date, idFacebook;
    FirebaseAnalytics varFBA;
    String currentView;
    String USER_ERROR_400 = "userError400", USER_ERROR_410 = "userError410",USER_ERROR_404 = "userError404",
            USER_ERROR_403 = "userError403",USER_ERROR_422 = "userError422", USER_ERROR_500 = "userError500",
           USER_ERROR_UNEXPECTED = "userErrorUnexpected", USER_ERROR_ON_FAILURE = "onFailure", USER_NO_INTERNET = "noNetwork";
    String PERMISSION_LOCATION = "location", PERMISSION_CAMERA = "camera", PERMISSION_STORAGE= "storage";
    String LOGIN_FACEBOOK_SUCCESS = "Success", LOGIN_FACEBOOK_CANCEL= "Cancel" , LOGIN_FACEBOOK_ERROR = "ERROR";
    public static final String LOGIN_FACEBOOK_STATUS = "loginFacebookStatus";
    public static final String SERVER_REPONSE_SUCCESSFUL = "418";
    public static final String EVENT_SERVER_RESPONSE_SUCCESS = "formSubmittedSuccessful";
    String VIEW_BUTTON_LOGIN_FACEBOOK = "clickOnButtonLoginFacebook", VIEW_BUTTON_SUBMIT_FORM = "clickOnButtonSubmit";
    List<String> userErrorList = new ArrayList<String>() {
        {
            add(USER_ERROR_400);
            add(USER_ERROR_403);
            add(USER_ERROR_404);
            add(USER_ERROR_410);
            add(USER_ERROR_422);
            add(USER_ERROR_500);
            add(USER_ERROR_UNEXPECTED);
            add(USER_NO_INTERNET);
            add(USER_NO_INTERNET);
        }
    };
    List<String> permissionList = new ArrayList<String>() {
        {
            add(PERMISSION_LOCATION);
            add(PERMISSION_CAMERA);
            add(PERMISSION_STORAGE);
        }
    };
    List<String> facebookStatusCases = new ArrayList<String>(){
        {
            add(LOGIN_FACEBOOK_SUCCESS);
            add(LOGIN_FACEBOOK_CANCEL);
            add(LOGIN_FACEBOOK_ERROR);
        }
    };
    List<String> viewClickedList = new ArrayList<String>(){
        {
            add(VIEW_BUTTON_LOGIN_FACEBOOK);
            add(VIEW_BUTTON_SUBMIT_FORM);
        }
    };


    public FireBaseDataService(FirebaseAnalytics varFBA, String idFacebook, String currentView) {
        this.varFBA = varFBA;
        this.idFacebook = idFacebook;
        this.currentView = currentView;
    }

    public void loginFacebookStatus(String facebookStatus) throws Exception {
        if (validationEvent((ArrayList<String>) facebookStatusCases, facebookStatus)) {
            varFBA.logEvent(LOGIN_FACEBOOK_STATUS+facebookStatus, startingBundle(null, null, currentView));
        }else {
            throw new Exception("Incorrect Status");
        }
    }

    public void clickOnView(String event) throws Exception {
        if (validationEvent((ArrayList<String>) viewClickedList, event)) {
            varFBA.logEvent(event, startingBundle(null, null, currentView));
        }else {
            throw new Exception("Incorrect View has been passed");
        }
    }

    public void formSubmittedFailed(String messageLabel, String messageResponse, String nameOfTheEvent) throws Exception {
        if (validationEvent((ArrayList<String>) userErrorList, nameOfTheEvent)) {
            varFBA.logEvent(nameOfTheEvent, startingBundle(messageLabel, messageResponse, currentView));
        }else {
            throw new Exception("Incorrect Code Response");
        }
    }

    public void formSubmittedSuccessful(String messageLabel, String messageResponse, String code) throws Exception{
        if (code.compareTo(SERVER_REPONSE_SUCCESSFUL) == 0) {
            varFBA.logEvent(EVENT_SERVER_RESPONSE_SUCCESS, startingBundle(messageLabel, messageResponse, currentView));
        }else {
            throw new Exception("Error on code of server response");
        }
    }
    public void statusPermission(String nameOfThePermission, String status) throws Exception {
        if (validationEvent((ArrayList<String>) permissionList, nameOfThePermission)) {
            varFBA.logEvent(nameOfThePermission+status, startingBundle(null, null, currentView));
        }else {
            throw new Exception("Incorrect Status");
        }
    }

    public void counterCamera(String camera, int counter) {
        String event = "nonCamera";
        if(camera.compareTo("DNI") == 0) {
            event = "clickOnDNIPictureButton";
        }else if (camera.compareTo("SIGN") == 0){
            event = "clickOnSignPictureButton";
        }
        Bundle newBundle = startingBundle("Camera", camera, currentView);
        newBundle.putInt("times_opened", counter);
        varFBA.logEvent(event, newBundle);
    }

    private Bundle startingBundle(String label, String data, String view) {
        Bundle bundle = new Bundle();
        df = new SimpleDateFormat("dd MM yyyy, HH:mm:ss");
        date = df.format(Calendar.getInstance().getTime());
        bundle.putString("time", date);
        if (view != null) {
            bundle.putString("currentView", view);
        }
        if(idFacebook != null){
            bundle.putString("idFacebook", idFacebook);
        }
        if(data != null) {
            bundle.putString(label,data);
        }
        return bundle;
    }

    Boolean validationEvent(ArrayList<String> aList, String permissionToSearch){
        boolean permissionExist = false;
        for (String permission : aList) {
            if (permission.compareTo(permissionToSearch) == 0) {
                permissionExist = true;
                break;
            } else {
                permissionExist = false;
            }
        }
        return permissionExist;
    }
}
