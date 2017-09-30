package pe.cibertec.cleanarquitecturedemo.data.repository;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.NewsEntity;
import pe.cibertec.cleanarquitecturedemo.data.entity.mapper.NewsEntityDataMapper;
import pe.cibertec.cleanarquitecturedemo.data.repository.datasource.DataSourceCallback;
import pe.cibertec.cleanarquitecturedemo.data.repository.datasource.NewsDataSource;
import pe.cibertec.cleanarquitecturedemo.data.repository.datasource.NewsDataSourceFactory;
import pe.cibertec.cleanarquitecturedemo.domain.model.News;
import pe.cibertec.cleanarquitecturedemo.domain.repository.NewsRepository;
import pe.cibertec.cleanarquitecturedemo.domain.repository.RepositoryCallback;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsDataRepository implements NewsRepository {

    private final NewsDataSourceFactory newsDataSourceFactory;
    private final NewsEntityDataMapper newsEntityDataMapper;

    public NewsDataRepository(NewsDataSourceFactory newsDataSourceFactory, NewsEntityDataMapper newsEntityDataMapper) {
        this.newsDataSourceFactory = newsDataSourceFactory;
        this.newsEntityDataMapper = newsEntityDataMapper;
    }

    @Override
    public void news(final RepositoryCallback<List<News>> callback) {
        final NewsDataSource newsDataSource = newsDataSourceFactory.create();
        newsDataSource.newsEntityList(new DataSourceCallback<List<NewsEntity>>() {
            @Override
            public void onSuccess(List<NewsEntity> newsEntities) {
                callback.onSuccess(newsEntityDataMapper.transform(newsEntityList));
            }

            @Override
            public void onError(Throwable excepetion) {
                callback.onError(excepetion);
            }
        });
    }
}
