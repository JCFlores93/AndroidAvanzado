package com.cesarynga.cleanarchitecture.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cesarynga.cleanarchitecture.data.entity.UserEntity;
import com.cesarynga.cleanarchitecture.data.exception.NetworkConnectionException;
import com.cesarynga.cleanarchitecture.data.exception.UserNotFoundException;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiImpl implements RestApi {

    private final ApiService apiService;
    private final Context context;

    public RestApiImpl(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.apiService = retrofit.create(ApiService.class);
        this.context = context;
    }

    @Override
    public List<UserEntity> getUserList() throws Exception {
        if (isThereInternetConnection()) {
            Call<List<UserEntity>> call = apiService.userEntityList();
            Response<List<UserEntity>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new NetworkConnectionException();
            }
        } else {
            throw new NetworkConnectionException();
        }
    }

    @Override
    public UserEntity getUser(int userId) throws Exception {
        if (isThereInternetConnection()) {
            Call<UserEntity> call = apiService.userEntity(userId);
            Response<UserEntity> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new UserNotFoundException("User with id " + userId + " was not found");
            }
        } else {
            throw new NetworkConnectionException();
        }
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
