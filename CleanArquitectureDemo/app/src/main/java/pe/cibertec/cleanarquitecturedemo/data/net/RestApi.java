package pe.cibertec.cleanarquitecturedemo.data.net;

import java.util.List;

import pe.cibertec.cleanarquitecturedemo.data.entity.NewsEntity;
import pe.cibertec.cleanarquitecturedemo.domain.usercase.UserCase;

/**
 * Created by Android on 27/05/2017.
 */

public interface RestApi {

    void getNewsList(CallBack<List<NewsEntity>> callBack);

    interface Callback<T> {
        void onSuccess(T response);

        void onError(Throwable e);
    }
}
