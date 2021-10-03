package com.dcoders.satishkumar.g.moviesappstage2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.dcoders.satishkumar.g.moviesappstage2.adapters.YoutubeRecyclerAdapter;
import com.dcoders.satishkumar.g.moviesappstage2.modelClasses.VideoData;
import com.dcoders.satishkumar.g.moviesappstage2.movie.Result;
import com.dcoders.satishkumar.g.moviesappstage2.room.FavMovieDatabase;
import com.dcoders.satishkumar.g.moviesappstage2.room.FavMovieViewModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dcoders.satishkumar.g.moviesappstage2.MainActivity.KEY;
import static com.dcoders.satishkumar.g.moviesappstage2.MainActivity.MOVIE_BASE_URL;
import static com.dcoders.satishkumar.g.moviesappstage2.MainActivity.SAVE_KEY;
import static com.dcoders.satishkumar.g.moviesappstage2.adapters.MainAdapter.IMAGE_BASE_URL;
import static com.dcoders.satishkumar.g.moviesappstage2.adapters.MainAdapter.SHARE;

public class OverView extends AppCompatActivity {
    FavMovieViewModel viewModel;
    FavMovieDatabase database;
    List<Result> checklist;
    @BindView(R.id.over_view_poster)
    ImageView poster;
    @BindView(R.id.over_view_overView_tv)
    TextView overView_tv;
    @BindView(R.id.over_view_vote_count_tv)
    TextView voteCount_tv;
    @BindView(R.id.over_view_rating_tv)
    TextView rating_tv;
    @BindView(R.id.over_view_favorites_image)
    ImageView favorite_image;
    @BindView(R.id.over_view_release_date_tv)
    TextView releaseDate_tv;
    Result mresult;
    @BindView(R.id.over_view_rating_label)
    TextView overViewRatingLabel;
    @BindView(R.id.over_view_release_date_label)
    TextView overViewReleaseDateLabel;
    @BindView(R.id.over_view_vote_count_label)
    TextView overViewVoteCountLabel;
    @BindView(R.id.over_view_favorites_label)
    TextView overViewFavoritesLabel;
    @BindView(R.id.over_view_overView_label)
    TextView overViewOverViewLabel;
    @BindView(R.id.over_view_recyclerView)
    RecyclerView overViewRecycler;
    List<VideoData> list = new ArrayList<>();
    LinearLayoutManager layoutManager;
    boolean state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(FavMovieViewModel.class);
        Bundle bundle = getIntent().getExtras();
        checklist=new ArrayList<>();
        assert bundle != null;
        mresult = (Result) bundle.getSerializable(SHARE);
        assert mresult != null;
        setTitle(mresult.getOriginalTitle());

        Glide.with(this)
                .load(IMAGE_BASE_URL + mresult.getPosterPath())
                .into(poster);
        overView_tv.setText(mresult.getOverview());
        rating_tv.setText(String.valueOf(mresult.getVoteAverage()));
        releaseDate_tv.setText(mresult.getReleaseDate());
        voteCount_tv.setText(String.valueOf(mresult.getVoteCount()));
        updateImageView();
        if (savedInstanceState!=null)
        {
            int position=savedInstanceState.getInt(SAVE_KEY);
            list= (List<VideoData>) savedInstanceState.getSerializable(KEY);
            overViewRecycler.setAdapter(new YoutubeRecyclerAdapter(list, OverView.this));
            layoutManager = new LinearLayoutManager(OverView.this);
            overViewRecycler.setLayoutManager(layoutManager);
        }
        else {
            parseYoputube(mresult.getId());
            updateImageView();
        }
    }

    private void updateImageView()
    {
        Result r =  viewModel.checkMovieInDatabase(mresult.getId());
        if(r!=null)
        {
            favorite_image.setImageResource(R.drawable.ic_star_green_24dp);
            state = true;
        }
        else
        {
            favorite_image.setImageResource(R.drawable.ic_star_border_black_24dp);
            state = false;
        }
    }

    public void parseYoputube(String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri uri = Uri.parse(MOVIE_BASE_URL + id + "/videos?&api_key=" + BuildConfig.API_KEY);
        String url = uri.toString();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject root = new JSONObject(response);
                            JSONArray array = root.optJSONArray("results");
                            int len = array.length();
                            for (int i = 0; i < len; i++) {
                                JSONObject object = array.optJSONObject(i);
                                String key = object.optString("key");
                                String name = object.optString("name");
                                VideoData vd = new VideoData(name, key);
                                list.add(vd);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        overViewRecycler.setAdapter(new YoutubeRecyclerAdapter(list, OverView.this));
                        layoutManager = new LinearLayoutManager(OverView.this);
                        overViewRecycler.setLayoutManager(layoutManager);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof TimeoutError
                        ||error instanceof AuthFailureError||error instanceof ParseError
                        ||error instanceof NetworkError||error instanceof ServerError) {
                    new AlertDialog.Builder(OverView.this)
                            .setMessage("")
                            .setPositiveButton("",null)
                            .show();
                }

            }
        });
        queue.add(request);
    }


    public void openReview(View view) {
        Intent intent = new Intent(this, Review.class);
        intent.putExtra("name", mresult.getOriginalTitle());
        intent.putExtra("id", mresult.getId());
        startActivity(intent);
    }


    public void change(View view)
    {

        if (state) {
            delete();
            favorite_image.setImageResource(R.drawable.ic_star_border_black_24dp);
            state = !state;
        } else {
            insert();
            favorite_image.setImageResource(R.drawable.ic_star_green_24dp);
            state = !state;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void insert()  {
        String name = mresult.getOriginalTitle();
        String overview = mresult.getOverview();
        String releasedate = mresult.getReleaseDate();
        String poster = mresult.getPosterPath();
        double rating = mresult.getVoteAverage();
        String id = mresult.getId();
        int votcount = mresult.getVoteCount();
        Result result = new Result();
        result.setId(id);
        result.setOriginalTitle(name);
        result.setPosterPath(poster);
        result.setOverview(overview);
        result.setReleaseDate(releasedate);
        result.setVoteAverage(rating);
        result.setVoteCount(votcount);
        viewModel.insert(result);
        Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();

    }

    public void delete() {
        String name = mresult.getOriginalTitle();
        String overview = mresult.getOverview();
        String releasedate = mresult.getReleaseDate();
        String poster = mresult.getPosterPath();
        Double rating = mresult.getVoteAverage();
        String id = mresult.getId();
        int votcount = mresult.getVoteCount();
        Result result = new Result();
        result.setOriginalTitle(name);
        result.setVoteCount(votcount);
        result.setPosterPath(poster);
        result.setOverview(overview);
        result.setId(id);
        result.setVoteAverage(rating);
        result.setReleaseDate(releasedate);
        viewModel.delete(result);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManager!=null)
        {
            int pos=layoutManager.findFirstCompletelyVisibleItemPosition();
            outState.putInt(SAVE_KEY,pos);
        }
        outState.putSerializable(KEY, (Serializable) list);

    }
}
