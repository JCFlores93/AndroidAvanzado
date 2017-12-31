package com.apurata.prestamos.creditos.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apurata.prestamos.creditos.R;
import com.apurata.prestamos.creditos.RequestModels.Coord;
import com.apurata.prestamos.creditos.RequestModels.UserStatus;
import com.apurata.prestamos.creditos.Service.GpsBackgroundService;
import com.apurata.prestamos.creditos.BackendNetwork.RequestInterfaces.EveConnection;
import com.apurata.prestamos.creditos.Models.User;
import com.apurata.prestamos.creditos.BackendNetwork.EventSender;
import com.apurata.prestamos.creditos.BackendNetwork.BackendConnection;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.apurata.prestamos.creditos.Service.GpsBackgroundService.PREV_PREF;


public class LoadingActivity extends AppCompatActivity {

    private ProgressBar pbMain;
    private TextView errorMessageNull;
    private Button btnGoToApurataCom;
    private View parentView;
    private UserStatus newStatus;
    private Intent intentOpenWindow;
    private boolean gpsStatus;
    private int responseCode = 200;
    private static final int PERMISSION_GPS_REQUEST_CODE = 10;
    private Location location;
    private LocationListener locationListener;
    private String brandCellphone, modelCellphone, versionCellphone, statusValidation;
    private ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
    private ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();
    private int widthSizeScreen, heightSizeScreen, pxWidthFrontCamera, pxHeightFrontCamera, pxWidthBackCamera, pxHeightBackCamera;
    private MyCameraAsynTask myCameraAsynTask;
    boolean responseValidation = false, responseCamera;
    private Coord coord = null;
    private String TAG_MAIN = "LOADING_ACTIVITY";
    private boolean gpsConnected;
    Coord coordLoginActivity = null;
    LocationManager service;
    User user = new User();
    public static final String OBJECT_STATUS = "STATUS";
    Intent trackGpsIntent;
    private int PERMISSION_CAMERA_REQUEST_CODE = 11;
    private int PERMISSION_EXTERNAL_REQUEST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        parentView = findViewById(R.id.parentView);
        pbMain = (ProgressBar) findViewById(R.id.pbMain);
        errorMessageNull = (TextView) findViewById(R.id.txtMessageNull);
        btnGoToApurataCom = (Button) findViewById(R.id.btnGoToApurataCom);
        Bundle bundle = getIntent().getExtras();
        trackGpsIntent = new Intent(LoadingActivity.this, GpsBackgroundService.class);

        if (bundle.getBoolean(OBJECT_STATUS)) {
            user.setId(bundle.getString("id"));
            if (bundle.getString("email") != null) {
                user.setEmail(bundle.getString("email"));
            }
            if (bundle.getString("name") != null) {
                user.setFirst_name(bundle.getString("name"));
            }
            gpsConnected = bundle.getBoolean("gpsConnected");
            logUser(user);
        } else {
            user.setId(bundle.getString("id"));
            user.setEmail("No se encontró");
            user.setFirst_name("No se encontró");
            logUser(user);
            gpsConnected = bundle.getBoolean("gpsConnected");
        }

        pbMain.setVisibility(View.VISIBLE);
        coord = checkPermissionForGPS();

