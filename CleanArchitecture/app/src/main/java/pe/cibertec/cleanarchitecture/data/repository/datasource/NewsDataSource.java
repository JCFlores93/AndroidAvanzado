package pe.cibertec.cleanarchitecture.data.repository.datasource;

import java.util.List;

import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;

/**
 * Created by Android on 27/05/2017.
 */

public interface NewsDataSource {

    void newsEntityList(DataSourceCallback<List<NewsEntity>> callback);
}
