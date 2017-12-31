package com.cesarynga.cleanarchitecture.data.repository.datasource;

import com.cesarynga.cleanarchitecture.data.entity.UserEntity;

import java.util.List;

public class DiskUserDataSource implements UserDataSource {

    @Override
    public void userEntityList(DataSourceCallback<List<UserEntity>> callback) {

    }

    @Override
    public void userEntityDetails(int userId, DataSourceCallback<UserEntity> callback) {

    }
}
