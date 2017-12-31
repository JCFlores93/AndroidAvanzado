package pe.cibertec.trabajofinalcibertec.data.cache;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;

/**
 * Created by USUARIO on 3/06/2017.
 */

public interface TasksCache {

    void list(Callback<List<TasksEntity>> callback);

    void put(List<TasksEntity> usersEntities);

    interface Callback<T>{
        void onSuccess(T t);
        void onError(Throwable e);
    }
}
