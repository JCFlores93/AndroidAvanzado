package com.cesarynga.cleanarchitecture.data.entity.mapper;

import com.cesarynga.cleanarchitecture.data.entity.UserEntity;
import com.cesarynga.cleanarchitecture.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserEntityDataMapper {

    public UserEntityDataMapper() {
    }

    public User transform(UserEntity userEntity) {
        User user = new User(userEntity.getId());
        user.setName(userEntity.getName());
        user.setUsername("@" + userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        return user;
    }

    public List<User> transform(List<UserEntity> userEntityList) {
        List<User> userList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList) {
            userList.add(transform(userEntity));
        }
        return userList;
    }
}
