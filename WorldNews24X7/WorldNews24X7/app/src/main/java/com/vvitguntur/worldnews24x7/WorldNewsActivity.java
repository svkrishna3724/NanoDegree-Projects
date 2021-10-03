package com.vvitguntur.worldnews24x7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vvitguntur.worldnews24x7.utilities.NetworkUtils;

public class WorldNewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private String user_name;
    private String user_email;
    private String photo_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(NetworkUtils.isInternetAvailable(this))
        {

        }
        else
        {
            NetworkUtils.showAlertDialog(this);
        }
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(WorldNewsActivity.this,FeedbackActivity.class));
            }
        });

        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        if(bundle!=null)
        {
            user_name  = bundle.getString(WorldNewsLogin.NAME);
            user_email = bundle.getString(WorldNewsLogin.EMAIL);
            photo_url = bundle.getString(WorldNewsLogin.PHOTO_URL);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView uName = view.findViewById(R.id.u_name);
        TextView uEmail = view.findViewById(R.id.email_user);
        ImageView p_url = view.findViewById(R.id.imageView);

        if(user_name!=null)
            uName.setText(user_name);
        if(user_email!=null)
            uEmail.setText(user_email);
        if(photo_url!=null)
            Glide.with(this).load(photo_url).into(p_url);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.world_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.saved_articles){
            startActivity(new Intent(this,ShowSavedNewsArticles.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.general_news)
        {
            Fragment fragment = new MainNewsArticles();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.category_key),getString(R.string.category_general));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.news_container,fragment).commit();
        }
        else if (id == R.id.business_news)
        {
            Fragment fragment = new MainNewsArticles();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.category_key),getString(R.string.category_business));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.news_container,fragment).commit();
        }
        else if (id == R.id.technology_news)
        {   Fragment fragment = new MainNewsArticles();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.category_key),getString(R.string.category_technology));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.news_container,fragment).commit();
        }
        else if (id == R.id.science_news)
        {
            Fragment fragment = new MainNewsArticles();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.category_key),getString(R.string.category_science));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.news_container,fragment).commit();
        }
        else if (id == R.id.sports_news)
        {
            Fragment fragment = new MainNewsArticles();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.category_key),getString(R.string.category_sports));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.news_container,fragment).commit();
        }
        else if (id == R.id.entertainment_news)
        {
            Fragment fragment = new MainNewsArticles();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.category_key),getString(R.string.category_entertainment));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.news_container,fragment).commit();
        }
        else if(id == R.id.health_news)
        {
            Fragment fragment = new MainNewsArticles();
            FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.category_key),getString(R.string.category_health));
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.news_container,fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
