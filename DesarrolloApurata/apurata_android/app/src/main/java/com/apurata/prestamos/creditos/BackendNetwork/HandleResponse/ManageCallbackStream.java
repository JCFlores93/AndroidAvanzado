package com.apurata.prestamos.creditos.BackendNetwork.HandleResponse;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import com.apurata.prestamos.creditos.LocalStorage.Operations.CoordSQLite;
import com.apurata.prestamos.creditos.RequestModels.CoordBGService;


/**
 * Created by apurata on 10/25/17.
 */

public class ManageCallbackStream<T> extends CallbackWithRetry {

    private static final String TAG = "GPS_TRACKER";
    private CoordSQLite coordSQLite;
    private CoordBGService coord;

    public ManageCallbackStream(Call call, CoordBGService coord, Context context) {
        super(call);
        this.coord = coord;
        this.coordSQLite = new CoordSQLite(context);
    }

    @Override
    public void neverSuccess() {
        Log.i("GPS_TRACKER", "No success so insert in db");
        coordSQLite.insertCoord(coord);
    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.e(TAG, "in Response GPS stream");
        Log.i(TAG, String.valueOf(response.code()) + " on #event " +
                String.valueOf(this.coord.getCounter()));
    }
}
