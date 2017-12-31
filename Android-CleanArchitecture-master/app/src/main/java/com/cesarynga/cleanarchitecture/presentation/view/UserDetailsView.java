package com.cesarynga.cleanarchitecture.presentation.view;

import com.cesarynga.cleanarchitecture.presentation.model.UserModel;

public interface UserDetailsView extends LoadingView {

    void renderUser(UserModel userModel);
}
