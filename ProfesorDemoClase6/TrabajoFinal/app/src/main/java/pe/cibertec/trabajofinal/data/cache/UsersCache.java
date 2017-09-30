package pe.cibertec.trabajofinal.data.cache;

import java.util.List;

import pe.cibertec.trabajofinal.data.entity.UsersEntity;

/**
 * Created by Android on 27/05/2017.
 */

public interface UsersCache {

    void list(Callback<List<UsersEntity>> callback);

    void put(List<UsersEntity> newsEntityList);

    interface Callback<T> {
        void onSuccess(T t);
        void onError(Throwable e);
    }
}
