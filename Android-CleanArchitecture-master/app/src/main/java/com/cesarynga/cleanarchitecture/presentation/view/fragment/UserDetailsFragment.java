package com.cesarynga.cleanarchitecture.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cesarynga.cleanarchitecture.R;
import com.cesarynga.cleanarchitecture.presentation.model.UserModel;
import com.cesarynga.cleanarchitecture.presentation.presenter.UserDetailsPresenter;
import com.cesarynga.cleanarchitecture.presentation.view.UserDetailsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserDetailsFragment extends Fragment implements UserDetailsView {

    private static final String ARG_USER_ID = "user_id";

    @BindView(R.id.txt_name)
    TextView txtName;

    @BindView(R.id.txt_username)
    TextView txtUsername;

    @BindView(R.id.txt_email)
    TextView txtEmail;

    @BindView(R.id.txt_phone)
    TextView txtPhone;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.content)
    View content;

    Unbinder unbinder;

    private int userId;

    private UserDetailsPresenter userDetailsPresenter;

    public static final UserDetailsFragment newInstance(int userId) {
        UserDetailsFragment f = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        f.setArguments(args);
        return f;
    }

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        content.setVisibility(View.GONE);

        this.userDetailsPresenter = new UserDetailsPresenter(this);

        if (savedInstanceState == null) {
            this.userDetailsPresenter.getUserDetails(this.userId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.userDetailsPresenter.destroy();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void renderUser(UserModel userModel) {
        content.setVisibility(View.VISIBLE);
        txtName.setText(userModel.getName());
        txtUsername.setText(userModel.getUsername());
        txtEmail.setText(userModel.getEmail());
        txtPhone.setText(userModel.getPhone());
    }
}
