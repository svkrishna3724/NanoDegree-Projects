package com.dcoders.satishkumar.g.moviesappstage2;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

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
import com.dcoders.satishkumar.g.moviesappstage2.adapters.ReviewRecyclerAdapter;
import com.dcoders.satishkumar.g.moviesappstage2.modelClasses.ReviewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dcoders.satishkumar.g.moviesappstage2.MainActivity.MOVIE_BASE_URL;

public class Review extends AppCompatActivity {
    @BindView(R.id.review_recycler_view)
    RecyclerView reviewRecycler;
    LinearLayoutManager linearLayoutManager;
    List<ReviewData> reviewDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        reviewDataList = new ArrayList<>();
        String id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("name");
        setTitle(title);
        load(id);
    }

    public void load(String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MOVIE_BASE_URL + String.valueOf(id) + "/reviews?api_key=" + BuildConfig.API_KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            JSONArray array = root.optJSONArray("results");
                            if (array.length() == 0) {

                                new AlertDialog.Builder(Review.this)
                                        .setTitle(getTitle())
                                        .setMessage("There are no reviews to Dispaly")
                                        .show();
                            }
                            int len = array.length();
                            for (int i = 0; i < len; i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String author = jsonObject.optString("author");
                                String comment = jsonObject.optString("content");

                                ReviewData data = new ReviewData(author, comment);
                                reviewDataList.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        reviewRecycler.setAdapter(new ReviewRecyclerAdapter(reviewDataList, Review.this));
                        linearLayoutManager = new LinearLayoutManager(Review.this);
                        reviewRecycler.setLayoutManager(linearLayoutManager);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof TimeoutError
                        ||error instanceof AuthFailureError||error instanceof ParseError
                        ||error instanceof NetworkError||error instanceof ServerError) {
                    new AlertDialog.Builder(Review.this)
                            .setMessage("")
                            .setPositiveButton("",null)
                            .show();
                       }

            }
        });
        queue.add(request);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return  true;
    }
}
