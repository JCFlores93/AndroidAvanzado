package pe.cibertec.cleanarchitecture.presentation.presenter;

import android.util.Log;

import java.util.List;

import pe.cibertec.cleanarchitecture.data.entity.mapper.NewsEntityDataMapper;
import pe.cibertec.cleanarchitecture.data.repository.NewsDataRepository;
import pe.cibertec.cleanarchitecture.data.repository.datasource.NewsDataSourceFactory;
import pe.cibertec.cleanarchitecture.domain.executor.JobExecutor;
import pe.cibertec.cleanarchitecture.domain.executor.UIThread;
import pe.cibertec.cleanarchitecture.domain.model.News;
import pe.cibertec.cleanarchitecture.domain.repository.NewsRepository;
import pe.cibertec.cleanarchitecture.domain.usecase.GetNews;
import pe.cibertec.cleanarchitecture.domain.usecase.UseCase;
import pe.cibertec.cleanarchitecture.presentation.exception.ErrorMessageFactory;
import pe.cibertec.cleanarchitecture.presentation.model.mapper.NewsModelDataMapper;
import pe.cibertec.cleanarchitecture.presentation.view.NewsView;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsPresenter extends BasePresenter<NewsView> {

    private final GetNews getNews;
    private final NewsModelDataMapper newsModelDataMapper;

    public NewsPresenter(NewsView view) {
        super(view);
        NewsRepository newsRepository = new NewsDataRepository(
                new NewsDataSourceFactory(view.context()),
                new NewsEntityDataMapper()
        );
        this.getNews = new GetNews(newsRepository,
                new JobExecutor(), new UIThread());
        this.newsModelDataMapper = new NewsModelDataMapper();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.view = null;
        this.getNews.cancel();
    }

    public void getNews() {
        showLoadingView();
        getNews.execute(new UseCase.Callback<List<News>>() {
            @Override
            public void onSuccess(List<News> newsList) {
                hideLoadingView();
                renderNewsInView(newsList);
                Log.i("ITEMS", newsList.size() + " items");
            }

            @Override
            public void onError(Throwable exception) {
                Log.e("NewsPresenter", "error getting news", exception);
                hideLoadingView();
                showErrorMessage((Exception) exception);
            }
        });
    }

    private void renderNewsInView(List<News> newsList) {
        view.renderNews(newsModelDataMapper.transform(newsList));
    }

    private void showErrorMessage(Exception e) {
        view.showError(ErrorMessageFactory.create(view.context(), e));
    }

    private void showLoadingView() {
        view.showLoading();
    }

    private void hideLoadingView() {
        view.hideLoading();
    }

}
