package pe.cibertec.cleanarchitecture.data.repository.datasource;

import android.content.Context;

import pe.cibertec.cleanarchitecture.data.cache.NewsCache;
import pe.cibertec.cleanarchitecture.data.cache.NewsNewsCacheImpl;
import pe.cibertec.cleanarchitecture.data.net.RestApi;
import pe.cibertec.cleanarchitecture.data.net.RestApiImpl;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsDataSourceFactory {

    private final Context context;

    public NewsDataSourceFactory(Context context) {
        this.context = context;
    }

    public NewsDataSource createNetworkDataSource() {
        RestApi restApi = new RestApiImpl(context);
        NewsCache newsCache = new NewsNewsCacheImpl();
        return new NetworkNewsDataSource(restApi, newsCache);
    }

    public NewsDataSource createDiskDataSource() {
        NewsCache newsCache = new NewsNewsCacheImpl();
        return new DiskNewsDataSource(newsCache);
    }
}
