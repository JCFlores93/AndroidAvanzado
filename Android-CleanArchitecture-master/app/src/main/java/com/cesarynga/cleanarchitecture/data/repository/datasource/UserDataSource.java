package com.cesarynga.cleanarchitecture.data.repository.datasource;

import com.cesarynga.cleanarchitecture.data.entity.UserEntity;

import java.util.List;

public interface UserDataSource {

    void userEntityList(DataSourceCallback<List<UserEntity>> callback);

    void userEntityDetails(int userId, DataSourceCallback<UserEntity> callback);
}
