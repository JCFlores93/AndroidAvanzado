package com.cesarynga.cleanarchitecture.presentation.presenter;

import com.cesarynga.cleanarchitecture.data.entity.mapper.UserEntityDataMapper;
import com.cesarynga.cleanarchitecture.data.repository.UserDataRepository;
import com.cesarynga.cleanarchitecture.data.repository.datasource.UserDataSourceFactory;
import com.cesarynga.cleanarchitecture.domain.executor.JobExecutor;
import com.cesarynga.cleanarchitecture.domain.executor.UIThread;
import com.cesarynga.cleanarchitecture.domain.model.User;
import com.cesarynga.cleanarchitecture.domain.repository.UserRepository;
import com.cesarynga.cleanarchitecture.domain.usecase.GetUser;
import com.cesarynga.cleanarchitecture.domain.usecase.UseCase;
import com.cesarynga.cleanarchitecture.presentation.exception.ErrorMessageFactory;
import com.cesarynga.cleanarchitecture.presentation.model.UserModelDataMapper;
import com.cesarynga.cleanarchitecture.presentation.view.UserDetailsView;

import java.util.List;

public class UserDetailsPresenter extends BasePresenter<UserDetailsView> {

    private final GetUser getUser;
    private final UserModelDataMapper userModelDataMapper;

    public UserDetailsPresenter(UserDetailsView view) {
        super(view);

        UserRepository userRepository = new UserDataRepository(
                new UserDataSourceFactory(view.context()), new UserEntityDataMapper());
        this.getUser = new GetUser(userRepository, new JobExecutor(), new UIThread());
        this.userModelDataMapper = new UserModelDataMapper();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getUser.cancel();
        this.view = null;
    }

    private void renderUserInView(User user) {
        view.renderUser(userModelDataMapper.transform(user));
    }

    private void showErrorMessage(Exception e) {
        String errorMessage = ErrorMessageFactory.create(view.context(), e);
        view.showError(errorMessage);
    }

    private void showLoadingView() {
        view.showLoading();
    }

    private void hideLoadingView() {
        view.hideLoading();
    }

    public void getUserDetails(int userId) {
        this.showLoadingView();
        this.getUser.setParams(userId);
        this.getUser.execute(new UseCase.Callback<User>() {
            @Override
            public void onSuccess(User user) {
                hideLoadingView();
                renderUserInView(user);
            }

            @Override
            public void onError(Throwable exception) {
                hideLoadingView();
                showErrorMessage((Exception) exception);
            }
        });
    }
}
