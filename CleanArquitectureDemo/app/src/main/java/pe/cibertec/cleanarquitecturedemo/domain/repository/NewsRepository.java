package pe.cibertec.cleanarquitecturedemo.domain.repository;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.domain.model.News;

/**
 * Created by Android on 27/05/2017.
 */

public interface NewsRepository {

    void news(RepositoryCallback<List<News>> callback);

}