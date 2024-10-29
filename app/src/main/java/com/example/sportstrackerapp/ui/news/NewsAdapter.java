package com.example.sportstrackerapp.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportstrackerapp.R;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Article> articles;

    public NewsAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.getHeadline());
        holder.descriptionTextView.setText(article.getDescription());
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            descriptionTextView = itemView.findViewById(R.id.textDescription);
        }
    }
}
