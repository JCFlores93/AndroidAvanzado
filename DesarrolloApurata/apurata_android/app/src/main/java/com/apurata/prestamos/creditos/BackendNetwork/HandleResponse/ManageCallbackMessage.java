package com.apurata.prestamos.creditos.BackendNetwork.HandleResponse;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by apurata on 10/25/17.
 */

public class ManageCallbackMessage extends CallbackWithRetry {

    private static final String TAG = "GPS_TRACKER";
    private String type;

    public ManageCallbackMessage(Call call, String type) {
        super(call);
        this.type = type;
    }

    @Override
    public void neverSuccess() {
        Log.w(TAG, "in Response " + type);
    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.w(TAG, "in Response " + type);
    }
}
