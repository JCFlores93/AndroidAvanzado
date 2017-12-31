package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.cache.TasksCache;
import pe.cibertec.trabajofinalcibertec.data.cache.UsersCache;
import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;

/**
 * Created by USUARIO on 4/06/2017.
 */

public class DiskTasksDataSource implements TasksDataSource{

    private final TasksCache tasksCache;

    public DiskTasksDataSource(TasksCache tasksCache) {
        this.tasksCache = tasksCache;
    }

    @Override
    public void tasksEntityList(String user_token, final DataSourceCallback<List<TasksEntity>> callback) {
        this.tasksCache.list(new TasksCache.Callback<List<TasksEntity>>() {
            @Override
            public void onSuccess(List<TasksEntity> tasksEntityList) {
                callback.onSuccess(tasksEntityList);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);

            }
        });
    }

    @Override
    public void tasksEntity(String user_token, TasksEntity tasksEntity, DataSourceCallback<TasksEntity> callback) {

    }

    @Override
    public void tasksEntityInsert(String user_token, TasksEntity tasksEntity, DataSourceCallback<TasksEntity> callback) {

    }

    @Override
    public void tasksEntityUpdate(String user_token, TasksEntity tasksEntity, DataSourceCallback<TasksEntity> callback) {

    }

    @Override
    public void tasksEntityDelete(String user_token, TasksEntity tasksEntity, DataSourceCallback<TasksEntity> callback) {

    }


}
