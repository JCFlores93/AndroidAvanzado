package pe.cibertec.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MainActivity";


    private EditText edtTitle,edtDetail,edtImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTitle = (EditText) findViewById(R.id.edt_title);
        edtDetail= (EditText) findViewById(R.id.edt_detail);
        edtImageUrl = (EditText) findViewById(R.id.edt_image_url);

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MediaType mediaType = MediaType.parse("application/json");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        News news = new News();
        news.title =edtTitle.getText().toString();
        news.detail =edtDetail.getText().toString();
        news.imageUrl =edtImageUrl.getText().toString();

        Gson gson = new Gson();
        String jsonBody = gson.toJson(news);

        //Aqu{i enviamos los datos al servidor
        RequestBody requestBody = RequestBody.create(mediaType,jsonBody);

        Request request = new Request.Builder()
                .url(Constants.URL)
                .post(requestBody)
                .build();
     //Nos permite devolver la llamada al hilo donde fue llamado
            okHttpClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e(TAG,"onFailure : ",e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String jsonResponse = response.body().string();
                            Log.d(TAG,"response ok");
                        }
                    });


    }
}
