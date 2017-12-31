package com.cesarynga.cleanarchitecture.data.net;

import com.cesarynga.cleanarchitecture.data.entity.UserEntity;

import java.util.List;

public interface RestApi {

    List<UserEntity> getUserList() throws Exception;

    UserEntity getUser(int userId) throws Exception;

}
