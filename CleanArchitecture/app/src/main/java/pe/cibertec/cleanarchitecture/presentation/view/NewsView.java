package pe.cibertec.cleanarchitecture.presentation.view;

import java.util.List;

import pe.cibertec.cleanarchitecture.presentation.model.NewsModel;

/**
 * Created by Android on 27/05/2017.
 */

public interface NewsView extends LoadingView {

    void getNews();

    void renderNews(List<NewsModel> newsModelList);

}
