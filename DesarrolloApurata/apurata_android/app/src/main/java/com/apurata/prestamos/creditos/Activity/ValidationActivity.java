package com.apurata.prestamos.creditos.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apurata.prestamos.creditos.BackendNetwork.HandleResponse.ErrorResponse.ResponseErrorValidation;
import com.apurata.prestamos.creditos.R;
import com.apurata.prestamos.creditos.RequestModels.Coord;
import com.apurata.prestamos.creditos.RequestModels.CoordActivity;
import com.apurata.prestamos.creditos.RequestModels.DataFromValidation;
import com.apurata.prestamos.creditos.RequestModels.UserStatus;
import com.apurata.prestamos.creditos.Service.CameraPixelService;
import com.apurata.prestamos.creditos.Service.FireBaseDataService;
import com.apurata.prestamos.creditos.Utils.ViewAccess;
import com.apurata.prestamos.creditos.BackendNetwork.RequestInterfaces.EveConnection;
import com.apurata.prestamos.creditos.Utils.TextValidator;
import com.apurata.prestamos.creditos.Models.User;
import com.apurata.prestamos.creditos.BackendNetwork.BackendConnection;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;


public class ValidationActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FireBaseDataService fireBaseDataService;
    private UserStatus newLendStatus;
    private String ACTIVITY_VIEW = "ValidationActivity";
    private int responseCode = 200;
    private Intent intentOpenWindow;
    User user = new User();
    Spinner listOptions;
    int counterCameraPermissions = 0, counterStoragePermissions= 0;

    TextView txtTitle, txtText1, txtTitle2, txtText2, txtTitle3, txtTitle4, txtText3, txtTitle5, txtText4;

    private MyMainCameraAsynTask myCameraAsynTask;
    UserStatus newStatus;
    EditText edtAccountNumber, edtVerificationCode;
    Button btnGetDni, btnGetSign, btnStart, btnGoToApurataCom;
    ImageView imgViewGetDni, imgViewGetSign;
    View parentMainActivity, scrollView, linearLayoutLoading;
    ExifInterface exif;
    Uri fileUri;
    Bitmap bitmap;
    AVLoadingIndicatorView avi;
    String firstTitle, firstText, secondTitle, secondText, thirdTitle, fourthTitle, thirdText, fifthTitle, fourthText, idFacebook, nuevoDNI, newSign, brandCellphone, modelCellphone, versionCellphone, wPaperCamera, wDniCamera;
    Coord coordFacebook, coordDNI, coordSign, coordStart;
    private Location location;
    boolean validated = false, PICTURE_DNI = false, PICTURE_SIGN = false, responseValidation = false;
    private static final int PERMISSION_GPS_REQUEST_CODE = 10;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 11;
    private static final int PERMISSION_EXTERNAL_REQUEST_CODE = 12;
    public static final String PERMISSION_LOCATION = "location";
    public static final String PERMISSION_CAMERA = "camera";
    public static final String PERMISSION_STORAGE= "storage";
    public static final String PERMISSION_GRANTED = "Granted";
    public static final String PERMISSION_DENIED = "Denied";
    private static final int ACCESS_CAMERA_DNI = 12;
    private static final int ACCESS_CAMERA_SIGN = 13;
    long timeStapLoginFacebook, timeStapDNI, timeStapSign, timeStapSend;
    private boolean gpsStatus;
    private Snackbar validationGPS;
    private LocationManager locationManager;
    private String mCurrentPhotoPath, statusValidation;
    private int targetW, targetH, widthSizeScreen, heightSizeScreen, imgPaperHeight, imgPaperWidth, imgDNIHeight, imgDNIWidth, pxPaper, pxDNI;
    ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
    ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();
    File filePhoto;
    boolean gpsWorking;
    int pxWidthFrontCamera, pxHeightFrontCamera, pxWidthBackCamera, pxHeightBackCamera;
    ConnectivityManager connectionManager;
    NetworkInfo wifiCheck;

    LocationListener locationListener, locationListener2;
    LocationManager service;

    ViewAccess viewAccess = new ViewAccess();
    ProgressBar pbMain;
    TextView errorMessageNull;
    int openCameraDNI = 0, openCameraSign = 0;
    boolean permissionsGranted = true;
    boolean permissionNoGranted = true;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MultiDex.install(this);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);
        txtTitle = (TextView) findViewById(R.id.txtTitle1);
        txtText1 = (TextView) findViewById(R.id.txtText1);
        txtTitle2 = (TextView) findViewById(R.id.txtTitle2);
        txtText2 = (TextView) findViewById(R.id.txtText2);
        txtTitle3 = (TextView) findViewById(R.id.txtTitle3);
        txtTitle4 = (TextView) findViewById(R.id.txtTitle4);
        txtText3 = (TextView) findViewById(R.id.txtText3);
        txtTitle5 = (TextView) findViewById(R.id.txtTitle5);
        txtText4 = (TextView) findViewById(R.id.txtText4);
        edtAccountNumber = (EditText) findViewById(R.id.edtAccountNumber);
        edtVerificationCode = (EditText) findViewById(R.id.edtVerificationCode);
        btnGetDni = (Button) findViewById(R.id.btnGetDni);
        btnGetSign = (Button) findViewById(R.id.btnGetSign);
        imgViewGetDni = (ImageView) findViewById(R.id.imgViewGetDni);
        imgViewGetSign = (ImageView) findViewById(R.id.imgViewGetSign);
        listOptions = (Spinner) findViewById(R.id.listOptions);
        btnStart = (Button) findViewById(R.id.btnStart);
        scrollView = findViewById(R.id.scrollView);
        linearLayoutLoading = findViewById(R.id.linearlLoading);
        linearLayoutLoading.setVisibility(GONE);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        pbMain = (ProgressBar) findViewById(R.id.pbMain);
        errorMessageNull = (TextView) findViewById(R.id.txtMessageNull);
        btnGoToApurataCom = (Button) findViewById(R.id.btnGoToApurataCom);


        Bundle bundle = getIntent().getExtras();
        idFacebook = bundle.getString("id");
        String email = bundle.getString("email");
        String name = bundle.getString("name");
        logUser(idFacebook, email, name);
        fireBaseDataService = new FireBaseDataService(mFirebaseAnalytics, idFacebook, ACTIVITY_VIEW);


        bundle.setClassLoader(Coord.class.getClassLoader());
        timeStapLoginFacebook = bundle.getLong("TimeStapLoginFacebook");
        gpsStatus = bundle.getBoolean("GPS");
        Bundle bundllle = getIntent().getBundleExtra("bundle");
        coordFacebook = bundllle.getParcelable("Coordinate");

        final Context context = this;
        parentMainActivity = findViewById(R.id.parentMainActivity);
        validationGPS = Snackbar.make(parentMainActivity, "Es necesario encender el GPS", Snackbar.LENGTH_INDEFINITE)
                .setAction("Activar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(ValidationActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSION_GPS_REQUEST_CODE);
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), PERMISSION_GPS_REQUEST_CODE);
                        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    }
                });


        checkPermissionForGPSWithOutObject();

        int maxLength = 6;
        edtVerificationCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        edtVerificationCode.addTextChangedListener(new TextValidator(edtVerificationCode) {
            @Override
            public void validate(EditText editText, String text) {
                if (text.length() < 6) {
                    Drawable warning = ContextCompat.getDrawable(context, R.mipmap.ic_warning_icon);
                    warning.setBounds(0, 0, warning.getIntrinsicWidth(), warning.getIntrinsicHeight());
                    edtVerificationCode.setError("El código pin consta de 6 dígitos", warning);
                }
            }
        });
        int maxLengthEdtAccountNumber = 40;
        edtAccountNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthEdtAccountNumber)});
        edtAccountNumber.addTextChangedListener(new TextValidator(edtAccountNumber) {
            @Override
            public void validate(EditText editText, String text) {
                if (text.length() == 0) {
                    Drawable warning = ContextCompat.getDrawable(context, R.mipmap.ic_warning_icon);
                    warning.setBounds(0, 0, warning.getIntrinsicWidth(), warning.getIntrinsicHeight());
                    edtAccountNumber.setError("No puede dejar este campo vacío", warning);
                }
            }
        });
        firstTitle = getResources().getString(R.string.String1MainView);
        firstText = getResources().getString(R.string.String2MainView) +
                getResources().getString(R.string.String3MainView)
                + getResources().getString(R.string.String4MainView);

        secondTitle = getResources().getString(R.string.String5MainView);

        secondText = getResources().getString(R.string.String6MainView) +
                getResources().getString(R.string.String7MainView);

        thirdTitle = getResources().getString(R.string.String8MainView);
        fourthTitle = getResources().getString(R.string.String9MainView);
        thirdText = getResources().getString(R.string.String10MainView) + " " +
                getResources().getString(R.string.String11MainView) + " " +
                getResources().getString(R.string.String12MainView);
        fifthTitle = getResources().getString(R.string.String13MainView);
        fourthText = getResources().getString(R.string.String14MainView) + " " +
                getResources().getString(R.string.String15MainView) + " " +
                getResources().getString(R.string.String16MainView);
        txtTitle.setText(firstTitle);
        txtText1.setText(firstText);
        txtTitle2.setText(secondTitle);
        txtText2.setText(secondText);
        txtTitle3.setText(thirdTitle);
        txtTitle4.setText(fourthTitle);
        txtText3.setText(thirdText);
        txtTitle5.setText(fifthTitle);
        txtText4.setText(fourthText);
        btnGetDni.setOnClickListener(this);
        btnGetSign.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        imgViewGetDni.setVisibility(GONE);
        imgViewGetSign.setVisibility(GONE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.valuesListSpinner, R.layout.support_simple_spinner_dropdown_item);
        listOptions.setAdapter(adapter);
        listOptions.setSelection(0);
        /*   Si deseamos mostrar otros datos de Facebook
        String firstName = bundle.getString("first_name");
        String last_name = bundle.getString("last_name");
        txtFacebookData.setText("DATOS :"+idFacebook+" "+firstName+" "+" "+last_name);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetDni:
                openCameraDNI++;
                PICTURE_DNI = true;
                PICTURE_SIGN = false;
                openCamera(ACCESS_CAMERA_DNI);
                coordDNI = checkPermissionForGPS();
                break;
            case R.id.btnGetSign:
                openCameraSign++;
                PICTURE_DNI = false;
                PICTURE_SIGN = true;
                openCamera(ACCESS_CAMERA_SIGN);
                coordSign = checkPermissionForGPS();
                break;
            case R.id.btnStart:
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                boolean isMobileConn = false;
                if (networkInfo != null) {
                    isMobileConn = networkInfo.isConnected();
                }
                connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (!wifiCheck.isConnected() && !isMobileConn) {
                    Toast.makeText(getBaseContext(), "Es necesario que se conecte a Internet para proseguir ...", Toast.LENGTH_SHORT).show();
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtVerificationCode.getWindowToken(), 0);
                    if (scrollView.getVisibility() == View.VISIBLE) {
                        scrollView.setVisibility(View.GONE);
                        linearLayoutLoading.setVisibility(View.VISIBLE);
                    }
                    try {
                        fireBaseDataService.clickOnView("clickOnButtonSubmit");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startTransition();
                    coordStart = checkPermissionForGPS();
                    timeStapSend = new Date().getTime();
                    validated = true;
                    if (nuevoDNI == null || nuevoDNI.equals("")) {
                        stopTransition();
                        Toast.makeText(this, "Debe enviar la foto de su DNI", Toast.LENGTH_LONG).show();
                        txtTitle.requestFocus();
                    } else {
                        if (newSign == null || newSign.equals("")) {
                            stopTransition();
                            Toast.makeText(this, "Debe enviar la foto con su firma", Toast.LENGTH_LONG).show();
                            btnGetSign.setFocusableInTouchMode(true);
                            btnGetSign.requestFocus();
                        } else {
                            if (listOptions.getSelectedItem().toString().equals("Seleccione una opción")) {
                                stopTransition();
                                Toast.makeText(this, "Debe seleccionar su ubicación", Toast.LENGTH_LONG).show();
                                txtTitle3.requestFocus();
                            } else {
                                if (TextUtils.isEmpty(edtAccountNumber.getText().toString()) && edtAccountNumber.getText().length() == 0) {
                                    stopTransition();
                                    Toast.makeText(this, "Debe ingresar el número de su cuenta", Toast.LENGTH_LONG).show();
                                    txtTitle4.requestFocus();
                                } else {
                                    if (TextUtils.isEmpty(edtVerificationCode.getText().toString()) && edtVerificationCode.getText().length() < 6) {
                                        stopTransition();
                                        Toast.makeText(this, "Debe ingresar el número pin que se le envió", Toast.LENGTH_LONG).show();
                                        edtVerificationCode.requestFocus();
                                    } else {
                                        getLendStatus(idFacebook);
                                    }
                                }
                            }
                        }
                    }

                }
                break;
            default:
                break;
        }
    }

    private void sendDataFromStep_5() {
        final EveConnection lendService = BackendConnection.getLendService();
        String itemSelected = listOptions.getSelectedItem().toString();
        final String edt_account_number = edtAccountNumber.getText().toString();
        String phone_pin = edtVerificationCode.getText().toString();
        DataFromValidation item = new DataFromValidation();
        item.setUserID(idFacebook);
        item.setDniPhoto(nuevoDNI);
        item.setSignedPaperPhoto(newSign);
        item.setCel_brand(brandCellphone);
        item.setCel_model(modelCellphone);
        item.setAndroid_version(versionCellphone);
        item.setWindow_size_height(heightSizeScreen);
        item.setWindow_size_width(widthSizeScreen);
        item.setValidationDeclaredUbication(itemSelected);
        item.setBankAccount(edt_account_number);
        item.setPhonePin(phone_pin);
        if (pxWidthFrontCamera != 0) {
            item.setFront_camera_width(pxWidthFrontCamera);
            item.setFront_camera_height(pxHeightFrontCamera);
        }

        item.setBack_camera_width(pxWidthBackCamera);
        item.setBack_camera_height(pxHeightBackCamera);
        if (pxWidthFrontCamera != 0) {
            if (pxDNI <= item.getFront_camera_width() * item.getFront_camera_height() / 1024000) {
                wDniCamera = "Front Camera";
            }
        }
        if (pxDNI <= (item.getBack_camera_width() * item.getBack_camera_height() / 1024000) && pxDNI > (pxWidthFrontCamera * pxHeightFrontCamera / 1024000)) {
            wDniCamera = "Back Camera";
        }
        if (pxWidthFrontCamera != 0) {
            if (pxPaper <= item.getFront_camera_width() * item.getFront_camera_height() / 1024000) {
                wPaperCamera = "Front Camera";
            }
        }
        if (pxPaper <= (pxWidthBackCamera * pxHeightBackCamera / 1024000) && pxPaper > (pxWidthFrontCamera * pxHeightFrontCamera / 1024000)) {
            wPaperCamera = "Back Camera";
        }
        item.setDni_camera(wDniCamera);
        item.setPaper_camera(wPaperCamera);
        List<CoordActivity> listCoordActivity = new ArrayList<>();
        CoordActivity coordActivity = new CoordActivity();
        coordActivity.setTimesTamp(timeStapLoginFacebook);
        coordActivity.setCoord(coordFacebook);
        coordActivity.setOwner("first_gps");
        listCoordActivity.add(coordActivity);
        coordActivity = new CoordActivity();
        coordActivity.setTimesTamp(timeStapDNI);
        coordActivity.setCoord(coordDNI);
        coordActivity.setOwner("dni_gps");
        listCoordActivity.add(coordActivity);
        coordActivity = new CoordActivity();
        coordActivity.setTimesTamp(timeStapSign);
        coordActivity.setCoord(coordSign);
        coordActivity.setOwner("paper_gps");
        listCoordActivity.add(coordActivity);
        coordActivity = new CoordActivity();
        coordActivity.setTimesTamp(timeStapSend);
        coordActivity.setCoord(coordStart);
        coordActivity.setOwner("lastGPS");
        listCoordActivity.add(coordActivity);
        item.setCoordActivity(listCoordActivity);
        item.setGpsWorking(gpsWorking);
        if (idFacebook.compareTo("1") != 0) {

            lendService.insertUsers(item).enqueue(new Callback<DataFromValidation>() {
                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                DateFormat df;
                String date;

                @Override
                public void onResponse(Call<DataFromValidation> call, Response<DataFromValidation> response) {
                    switch (response.code()) {
                        case 404:
                            try {
                                fireBaseDataService.formSubmittedFailed("messageError", "Usuario incorrecto en Facebook", "userError404");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            stopTransition();
                            Toast.makeText(getApplicationContext(), "Usuario incorrecto en Facebook", Toast.LENGTH_LONG).show();
                            break;
                        case 403:
                            try {
                                fireBaseDataService.formSubmittedFailed("messageError", "El número pin es incorrecto", "userError403");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            stopTransition();
                            Toast.makeText(getApplicationContext(), "El número pin es incorrecto", Toast.LENGTH_LONG).show();
                            break;
                        case 410:
                            try {
                                fireBaseDataService.formSubmittedFailed("messageError", "go to login", "userError410");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            stopTransition();
                            Intent openBrowser410 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apurata.com/login"));
                            startActivity(openBrowser410);
                            finish();
                            System.exit(0);
                            break;
                        case 418:
                            try {
                                fireBaseDataService.formSubmittedSuccessful("message", "I'm a teapot", "418");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            stopTransition();
                            Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apurata.com/login"));
                            startActivity(openBrowser);
                            finish();
                            System.exit(0);
                            break;
                        case 422:
                            df = new SimpleDateFormat("dd MM yyyy, HH:mm:ss");
                            date = df.format(Calendar.getInstance().getTime());
                            bundle.putString("Time", date);
                            bundle.putInt("codeResponse", 422);
                            bundle.putString("idFacebook", idFacebook);
                            stopTransition();
                            String errorBodyRaw = null;
                            try {
                                errorBodyRaw = response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String body = errorBodyRaw.substring(0, errorBodyRaw.length());
                            JsonObject jsonObjectParsed = new JsonParser().parse(errorBodyRaw).getAsJsonObject();

                            ResponseErrorValidation obj = gson.fromJson(jsonObjectParsed, ResponseErrorValidation.class);
                            if (obj.getStatus() != null) {
                                try {
                                    fireBaseDataService.formSubmittedFailed("messageError", "Fallo al enviar, verifique sus datos", "userError422");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), "Fallo al enviar, verifique sus datos", Toast.LENGTH_LONG).show();
                            } else {
                                if (obj.getValidationError().getPhonePin() != null) {
                                    try {
                                        fireBaseDataService.formSubmittedFailed("messageError", "Ingrese un código pin válido", "userError422");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getApplicationContext(), "Ingrese un código pin válido", Toast.LENGTH_LONG).show();
                                } else {
                                    if (obj.getValidationError().getDniPhoto() != null) {
                                        try {
                                            fireBaseDataService.formSubmittedFailed("messageError", "Ingrese la foto de su DNI de nuevo", "userError422");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "Ingrese la foto de su DNI de nuevo", Toast.LENGTH_LONG).show();
                                    } else {
                                        if (obj.getValidationError().getSignedPaperPhoto() != null) {
                                            try {
                                                fireBaseDataService.formSubmittedFailed("messageError", "Ingrese la foto de su firma de nuevo ", "userError422");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            Toast.makeText(getApplicationContext(), "Ingrese la foto de su firma de nuevo ", Toast.LENGTH_LONG).show();
                                        } else {
                                            if (obj.getValidationError().getBankAccount() != null) {
                                                try {
                                                    fireBaseDataService.formSubmittedFailed("messageError", "Ingrese el código de su cuenta nuevamente", "userError422");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                Toast.makeText(getApplicationContext(), "Ingrese el código de su cuenta nuevamente ", Toast.LENGTH_LONG).show();
                                            } else {
                                                if (obj.getError().getMessage() != null) {
                                                    try {
                                                        fireBaseDataService.formSubmittedFailed( "messageError", "Se ingresaron los datos correctamente", "userError422");
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 500:
                            try {
                                fireBaseDataService.formSubmittedFailed("messageError", "Server has a problem. Check it.", "userError500");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            stopTransition();
                            Toast.makeText(getApplicationContext(), "Ocurrió un problema , inténtelo de nuevo", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            try {
                                fireBaseDataService.formSubmittedFailed("messageError", "Unexpected error.", "serverResponseWithAnErrorUnexpected");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            stopTransition();
                            Toast.makeText(getApplicationContext(), "Error inesperado", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<DataFromValidation> call, Throwable t) {
                    try {
                        fireBaseDataService.formSubmittedFailed("messageError", "Unexpected error.", "onFailure500");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stopTransition();
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    boolean isMobileConn = networkInfo.isConnected();
                    connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (!wifiCheck.isConnected() && !isMobileConn) {
                        Toast.makeText(getBaseContext(), "Es necesario que se conecte a Internet para proseguir ...", Toast.LENGTH_SHORT).show();
                        try {
                            fireBaseDataService.formSubmittedFailed("messageError", "el usuario necesita conexión internet/Unexpected error.", "noNetwork");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
        } else {
            Bundle bundle = new Bundle();
            Intent openGodMode = new Intent(this, GodModeActivity.class);
            bundle.putParcelable("item", item);
            openGodMode.putExtra("bundle", bundle);
            openGodMode.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) item.getCoordActivity());
            startActivity(openGodMode);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            if (requestCode == ACCESS_CAMERA_DNI || requestCode == ACCESS_CAMERA_SIGN) {
                if (checkPermissionForExternalStorage()) {
                    if (PICTURE_DNI) {
                        try {
                            nuevoDNI = setPic(imgViewGetDni/*,thumbnail*/);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (PICTURE_SIGN) {
                        try {
                            newSign = setPic(imgViewGetSign/*,thumbnail*/);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (nuevoDNI != null && newSign != null) {
                        myCameraAsynTask = new MyMainCameraAsynTask(this);
                        myCameraAsynTask.execute();
                    }
                } else if (requestCode == PERMISSION_GPS_REQUEST_CODE) {
//                if (resultCode == RESULT_CANCELED){
                    gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    //todo ver el como retorna
                    // gpsStatus = false;
                    if (!gpsStatus) {
                        Snackbar.make(parentMainActivity, "Es necesario encender el GPS", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Activar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(ValidationActivity.this,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                PERMISSION_GPS_REQUEST_CODE);
                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                                                PERMISSION_GPS_REQUEST_CODE);
                                        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                    }
                                }).show();
                        viewAccess.showAlert(btnGetDni, btnGetSign, btnStart);
                    }
                }
            }
        }
    }

    private void openCamera(int code) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (permissionNoGranted) {
                try {
                    fireBaseDataService.statusPermission(PERMISSION_CAMERA, PERMISSION_DENIED);
                    permissionsGranted = true;
                    permissionNoGranted = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (code == ACCESS_CAMERA_DNI || code == ACCESS_CAMERA_SIGN) {
                btnGetDni.setBackgroundColor(Color.parseColor("#808080"));
                btnGetSign.setBackgroundColor(Color.parseColor("#808080"));
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Snackbar.make(parentMainActivity, "Es necesario el permiso de la camara ", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Permitir", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(ValidationActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        PERMISSION_CAMERA_REQUEST_CODE);
                            }
                        }).show();
            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_CAMERA_REQUEST_CODE);
            }

        } else {
            if (permissionsGranted) {
                    try {
                        fireBaseDataService.statusPermission(PERMISSION_CAMERA, PERMISSION_GRANTED);
                        permissionsGranted = false;
                        permissionNoGranted = true;
                        } catch (Exception e) {
                        e.printStackTrace();
                    }
            }


            if (Build.VERSION.SDK_INT < 24) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (checkPermissionForExternalStorage()) {
                        counterStoragePermissions++;
                        if (counterStoragePermissions == 1){
                            try {
                                fireBaseDataService.statusPermission(PERMISSION_STORAGE, PERMISSION_GRANTED);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        btnGetDni.setBackgroundColor(Color.parseColor("#28B463"));
                        btnGetSign.setBackgroundColor(Color.parseColor("#28B463"));
                        if (photoFile != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            startActivityForResult(intent, code);
                        }
                    }
                }
            } else {
                if (checkPermissionForExternalStorage()) {
                    btnGetDni.setBackgroundColor(Color.parseColor("#28B463"));
                    btnGetSign.setBackgroundColor(Color.parseColor("#28B463"));
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        ContentValues values = new ContentValues(1);
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        startActivityForResult(intent, code);
                    } else {
                        Toast.makeText(this, getString(R.string.error_no_camera), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss.SSS").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String setPic(ImageView v/*,Bitmap thumbnail*/) throws IOException {
        targetW = 320;
        targetH = 250;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        if (Build.VERSION.SDK_INT < 24) {
            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            exif = new ExifInterface(mCurrentPhotoPath);
        } else {
            bitmap = BitmapFactory.decodeFile(getRealPathFromUri(getBaseContext(), fileUri), bmOptions);
            exif = new ExifInterface(getRealPathFromUri(getBaseContext(), fileUri));
        }

        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        exif.getAttribute("Camera Identifier");
        exif.getAttribute("Orientation");
        int rotationInDegrees = exifToDegrees(rotation);
        Matrix matrix = new Matrix();
        if (rotation != 0f) {
            matrix.preRotate(rotationInDegrees);
        }
        Bitmap adjustedBitmap = null;
        if (bitmap != null) {
            adjustedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else {
            //   adjustedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
        }
        if (PICTURE_DNI) {
            timeStapDNI = new Date().getTime();
        } else {
            timeStapSign = new Date().getTime();
        }
        if (Build.VERSION.SDK_INT < 24) {
            filePhoto = new File(mCurrentPhotoPath);
        } else {
            filePhoto = new File(getRealPathFromUri(getBaseContext(), fileUri));
        }

        File compressedImageFile = new Compressor(this)
                .setQuality(50)
                .setMaxHeight(480)
                .setMaxWidth(480)
                .compressToFile(filePhoto);
        byte[] fileData = new byte[(int) compressedImageFile.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(compressedImageFile));
        dis.readFully(fileData);
        dis.close();
        String encodedPrueba = Base64.encodeToString(fileData, Base64.DEFAULT);
        Bitmap compressedImageBitmap = new Compressor(this).compressToBitmap(filePhoto);
        String picture = "";
        if (PICTURE_DNI) {
            picture = "DNI";
            showImageInView(v, compressedImageBitmap);
            fireBaseDataService.counterCamera("DNI", openCameraDNI);
        } else if (PICTURE_SIGN) {
            picture = "SIGN";
            showImageInView(v, compressedImageBitmap);
            fireBaseDataService.counterCamera("SIGN", openCameraDNI);        }
        return encodedPrueba;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_GPS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        try {
                            fireBaseDataService.statusPermission(PERMISSION_LOCATION, PERMISSION_GRANTED);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }gpsStatus = true;
                    }
                } else {
                    try {
                        fireBaseDataService.statusPermission(PERMISSION_LOCATION, PERMISSION_DENIED);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ActivityCompat.requestPermissions(ValidationActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_GPS_REQUEST_CODE);
                }
            }
            case PERMISSION_EXTERNAL_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //todo verificar el codigo de entrada
                        btnGetDni.setBackgroundColor(Color.parseColor("#28B463"));
                        btnGetSign.setBackgroundColor(Color.parseColor("#28B463"));
                    }
                } else {

                    /*ActivityCompat.requestPermissions(ValidationActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_EXTERNAL_REQUEST_CODE);*/
                    try {
                        fireBaseDataService.statusPermission(PERMISSION_STORAGE, PERMISSION_DENIED);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    checkPermissionForExternalStorage();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        targetW = imgViewGetDni.getWidth();
        targetH = imgViewGetSign.getHeight();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public Coord checkPermissionForGPS() {
        Coord coordLoginActivity = null;
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Snackbar.make(parentMainActivity, "Debe activar el permiso de GPS", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Activar", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(ValidationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_GPS_REQUEST_CODE);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                        , PERMISSION_GPS_REQUEST_CODE);
            }
        } else {

          /* service.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener2);
            service.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,0,locationListener2);*/
            if (location == null) {
                service = (LocationManager) getSystemService(LOCATION_SERVICE);
                location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
            if (location != null) {
                coordLoginActivity = new Coord();
                coordLoginActivity.setSpeed(location.getSpeed());
                coordLoginActivity.setAccuracy(location.getAccuracy());
                coordLoginActivity.setAltitude(Float.parseFloat(String.valueOf(location.getAltitude())));
                coordLoginActivity.setLongitude(Float.parseFloat(String.valueOf(location.getLongitude())));
                coordLoginActivity.setLatitude(Float.parseFloat(String.valueOf(location.getLatitude())));
            } else {
                coordLoginActivity = null;
            }
        }

        return coordLoginActivity;
    }

    void startAnim() {
        avi.show();
    }

    void stopAnim() {
        avi.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public void startTransition() {
        scrollView.setVisibility(View.GONE);
        scrollView.clearAnimation();
        linearLayoutLoading.setVisibility(View.VISIBLE);
        startAnim();
    }

    public void stopTransition() {
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutLoading.setVisibility(View.GONE);
        stopAnim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //todo camera failed
        if (myCameraAsynTask != null) {
            myCameraAsynTask.cancel(true);
        }
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        moveTaskToBack(true);
        finish();
        return super.moveTaskToBack(nonRoot);
    }

    private void logUser(String userFacebook, String email, String name) {
        Crashlytics.setUserIdentifier(userFacebook);
        Crashlytics.setUserEmail(email);
        Crashlytics.setUserName(name);
    }

    public boolean checkPermissionForExternalStorage() {
        boolean boolResult = false;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            btnGetDni.setBackgroundColor(Color.parseColor("#808080"));
            btnGetSign.setBackgroundColor(Color.parseColor("#808080"));

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Snackbar.make(parentMainActivity, "Es necesario el permiso de acceso a la memoria externa", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Permitir", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(ValidationActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSION_EXTERNAL_REQUEST_CODE);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_EXTERNAL_REQUEST_CODE);
            }
        } else {
            btnGetDni.setBackgroundColor(Color.parseColor("#28B463"));
            btnGetSign.setBackgroundColor(Color.parseColor("#28B463"));
            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                boolResult = true;
            } else {
                boolResult = false;
            }
        }

        return boolResult;
    }


    private void checkPermissionForGPSWithOutObject() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                validationGPS.show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                        , PERMISSION_GPS_REQUEST_CODE);
            }
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(Location newLocation) {
                    location = newLocation;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    if (LocationManager.GPS_PROVIDER.equals(provider)) {
                        Toast.makeText(getApplicationContext(), "GPS on", Toast.LENGTH_SHORT).show();
                        gpsStatus = true;
                        if (gpsStatus) {
                            validationGPS.dismiss();
                            viewAccess.accessGranted(btnGetDni, btnGetSign, btnStart);
                        }
                    }
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(getApplicationContext(), "Es necesario encender el GPS", Toast.LENGTH_LONG);
                    finish();
                    if (LocationManager.GPS_PROVIDER.equals(provider)) {
                        Toast.makeText(getApplicationContext(), "GPS off", Toast.LENGTH_SHORT).show();
                        validationGPS.show();
                        gpsStatus = false;
                        if (!gpsStatus) {
                            viewAccess.showAlert(btnGetDni, btnGetSign, btnStart);
                        }
                    }
                }
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
            gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (gpsStatus) {
                viewAccess.accessGranted(btnGetDni, btnGetSign, btnStart);


            } else {
                viewAccess.showAlert(btnGetDni, btnGetSign, btnStart);
            }
        }

    }


    private class MyMainCameraAsynTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<ValidationActivity> activityRef;
        CameraPixelService cameraPixelService = new CameraPixelService();

        private MyMainCameraAsynTask(ValidationActivity activityRef) {
            this.activityRef = new WeakReference<ValidationActivity>(activityRef);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewAccess.showAlert(btnGetDni, btnGetSign, btnStart);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Se están validando los datos", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {
         /*   runOnUiThread(new Runnable() {
                @Override
                public void run() {*/
            brandCellphone = cameraPixelService.getBrandCellPhone();
            brandCellphone = cameraPixelService.getBrandCellPhone();
            modelCellphone = cameraPixelService.getModelCellPhone();
            versionCellphone = cameraPixelService.getVersionCellPhone();
            getSizeScreen();
            if (cameraPixelService.getWidthFrontCamera() != 0) {
                pxWidthFrontCamera = cameraPixelService.getWidthFrontCamera();
                pxHeightFrontCamera = cameraPixelService.getHeightFrontCamera();
                arrayListForHeight.clear();
                arrayListForWidth.clear();
            }
            pxWidthBackCamera = cameraPixelService.getWidthBackCamera();
            pxHeightBackCamera = cameraPixelService.getHeightBackCamera();
            arrayListForHeight.clear();
            arrayListForWidth.clear();

       /* }
            });*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewAccess.accessGranted(btnGetDni, btnGetSign, btnStart);
        }
    }

    public void getSizeScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthSizeScreen = size.x;
        heightSizeScreen = size.y;
    }


    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void showImageInView(ImageView v, Bitmap imageInBitmap) {
        imgDNIWidth = bitmap.getWidth();
        imgDNIHeight = bitmap.getHeight();
        pxDNI = imgDNIHeight * imgDNIWidth / 1024000;
        if (Build.VERSION.SDK_INT < 24) {
            v.setImageBitmap(imageInBitmap);
        } else {
            Glide.with(this)
                    .load(fileUri)
                    .into(v);
        }
        v.setVisibility(View.VISIBLE);
    }

    public void getLendStatus(String idFacebook) {
        final EveConnection lendService = BackendConnection.getLendService();
        lendService.getUser(idFacebook).enqueue(new Callback<UserStatus>() {
            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {
                newLendStatus = response.body();
                switch (response.code()) {
                    case 200:
                        if (newLendStatus.getAproval_status().compareTo("APROVED") == 0 &&
                                (newLendStatus.getFunding_status().compareTo("WAITING_DATA") == 0) || (newLendStatus.getFunding_status().compareTo("RETRY_ADD_INFO") == 0)) {
                            sendDataFromStep_5();
                        } else {
                            if (newLendStatus.getAproval_status().compareTo("") == 0 && newLendStatus.getFunding_status().compareTo("") == 0) {
                                responseCode = 200;
                                statusValidation = "ReturnToWebActivity";
                                responseValidation = true;
                                stopTransition();
                                linearLayoutLoading.setVisibility(GONE);
                                scrollView.setVisibility(GONE);
                                errorMessageNull.setVisibility(View.VISIBLE);
                                btnGoToApurataCom.setVisibility(View.VISIBLE);
                            } else {
                                responseCode = 200;
                                statusValidation = "ReturnToWebActivity";
                                responseValidation = true;
                            }
                        }
                        break;
                    case 404:
                        responseCode = 404;
                        responseValidation = true;
                        intentOpenWindow = new Intent(getApplicationContext(), ReturnToWebActivity.class);
                        intentOpenWindow.putExtra("CODE", responseCode);
                        intentOpenWindow.putExtra("id", user.getId());
                        if (user.getEmail() != null) {
                            intentOpenWindow.putExtra("email", user.getEmail());
                        }
                        if (user.getFirst_name() != null) {
                            intentOpenWindow.putExtra("name", user.getFirst_name());
                        }
                        startActivity(intentOpenWindow);
                        break;
                    default:
                        if (response.code() >= 500) {
                            responseValidation = true;
                            responseCode = response.code();
                            intentOpenWindow = new Intent(getApplicationContext(), ReturnToWebActivity.class);
                            intentOpenWindow.putExtra("CODE", responseCode);
                            intentOpenWindow.putExtra("id", user.getId());
                            if (user.getEmail() != null) {
                                intentOpenWindow.putExtra("email", user.getEmail());
                            }
                            if (user.getFirst_name() != null) {
                                intentOpenWindow.putExtra("name", user.getFirst_name());
                            }
                            startActivity(intentOpenWindow);
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {

            }
        });
    }
}

