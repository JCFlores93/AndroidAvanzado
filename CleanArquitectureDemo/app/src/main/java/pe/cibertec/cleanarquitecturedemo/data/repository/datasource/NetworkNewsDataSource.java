package pe.cibertec.cleanarquitecturedemo.data.repository.datasource;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.NewsEntity;
import pe.cibertec.cleanarquitecturedemo.data.net.RestApi;

/**
 * Created by Android on 27/05/2017.
 */

public class NetworkNewsDataSource implements NewsDataSource {

    private final RestApi restApi;

    public NetworkNewsDataSource(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public void newsEntityList(final DataSourceCallback<List<NewsEntity>> callback) {
        this.restApi.getNewsList(new RestApi.Callback<List<NewsEntity>>() {
            @Override
            public void onSuccess(List<NewsEntity> response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(response);
            }
        });
    }
}
