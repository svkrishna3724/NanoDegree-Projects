package com.vvitguntur.worldnews24x7;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vvitguntur.worldnews24x7.database_classes.SavedNews;
import com.vvitguntur.worldnews24x7.database_classes.SavedNewsViewModel;
import java.util.List;

public class DetailsScreen extends AppCompatActivity {

    private String imageUrl;
    private String ntitle;
    private String ndescription;
    private String nnews_url;
    private SavedNewsViewModel mSavedNewsViewModel;
    private CoordinatorLayout mCoordinatorLayout;
    private Button likeButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("World News");
        mSavedNewsViewModel = ViewModelProviders.of(this).get(SavedNewsViewModel.class);
        ImageView imageView = findViewById(R.id.bannerImage);
        likeButton = findViewById(R.id.save_unsave_button);
        TextView title = findViewById(R.id.news_title);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        TextView description = findViewById(R.id.news_description);
        TextView url_to_news_article = findViewById(R.id.news_web_link);
        SharedPreferences sharedPreferences = getSharedPreferences("WIDGET_SHARED_PREFERENCES", MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            imageUrl = bundle.getString(getResources().getString(R.string.image_url_key));
            ntitle = bundle.getString(getString(R.string.title_key));
            ndescription = bundle.getString(getString(R.string.description_key));
            nnews_url = bundle.getString(getString(R.string.news_url_key));
        }

        Glide.with(this).load(imageUrl).into(imageView);
        title.setText(ntitle);
        description.setText(ndescription);
        url_to_news_article.setText(nnews_url);
        setLikeButtonStatus(ntitle);
        String widgetText = "Title:"+ntitle+"\n\nDescription:"+ndescription+"\n\n"+nnews_url;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("WIDGET",widgetText);
        editor.apply();

    }

    private void setLikeButtonStatus(final String titles) {
        mSavedNewsViewModel.getAllSavedNews().observe(this, new Observer<List<SavedNews>>() {
            @Override
            public void onChanged(@Nullable List<SavedNews> savedNews) {
                showStatusOnLikeButton(savedNews,titles);
            }
        });
    }

    private void showStatusOnLikeButton(List<SavedNews> savedNews,String titles)
    {
        for(int i=0;i<savedNews.size();i++)
        {
            if(titles.equals(savedNews.get(i).getTitle()))
            {
                likeButton.setText(R.string.unsave_article_button_text);
            }
        }
    }

    public void saveArticle(View view)
    {
        if((likeButton.getText().toString()).equals(getResources().getString(R.string.click_here_to_save_the_article)))
        {
            SavedNews savedNews = new SavedNews();
            savedNews.setTitle(ntitle);
            savedNews.setDescription(ndescription);
            savedNews.setImage_url(imageUrl);
            savedNews.setNews_url(nnews_url);
            mSavedNewsViewModel.insert(savedNews);
            Snackbar.make(mCoordinatorLayout, "DATA INSERTION SUCCESSFUL", BaseTransientBottomBar.LENGTH_LONG).show();
            likeButton.setText(R.string.unsave_article_button_text);
        }
        else
        {
            SavedNews savedNews = new SavedNews();
            savedNews.setTitle(ntitle);
            savedNews.setDescription(ndescription);
            savedNews.setImage_url(imageUrl);
            savedNews.setNews_url(nnews_url);
            mSavedNewsViewModel.delete(savedNews);
            Snackbar.make(mCoordinatorLayout, "DATA DELETED SUCCESSFULLY", BaseTransientBottomBar.LENGTH_LONG).show();
            likeButton.setText(R.string.click_here_to_save_the_article);
        }
    }
}
