package com.cesarynga.cleanarchitecture.presentation.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cesarynga.cleanarchitecture.R;
import com.cesarynga.cleanarchitecture.presentation.model.UserModel;
import com.cesarynga.cleanarchitecture.presentation.presenter.UserListPresenter;
import com.cesarynga.cleanarchitecture.presentation.view.UserListView;
import com.cesarynga.cleanarchitecture.presentation.view.activity.UserDetailsActivity;
import com.cesarynga.cleanarchitecture.presentation.view.adapter.UsersAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserListFragment extends Fragment implements UserListView,
        UsersAdapter.OnItemClickListener {

    private UserListPresenter userListPresenter;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private UsersAdapter usersAdapter;

    private Unbinder unbinder;

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        usersAdapter = new UsersAdapter();
        usersAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(usersAdapter);

        userListPresenter = new UserListPresenter(this);

        if (savedInstanceState == null) {
            userListPresenter.getUserList();
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
        userListPresenter.destroy();
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
    public void renderUserList(List<UserModel> userModelList) {
        usersAdapter.setUserList(userModelList);
    }

    @Override
    public void viewUserDetails(UserModel userModel) {
        Intent intent = new Intent(getContext(), UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_USER_ID, userModel.getId());
        startActivity(intent);
    }

    @Override
    public void onUserItemClick(UserModel userModel) {
        userListPresenter.onUserClicked(userModel);
    }
}
