package com.dcoders.satishkumar.g.moviesappstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcoders.satishkumar.g.moviesappstage2.R;
import com.dcoders.satishkumar.g.moviesappstage2.modelClasses.VideoData;

import java.util.List;

public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<YoutubeRecyclerAdapter.ViewHolderClass>
{
    public  static final String BASE_YOUTUBE_URL="https://www.youtube.com/watch?v=";
    private List<VideoData> mlist;

    public YoutubeRecyclerAdapter(List<VideoData> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    private Context context;
    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderClass(LayoutInflater.from(context).inflate(R.layout.video_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position)
    {
      holder.name.setText(mlist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name;
        public ViewHolderClass(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.youtubetext);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int pos=getAdapterPosition();
            String key=mlist.get(pos).getKey();
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL+key));
            context.startActivity(intent);

        }
    }
}
