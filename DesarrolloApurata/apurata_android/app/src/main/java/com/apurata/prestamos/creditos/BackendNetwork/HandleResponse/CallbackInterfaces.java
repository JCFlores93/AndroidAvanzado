package com.apurata.prestamos.creditos.BackendNetwork.HandleResponse;

import android.content.Context;

import com.apurata.prestamos.creditos.RequestModels.CoordBGService;

import retrofit2.Call;


/**
 * Created by JEAN on 23/10/2017.
 */

public class CallbackInterfaces {
    public static <T> void enqueueWithRetryStream(Call<T> call, CoordBGService coord, Context context) {
        call.enqueue(new ManageCallbackStream<T>(call, coord, context));
    }

    public static <T> void enqueueWithRetryDb(Call<T> call, CoordBGService coord, Context context) {
        call.enqueue(new ManageCallbackDb<T>(call, coord, context));
    }

    public static <T> void enqueueWithRetryMessage(Call<T> call, String type) {
        call.enqueue(new ManageCallbackMessage(call, type));
    }
}
