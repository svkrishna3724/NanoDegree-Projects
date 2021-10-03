package com.blogspot.pavankreddy.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.pavankreddy.bakingapp.R;
import com.blogspot.pavankreddy.bakingapp.data.Ingredient;

import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.ViewInfo>{

    Context context;
    List<Ingredient> lists;

    public IngredientsListAdapter(Context context, List<Ingredient> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewInfo(LayoutInflater.from(context).inflate(R.layout.ingredients_entry,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewInfo holder, int position)
    {
        holder.ingredient.setText(lists.get(position).getIngredient());
        holder.measure.setText(lists.get(position).getMeasure());
        holder.quantity.setText(String.valueOf(lists.get(position).getQuantity()));
    }

    @Override
    public int getItemCount()
    {
        return lists.size();
    }

    public class ViewInfo extends RecyclerView.ViewHolder
    {
        TextView ingredient;
        TextView measure,quantity;


        public ViewInfo(View itemView)
        {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient);
            quantity = itemView.findViewById(R.id.quantity);
            measure = itemView.findViewById(R.id.measure);
        }
    }
}
