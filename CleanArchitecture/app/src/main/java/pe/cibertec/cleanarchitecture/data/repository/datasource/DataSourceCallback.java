package pe.cibertec.cleanarchitecture.data.repository.datasource;

/**
 * Created by Android on 27/05/2017.
 */

public interface DataSourceCallback<T> {

    void onSuccess(T t);

    void onError(Throwable exception);
}
