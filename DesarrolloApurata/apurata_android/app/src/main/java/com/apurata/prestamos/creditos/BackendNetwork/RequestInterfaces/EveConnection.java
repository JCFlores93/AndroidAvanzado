package com.apurata.prestamos.creditos.BackendNetwork.RequestInterfaces;

import com.apurata.prestamos.creditos.RequestModels.DataFromValidation;
import com.apurata.prestamos.creditos.RequestModels.UserStatus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by USUARIO on 2/06/2017.
 */


    public interface EveConnection {
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("lend")
        Call<DataFromValidation> insertUsers(@Body DataFromValidation items);

        @GET("users/{user_id}/status")
        Call<UserStatus> getUser(@Path("user_id") String userID);

    }

