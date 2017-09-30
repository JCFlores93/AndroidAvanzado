package pe.cibertec.cleanarchitecture.data.net;

import java.util.List;

import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;

/**
 * Created by Android on 27/05/2017.
 */

public interface RestApi {

    void getNewsList(Callback<List<NewsEntity>> callback);

    interface Callback<T> {
        void onSuccess(T response);

        void onError(Throwable e);
    }
}
