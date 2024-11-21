package com.example.sportsnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsnews.R;
import com.example.sportsnews.activity.BrowseNewsActivity;
import com.example.sportsnews.model.Collection;

import java.util.List;

public class CollAdapter extends RecyclerView.Adapter<CollAdapter.ViewHolder>{
    private List<Collection> collectionList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View collView;
        TextView title;
        TextView author_name;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
            collView = itemView;
            title = itemView.findViewById(R.id.coll_title);
            author_name = itemView.findViewById(R.id.coll_author_name);
            date = itemView.findViewById(R.id.coll_date);
        }
    }

    public CollAdapter(List<Collection> collectionList, Context context) {
        this.collectionList = collectionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.collView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Collection news = collectionList.get(position);
                Intent intent = new Intent(context, BrowseNewsActivity.class);
                intent.putExtra("title",news.getTitle());
                intent.putExtra("source",news.getAuthor_name());
                intent.putExtra("time",news.getDate());
                intent.putExtra("url",news.getUrl());
                intent.putExtra("starFlag",2);      // 从收藏页面跳转,文章状态为已收藏
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection coll = collectionList.get(position);
        holder.title.setText(coll.getTitle());
        holder.author_name.setText(coll.getAuthor_name());
        holder.date.setText(coll.getDate());
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }


}
