package pe.cibertec.cleanarquitecturedemo.domain.usercase;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.domain.executor.PostExecutionThread;
import pe.cibertec.cleanarquitecturedemo.domain.executor.ThreadExecutor;
import pe.cibertec.cleanarquitecturedemo.domain.model.News;
import pe.cibertec.cleanarquitecturedemo.domain.repository.NewsRepository;
import pe.cibertec.cleanarquitecturedemo.domain.repository.RepositoryCallback;

/**
 * Created by Android on 27/05/2017.
 */

public class GetNews extends UserCase<List<News>> {

    private final NewsRepository newsRepository;


    public GetNews(NewsRepository newsRepository,
                   ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread)
    {
        super(threadExecutor, postExecutionThread);
        this.newsRepository = newsRepository;
    }

    @Override
    protected void buildUserCase() {
        super.buildUserCase();
        this.newsRepository.news(new RepositoryCallback<List<News>>() {
            @Override
            public void onSuccess(List<News> newsList) {
                notifyUseCaseSuccess(newsList);
            }

            @Override
            public void onError(Throwable excepetion) {
                notifyUseCaseError(excepetion);
             }
        });
    }
}
