package pe.cibertec.cleanarchitecture.data.repository.datasource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.cleanarchitecture.data.cache.NewsCache;
import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;
import pe.cibertec.cleanarchitecture.data.net.RestApi;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Android on 03/06/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class NetworkNewsDataSourceTest {

    private NetworkNewsDataSource networkNewsDataSource;

    @Mock
    private RestApi mockRestApi;

    @Mock
    private NewsCache mockNewsCache;

    @Mock
    private DataSourceCallback<List<NewsEntity>> mockNewsDataSourceCallback;

    @Captor
    private ArgumentCaptor<RestApi.Callback<List<NewsEntity>>> restApiCallbackCaptor;
    @Test
    public void setUp() throws Exception {
        networkNewsDataSource = new NetworkNewsDataSource(
                mockRestApi,mockNewsCache
        );
    }

    @Test
    public void testGetNewsFromApi(){
        networkNewsDataSource.newsEntityList(mockNewsDataSourceCallback);
        verify(mockRestApi).getNewsList(restApiCallbackCaptor.capture());

        List<NewsEntity> newsEntityList = new ArrayList<>();

        restApiCallbackCaptor.getValue().onSuccess(newsEntityList);
        verify(mockNewsCache).put(new ArrayList<NewsEntity>());

    }


}