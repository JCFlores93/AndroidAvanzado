package pe.cibertec.trabajofinalcibertec.data.net;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USUARIO on 3/06/2017.
 */

public interface TasksService {

    String BASE_URL = "https://api.backendless.com/AC6D7008-261B-EEDB-FFF2-CB324366E900/25C3B188-DF06-03F0-FF1E-BD96B0768500/data/";

    @Headers("Content-Type: application/json")
    @GET("Reminder")
    Call<List<TasksEntity>> getTasksList(@Header("user-token") String token,@Query("pageSize") int pageSize);

    @Headers("Content-Type: application/json")
    @GET("Reminder/{objectId}")
     Call<TasksEntity> getTask(@Header("user-token") String token,@Path("objectId") String objectId);

    @Headers("Content-Type: application/json")
    @POST("Reminder")
    Call<TasksEntity> saveTasks(@Header("user-token") String token, @Body TasksEntity tasksEntity);

    @Headers("Content-Type: application/json")
    @PUT("Reminder/{objectId}")
    Call<TasksEntity> updateTasks(@Header("user-token") String token,@Path("objectId") String objectId,@Body TasksEntity tasksEntity);

    @Headers("Content-Type: application/json")
    @DELETE("Reminder/{objectId}")
    Call<TasksEntity> deleteTasks(@Header("user-token") String token,@Path("objectId") String objectId);

}
