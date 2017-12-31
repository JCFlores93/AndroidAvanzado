package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.cache.TasksCache;
import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;

/**
 * Created by USUARIO on 4/06/2017.
 */

public interface TasksDataSource {

    //Llamamos este metodo para obtener las tareas
    void tasksEntityList(String user_token,DataSourceCallback<List<TasksEntity>> callback);
    //Llamamos este metodo para obtener una tarea
    void tasksEntity(String user_token,TasksEntity tasksEntity,DataSourceCallback<TasksEntity> callback);
    //Llamamos este metodo para crear una nueva tarea
    void tasksEntityInsert(String user_token,TasksEntity tasksEntity,DataSourceCallback<TasksEntity> callback);
    //Llamamos este metodo para modificar una tarea
    void tasksEntityUpdate(String user_token,TasksEntity tasksEntity,DataSourceCallback<TasksEntity> callback);
    //Llamamos este metodo para eliminar una tarea
    void tasksEntityDelete(String user_token,TasksEntity tasksEntity,DataSourceCallback<TasksEntity> callback);
}
