package com.cesarynga.cleanarchitecture.domain.repository;

public interface RepositoryCallback<T> {

    void onSuccess(T response);

    void onError(Throwable exception);
}
