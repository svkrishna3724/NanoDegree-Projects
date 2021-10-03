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
import com.vvitguntur.worldnews24x7.news_articles_pojo_classes.Article;

import java.util.List;

public class NewsArticlesAdapter extends RecyclerView.Adapter<NewsArticlesAdapter.ViewInformation>
{

    private final Context context;
    private final List<Article> articles;

    public NewsArticlesAdapter(Context context, List<Article> articles)
    {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewInformation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewInformation(LayoutInflater.from(context).inflate(R.layout.news_articles_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewInformation holder, int position)
    {
        if(articles.get(position).getUrlToImage()!=null)
        {
            Glide.with(context).load(articles.get(position).getUrlToImage()).into(holder.news_article_image);
        }
        holder.news_article_title.setText(articles.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewInformation extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        final TextView news_article_title;
        final ImageView news_article_image;
        ViewInformation(View itemView) {
            super(itemView);
            news_article_image = itemView.findViewById(R.id.news_article_image);
            news_article_title = itemView.findViewById(R.id.news_article_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
           int position = getAdapterPosition();
           Article article = articles.get(position);
           Intent intent = new Intent(context,DetailsScreen.class);
           Bundle bundle = new Bundle();
           bundle.putString(context.getString(R.string.title_key),article.getTitle());
           bundle.putString(context.getString(R.string.description_key),article.getDescription());
           bundle.putString(context.getString(R.string.image_url_key),article.getUrlToImage());
           bundle.putString(context.getString(R.string.news_url_key),article.getUrl());
           intent.putExtras(bundle);
           context.startActivity(intent);
        }
    }
}
