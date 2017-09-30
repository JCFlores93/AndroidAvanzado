package pe.cibertec.cleanarchitecture.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.List;

import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;
import pe.cibertec.cleanarchitecture.data.exception.NetworkException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android on 27/05/2017.
 */

public class RestApiImpl implements RestApi {

    private final Context context;
    private final NewsService newsService;

    public RestApiImpl(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.newsService = retrofit.create(NewsService.class);
    }

    @Override
    public void getNewsList(Callback<List<NewsEntity>> callback) {
        if (hasConnection()) {
            Call<List<NewsEntity>> call = newsService.getNews(100);
            try {
                Response<List<NewsEntity>> response = call.execute();
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new NetworkException());
                }
            } catch (IOException e) {
                callback.onError(e);
            }
        } else {
            callback.onError(new NetworkException());
        }
    }

    private boolean hasConnection() {
        boolean isConnected;
        ConnectivityManager manager = (ConnectivityManager) this.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        isConnected = (info != null && info.isConnectedOrConnecting());
        return isConnected;
    }
}
