package pe.cibertec.trabajofinalcibertec.data.cache;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;

/**
 * Created by USUARIO on 2/06/2017.
 */

public interface UsersCache {

    void list(Callback<List<UsersEntity>> callback);

    void put(List<UsersEntity> usersEntities);

    interface Callback<T>{
        void onSuccess(T t);
        void onError(Throwable e);
    }
}
