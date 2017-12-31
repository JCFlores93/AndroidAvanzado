package pe.cibertec.trabajofinalcibertec.data.net;

import java.util.List;

import javax.security.auth.callback.Callback;

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
 * Created by USUARIO on 2/06/2017.
 */

public interface UsersService {

    String BASE_URL = "https://api.backendless.com/AC6D7008-261B-EEDB-FFF2-CB324366E900/25C3B188-DF06-03F0-FF1E-BD96B0768500/users/";

    @Headers("Content-Type : application/json")
    @GET("login")
    Call<List<UsersEntity>> getUsers(@Query("pageSize") int pageSize);

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<UsersEntity> loginUsers(@Body UsersEntity usersEntity);


    @POST("register")
    Call<UsersEntity> insertUsers(@Body UsersEntity usersEntity);

    @PUT("register/{objectId}")
    Call<UsersEntity> updateUsers(@Path("objectId") String objectId,@Body UsersEntity usersEntity);

    @DELETE("register/{objectId}")
    Call<Void> deleteUsers(@Path("objectId") String objectId);

    @GET("login/{objectId}")
    Call<List<UsersEntity>> loginUsers(@Path("objectId") String objectId);
}
