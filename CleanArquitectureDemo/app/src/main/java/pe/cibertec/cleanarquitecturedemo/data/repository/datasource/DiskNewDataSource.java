package pe.cibertec.cleanarquitecturedemo.data.repository.datasource;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.NewsEntity;

/**
 * Created by Android on 27/05/2017.
 */

public class DiskNewDataSource implements NewsDataSource{
    @Override
    public void newsEntityList(DataSourceCallback<List<NewsEntity>> callback) {

    }
}
