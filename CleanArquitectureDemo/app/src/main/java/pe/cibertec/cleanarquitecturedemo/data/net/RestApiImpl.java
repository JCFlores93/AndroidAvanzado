package pe.cibertec.cleanarquitecturedemo.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;

import java.io.IOException;
import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.NewsEntity;
import pe.cibertec.cleanarquitecturedemo.data.exception.NetWorkException;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android on 27/05/2017.
 */

public class RestApiImpl implements RestApi{

    private  final Context context;
    private final NewsService newsService;

    public RestApiImpl(Context context, NewsService newsService) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.newsService =retrofit.create(NewsService.class);
    }

    @Override
    public void getNewsList(CallBack<List<NewsEntity>> callBack) {
        if (hasConnection()){
            Call<List<NewsEntity>> call = newsService.getNews(100);
            try {
                Response<List<NewsEntity>> response = call.execute();
                if (response.isSuccessful()){
                    callback.onSuccess(response.body);
                }
            }catch (IOException e){
                callback.onError(e);
            }

        }else{
            callBack.onError(new NetWorkException());
        }
    }

    //Verificamos si tenemos conexion a internet
    private boolean hasConnection(){
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        isConnected = (info != null && info.isConnectedOrConnecting());
        return isConnected;
    }
}
