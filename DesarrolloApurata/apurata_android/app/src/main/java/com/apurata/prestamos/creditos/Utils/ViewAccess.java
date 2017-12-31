package com.apurata.prestamos.creditos.Utils;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;

/**
 * Created by USUARIO on 28/07/2017.
 */

public class ViewAccess {

    public void showAlert(LoginButton loginButton, ImageView imageAlertView, TextView textAlert, TextView txtMessage1, TextView txtMessage2) {
        loginButton.setEnabled(false);
        loginButton.setBackgroundColor(Color.parseColor("#808080"));
        imageAlertView.setVisibility(View.VISIBLE);
        textAlert.setVisibility(View.VISIBLE);
        txtMessage1.setVisibility(View.GONE);
        txtMessage2.setVisibility(View.GONE);
    }

    public void showAlert(Button button1, Button button2, Button button3) {
        button1.setBackgroundColor(Color.parseColor("#808080"));
        button2.setBackgroundColor(Color.parseColor("#808080"));
        button3.setBackgroundColor(Color.parseColor("#808080"));

        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
    }

    public void accessGranted(LoginButton loginButton, ImageView imageAlertView, TextView textAlert, TextView txtMessage1, TextView txtMessage2) {
        loginButton.setVisibility(View.VISIBLE);
        loginButton.setEnabled(true);
        loginButton.setClickable(true);
        loginButton.setBackgroundColor(Color.parseColor("#3b5998"));
        imageAlertView.setVisibility(View.GONE);
        textAlert.setVisibility(View.GONE);
        txtMessage1.setVisibility(View.VISIBLE);
        txtMessage2.setVisibility(View.VISIBLE);
    }

    public void accessGranted(Button button1, Button button2, Button button3) {
        button1.setBackgroundColor(Color.parseColor("#4CAF50"));
        button2.setBackgroundColor(Color.parseColor("#4CAF50"));
        button3.setBackgroundColor(Color.parseColor("#4CAF50"));

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
    }
}

