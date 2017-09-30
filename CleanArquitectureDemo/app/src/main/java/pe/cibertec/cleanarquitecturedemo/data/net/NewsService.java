package pe.cibertec.cleanarquitecturedemo.data.net;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.NewsEntity;
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

    String BASE_URL = "https://api.backendless.com/9A93628B-54D0-4D9C-FF79-03C4606CCB00/22C006FB-0F32-7A42-FF75-C799CD790300/data/";

    @GET("News")
    Call<List<NewsEntity>> getNews(@Query("pageSize") int pageSize);

    //@Body nos permite serializar to dos los datos enviados
    @POST("News")
    Call<NewsEntity> insertNews(@Body NewsEntity news);

    @PUT("News/{objectId}")
    Call<NewsEntity> updateNews(@Path("objectId") String objectId, @Body NewsEntity news);

    @DELETE("News/{objectId}")
    Call<Void> deleteNews(@Path("objectId") String objectId);
}
