package pe.cibertec.trabajofinalcibertec.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;
import pe.cibertec.trabajofinalcibertec.data.exception.NetworkException;
import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class RestApiImpl implements RestApi{

    private final Context context;
    private final UsersService usersService;
    private final TasksService tasksService;

    public RestApiImpl(Context context) {
        this.context = context;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsersService.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit retrofitTasks = new Retrofit.Builder()
                .baseUrl(TasksService.BASE_URL)
                //.client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.tasksService = retrofitTasks.create(TasksService.class);

        this.usersService = retrofit.create(UsersService.class);
    }

    @Override
    public void getUsersList(Callback<List<UsersEntity>> callback) {
            if (hasConnection()){
                Call<List<UsersEntity>> call = usersService.getUsers(100);
                try{
                    Response<List<UsersEntity>> response = call.execute();
                    if (response.isSuccessful()){
                        callback.onSuccess(response.body());
                    }else{
                        callback.onError(new NetworkException());
                    }

                }catch (IOException e){
                    callback.onError(e);
                }
            }else{
                callback.onError(new NetworkException());
            }
    }

    @Override
    public void loginUsers(UsersEntity usersEntity, Callback<UsersEntity> callback) {
        Log.i("Se cayó","1");
        if (hasConnection()){
            Log.i("Se cayó","2");
            Call<UsersEntity> call = usersService.loginUsers(usersEntity);
            try{
                Response<UsersEntity> response = call.execute();
                Log.i("Se cayó","3");
                if (response.isSuccessful()){
                    Log.i("Se cayó","4");
                    callback.onSuccess(response.body());
                    Log.i("Respuesta :"," "+response.body());
                }else{
                    Log.i("Se cayó","5");
                    callback.onError(new NetworkException());
                }
            }catch (IOException e){
                callback.onError(new NetworkException());
            }
        }

    }

    @Override
    public void getTasksList(String user_token,Callback<List<TasksEntity>> callback) {
        Log.i("Se cayó","1");
        if (hasConnection()){
            Log.i("Se cayó","2");
            Call<List<TasksEntity>> call = tasksService.getTasksList(user_token,25);
            try{
                Response<List<TasksEntity>> response = call.execute();
                Log.i("Se cayó","3");
                if (response.isSuccessful()){
                    Log.i("Se cayó","4");
                    callback.onSuccess(response.body());
                    Log.i("Respuesta :"," "+response.body());
                }else{
                    Log.i("Se cayó","5");
                    callback.onError(new NetworkException());
                }
            }catch (IOException e){
                callback.onError(new NetworkException());
            }
        }

    }

    @Override
    public void getTask(String user_token, TasksEntity tasksEntity, Callback<TasksEntity> callback) {
        Log.i("Se cayó","1");
        if (hasConnection()){
            Log.i("Se cayó","2");
            Call<TasksEntity> call = tasksService.getTask(user_token,tasksEntity.getObjectId());
            try{
                Response<TasksEntity> response = call.execute();
                Log.i("Se cayó","3");
                if (response.isSuccessful()){
                    Log.i("Se cayó","4");
                    callback.onSuccess(response.body());
                    Log.i("Respuesta :"," "+response.body());
                }else{
                    Log.i("Se cayó","5");
                    callback.onError(new NetworkException());
                }
            }catch (IOException e){
                callback.onError(new NetworkException());
            }
        }
    }

    @Override
    public void insertTask(String user_token, TasksEntity tasksEntity, Callback<TasksEntity> callback) {
        Log.i("Se cayó","1");
        if (hasConnection()){
            Log.i("Se cayó","2");
            Call<TasksEntity> call = tasksService.saveTasks(user_token,tasksEntity);
            try{
                Response<TasksEntity> response = call.execute();
                Log.i("Se cayó","3");
                if (response.isSuccessful()){
                    Log.i("Se cayó","4");
                    callback.onSuccess(response.body());
                    Log.i("Respuesta :"," "+response.body());
                }else{
                    Log.i("Se cayó","5");
                    callback.onError(new NetworkException());
                }
            }catch (IOException e){
                callback.onError(new NetworkException());
            }
        }
    }

    @Override
    public void updateTask(String user_token, TasksEntity tasksEntity, Callback<TasksEntity> callback) {
        Log.i("Se cayó","1");
        if (hasConnection()){
            Log.i("Se cayó","2");
            Call<TasksEntity> call = tasksService.updateTasks(user_token,tasksEntity.getObjectId(),tasksEntity);
            try{
                Response<TasksEntity> response = call.execute();
                Log.i("Se cayó","3");
                if (response.isSuccessful()){
                    Log.i("Se cayó","4");
                    callback.onSuccess(response.body());
                    Log.i("Respuesta :"," "+response.body());
                }else{
                    Log.i("Se cayó","5");
                    callback.onError(new NetworkException());
                }
            }catch (IOException e){
                callback.onError(new NetworkException());
            }
        }
    }

    @Override
    public void deleteTask(String user_token, TasksEntity tasksEntity, Callback<TasksEntity> callback) {
        Log.i("Se cayó","1");
        if (hasConnection()){
            Log.i("Se cayó","2");
            Call<TasksEntity> call = tasksService.deleteTasks(user_token,tasksEntity.getObjectId());
            try{
                Response<TasksEntity> response = call.execute();
                Log.i("Se cayó","3");
                if (response.isSuccessful()){
                    Log.i("Se cayó","4");
                    callback.onSuccess(response.body());
                    Log.i("Respuesta :"," "+response.body());
                }else{
                    Log.i("Se cayó","5");
                    callback.onError(new NetworkException());
                }
            }catch (IOException e){
                callback.onError(new NetworkException());
            }
        }
    }


    private boolean hasConnection(){
        boolean isConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager)this.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }
}
