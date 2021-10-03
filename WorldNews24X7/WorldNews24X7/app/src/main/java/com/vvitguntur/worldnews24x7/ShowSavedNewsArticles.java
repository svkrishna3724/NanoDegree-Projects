package com.vvitguntur.worldnews24x7;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.vvitguntur.worldnews24x7.database_classes.SavedNews;
import com.vvitguntur.worldnews24x7.database_classes.SavedNewsViewModel;

import java.util.List;

public class ShowSavedNewsArticles extends AppCompatActivity{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_news_articles);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.saved_articles_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        SavedNewsViewModel savedNewsViewModel = ViewModelProviders.of(this).get(SavedNewsViewModel.class);
        savedNewsViewModel.getAllSavedNews().observe(this, new Observer<List<SavedNews>>() {
            @Override
            public void onChanged(@Nullable List<SavedNews> savedNews) {
                showDataOnRecycler(savedNews);
            }
        });

    }

    private void showDataOnRecycler(List<SavedNews> savedNews)
    {
        FavNewsAdapterRV favNewsAdapterRV = new FavNewsAdapterRV(this,savedNews);
        recyclerView.setAdapter(favNewsAdapterRV);
    }
}
