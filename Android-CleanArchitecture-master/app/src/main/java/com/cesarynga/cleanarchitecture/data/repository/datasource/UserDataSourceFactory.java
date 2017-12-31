package com.cesarynga.cleanarchitecture.data.repository.datasource;

import android.content.Context;

import com.cesarynga.cleanarchitecture.data.net.RestApi;
import com.cesarynga.cleanarchitecture.data.net.RestApiImpl;

public class UserDataSourceFactory {

    private final Context context;

    public UserDataSourceFactory(Context context) {
        this.context = context;
    }

    public UserDataSource create() {
        RestApi restApi = new RestApiImpl(context);
        return new NetworkUserDataSource(restApi);
    }
}
