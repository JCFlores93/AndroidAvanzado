package pe.cibertec.cleanarquitecturedemo.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pe.cibertec.cleanarquitecturedemo.R;
import pe.cibertec.cleanarquitecturedemo.presentation.model.NewsModel;
import pe.cibertec.cleanarquitecturedemo.presentation.view.NewsView;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsFragment extends Fragment
        implements NewsView{

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Liberar la memoria de las vistas
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Context context() {
        return  getContext();
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
        Toast.makeText(context(),errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getNews() {

    }

    @Override
    public void renderNews(List<NewsModel> newsModels) {

    }
}
