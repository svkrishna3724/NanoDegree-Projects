package com.dcoders.satishkumar.g.moviesappstage2;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dcoders.satishkumar.g.moviesappstage2.adapters.MainAdapter;
import com.dcoders.satishkumar.g.moviesappstage2.movie.MovieDetails;
import com.dcoders.satishkumar.g.moviesappstage2.movie.Result;
import com.dcoders.satishkumar.g.moviesappstage2.room.FavMovieDatabase;
import com.dcoders.satishkumar.g.moviesappstage2.room.FavMovieViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    public static final String MOVIE_BASE_URL="http://api.themoviedb.org/3/movie/";
    public static final String MOVIE_END_URL="?api_key="+BuildConfig.API_KEY;
    public static final String POPULAR="popular";
    public static final String RATED="top_rated";
    public static final String SAVE_KEY="SAVE";
    public static final String KEY="KEY";
    public static final String TITLE="TITLE";
    String sort;
    String titletoSave;
    List<Result> results;
    MainAdapter mainAdapter;
    FavMovieViewModel mFavMovieViewModel;
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.main_activity_progress_bar)
    ProgressBar main_progressBar;
    @BindView(R.id.main_activity_recyclerView)
    RecyclerView main_recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sort=POPULAR;
        results=new ArrayList<>();
        main_progressBar.setVisibility(View.VISIBLE);
        gridLayoutManager=new GridLayoutManager(this,checkTheColumnns(this));

        mFavMovieViewModel = ViewModelProviders.of(this).get(FavMovieViewModel.class);
        if (savedInstanceState!=null)
        {
            String title=savedInstanceState.getString("TITLE");
            setTitle(title);
            int position=savedInstanceState.getInt(SAVE_KEY);
            results= (List<Result>) savedInstanceState.getSerializable(KEY);
            main_progressBar.setVisibility(View.GONE);
            main_recyclerView.setAdapter(new MainAdapter(this,results));

            main_recyclerView.setLayoutManager(gridLayoutManager);
            gridLayoutManager.scrollToPosition(position);
        } else {
            if (connection())
            {
                fetchMovies(sort);
            }
            else
            {
                loadFavorites();
            }
        }
    }

    private boolean connection()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        return info!=null&&info.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.popular:
                sort=POPULAR;
                setTitle(getResources().getString(R.string.popular_movies));
                fetchMovies(sort);
                break;
            case R.id.rated:
                sort=RATED;
                setTitle(getResources().getString(R.string.top_rated_moviw));
                fetchMovies(sort);
                break;
            case R.id.favorites:
                setTitle(getResources().getString(R.string.favorite_movies));
                loadFavorites();
                break;

        }
        return true;

    }

    private void loadFavorites() {
        setTitle(getResources().getString(R.string.favorite_movies));
        main_progressBar.setVisibility(View.GONE);
        mFavMovieViewModel.getAllResults().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> resultss)
            {
                results=resultss;
                mainAdapter = new MainAdapter(MainActivity.this,resultss);
                main_recyclerView.setAdapter(mainAdapter);
                main_recyclerView.setLayoutManager(gridLayoutManager);
            }
        });
    }

    public void fetchMovies(String sORT)
    {
        if (results!=null)
        {
            results.clear();
        }

        RequestQueue queue= Volley.newRequestQueue(this);
        String url=MOVIE_BASE_URL+sORT+MOVIE_END_URL;
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Gson gson=new GsonBuilder().create();
                        MovieDetails movieDetails=gson.fromJson(response,MovieDetails.class);
                        results.addAll(movieDetails.getResults());
                        main_progressBar.setVisibility(View.GONE);
                        main_recyclerView.setAdapter(new MainAdapter(MainActivity.this,results));
                        main_recyclerView.setLayoutManager(gridLayoutManager);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof NoConnectionError || error instanceof TimeoutError
                        ||error instanceof AuthFailureError||error instanceof ParseError
                        ||error instanceof NetworkError||error instanceof ServerError) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error in Connection")
                            .setMessage("There is no intenet Connection")
                            .setPositiveButton("Ok", null)
                            .show();
                }

            }

    });
        queue.add(request);
    }

    public int checkTheColumnns(Context context)
    {
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        float width=displayMetrics.widthPixels/displayMetrics.density;
        int scale=150;
        int c= (int) (width/scale);
        return (c>=2?c:2);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (gridLayoutManager!=null)
        {
            int pos=gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            outState.putInt(SAVE_KEY,pos);
        }
        outState.putSerializable(KEY, (Serializable) results);
        outState.putString(TITLE,getTitle().toString());
        }

}
