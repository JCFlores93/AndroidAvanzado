package com.apurata.prestamos.creditos.Service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.apurata.prestamos.creditos.LocalStorage.Operations.CoordSQLite;
import com.apurata.prestamos.creditos.RequestModels.CoordBGService;
import com.apurata.prestamos.creditos.BackendNetwork.EventSender;
import com.apurata.prestamos.creditos.Utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.apurata.prestamos.creditos.Utils.Constants.FIRST_PERIOD_TIME_DAYS;
import static com.apurata.prestamos.creditos.Utils.Constants.LOCATION_DISTANCE;
import static com.apurata.prestamos.creditos.Utils.Constants.LOCATION_INTERVAL;
import static com.apurata.prestamos.creditos.Utils.Constants.SECOND_PERIOD_TIME_DAYS;
import static com.apurata.prestamos.creditos.Utils.Constants.SEND_DATA_FIRST_INTERVAL;
import static com.apurata.prestamos.creditos.Utils.Constants.SEND_DATA_SECOND_INTERVAL;

public class GpsBackgroundService extends Service implements LocationListener.ProviderListener,
        NetworkChangeReceiver.ProviderConnectionListener {
    private static final String TAG = "GPS_TRACKER";
    public static final String PREV_PREF = "PREV_PREF";
    public static final String PREV_PREF_ID = "uid";
    public static final String GLOBAL_PREF = "GLOBAL_PREF";
    public static int DELAY = 5 * 1000;
    public static String NOT_DEFINED_DATE = "";

    public static Location globalLocation;
    private CoordSQLite coordSQLite;
    private String userId;
    private SharedPreferences settings;
    private Date currentTime;
    private Date firstPeriod = null, secondPeriod = null;
    private Timer firstTimer = null, secondTimer = null;
    private LocationManager mLocationManager = null;
    LocationListener[] mLocationListeners;
    private SharedPreferences globalSettings;
    private int counterGps;
    private EventSender eventSender;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        globalLocation = null;
        eventSender = new EventSender();
        mLocationListeners = new LocationListener[]{
                new LocationListener(LocationManager.GPS_PROVIDER)
        };
        mLocationListeners[0].registerListener(this);

        NetworkChangeReceiver mNetworkChangeReceiver = new NetworkChangeReceiver();
        mNetworkChangeReceiver.registerListener(this);
        registerReceiver(mNetworkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        settings = getSharedPreferences(PREV_PREF, 0);
        globalSettings = getSharedPreferences(GLOBAL_PREF, Context.MODE_PRIVATE);
        /*
            might be confusing:
            startedService != previouslyStartedService:
            startedService restart each time user logs
        */
        Boolean startedService = settings.getBoolean("startedService", false);
        Boolean prevStartedService = globalSettings.getBoolean("prevStartedService", false);

        if (!prevStartedService) {
            SharedPreferences.Editor edit = settings.edit();
            edit.putBoolean("prevStartedService", Boolean.TRUE);
            edit.putInt("counterGps", 0);
            edit.commit();
        }

        currentTime = new Date();
        coordSQLite = new CoordSQLite(getApplicationContext());
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

        if (!startedService) {
            Log.i(TAG, "service not started");
            setPreferences(currentTime);
            pullPeriods();
            // start 1st timer
            firstTimer = new Timer();
            firstTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    storeLastLocation();
                    checkTimer();
                }
            }, DELAY, SEND_DATA_FIRST_INTERVAL);
        } else {
            Log.i(TAG, "service started");
            pullPeriods();
            if (firstPeriod == null || secondPeriod == null) {
                Log.w(TAG, "No parsed periods");
            }
            if (currentTime.before(firstPeriod)) {
                Log.i(TAG, "main: First period");
                // start 1st timer
                firstTimer = new Timer();
                firstTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        storeLastLocation();
                        checkTimer();
                    }
                }, DELAY, SEND_DATA_FIRST_INTERVAL);
            }
            if (currentTime.after(firstPeriod) && currentTime.before(secondPeriod)) {
                Log.d(TAG, "main: Second period");
                // start 2nd timer
                secondTimer = new Timer();
                secondTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        storeLastLocation();
                        checkTimer();
                    }
                }, DELAY, SEND_DATA_SECOND_INTERVAL);
            }
            if (currentTime.after(secondPeriod)) {
                // kill service
                Log.d(TAG, "main: Kill service");
                endService();
            }
        }
    }

    public void endService() {
        Log.e(TAG, "Ending Service");
        mLocationManager.removeUpdates(mLocationListeners[0]);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void sendDataToServer(List<CoordBGService> coords) {
        for (CoordBGService coord : coords) {
            Log.i(TAG, "coord counter " + String.valueOf(coord.getCounter()));
            eventSender.sendEventGpsFromDB(coord, getApplicationContext());
        }
    }

    private void storeLastLocation() {
        /* core gps tracking function  */
        /* if trackLocation is enable send data or save it to send when reconnect network */
        Log.i(TAG, "Store last location");

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGpsEnabled) {
            CoordBGService coordBGService;

            counterGps = globalSettings.getInt("counterGPS", 0) + 1;
            SharedPreferences.Editor editor = globalSettings.edit();
            editor.putInt("counterGPS", counterGps);
            editor.commit();
            Log.i(TAG, "the counter gps is: " + counterGps);

            if (globalLocation != null) {
                Log.i(TAG, "global location is not null");
                coordBGService = new CoordBGService(globalLocation);
                coordBGService.setCounter(counterGps);
            } else {
                initializeLocationManager();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation == null) {
                    eventSender.sendMessage("Couldn't get lastKnownLocation");
                    return;
                }
                Log.i(TAG, "lastKnownLocation" + lastKnownLocation);
                coordBGService = new CoordBGService(lastKnownLocation);
                coordBGService.setCounter(counterGps);
            }
            Log.i(TAG, "database size " + coordSQLite.size());
            if (isNetworkAvailable()) {
                eventSender.sendEventGpsStream(coordBGService, getApplicationContext());
                if (coordSQLite.size() > 1) {
                    sendDataToServer(coordSQLite.listCoords());
                    Log.i(TAG, "sendDataToServer");
                }
            } else {
                coordSQLite.insertCoord(coordBGService);
                Log.d(TAG, "Now there is :" + coordSQLite.size());
            }
        } else {
            Log.i(TAG, "with or without network, location is not enable");
            if (isNetworkAvailable()) {
                eventSender.sendMessage("Location is not enable");
            } else {
                Log.i(TAG, "location is not enable neither is network");
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        if (intent != null) {
            Log.d(TAG, String.valueOf(intent));
            Bundle bundle = intent.getExtras();
            if (intent.getStringExtra("userId") != null) {
                userId = bundle.getString("userId");
            }
        }

        if (userId == null) { // when service restart, when is background
            userId = settings.getString(PREV_PREF_ID, "");
        } else { // each time user logs in
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREV_PREF_ID, userId);
            editor.commit();
        }
        eventSender.setUserId(userId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext()
                    .getSystemService(Context.LOCATION_SERVICE);

        }
    }

    public void setPreferences(Date current_time) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SharedPreferences.Editor editor = settings.edit();

        Date firstPeriodTrack = Utils.addDays(current_time, FIRST_PERIOD_TIME_DAYS);
        String firstPeriodTrackStr = dateFormat.format(firstPeriodTrack);
        editor.putString("firstPeriodGPS", firstPeriodTrackStr);


        Date secondPeriodTrack = Utils.addDays(firstPeriodTrack, SECOND_PERIOD_TIME_DAYS);
        String secondPeriodTrackStr = dateFormat.format(secondPeriodTrack);
        editor.putString("secondPeriodGPS", secondPeriodTrackStr);

        editor.putBoolean("startedService", true);
        Log.i(TAG, "Periods: " + firstPeriodTrackStr + " " + secondPeriodTrackStr);
        editor.commit();
    }


    public void pullPeriods() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String firstPeriodTrackStr = settings.getString("firstPeriodGPS", NOT_DEFINED_DATE);
        String secondPeriodTrackStr = settings.getString("secondPeriodGPS", NOT_DEFINED_DATE);
        try {
            firstPeriod = dateFormat.parse(firstPeriodTrackStr);
            secondPeriod = dateFormat.parse(secondPeriodTrackStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void checkTimer() {
        currentTime = new Date();
        if (currentTime.before(firstPeriod)) {
            // checkTimer: first period
            Log.d(TAG, "First period");
        }

        if (currentTime.after(firstPeriod) && currentTime.before(secondPeriod)) {
            // checkTimer: first period
            Log.d(TAG, "Second period");
            if (firstTimer != null) {
                firstTimer.cancel();
            }
            if (secondTimer == null) {
                secondTimer = new Timer();
                secondTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        storeLastLocation();
                        checkTimer();
                    }
                }, DELAY, SEND_DATA_SECOND_INTERVAL);
            }
        }
        if (currentTime.after(secondPeriod)) {
            // checkTimer: end of service
            Log.d(TAG, "checkTimer: end of service");
            if (secondTimer != null) {
                secondTimer.cancel();
            }
            endService();
        }
    }

    @Override
    public void providerLocationIsEnable(String provider) {
        Log.i(TAG, "LOCATION IS ENABLED GONNA SEND");
        storeLastLocation();
    }

    @Override
    public void providerNetworkIsEnable() {
        Log.i(TAG, "NETWORK IS ENABLED GONNA SEND");
        globalLocation = getLastKnownLocation();
        storeLastLocation();
    }

    @Override
    public void networkIsJustDisconnected() {
        Log.i(TAG, "Network is just disconnected");
        // we could store this on db
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // mock location object
                // TODO: move mock location or change logic to don't need to send a location
                Location location = null;
                location.setTime(1509060000);
                location.setSpeed(1);
                location.setAccuracy(1);
                location.setAltitude(1);
                location.setLongitude(1);
                location.setLatitude(1);
                return location;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}