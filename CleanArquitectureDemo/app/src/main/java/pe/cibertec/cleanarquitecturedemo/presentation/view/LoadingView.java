package pe.cibertec.cleanarquitecturedemo.presentation.view;

/**
 * Created by Android on 27/05/2017.
 */

public interface LoadingView extends BaseView {

    void showLoading();

    void hideLoading();

    void showError(String errorMessage);

}
