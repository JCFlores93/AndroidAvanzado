package pe.cibertec.trabajofinal.presentation.presenter;

import pe.cibertec.trabajofinal.presentation.view.BaseView;

/**
 * Created by USUARIO on 1/06/2017.
 */

public abstract class BasePresenter <V extends BaseView> {

    protected V view;

    public BasePresenter(V view){
        this.view = view;
    }

    public abstract void resume();
    public abstract void pause();
    public abstract void destroy();
}
