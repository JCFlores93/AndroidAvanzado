package pe.cibertec.trabajofinalcibertec.presentation;

/**
 * Created by USUARIO on 3/06/2017.
 */

public interface LoadingView extends BaseView{

    void showLoading();

    void hideLoading();

    void showError(String errorMessage);
}
