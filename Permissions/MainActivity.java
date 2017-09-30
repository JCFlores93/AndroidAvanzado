package pe.cibertec.permissionsdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static pe.cibertec.permissionsdemo.R.id.parent;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CAMERA_REQUEST_CODE = 10;
    private static final int PERMISSION_CONTACTS_REQUEST_CODE = 11;
    private static final int PERMISSION_CONTACTS_CAMERA_REQUEST_CODE = 12;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        parentView=findViewById(parent);

        Button btnOpenCamera = (Button) findViewById(R.id.btn_open_camera);
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        Button btnAccederContactos = (Button) findViewById(R.id.btn_access_contacts);
        btnAccederContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accederContactos();
            }
        });

        Button btnAccederPrincipal = (Button) findViewById(R.id.btn_access_contacts_camera);
        btnAccederPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accederContactosCamara();
            }
        });

    }

    private void accederContactosCamara() {

        //Preguntamos si tenemos permisos antes de abrir la camara
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA )!= PackageManager.PERMISSION_GRANTED)
         || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS )!= PackageManager.PERMISSION_GRANTED))

            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA ,Manifest.permission.READ_CONTACTS},
                        PERMISSION_CONTACTS_CAMERA_REQUEST_CODE);

            }else {
            Toast.makeText(this,"Permissions OK", Toast.LENGTH_SHORT).show();
        }

    }

    private void accederContactos() {
        //Preguntamos si tenemos permisos antes de acceder los contactos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSION_CONTACTS_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Has permissions for contacts", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        //Preguntamos si tenemos permisos antes de abrir la camara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA))
            {
                Snackbar.make(parentView,"Permission is required",Snackbar.LENGTH_INDEFINITE)
                .setAction("Allow", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                PERMISSION_CAMERA_REQUEST_CODE);
                    }
                }).show();
            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_CAMERA_REQUEST_CODE);
            }

        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //El usuario a aceptado el permiso
       if (requestCode == PERMISSION_CAMERA_REQUEST_CODE && requestCode == PERMISSION_CONTACTS_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Has permissions for contacts", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }else{
                Toast.makeText(this,"Has no permissions for camera",Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == PERMISSION_CONTACTS_CAMERA_REQUEST_CODE){
           if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
           {
                Toast.makeText(this,"Permissions OK",Toast.LENGTH_SHORT).show();
           }else{
               Toast.makeText(this,"Permissions failed",Toast.LENGTH_SHORT).show();
           }
       }

       /* if (requestCode == PERMISSION_CONTACTS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Has permissions for contacts", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Has no permissions for contacts", Toast.LENGTH_SHORT).show();
            }
        }
*/

    }
