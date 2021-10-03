package com.dcoders.satishkumar.g.moviesappstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dcoders.satishkumar.g.moviesappstage2.movie.Result;
import com.dcoders.satishkumar.g.moviesappstage2.OverView;
import com.dcoders.satishkumar.g.moviesappstage2.R;

import java.io.Serializable;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>
{
    public static final String IMAGE_BASE_URL="http://image.tmdb.org/t/p/w185";
    public static final String SHARE="PASS_KEY";
    private Context mcontext;
    private List<Result> mresult;

    public MainAdapter(Context mcontext, List<Result> mresult) {
        this.mcontext = mcontext;
        this.mresult = mresult;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.grid_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.sample_image.setAdjustViewBounds(false);
        holder.sample_image.setPadding(2,0,4,0);
        Glide.with(mcontext)
                .load(IMAGE_BASE_URL+mresult.get(position).getPosterPath())
                .into(holder.sample_image);

    }

    @Override
    public int getItemCount() {
        return mresult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView sample_image;
        public ViewHolder(View itemView) {
            super(itemView);
            sample_image=itemView.findViewById(R.id.main_activity_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position=getAdapterPosition();
            Result result=mresult.get(position);
            Intent intent=new Intent(mcontext, OverView.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("PASS_KEY", (Serializable) result);
            intent.putExtras(bundle);
            mcontext.startActivity(intent);

        }
    }
}
