package com.apurata.prestamos.creditos.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apurata.prestamos.creditos.BuildConfig;
import com.apurata.prestamos.creditos.R;

public class UserMenuActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnNewApplication, btnContinueApplication, btnContactUs;

    String versionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        btnNewApplication = (Button) findViewById(R.id.btnNewApplication);
        btnContinueApplication = (Button) findViewById(R.id.btnContinueApplication);
        btnContactUs = (Button) findViewById(R.id.btnContactUs);
        btnNewApplication.setOnClickListener(this);
        btnContinueApplication.setOnClickListener(this);
        btnContactUs.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewApplication:
                versionName = BuildConfig.VERSION_NAME;
                Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apurata.com/panel?vrApp=" + versionName));
                startActivity(openBrowser);
                finish();
                break;
            case R.id.btnContinueApplication:
                versionName = BuildConfig.VERSION_NAME;
                openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apurata.com/panel?vrApp=" + versionName));
                startActivity(openBrowser);
                finish();
                break;

            case R.id.btnContactUs:
                versionName = BuildConfig.VERSION_NAME;
                openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apurata.com/panel?vrApp=" + versionName));
                startActivity(openBrowser);
                finish();
                break;
        }
    }
}
