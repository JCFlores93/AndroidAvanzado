package com.apurata.prestamos.creditos.BackendNetwork.RequestInterfaces;

import com.apurata.prestamos.creditos.RequestModels.SessionEventLocation;
import com.apurata.prestamos.creditos.RequestModels.SessionEventMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApurataConnection {
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("api/v1/events")
    Call<SessionEventLocation> postEventLocation(@Body SessionEventLocation event);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("api/v1/events")
    Call<SessionEventLocation> postEventMessage (@Body SessionEventMessage event);
}

