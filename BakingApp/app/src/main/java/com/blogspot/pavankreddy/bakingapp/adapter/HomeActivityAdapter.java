package com.blogspot.pavankreddy.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.pavankreddy.bakingapp.Constants_class.ProjectConstants;
import com.blogspot.pavankreddy.bakingapp.R;
import com.blogspot.pavankreddy.bakingapp.uiScreens.StepListActivity;
import com.blogspot.pavankreddy.bakingapp.data.Recipe;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class HomeActivityAdapter extends RecyclerView.Adapter<HomeActivityAdapter.ViewInformation>
{

    Context context;
    List<Recipe> list;
    public HomeActivityAdapter(Context context, List<Recipe> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewInformation onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewInformation(LayoutInflater.from(context).inflate(R.layout.recipie_details_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewInformation holder, int position)
    {
        holder.recipeNames.setText(list.get(position).getName());
        if(!TextUtils.isEmpty(list.get(position).getImage()))
        {
            Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewInformation extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView recipeNames;
        ImageView imageView;
        public ViewInformation(View itemView) {
            super(itemView);
            recipeNames = itemView.findViewById(R.id.recipe_title);
            imageView = itemView.findViewById(R.id.home_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, StepListActivity.class);
            intent.putExtra(ProjectConstants.RECIPIE_TITLE,list.get(position).getName());
            intent.putExtra(ProjectConstants.INGREDIENTS_LIST_KEY, (Serializable) list.get(position).getIngredients());
            intent.putExtra(ProjectConstants.STEPS_LIST_KEY, (Serializable) list.get(position).getSteps());
            context.startActivity(intent);
        }
    }
}