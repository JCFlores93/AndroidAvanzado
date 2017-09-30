package pe.cibertec.cleanarchitecture.domain.usecase;

import java.util.List;

import pe.cibertec.cleanarchitecture.domain.executor.PostExecutionThread;
import pe.cibertec.cleanarchitecture.domain.executor.ThreadExecutor;
import pe.cibertec.cleanarchitecture.domain.model.News;
import pe.cibertec.cleanarchitecture.domain.repository.NewsRepository;
import pe.cibertec.cleanarchitecture.domain.repository.RepositoryCallback;

/**
 * Created by Android on 27/05/2017.
 */

public class GetNews extends UseCase<List<News>> {

    private final NewsRepository newsRepository;

    public GetNews(NewsRepository newsRepository,
                   ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.newsRepository = newsRepository;
    }

    @Override
    protected void buildUseCase() {
        this.newsRepository.news(new RepositoryCallback<List<News>>() {
            @Override
            public void onSuccess(List<News> newsList) {
                notifyUseCaseSuccess(newsList);
            }

            @Override
            public void onError(Throwable exception) {
                notifyUseCaseError(exception);
            }
        });
    }
}
