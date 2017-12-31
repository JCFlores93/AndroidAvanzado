package com.cesarynga.cleanarchitecture.data.repository;

import com.cesarynga.cleanarchitecture.data.entity.UserEntity;
import com.cesarynga.cleanarchitecture.data.entity.mapper.UserEntityDataMapper;
import com.cesarynga.cleanarchitecture.data.repository.datasource.DataSourceCallback;
import com.cesarynga.cleanarchitecture.data.repository.datasource.UserDataSource;
import com.cesarynga.cleanarchitecture.data.repository.datasource.UserDataSourceFactory;
import com.cesarynga.cleanarchitecture.domain.model.User;
import com.cesarynga.cleanarchitecture.domain.repository.RepositoryCallback;
import com.cesarynga.cleanarchitecture.domain.repository.UserRepository;

import java.util.List;

public class UserDataRepository implements UserRepository {

    private final UserDataSourceFactory userDataSourceFactory;
    private final UserEntityDataMapper userEntityDataMapper;

    public UserDataRepository(UserDataSourceFactory userDataSourceFactory,
                              UserEntityDataMapper userEntityDataMapper) {
        this.userDataSourceFactory = userDataSourceFactory;
        this.userEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public void users(final RepositoryCallback<List<User>> callback) {
        final UserDataSource userDataSource = userDataSourceFactory.create();
        userDataSource.userEntityList(new DataSourceCallback<List<UserEntity>>() {
            @Override
            public void onSuccess(List<UserEntity> response) {
                callback.onSuccess(userEntityDataMapper.transform(response));
            }

            @Override
            public void onError(Throwable exception) {
                callback.onError(exception);
            }
        });
    }

    @Override
    public void user(int userId, final RepositoryCallback<User> callback) {
        final UserDataSource userDataSource = userDataSourceFactory.create();
        userDataSource.userEntityDetails(userId, new DataSourceCallback<UserEntity>() {
            @Override
            public void onSuccess(UserEntity response) {
                callback.onSuccess(userEntityDataMapper.transform(response));
            }

            @Override
            public void onError(Throwable exception) {
                callback.onError(exception);
            }
        });
    }
}
