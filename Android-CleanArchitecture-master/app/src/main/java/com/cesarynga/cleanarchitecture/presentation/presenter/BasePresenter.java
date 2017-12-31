package com.cesarynga.cleanarchitecture.presentation.presenter;

import com.cesarynga.cleanarchitecture.presentation.view.BaseView;

public abstract class BasePresenter<V extends BaseView> {

    protected V view;

    public BasePresenter(V view) {
        this.view = view;
    }

    public abstract void resume();

    public abstract void pause();

    public abstract void destroy();
}
