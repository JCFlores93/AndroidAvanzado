package com.apurata.prestamos.creditos.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


/**
 * Created by apurata on 10/23/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    public interface ProviderConnectionListener{
        public void providerNetworkIsEnable();
        public void networkIsJustDisconnected();
    }

    private ProviderConnectionListener provListener = null;

    public void registerListener(ProviderConnectionListener provider){
        Log.d("GPS_TRACKER", "is registering a new listener");
        provListener = provider;
    }

    @Override
    public void onReceive( Context context, Intent intent )
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (provListener != null) {
            if (activeNetInfo != null) {
                provListener.providerNetworkIsEnable();
                Log.i("GPS_TRACKER", "ACTIVE NETWORK");
                Log.i("GPS_TRACKER", "Active Network Type : " + activeNetInfo.getTypeName());
            } else {
                Log.i("GPS_TRACKER", "NETWORK IS JUST DISCONNECTED");
                provListener.networkIsJustDisconnected();
            }
        }
    }
}
