package pe.cibertec.cleanarchitecture.presentation.model;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.cleanarchitecture.domain.model.News;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsModelDataMapper {
    public NewsModelDataMapper() {
    }

    public NewsModel transform(News news){
        NewsModel newsModel = new NewsModel(news.getObjectId());
        newsModel.setTitle(news.getTitle());
        return newsModel;
    }

    public List<NewsModel> transform (List<News> newsList){
        List<NewsModel> newsModels = new ArrayList<>();
        for (News news : newsList) {
            newsModelsList.add(transform(news));
        }
        return ;
    }
}
