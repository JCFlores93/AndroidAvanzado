package pe.cibertec.trabajofinalcibertec.data.net;

import java.util.List;

import javax.security.auth.callback.Callback;

import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;


/**
 * Created by USUARIO on 2/06/2017.
 */

public interface RestApi {

    void getUsersList(Callback<List<UsersEntity>> callback);

    void loginUsers(UsersEntity usersEntity, Callback<UsersEntity> callback);

    void getTasksList(String user_token,Callback<List<TasksEntity>> callBack);

    void getTask(String user_token,TasksEntity tasksEntity,Callback<TasksEntity> callBack);

    void insertTask(String user_token,TasksEntity tasksEntity,Callback<TasksEntity> callBack);

    void updateTask(String user_token,TasksEntity tasksEntity,Callback<TasksEntity> callBack);

    void deleteTask(String user_token,TasksEntity tasksEntity,Callback<TasksEntity> callBack);

    interface Callback<T>{
        void onSuccess(T response);

        void onError(Throwable e);
    }
}
