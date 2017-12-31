package com.cesarynga.cleanarchitecture.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.cesarynga.cleanarchitecture.presentation.view.fragment.UserDetailsFragment;

public class UserDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "user_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int userId = getIntent().getIntExtra(EXTRA_USER_ID, 0);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, UserDetailsFragment.newInstance(userId));
            ft.commit();
        }
    }
}
