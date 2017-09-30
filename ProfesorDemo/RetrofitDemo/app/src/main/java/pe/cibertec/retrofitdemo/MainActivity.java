package pe.cibertec.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private Button btnSearch,btnAdd,btnUpdate,btnDelete;
    private EditText edtName,edtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        edtName = (EditText) findViewById(R.id.edtName);
        edtView = (EditText) findViewById(R.id.edtView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new NewsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        getNews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getNews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSearch :
                break;
            case R.id.btnAdd:
                String newName = edtName.getText().toString().trim();
                String newImg = edtView.getText().toString().trim();
                if (!TextUtils.isEmpty(newName) && !TextUtils.isEmpty(newImg)){
                    News newInsert = new News();
                    newInsert.setTitle(newName);
                    newInsert.setImageUrl(newImg);
                    insert(newInsert);
                }else{
                    Toast.makeText(this,"Ingrese los datos",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnUpdate:
                String updateName = edtName.getText().toString().trim();
                String updateImg = edtView.getText().toString().trim();
                if (!TextUtils.isEmpty(updateName) && !TextUtils.isEmpty(updateImg)){
                News uNews = new News();
                uNews.setTitle(updateName);
                update(updateName,updateImg);
                }else{
                    Toast.makeText(this,"Ingrese los datos",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnDelete :
                String deleteName = edtName.getText().toString().trim();
                if (!TextUtils.isEmpty(deleteName)){
                News dNews = new News();
                dNews.setTitle(deleteName);
                delete(deleteName);
                }else{Toast.makeText(this,"Ingrese los datos",Toast.LENGTH_LONG).show();
                }
                break;


        }
    }


    private void getNews() {
        NewsService newsService = RetrofitUtils.getNewsService();

        newsService.getNews(100).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                final List<News> newsList = response.body();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setList(newsList);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    private void insert(News newRow) {
        NewsService newsService = RetrofitUtils.getNewsService();
        //enqueue : envía la petición de manera asíncrona
        //callback : se obtiene la respuesta
        newsService.insertNews(newRow).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
               Log.d("Not working",""+response.code()) ;
                if (response.isSuccessful()) {
                    Log.i("Insertando ","Se insertó en API : " + response.body());

                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e("Main Activity", "Unable to submit post to API.");
                if (call.isCanceled()){
                    Log.e("Main Activity", "request was aborted");
                }else {
                    Log.e("Main Activity", "Unable to submit post to API.");
                }
            }
        });
    }

    private void update(final String uName, final String uImg){

         final NewsService newsService = RetrofitUtils.getNewsService();
        newsService.getNews(100).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
               final List<News> newsList  = response.body();
                News newsToUpdate = new News();
                if (newsList != null){
                        for (News object : newsList){
                            if(object.getTitle().compareTo(uName) == 0){
                                 newsToUpdate.setObjectId(object.getObjectId());
                                 newsToUpdate.setTitle(object.getTitle());
                                 newsToUpdate.setImageUrl(uImg);

                            }
                        }
                        newsService.updateNews(newsToUpdate.getObjectId(),newsToUpdate).enqueue(new Callback<News>() {
                            @Override
                            public void onResponse(Call<News> call, Response<News> response) {
                                if (response.isSuccessful()) {
                                    Log.i("Actualizado ","Se actualizó en API : " + response.body());

                                }
                            }

                            @Override
                            public void onFailure(Call<News> call, Throwable t) {

                            }
                        });
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });

    }

    private void delete(final String titleToDelete){
        final NewsService newsService = RetrofitUtils.getNewsService();
        newsService.getNews(100).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                final List<News> newsList  = response.body();
                String idToDelete = "";
                if (newsList != null){
                    for (News object : newsList){
                        if(object.getTitle().compareTo(titleToDelete) == 0){
                            idToDelete =object.getObjectId();
                        }
                    }
                    newsService.deleteNews(idToDelete).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.i("Eliminado ","Se elimnó en API : " + response.body());

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });

    }
}
