package com.apurata.prestamos.creditos.BackendNetwork.HandleResponse;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JEAN on 20/10/2017.
 */


public abstract class CallbackWithRetry<T> implements Callback<T> {

    private static final int TOTAL_RETRIES = 3;
    private static final String TAG = "GPS_TRACKER_SEND_EVENT";
    private final Call<T> call;
    public static int retryCount = 0;
    final Handler handler = new Handler(Looper.getMainLooper());

    public CallbackWithRetry(Call<T> call) {
        this.call = call;
    }

    public abstract void neverSuccess();

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG, t.getLocalizedMessage());
        retryCount++;
        if (retryCount >= TOTAL_RETRIES) {
            neverSuccess();
            Log.i(TAG, "overpass total retries");
        }
        else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.v(TAG, "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                    retry();
                }
            }, 2000);
        }
    }
    private void retry() {
        call.clone().enqueue(this);
    }
}
