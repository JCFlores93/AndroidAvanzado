package pe.cibertec.trabajofinalcibertec.domain.repository;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;

/**
 * Created by USUARIO on 4/06/2017.
 */

public interface TasksRepository {

    //Mandamos el token y la tarea a obtener
    void getTaskList(String user_token,RepositoryCallback<List<Tasks>> callback);
    //Mandamos el token y la tarea a obtener
    void getTask(String user_token,Tasks tasks,RepositoryCallback<Tasks> callback);
    //Mandamos el token y la tarea a crear
    void newTask(String user_token,Tasks tasks,RepositoryCallback<Tasks> callback);
    //Mandamos el token y la tarea a modificar
    void updateTask(String user_token,Tasks tasks,RepositoryCallback<Tasks> callback);
    //Mandamos el token y la tarea a eliminar
    void deleteTask(String user_token,Tasks tasks,RepositoryCallback<Tasks> callback);

}
