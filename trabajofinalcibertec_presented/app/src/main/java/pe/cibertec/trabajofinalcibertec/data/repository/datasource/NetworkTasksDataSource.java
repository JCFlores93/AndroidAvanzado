package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.cache.TasksCache;
import pe.cibertec.trabajofinalcibertec.data.cache.UsersCache;
import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.net.RestApi;

/**
 * Created by USUARIO on 4/06/2017.
 */

public class NetworkTasksDataSource implements TasksDataSource{


    private final RestApi restApi;
    private final TasksCache tasksCache;

    public NetworkTasksDataSource(RestApi restApi, TasksCache tasksCache) {
        this.restApi = restApi;
        this.tasksCache = tasksCache;
    }

    @Override
    public void tasksEntityList(String user_token, final DataSourceCallback<List<TasksEntity>> callback) {
        this.restApi.getTasksList(user_token, new RestApi.Callback<List<TasksEntity>>() {
            @Override
            public void onSuccess(List<TasksEntity> response) {
                tasksCache.put(response);
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void tasksEntity(String user_token, TasksEntity tasksEntity, final DataSourceCallback<TasksEntity> callback) {
        this.restApi.getTask(user_token, tasksEntity, new RestApi.Callback<TasksEntity>() {
            @Override
            public void onSuccess(TasksEntity response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void tasksEntityInsert(String user_token, TasksEntity tasksEntity, final DataSourceCallback<TasksEntity> callback) {
            this.restApi.insertTask(user_token, tasksEntity, new RestApi.Callback<TasksEntity>() {
                @Override
                public void onSuccess(TasksEntity response) {
                    callback.onSuccess(response);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
    }

    @Override
    public void tasksEntityUpdate(String user_token, TasksEntity tasksEntity, final DataSourceCallback<TasksEntity> callback) {
        this.restApi.updateTask(user_token, tasksEntity, new RestApi.Callback<TasksEntity>() {
            @Override
            public void onSuccess(TasksEntity response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void tasksEntityDelete(String user_token, TasksEntity tasksEntity, final DataSourceCallback<TasksEntity> callback) {
        this.restApi.deleteTask(user_token, tasksEntity, new RestApi.Callback<TasksEntity>() {
            @Override
            public void onSuccess(TasksEntity response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