        myCameraAsynTask = new MyCameraAsynTask(this);
        myCameraAsynTask.execute();
    }


    public void getStatusUser(String userID) {
        final EveConnection userService = BackendConnection.getLendService();
        userService.getUser(userID).enqueue(new Callback<UserStatus>() {
            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {
                newStatus = response.body();
                switch (response.code()) {
                    case 200:
                        if (newStatus.getAproval_status().compareTo("APROVED") == 0 &&
                                (newStatus.getFunding_status().compareTo("WAITING_DATA") == 0) || (newStatus.getFunding_status().compareTo("RETRY_ADD_INFO") == 0)) {
                            Date currentTime = new Date();
                            SharedPreferences settings;
                            settings = getSharedPreferences(PREV_PREF, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("startedService", false);
                            editor.commit();
                            EventSender eventSender = new EventSender();
                            eventSender.sendMessage("Starting GPS Service");
                            // Should start track gps service, but need to know if a previous service is running
                            trackGpsIntent.putExtra("userId", user.getId());
                            startService(trackGpsIntent);
                            responseCode = 200;
                            statusValidation = "ValidationActivity";
                            responseValidation = true;
                            intentOpenWindow = new Intent(getApplicationContext(), ValidationActivity.class);
                        } else {
                            if (newStatus.getAproval_status().compareTo("") == 0 && newStatus.getFunding_status().compareTo("") == 0) {
                                responseCode = 200;
                                statusValidation = "ReturnToWebActivity";
                                responseValidation = true;
                                errorMessageNull.setVisibility(View.VISIBLE);
                                btnGoToApurataCom.setVisibility(View.VISIBLE);
                                pbMain.setVisibility(View.GONE);
                            } else {
                                responseCode = 200;
                                statusValidation = "ReturnToWebActivity";
                                responseValidation = true;
                            }
                        }
                        break;
                    case 404:
                        responseCode = 404;
                        responseValidation = true;
                        intentOpenWindow = new Intent(getApplicationContext(), ReturnToWebActivity.class);
                        intentOpenWindow.putExtra("CODE", responseCode);
                        intentOpenWindow.putExtra("id", user.getId());
                        if(user.getEmail() != null) {
                            intentOpenWindow.putExtra("email", user.getEmail());
                        }
                        if (user.getFirst_name() != null){
                            intentOpenWindow.putExtra("name", user.getFirst_name());
                        }
                        startActivity(intentOpenWindow);
                        break;
                    default:
                        if (response.code() >= 500) {
                            responseValidation = true;
                            responseCode = response.code();
                            intentOpenWindow = new Intent(getApplicationContext(), ReturnToWebActivity.class);
                            intentOpenWindow.putExtra("CODE", responseCode);
                            intentOpenWindow.putExtra("id", user.getId());
                            if(user.getEmail() != null) {
                                intentOpenWindow.putExtra("email", user.getEmail());
                            }
                            if (user.getFirst_name() != null){
                                intentOpenWindow.putExtra("name", user.getFirst_name());
                            }
                            startActivity(intentOpenWindow);
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
            }
        });
    }

    public Coord checkPermissionForGPS() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Snackbar.make(parentView, "Debe activar el permiso de GPS", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Activar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(LoadingActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_GPS_REQUEST_CODE);
                            }
                        }).show();
            }


        } else {
            service = (LocationManager) getSystemService(LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location newLocation) {
                    location = newLocation;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(getApplicationContext(), "Es necesario encender el GPS", Toast.LENGTH_LONG);
                    finish();
                }
            };

        }

        trackGpsIntent.putExtra("userId", user.getId());
        startService(trackGpsIntent);

        service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
        service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
        if (location == null) {
            service = (LocationManager) getSystemService(LOCATION_SERVICE);
            location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        coordLoginActivity = new Coord();
        if (location != null) {
            coordLoginActivity.setSpeed(location.getSpeed());
            coordLoginActivity.setAccuracy(location.getAccuracy());
            coordLoginActivity.setAltitude(Float.parseFloat(String.valueOf(location.getAltitude())));
            coordLoginActivity.setLongitude(Float.parseFloat(String.valueOf(location.getLongitude())));
            coordLoginActivity.setLatitude(Float.parseFloat(String.valueOf(location.getLatitude())));
        }
        return coordLoginActivity;
    }

    private class MyCameraAsynTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<LoadingActivity> activityRef;

        private MyCameraAsynTask(LoadingActivity activityRef) {
            this.activityRef = new WeakReference<LoadingActivity>(activityRef);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (user.getId().compareTo("1") != 0) {
                getStatusUser(user.getId());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    while (coord == null) {
                        coord = checkPermissionForGPS();
                    }
                }
            });
            if (coord != null) {
                if (user.getId().compareTo("1") != 0) {
                    while (responseValidation == false || coord == null) {
                    }
                }

            } else {
                coord.setAccuracy(1);
                coord.setAltitude(1);
                coord.setAltitudeAccuracy(1);
                coord.setHeading("1");
                coord.setLatitude(1);
                coord.setLongitude(1);
                coord.setSpeed(1);
                if (user.getId().compareTo("1") != 0) {
                    while (!responseValidation) {
                    }
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (user.getId().compareTo("1") != 0) {
                if (responseCode == 200 && statusValidation.compareTo("ReturnToWebActivity") != 0) {
                    Bundle b = new Bundle();
                    b.putParcelable("Coordinate", coord);
                    intentOpenWindow.putExtra("bundle", b);
                    gpsStatus = true;
                    intentOpenWindow.putExtra("GPS", gpsStatus);
                    long millis = new Date().getTime();
                    pbMain.setVisibility(View.GONE);
                    intentOpenWindow.putExtra("TimeStapLoginFacebook", millis);
                    intentOpenWindow.putExtra("gpsConnected", gpsConnected);
                    intentOpenWindow.putExtra("id", user.getId());
                    if (user.getEmail() != null) {
                        intentOpenWindow.putExtra("email", user.getEmail());
                    }
                    if(user.getFirst_name() != null) {
                        intentOpenWindow.putExtra("name", user.getFirst_name());
                    }
                    pbMain.setVisibility(View.GONE);
                    startActivity(intentOpenWindow);
                    finish();
                } else {

                    if (responseCode == 200 && statusValidation.compareTo("ReturnToWebActivity") == 0) {
                        Intent userMenu = new Intent(getApplicationContext(), UserMenuActivity.class);
                        if (newStatus.getAproval_status().compareTo("") == 0 && newStatus.getFunding_status().compareTo("") == 0) {
                            pbMain.setVisibility(View.GONE);
                            errorMessageNull.setVisibility(View.VISIBLE);
                            btnGoToApurataCom.setVisibility(View.VISIBLE);
                            errorMessageNull.setText(getResources().getString(R.string.checkStatus));
                            startActivity(userMenu);
                            finish();
                        } else {
                            pbMain.setVisibility(View.GONE);
                            errorMessageNull.setVisibility(View.VISIBLE);
                            btnGoToApurataCom.setVisibility(View.VISIBLE);
                            errorMessageNull.setText(getResources().getString(R.string.checkStatus));
                            startActivity(userMenu);
                            finish();
                        }
                    }
                }
            } else {
                Intent intentOpenWindow = new Intent(getApplicationContext(), ValidationActivity.class);
                if (pxWidthFrontCamera != 0 && pxHeightFrontCamera != 0) {
                    intentOpenWindow.putExtra("pxWidthFrontCamera", pxWidthFrontCamera);
                    intentOpenWindow.putExtra("pxHeightFrontCamera", pxHeightFrontCamera);
                }
                intentOpenWindow.putExtra("pxWidthBackCamera", pxWidthBackCamera);
                intentOpenWindow.putExtra("pxHeightBackCamera", pxHeightBackCamera);
                intentOpenWindow.putExtra("widthSizeScreen", widthSizeScreen);
                intentOpenWindow.putExtra("heightSizeScreen", heightSizeScreen);
                intentOpenWindow.putExtra("brandCellphone", brandCellphone);
                intentOpenWindow.putExtra("modelCellphone", modelCellphone);
                intentOpenWindow.putExtra("versionCellphone", versionCellphone);
                Bundle b = new Bundle();
                b.putParcelable("Coordinate", coord);
                intentOpenWindow.putExtra("bundle", b);
                gpsStatus = true;
                intentOpenWindow.putExtra("GPS", gpsStatus);
                long millis = new Date().getTime();
                pbMain.setVisibility(View.GONE);
                intentOpenWindow.putExtra("TimeStapLoginFacebook", millis);
                intentOpenWindow.putExtra("gpsConnected", gpsConnected);
                intentOpenWindow.putExtra("id", user.getId());
                if(user.getEmail() != null) {
                    intentOpenWindow.putExtra("email", user.getEmail());
                }
                if (user.getFirst_name() != null){
                    intentOpenWindow.putExtra("name", user.getFirst_name());
                }
                pbMain.setVisibility(View.GONE);
                startActivity(intentOpenWindow);
                finish();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        service.removeUpdates(locationListener);
        myCameraAsynTask.cancel(true);
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        moveTaskToBack(true);
        finish();
        return super.moveTaskToBack(nonRoot);

    }

    private void logUser(User user) {
        String userData_ID = user.getId();
        String userData_Email = " ";
        String userData_FirstName = "";
        if (user.getEmail() != null) {
            userData_Email = user.getEmail();
        }
        if (user.getFirst_name() != null) {
            userData_FirstName = user.getFirst_name();
        }
        Crashlytics.setUserIdentifier(userData_ID);
        Crashlytics.setUserEmail(userData_Email);
        Crashlytics.setUserName(userData_FirstName);
    }

}
