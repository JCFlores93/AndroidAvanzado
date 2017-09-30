package pe.cibertec.retrofitdemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android on 20/05/2017.
 */

public class RetrofitUtils {

    private static NewsService newsService;

    public  static NewsService getNewsService() {
        if (newsService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NewsService newService = retrofit.create(NewsService.class);
        }
        return newsService;
    }
}
