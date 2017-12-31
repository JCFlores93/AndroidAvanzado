package com.cesarynga.cleanarchitecture.domain.repository;

import com.cesarynga.cleanarchitecture.domain.model.User;

import java.util.List;

public interface UserRepository {

    void users(RepositoryCallback<List<User>> callback);

    void user(int userId, RepositoryCallback<User> callback);
}
