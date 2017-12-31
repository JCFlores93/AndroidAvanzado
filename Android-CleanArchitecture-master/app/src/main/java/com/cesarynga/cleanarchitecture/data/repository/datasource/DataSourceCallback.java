package com.cesarynga.cleanarchitecture.data.repository.datasource;

public interface DataSourceCallback<T> {

    void onSuccess(T response);

    void onError(Throwable exception);
}
