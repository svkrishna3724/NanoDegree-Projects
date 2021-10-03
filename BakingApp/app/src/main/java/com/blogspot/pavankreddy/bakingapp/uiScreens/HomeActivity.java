package com.blogspot.pavankreddy.bakingapp.uiScreens;


import android.content.DialogInterface;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.pavankreddy.bakingapp.Constants_class.ProjectConstants;
import com.blogspot.pavankreddy.bakingapp.R;
import com.blogspot.pavankreddy.bakingapp.adapter.HomeActivityAdapter;

import com.blogspot.pavankreddy.bakingapp.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
{
    private static final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    List<Recipe> recipesList = new ArrayList<>();
    Recipe[] recipes;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.pro_bar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        if(savedInstanceState!=null)
        {
            if(savedInstanceState.getSerializable(ProjectConstants.HOME_ACTIVITY_SERIALIZABLE_KEY)!=null){
                recipesList = (List<Recipe>) savedInstanceState.getSerializable(ProjectConstants.HOME_ACTIVITY_SERIALIZABLE_KEY);
            }
            else if(isNetworkAvailable())
            {
                loadData();
            }
            else{
                showAlert();
            }
        }
        else if(isNetworkAvailable())
        {
            loadData();
        }
        else {
        showAlert();
    }

    }

    private void showAlert()
    {
        new AlertDialog.Builder(this)
                .setTitle("INTERNET IS NOT AVAILABLE")
                .setMessage("Kindly connect to internet to proceed Further. Click 'yes' to close app!")
                .setIcon(R.drawable.ic_internet_not_available)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void loadData()
    {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                recipes = gson.fromJson(response, Recipe[].class);
                recipesList.addAll(Arrays.asList(recipes));
                HomeActivityAdapter haa = new HomeActivityAdapter(HomeActivity.this,recipesList);
                recyclerView.setAdapter(haa);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(recipesList!=null)
        {
            outState.putSerializable(ProjectConstants.HOME_ACTIVITY_SERIALIZABLE_KEY, (Serializable) recipesList);
        }
    }
}
