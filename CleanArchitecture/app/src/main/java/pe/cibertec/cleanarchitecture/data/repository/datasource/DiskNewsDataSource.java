package pe.cibertec.cleanarchitecture.data.repository.datasource;

import java.util.List;

import pe.cibertec.cleanarchitecture.data.cache.NewsCache;
import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;

/**
 * Created by Android on 27/05/2017.
 */

public class DiskNewsDataSource implements NewsDataSource {

    private final NewsCache newsCache;

    public DiskNewsDataSource(NewsCache newsCache) {
        this.newsCache = newsCache;
    }

    @Override
    public void newsEntityList(final DataSourceCallback<List<NewsEntity>> callback) {
        this.newsCache.list(new NewsCache.Callback<List<NewsEntity>>() {
            @Override
            public void onSuccess(List<NewsEntity> newsEntityList) {
                callback.onSuccess(newsEntityList);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);
            }
        });
    }
}
