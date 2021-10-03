package com.blogspot.pavankreddy.bakingapp.uiScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.blogspot.pavankreddy.bakingapp.Constants_class.ProjectConstants;
import com.blogspot.pavankreddy.bakingapp.R;
import com.blogspot.pavankreddy.bakingapp.data.Step;
import java.io.Serializable;
import java.util.List;


public class StepDetailActivity extends AppCompatActivity {

    List<Step> steps;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (savedInstanceState == null) {
            Intent intent=getIntent();
            Bundle arguments =intent.getBundleExtra(ProjectConstants.BUNDLE_KEY);
            steps= (List<Step>) arguments.getSerializable(ProjectConstants.STEP_LIST_ACTIVITY_EXTRA_KEY);
            pos=arguments.getInt(ProjectConstants.POSITION_KEY,0);
            arguments.putSerializable(ProjectConstants.STEP_LIST_ACTIVITY_EXTRA_KEY,
                    (Serializable) steps);
            arguments.putInt(ProjectConstants.POSITION_KEY,pos);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();
            return true;
        }
        return true;
    }
}
