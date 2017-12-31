package com.cesarynga.cleanarchitecture.presentation.view;

import com.cesarynga.cleanarchitecture.presentation.model.UserModel;

import java.util.List;

public interface UserListView extends LoadingView {

    void renderUserList(List<UserModel> userModelList);

    void viewUserDetails(UserModel userModel);
}
