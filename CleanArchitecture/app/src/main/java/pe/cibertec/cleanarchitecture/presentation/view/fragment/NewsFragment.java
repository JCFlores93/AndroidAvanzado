package pe.cibertec.cleanarchitecture.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pe.cibertec.cleanarchitecture.R;
import pe.cibertec.cleanarchitecture.presentation.model.NewsModel;
import pe.cibertec.cleanarchitecture.presentation.presenter.NewsPresenter;
import pe.cibertec.cleanarchitecture.presentation.view.NewsView;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsFragment extends Fragment
        implements NewsView {

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Unbinder unbinder;

    private NewsPresenter newsPresenter;

    private ArrayAdapter<NewsModel> adapter;
    private List<NewsModel> newsModelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            newsPresenter = new NewsPresenter(this);
            getNews();

            adapter = new ArrayAdapter<NewsModel>(getContext(),
                    android.R.layout.simple_list_item_1, newsModelList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Context context() {
        return getContext();
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
        Snackbar.make(listView, errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNews();
                    }
                }).show();
    }

    @Override
    public void getNews() {
        newsPresenter.getNews();
    }

    @Override
    public void renderNews(List<NewsModel> newsModelList) {
        adapter.clear();
        adapter.addAll(newsModelList);
    }
}
