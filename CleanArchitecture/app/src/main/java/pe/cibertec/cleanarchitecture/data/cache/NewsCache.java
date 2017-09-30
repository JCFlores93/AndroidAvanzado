package pe.cibertec.cleanarchitecture.data.cache;

import java.util.List;

import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;

/**
 * Created by Android on 27/05/2017.
 */

public interface NewsCache {

    void list(Callback<List<NewsEntity>> callback);

    void put(List<NewsEntity> newsEntityList);

    interface Callback<T> {
        void onSuccess(T t);
        void onError(Throwable e);
    }
}
