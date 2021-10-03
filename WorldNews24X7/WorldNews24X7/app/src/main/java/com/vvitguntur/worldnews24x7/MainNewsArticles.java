package com.vvitguntur.worldnews24x7;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vvitguntur.worldnews24x7.news_articles_pojo_classes.Article;
import com.vvitguntur.worldnews24x7.news_articles_pojo_classes.NewsArticleDetails;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainNewsArticles extends Fragment implements LoaderManager.LoaderCallbacks<String>
{

    private String category;
    private RecyclerView news_recycler_view;
    private LinearLayoutManager llm;
    private ProgressBar progress_spinner;
    private int LOADER_ID;
    private static final String save_list_state="save_state";
    private Parcelable listState;

    public MainNewsArticles()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_news_articles,container, false);
        news_recycler_view = view.findViewById(R.id.news_article_recycler_view);
        progress_spinner = view.findViewById(R.id.progress_spinner);
        progress_spinner.setVisibility(View.INVISIBLE);
        llm = new LinearLayoutManager(getActivity());
        news_recycler_view.setLayoutManager(llm);
        if(savedInstanceState!=null)
        {
            int position = savedInstanceState.getInt(getResources().getString(R.string.scroll_position));
            llm.scrollToPosition(position);
        }
        if(getActivity().getSupportLoaderManager().getLoader(LOADER_ID)!=null)
        {
            getActivity().getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        }
        else
        {
            getActivity().getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            category = getArguments().getString(getResources().getString(R.string.category_key));
            if(category.equals(getResources().getString(R.string.category_general)))
                LOADER_ID = 1;
            if(category.equals(getResources().getString(R.string.category_business)))
                LOADER_ID = 2;
            if(category.equals(getResources().getString(R.string.category_entertainment)))
                LOADER_ID = 3;
            if(category.equals(getResources().getString(R.string.category_health)))
                LOADER_ID = 4;
            if(category.equals(getResources().getString(R.string.category_science)))
                LOADER_ID = 5;
            if(category.equals(getResources().getString(R.string.category_sports)))
                LOADER_ID = 6;
            if(category.equals(getResources().getString(R.string.category_technology)))
                LOADER_ID = 7;
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        progress_spinner.setVisibility(View.VISIBLE);
        return new LoadNewsData(getActivity(),category);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        progress_spinner.setVisibility(View.INVISIBLE);
        Gson gson = new GsonBuilder().create();
        NewsArticleDetails newsArticleDetails = gson.fromJson(data,NewsArticleDetails.class);
        List<Article> news_articles = new ArrayList<>();
        news_articles = newsArticleDetails.getArticles();
        NewsArticlesAdapter newsArticlesAdapter = new NewsArticlesAdapter(getActivity(),news_articles);
        news_recycler_view.setAdapter(newsArticlesAdapter);
        llm.onRestoreInstanceState(listState);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        listState=llm.onSaveInstanceState();
        outState.putParcelable(save_list_state,listState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null)
        {
            listState=savedInstanceState.getParcelable(save_list_state);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listState!=null)
        {
            llm.onRestoreInstanceState(listState);
        }
    }
}
