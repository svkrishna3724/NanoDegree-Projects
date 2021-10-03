package com.blogspot.pavankreddy.bakingapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.pavankreddy.bakingapp.Constants_class.ProjectConstants;
import com.blogspot.pavankreddy.bakingapp.R;
import com.blogspot.pavankreddy.bakingapp.uiScreens.StepDetailActivity;
import com.blogspot.pavankreddy.bakingapp.uiScreens.StepDetailFragment;
import com.blogspot.pavankreddy.bakingapp.uiScreens.StepListActivity;
import com.blogspot.pavankreddy.bakingapp.data.Step;

import java.io.Serializable;
import java.util.List;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.ViewInfor>
{

    StepListActivity stepsListActivity;
    List<Step> lists;
    public boolean mTwoPane;

    public StepsListAdapter(StepListActivity context, List<Step> lists, boolean mTwoPane)
    {
        this.stepsListActivity = context;
        this.lists = lists;
        this.mTwoPane = mTwoPane;
    }

    @NonNull
    @Override
    public ViewInfor onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewInfor(LayoutInflater.from(stepsListActivity).inflate(R.layout.steps_list_entry,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewInfor holder, int position)
    {
      holder.steps.setText(lists.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewInfor extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView steps;
        public ViewInfor(View itemView)
        {
            super(itemView);
            steps = itemView.findViewById(R.id.step);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(mTwoPane)
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProjectConstants.STEP_LIST_ACTIVITY_EXTRA_KEY, (Serializable) lists);
                bundle.putInt(ProjectConstants.POSITION_KEY,position);
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setArguments(bundle);
                stepsListActivity.getSupportFragmentManager().beginTransaction().replace(R.id.step_detail_container,stepDetailFragment)
                        .commit();
            }
            else
            {

                Intent intent = new Intent(stepsListActivity, StepDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProjectConstants.STEP_LIST_ACTIVITY_EXTRA_KEY, (Serializable) lists);
                bundle.putInt(ProjectConstants.POSITION_KEY,position);
                intent.putExtra(ProjectConstants.BUNDLE_KEY,bundle);
                stepsListActivity.startActivity(intent);
            }

        }
    }
}
