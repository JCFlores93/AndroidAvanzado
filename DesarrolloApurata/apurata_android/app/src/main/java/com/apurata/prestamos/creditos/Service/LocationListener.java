package com.apurata.prestamos.creditos.Service;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by apurata on 10/19/17.
 */

public class LocationListener implements android.location.LocationListener {

    private static final String TAG = "GPS_TRACKER";

    public LocationListener(String provider)
    {
        Log.e(TAG, "LocationListener " + provider);
    }

    public interface ProviderListener{
        public void providerLocationIsEnable(String provider);
    }

    private ProviderListener provListener = null;

    public void registerListener(ProviderListener provider){
        Log.i(TAG, "is registering a new listener");
        provListener = provider;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        GpsBackgroundService.globalLocation = location;
        Log.i(TAG, "onLocationChanged: " + location);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Log.e(TAG, "onProviderDisabled: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        if (provListener != null) {
            provListener.providerLocationIsEnable(provider);
        }
        else {
            Log.e(TAG, "Location provider listener is null");
        }
        Log.e(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.e(TAG, "onStatusChanged: " + provider + ". New status: " + status);
    }
}

