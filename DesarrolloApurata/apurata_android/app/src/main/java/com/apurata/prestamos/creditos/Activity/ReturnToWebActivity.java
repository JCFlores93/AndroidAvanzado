package com.apurata.prestamos.creditos.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apurata.prestamos.creditos.BuildConfig;
import com.apurata.prestamos.creditos.R;

import static android.support.constraint.R.id.parent;

public class ReturnToWebActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGoToWebPage, btnCall;

    TextView txtMainText;

    private static final int PERMISSION_PHONE_REQUEST_CODE = 10;

    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_to_web);
        parentView = findViewById(parent);
        btnGoToWebPage = (Button) findViewById(R.id.btnGoToWebPage);
        txtMainText = (TextView) findViewById(R.id.txtMainText);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnGoToWebPage.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            switch (bundle.getInt("CODE")) {
                case 404:
                    btnGoToWebPage.setVisibility(View.VISIBLE);
                    btnCall.setVisibility(View.GONE);
                    break;
                default:
                    if (bundle.getInt("CODE") >= 500) {
                        txtMainText.setText(R.string.error500);
                        btnGoToWebPage.setVisibility(View.GONE);
                        btnCall.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToWebPage:
                String versionName = BuildConfig.VERSION_NAME;
                Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apurata.com/panel?vrApp=" + versionName));
                startActivity(openBrowser);
                finish();
                break;
            case R.id.btnCall:
                makeCall();
                break;
            default:
                break;
        }
    }

    public void makeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Snackbar.make(parentView, "Se necesita el permiso para poder llamar", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Aceptar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(ReturnToWebActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                        PERMISSION_PHONE_REQUEST_CODE);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONE_REQUEST_CODE);
            }

        } else {
            Intent intentCall = new Intent(Intent.ACTION_CALL);
            String number = "tel:" + getResources().getString(R.string.telephoneAPURATA);
            intentCall.setData(Uri.parse(number));
            startActivity(intentCall);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_PHONE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "No se puede acceder al teléfono", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
