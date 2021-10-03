package com.dcoders.satishkumar.g.moviesappstage2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoders.satishkumar.g.moviesappstage2.R;
import com.dcoders.satishkumar.g.moviesappstage2.modelClasses.ReviewData;

import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>
{
    private List<ReviewData> dataList;

    public ReviewRecyclerAdapter(List<ReviewData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        holder.author.setText(dataList.get(position).getAuthor());
        holder.content.setText(dataList.get(position).getComment());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView author;
        TextView content;
        private ViewHolder(View itemView) {
            super(itemView);
           author=itemView.findViewById(R.id.author_tv);
            content=itemView.findViewById(R.id.comment_tv);
        }
    }
}
