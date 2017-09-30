package pe.cibertec.trabajofinal.data.net;

import java.util.List;

import pe.cibertec.trabajofinal.data.entity.UsersEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USUARIO on 1/06/2017.
 */

public interface UsersService {

    String BASE_URL = "https://api.backendless.com/9A93628B-54D0-4D9C-FF79-03C4606CCB00/22C006FB-0F32-7A42-FF75-C799CD790300/data/";

    @GET("News")
    Call<List<UsersEntity>> getUsers(@Query("pageSize") int pageSize);

    @POST("News")
    Call<UsersEntity> insertUsers(@Body UsersEntity news);

    @PUT("News/{objectId}")
    Call<UsersEntity> updateUsers(@Path("objectId") String objectId, @Body UsersEntity news);

    @DELETE("News/{objectId}")
    Call<Void> deleteUsers(@Path("objectId") String objectId);

}
