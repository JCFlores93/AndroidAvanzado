package pe.cibertec.cleanarquitecturedemo.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.NewsEntity;
import pe.cibertec.cleanarquitecturedemo.domain.model.News;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsEntityDataMapper {
    public NewsEntityDataMapper(){

    }

    //transformacion de los datos
    public News transform(NewsEntity newsEntity){
        News news = new News(newsEntity.getObjectId());
        news.setTitle(newsEntity.getTitle());
        news.setDetail(newsEntity.getDetail());
        news.setImageUrl(newsEntity.getImageUrl());
        return  news;
    }

    public List<News> transform(List<NewsEntity> newsEntityList){
        List<News> newsList = new ArrayList<>();
        for (NewsEntity newsEntity : newsEntityList){
            newsList.add(transform(newsEntity));
        }
        return newsList;
    }
}
