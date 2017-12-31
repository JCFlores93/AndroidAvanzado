package pe.cibertec.trabajofinalcibertec.presentation.presenter;

import pe.cibertec.trabajofinalcibertec.presentation.BaseView;

/**
 * Created by USUARIO on 3/06/2017.
 */

public abstract class BasePresenter<V extends BaseView>{

    protected V view;

    public BasePresenter(V view){this.view = view;}

    public abstract void resume();

    public abstract void pause();

    public abstract void destroy();
}
