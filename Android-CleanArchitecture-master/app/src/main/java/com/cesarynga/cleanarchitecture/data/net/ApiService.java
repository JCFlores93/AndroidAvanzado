package com.cesarynga.cleanarchitecture.data.net;

import com.cesarynga.cleanarchitecture.data.entity.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "https://raw.githubusercontent.com/CesarYnga/SampleData/master/Clean-Architecture/";

    @GET("users.json")
    Call<List<UserEntity>>  userEntityList();

    @GET("user_{userId}.json")
    Call<UserEntity> userEntity(@Path("userId") int  userId);

}
