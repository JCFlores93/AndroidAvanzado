package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import android.content.Context;

import pe.cibertec.trabajofinalcibertec.data.cache.TasksCache;
import pe.cibertec.trabajofinalcibertec.data.cache.TasksCacheImpl;
import pe.cibertec.trabajofinalcibertec.data.cache.UsersCache;
import pe.cibertec.trabajofinalcibertec.data.cache.UsersCacheImpl;
import pe.cibertec.trabajofinalcibertec.data.net.RestApi;
import pe.cibertec.trabajofinalcibertec.data.net.RestApiImpl;

/**
 * Created by USUARIO on 4/06/2017.
 */

public class TasksDataSourceFactory {

    private final Context context;

    public TasksDataSourceFactory(Context context) {
        this.context = context;
    }

    public TasksDataSource createNetworkDataSource(){
        RestApi restApi = new RestApiImpl(context);
        TasksCache tasksCache = new TasksCacheImpl();
        return new NetworkTasksDataSource(restApi,tasksCache);
    }

    public TasksDataSource createDiskDataSource(){
        TasksCache tasksCache = new TasksCacheImpl();
        return new DiskTasksDataSource(tasksCache);
    }
}
