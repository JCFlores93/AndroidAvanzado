package pe.cibertec.cleanarquitecturedemo.presentation.presenter;

import pe.cibertec.cleanarquitecturedemo.presentation.view.BaseView;

/**
 * Created by Android on 27/05/2017.
 */

public abstract class BasePresenter <V extends BaseView>{

    protected V view;

    public BasePresenter(V view) {
        this.view = view;
    }

    public abstract void resume();

    public abstract void pause();

    public abstract void destroy();

}
