package pe.cibertec.cleanarquitecturedemo.presentation.presenter;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.mapper.NewsEntityDataMapper;
import pe.cibertec.cleanarquitecturedemo.data.repository.NewsDataRepository;
import pe.cibertec.cleanarquitecturedemo.data.repository.datasource.NewsDataSourceFactory;
import pe.cibertec.cleanarquitecturedemo.domain.executor.JobExecutor;
import pe.cibertec.cleanarquitecturedemo.domain.executor.UIThread;
import pe.cibertec.cleanarquitecturedemo.domain.model.News;
import pe.cibertec.cleanarquitecturedemo.domain.repository.NewsRepository;
import pe.cibertec.cleanarquitecturedemo.domain.usercase.GetNews;
import pe.cibertec.cleanarquitecturedemo.domain.usercase.UserCase;
import pe.cibertec.cleanarquitecturedemo.presentation.view.NewsView;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsPresenter extends BasePresenter<NewsView> {

    private final GetNews getNews;

    public NewsPresenter(NewsView view) {
        super(view);
        NewsRepository newsRepository = new NewsDataRepository(
                new NewsDataSourceFactory(view.context()),
                new NewsEntityDataMapper()
        );
        this.getNews = new GetNews(newsRepository,
                new JobExecutor(),
                new UIThread());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    //Referencia al Fragment si en caso se haya eliminado lo cancelamos
    @Override
    public void destroy() {
        this.view =null;
        this.getNews.cancel();
    }

    public void getNews(){
        showLoadingView();
        getNews.execute(new UserCase.CallBack<List<News>>() {
            @Override
            public void onSuccess(List<News> newses) {
                hideLoadingView();
                renderNewsInView(newsList);
            }

            @Override
            public void onError(Throwable exception) {
                hideLoadingView();
                showErrorMessage((Exception) exception);
            }
        });
    }

    private void renderNewsInView(List<News> newsList){

    }

    private  void showLoadingView(){
        view.showLoading();
    }

    private  void hideLoadingView(){
        view.hideLoading();
    }

    private void showErrorMessage(Exception e){

    }
}
