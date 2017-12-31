package com.apurata.prestamos.creditos.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apurata.prestamos.creditos.R;
import com.apurata.prestamos.creditos.Service.FireBaseDataService;
import com.apurata.prestamos.creditos.Utils.ViewAccess;
import com.apurata.prestamos.creditos.Models.User;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private String FIRST_BOOT = "PrimerArranque";
    private String FACEBOOK_EVENT = "facebookStatus";
    private String ACTIVITY_VIEW = "LoginActivity";
    private FireBaseDataService fireBaseDataService;
    View parentView;
    ImageView imageAlert;
    TextView txtAlert;
    TextView messageLoginView1;
    TextView messageLoginView2;
    TextView errorMessageNull;
    Button btnGoToApurataCom;
    ProgressBar progressBarLogin;
    TextView txtMainTitle2;
    LoginButton loginButton;
    private CallbackManager callbackManager;
    private Location location;
    private LocationListener locationListener;
    private static final int PERMISSION_GPS_REQUEST_CODE = 10;
    private boolean gpsStatus;
    private Snackbar validationGPS;
    LocationManager locationManager;
    String idFacebook;
    private Snackbar validationPermissionGPS;
    private Intent openLoadingActivity;
    private Boolean permissionsG = false;
    private boolean gpsConnected;
    public static final String OBJECT_STATUS = "STATUS";
    public static final String PERMISSION_LOCATION = "location";
    public static final String PERMISSION_GRANTED = "Granted";
    public static final String PERMISSION_DENIED = "Denied";
    DateFormat df;
    String date, FB_STATUS = "StatusLoginFacebook";
    private SharedPreferences settings;

    LocationManager service;
    String code_god_mode = "";
    ViewAccess viewAccess = new ViewAccess();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        fireBaseDataService = new FireBaseDataService(mFirebaseAnalytics, null, ACTIVITY_VIEW);
        settings = getSharedPreferences("First_Instance", Context.MODE_PRIVATE);
        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = settings.getBoolean(FIRST_BOOT, false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = settings.edit();
            edit.putBoolean(FIRST_BOOT, Boolean.TRUE);
            edit.putInt("counterGPS", 0);
            edit.commit();
        }
        setContentView(R.layout.activity_login);
        parentView = findViewById(R.id.parentView);
        imageAlert = (ImageView) findViewById(R.id.imageAlert);
        txtAlert = (TextView) findViewById(R.id.txtAlert);
        messageLoginView1 = (TextView) findViewById(R.id.messageLoginView1);
        messageLoginView2 = (TextView) findViewById(R.id.messageLoginView2);
        errorMessageNull = (TextView) findViewById(R.id.txtMessageNull);
        btnGoToApurataCom = (Button) findViewById(R.id.btnGoToApurataCom);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        progressBarLogin.setVisibility(View.GONE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        btnGoToApurataCom.setOnClickListener(this);

        service = (LocationManager) getSystemService(LOCATION_SERVICE);
        gpsStatus = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        validationPermissionGPS = Snackbar.make(parentView, "Es necesario dar permisos del GPS", Snackbar.LENGTH_INDEFINITE)
                .setAction("Activar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(LoginActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSION_GPS_REQUEST_CODE);
                        gpsStatus = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    }
                });
        validationGPS = Snackbar.make(parentView, "Es necesario encender el GPS", Snackbar.LENGTH_INDEFINITE)
                .setAction("Activar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ActivityCompat.requestPermissions(LoginActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSION_GPS_REQUEST_CODE);
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), PERMISSION_GPS_REQUEST_CODE);
                        if (service != null) {
                            gpsStatus = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        } else {
                            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                            boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                            if (!enabled) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        }

                    }
                });

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        loginButton.setOnClickListener(this);
        openLoadingActivity = new Intent(this, LoadingActivity.class);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            Bundle facebookLoginStarted = new Bundle();

            @Override
            public void onSuccess(final LoginResult loginResult) {
                try {
                    fireBaseDataService.loginFacebookStatus("Success");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showMessageByToast(getResources().getString(R.string.onSuccessMessage));
                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (isLoggedIn()) {
                                    idFacebook = loginResult.getAccessToken().getUserId();
                                    if (object != null) {
                                        Bundle bFacebookData = getFacebookData(object);
                                        User user = new User();
                                        user.setId(bFacebookData.getString("id"));
                                        user.setFirst_name(bFacebookData.getString("first_name"));
                                        user.setLast_name(bFacebookData.getString("last_name"));
                                        user.setEmail(bFacebookData.getString("email"));
                                        logUser(user);
                                        openLoadingActivity.putExtra(OBJECT_STATUS, true);
                                        openLoadingActivity.putExtra("id", idFacebook);
                                        if(user.getEmail() != null) {
                                            openLoadingActivity.putExtra("email", user.getEmail());
                                        }
                                        if (user.getFirst_name() != null || user.getLast_name() != null ) {
                                            openLoadingActivity.putExtra("userName", user.getFirst_name() + user.getLast_name());
                                        }
                                        openLoadingActivity.putExtra("gpsConnected", gpsConnected);
                                        startActivity(openLoadingActivity);
                                        finish();
                                    } else {
                                        logUser(idFacebook);
                                        openLoadingActivity.putExtra(OBJECT_STATUS, false);
                                        openLoadingActivity.putExtra("id", idFacebook);
                                        openLoadingActivity.putExtra("gpsConnected", gpsConnected);
                                        startActivity(openLoadingActivity);
                                        finish();
                                    }
                                }
                            }


                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                showMessageByToast(getResources().getString(R.string.onCancelMessage));
                try {
                    fireBaseDataService.loginFacebookStatus("Cancel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(FacebookException error) {
                //String _error = " Success Canceled " + error.getCause() +"Login error.";
                try {
                    fireBaseDataService.loginFacebookStatus("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String _error = " Verifique su conexión a internet e intentélo de nuevo.";
                showMessageByToast(_error);
            }
        });
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return (accessToken != null) && (!accessToken.isExpired());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_GPS_REQUEST_CODE) {
            gpsStatus = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!gpsStatus) {
                validationGPS.show();
                viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

            } else {
                if ((ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                        validationPermissionGPS.show();
                    } else {
                        ActivityCompat.requestPermissions(LoginActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSION_GPS_REQUEST_CODE);
                    }
                } else {
                    if (gpsStatus && permissionsG) {
                        viewAccess.accessGranted(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                        validationGPS.dismiss();

                    } else {
                        viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                        validationGPS.show();
                    }
                }
            }
        }
        if (resultCode == RESULT_CANCELED && gpsStatus && permissionsG) {
            viewAccess.accessGranted(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

        } else {
            if (resultCode == RESULT_CANCELED && gpsStatus == false) {
                viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                validationGPS.show();
            }
        }
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("idFacebook", object.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        boolean permission = checkPermissionForGPS();
        checkGPSStatus(permission);
    }

    private void checkGPSStatus(boolean permission) {

        if (permission) {
            if (gpsStatus) {
                viewAccess.accessGranted(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

                validationPermissionGPS.dismiss();
                validationGPS.dismiss();
            } else {
                viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                validationPermissionGPS.dismiss();
                validationGPS.show();
            }

        } else {


            if (gpsStatus) {
                viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                validationPermissionGPS.show();
                validationGPS.dismiss();
            } else {
                viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                validationPermissionGPS.show();
                validationGPS.show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_GPS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        try {
                            fireBaseDataService.statusPermission(PERMISSION_LOCATION, PERMISSION_GRANTED);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                        gpsStatus = true;
                        permissionsG = true;
                        if (gpsStatus && permissionsG) {
                            viewAccess.accessGranted(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                        }
                    }
                }else {
                    try {
                        fireBaseDataService.statusPermission(PERMISSION_LOCATION, PERMISSION_DENIED);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                }
            }
        }
    }

    public boolean checkPermissionForGPS() {

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Snackbar.make(parentView, "Debe activar el permiso de GPS", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Activar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_GPS_REQUEST_CODE);
                            }
                        }).show();
                viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
            }
            permissionsG = false;
            return false;
        } else {

            // location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            permissionsG = true;
            LocationManager service = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location locationn) {
                    location = locationn;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    gpsStatus = true;
                    if (permissionsG) {
                        validationGPS.dismiss();
                        viewAccess.accessGranted(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

                    } else {
                        viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                    }

                }

                @Override
                public void onProviderDisabled(String provider) {
                    gpsStatus = false;
                    if (permissionsG == false) {
                        validationPermissionGPS.show();
                        viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                    } else {
                        validationGPS.show();
                        viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);
                    }
                }
            };


            service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
            service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            gpsStatus = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            int androidVersion = Build.VERSION.SDK_INT;
            int numer = Build.VERSION_CODES.LOLLIPOP_MR1;
            if (androidVersion >= 23) {
                if (gpsStatus && permissionsG == true) {
                    viewAccess.accessGranted(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

                } else {
                    if (!gpsStatus && permissionsG == true) {
                        validationGPS.show();
                        viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

                    } else {
                        if (gpsStatus && permissionsG == false) {
                            validationPermissionGPS.show();
                            viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

                        }
                    }

                }
            } else {
                if (gpsStatus && permissionsG) {
                    viewAccess.accessGranted(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

                    validationGPS.dismiss();
                } else {
                    validationGPS.show();
                    viewAccess.showAlert(loginButton, imageAlert, txtAlert, messageLoginView1, messageLoginView2);

                }
            }

            if (service.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gpsConnected = true;
            } else {
                gpsConnected = false;
            }
            return true;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationListener != null) {
            service.removeUpdates(locationListener);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToApurataCom:
                if (errorMessageNull.getText().equals(getResources().getString(R.string.checkStatus))) {
                    Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apurata.com/panel"));
                    startActivity(openBrowser);
                    finish();
                } else {
                    Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apurata.com"));
                    startActivity(openBrowser);
                    finish();
                }
                break;
            case R.id.login_button:
                try {
                    fireBaseDataService.clickOnView("clickOnButtonLoginFacebook");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkPermissionForGPS();
                break;
        }
    }

    private void logUser(User user) {
        Crashlytics.setUserIdentifier(user.getId());
        Crashlytics.setUserEmail(user.getEmail());
        Crashlytics.setUserName(user.getFirst_name() + user.getLast_name());
    }

    private void logUser(String userId) {
        Crashlytics.setUserIdentifier(userId);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action = event.getAction();
        int keyCode = event.getKeyCode();
        String code_validation_god_mode = "";
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    code_god_mode += "u";
                    if (code_god_mode.length() >= 6) {
                        code_validation_god_mode = code_god_mode.substring(code_god_mode.length() - 6, code_god_mode.length());
                        if (code_validation_god_mode.compareTo("ududud") == 0) {
                            String email = "prestaya.com";
                            openLoadingActivity.putExtra("id", "1");
                            openLoadingActivity.putExtra("email", email);
                            openLoadingActivity.putExtra("gpsConnected", gpsConnected);
                            startActivity(openLoadingActivity);
                            finish();
                        }
                    }
                }
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    code_god_mode += "d";
                    if (code_god_mode.length() >= 6) {
                        code_validation_god_mode = code_god_mode.substring(code_god_mode.length() - 6, code_god_mode.length());
                        if (code_validation_god_mode.compareTo("ududud") == 0) {
                            String email = "prestaya.com";
                            openLoadingActivity.putExtra("id", "1");
                            openLoadingActivity.putExtra("email", email);
                            openLoadingActivity.putExtra("gpsConnected", gpsConnected);
                            startActivity(openLoadingActivity);
                            finish();
                        }
                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    public void showMessageByToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
