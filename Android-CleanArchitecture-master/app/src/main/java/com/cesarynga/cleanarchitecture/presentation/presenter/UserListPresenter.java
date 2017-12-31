package com.cesarynga.cleanarchitecture.presentation.presenter;

import com.cesarynga.cleanarchitecture.data.entity.mapper.UserEntityDataMapper;
import com.cesarynga.cleanarchitecture.data.repository.UserDataRepository;
import com.cesarynga.cleanarchitecture.data.repository.datasource.UserDataSourceFactory;
import com.cesarynga.cleanarchitecture.domain.executor.JobExecutor;
import com.cesarynga.cleanarchitecture.domain.executor.UIThread;
import com.cesarynga.cleanarchitecture.domain.model.User;
import com.cesarynga.cleanarchitecture.domain.repository.UserRepository;
import com.cesarynga.cleanarchitecture.domain.usecase.GetUserList;
import com.cesarynga.cleanarchitecture.domain.usecase.UseCase;
import com.cesarynga.cleanarchitecture.presentation.exception.ErrorMessageFactory;
import com.cesarynga.cleanarchitecture.presentation.model.UserModel;
import com.cesarynga.cleanarchitecture.presentation.model.UserModelDataMapper;
import com.cesarynga.cleanarchitecture.presentation.view.UserListView;

import java.util.List;

public class UserListPresenter extends BasePresenter<UserListView> {

    private final GetUserList getUserList;
    private final UserModelDataMapper userModelDataMapper;

    public UserListPresenter(UserListView view) {
        super(view);

        UserRepository userRepository = new UserDataRepository(
                new UserDataSourceFactory(view.context()), new UserEntityDataMapper());
        this.getUserList = new GetUserList(userRepository, new JobExecutor(), new UIThread());
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
        this.getUserList.cancel();
        this.view = null;
    }

    private void renderUserListInView(List<User> users) {
        view.renderUserList(userModelDataMapper.transform(users));
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

    public void onUserClicked(UserModel userModel) {
        view.viewUserDetails(userModel);
    }

    public void getUserList() {
        this.showLoadingView();
        this.getUserList.execute(new UseCase.Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                hideLoadingView();
                renderUserListInView(users);
            }

            @Override
            public void onError(Throwable exception) {
                hideLoadingView();
                showErrorMessage((Exception) exception);
            }
        });
    }
}
