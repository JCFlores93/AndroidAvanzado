package pe.cibertec.retrofitdemo;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Android on 20/05/2017.
 */

public interface NewsService {

    @GET("News")
    Call<List<News>> getNews(@Query("pageSize") int pageSize);

    //@Body nos permite serializar to dos los datos enviados
    @POST("News")
    Call<News> insertNews(@Body News news);

    @PUT("News/{objectId}")
    Call<News> updateNews(@Path("objectId") String objectId, @Body News news);

    @DELETE("News/{objectId}")
    Call<Void> deleteNews(@Path("objectId") String objectId);
}
