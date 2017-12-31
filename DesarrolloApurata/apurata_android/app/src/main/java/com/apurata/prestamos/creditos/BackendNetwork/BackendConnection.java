package com.apurata.prestamos.creditos.BackendNetwork;

import com.apurata.prestamos.creditos.BackendNetwork.RequestInterfaces.ApurataConnection;
import com.apurata.prestamos.creditos.BackendNetwork.RequestInterfaces.EveConnection;
import com.apurata.prestamos.creditos.Utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USUARIO on 2/06/2017.
 */

public class BackendConnection {

    private static EveConnection lendService;
    private static ApurataConnection httpApurata;
    public static Retrofit retrofit;
    public static Retrofit retrofit_apurata;

    public static EveConnection getLendService(){
        if (lendService == null){
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100,TimeUnit.SECONDS)
                    .build();
             retrofit= new Retrofit.Builder()
                    .baseUrl(Constants.EVE_URL)
                     .client(client )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            lendService = retrofit.create(EveConnection.class);
        }
        return  lendService;
    }

    public static ApurataConnection conApurata(){
        if (httpApurata == null){
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100,TimeUnit.SECONDS)
                    .build();
            retrofit_apurata = new Retrofit.Builder()
                    .baseUrl(Constants.APURATA_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            httpApurata = retrofit_apurata.create(ApurataConnection.class);
        }
        return  httpApurata;
    }


}
