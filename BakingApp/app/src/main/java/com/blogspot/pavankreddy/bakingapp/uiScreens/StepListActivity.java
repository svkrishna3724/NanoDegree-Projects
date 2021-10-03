package com.blogspot.pavankreddy.bakingapp.uiScreens;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.blogspot.pavankreddy.bakingapp.Constants_class.ProjectConstants;
import com.blogspot.pavankreddy.bakingapp.HomeScreenWidget.BakingAppWidget;
import com.blogspot.pavankreddy.bakingapp.R;
import com.blogspot.pavankreddy.bakingapp.adapter.IngredientsListAdapter;
import com.blogspot.pavankreddy.bakingapp.adapter.StepsListAdapter;
import com.blogspot.pavankreddy.bakingapp.data.Ingredient;
import com.blogspot.pavankreddy.bakingapp.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity
{
    private boolean mTwoPane;

    @BindView(R.id.ingredients_list)
    RecyclerView recyclerView;
    @BindView(R.id.steps_list) RecyclerView recycler;

    List<Ingredient> ingredients;
    List<Step> steps;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        setTitle(getIntent().getStringExtra(ProjectConstants.RECIPIE_TITLE));
        if(findViewById(R.id.step_detail_container)!=null)
        {
            mTwoPane = true;
        }

        ButterKnife.bind(this);

        ingredients = (List<Ingredient>) getIntent().getSerializableExtra(ProjectConstants.INGREDIENTS_LIST_KEY);
        recyclerView.setAdapter(new IngredientsListAdapter(this,ingredients));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        StringBuilder stringBuilder = new StringBuilder();
        int stepVal = 1;
        for(int i= 0 ;i<ingredients.size();i++)
        {
            stringBuilder.append(""+(stepVal)+". "+ingredients.get(i).getIngredient()+"\n");
            stepVal++;
        }
        String total_ingredients = stringBuilder.toString();
        sharedPreferences = getSharedPreferences(ProjectConstants.BAKING_APP_SHARED_PREFERENCES_KEY,0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(ProjectConstants.SP_TEXT_INGRED_KEY,total_ingredients);
        edit.putString(ProjectConstants.RECIPE_NAME_KEY,getIntent().getStringExtra(ProjectConstants.RECIPIE_TITLE));
        edit.apply();
        //call Widget to update it using BroadCastIntent
        Intent intent = new Intent(this, BakingAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(), BakingAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);

        steps = (List<Step>) getIntent().getSerializableExtra(ProjectConstants.STEPS_LIST_KEY);
        recycler.setAdapter(new StepsListAdapter(this,steps,mTwoPane));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            navigateUpTo(new Intent(this,HomeActivity.class));
        }
        return true;
    }
}
