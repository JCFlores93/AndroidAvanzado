package pe.cibertec.cleanarchitecture.data.repository;

import java.util.List;

import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;
import pe.cibertec.cleanarchitecture.data.entity.mapper.NewsEntityDataMapper;
import pe.cibertec.cleanarchitecture.data.exception.NetworkException;
import pe.cibertec.cleanarchitecture.data.repository.datasource.DataSourceCallback;
import pe.cibertec.cleanarchitecture.data.repository.datasource.NewsDataSource;
import pe.cibertec.cleanarchitecture.data.repository.datasource.NewsDataSourceFactory;
import pe.cibertec.cleanarchitecture.domain.model.News;
import pe.cibertec.cleanarchitecture.domain.repository.NewsRepository;
import pe.cibertec.cleanarchitecture.domain.repository.RepositoryCallback;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsDataRepository implements NewsRepository {

    private final NewsDataSourceFactory newsDataSourceFactory;
    private final NewsEntityDataMapper newsEntityDataMapper;

    public NewsDataRepository(NewsDataSourceFactory newsDataSourceFactory,
                              NewsEntityDataMapper newsEntityDataMapper) {
        this.newsDataSourceFactory = newsDataSourceFactory;
        this.newsEntityDataMapper = newsEntityDataMapper;
    }

    @Override
    public void news(final RepositoryCallback<List<News>> callback) {
        final NewsDataSource newsDataSource = newsDataSourceFactory.createNetworkDataSource();
        newsDataSource.newsEntityList(new DataSourceCallback<List<NewsEntity>>() {
            @Override
            public void onSuccess(List<NewsEntity> newsEntityList) {
                callback.onSuccess(newsEntityDataMapper.transform(newsEntityList));
            }

            @Override
            public void onError(Throwable exception) {
                callback.onError(exception);
                if (exception instanceof NetworkException) {
                    final NewsDataSource diskDataSource = newsDataSourceFactory.createDiskDataSource();
                    diskDataSource.newsEntityList(new DataSourceCallback<List<NewsEntity>>() {
                        @Override
                        public void onSuccess(List<NewsEntity> newsEntityList) {
                            callback.onSuccess(newsEntityDataMapper.transform(newsEntityList));
                        }

                        @Override
                        public void onError(Throwable exception) {
                            callback.onError(exception);
                        }
                    });

                }
            }
        });
    }
}
