package pe.cibertec.retrofitdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 20/05/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> list = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<News> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgNews;
        TextView txtNewsTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imgNews = (ImageView) itemView.findViewById(R.id.img_news);
            txtNewsTitle = (TextView) itemView.findViewById(R.id.txt_news);
        }

        public void bind(News news) {
            txtNewsTitle.setText(news.getTitle());
            Glide.with(imgNews.getContext())
                    .load(news.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .crossFade()
                    .into(imgNews);
        }
    }
}
