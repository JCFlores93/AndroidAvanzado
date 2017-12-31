package com.cesarynga.cleanarchitecture.presentation.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cesarynga.cleanarchitecture.R;
import com.cesarynga.cleanarchitecture.presentation.view.fragment.UserListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new UserListFragment());
            ft.commit();
        }
    }
}
