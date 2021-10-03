package com.vvitguntur.worldnews24x7;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vvitguntur.worldnews24x7.database_classes.SavedNews;

import java.util.List;

public class FavNewsAdapterRV extends RecyclerView.Adapter<FavNewsAdapterRV.ViewInformation> {
    private final Context context;
    private final List<SavedNews> savedNewsArticles;

    public FavNewsAdapterRV(Context context, List<SavedNews> savedNewsArticles) {
        this.context = context;
        this.savedNewsArticles = savedNewsArticles;
    }

    @NonNull
    @Override
    public ViewInformation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewInformation(LayoutInflater.from(context).inflate(R.layout.news_articles_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewInformation holder, int position) {
        holder.news_article_title.setText(savedNewsArticles.get(position).getTitle());
        Glide.with(context).load(savedNewsArticles.get(position).getImage_url()).into(holder.news_article_image);
    }

    @Override
    public int getItemCount() {
        return savedNewsArticles.size();
    }

    public class ViewInformation extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView news_article_title;
        final ImageView news_article_image;

        ViewInformation(View itemView) {
            super(itemView);
            news_article_image = itemView.findViewById(R.id.news_article_image);
            news_article_title = itemView.findViewById(R.id.news_article_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            SavedNews savedNews = savedNewsArticles.get(position);
            Intent intent = new Intent(context, DetailsScreen.class);
            Bundle bundle = new Bundle();
            bundle.putString(context.getString(R.string.title_key), savedNews.getTitle());
            bundle.putString(context.getString(R.string.description_key), savedNews.getDescription());
            bundle.putString(context.getString(R.string.image_url_key), savedNews.getImage_url());
            bundle.putString(context.getString(R.string.news_url_key), savedNews.getNews_url());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
