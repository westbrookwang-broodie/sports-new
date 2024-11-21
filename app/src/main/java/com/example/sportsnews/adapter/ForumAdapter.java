package com.example.sportsnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sportsnews.R;
import com.example.sportsnews.model.Forum;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ForumAdapter extends ArrayAdapter<Forum> {

    private int resourceId;

    public ForumAdapter(@NonNull Context context, int resource, @NonNull List<Forum> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Forum forum = getItem(position);

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }

        TextView titleTextView = view.findViewById(R.id.postTitleTextView);
        TextView authorTextView = view.findViewById(R.id.postAuthorTextView);
        TextView timeTextView = view.findViewById(R.id.postTimeTextView);

        titleTextView.setText(forum.getTitle());
        authorTextView.setText("作者: " + forum.getAuthor());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String formattedTime = sdf.format(forum.getTimestamp());
        timeTextView.setText("发布时间: " + formattedTime);

        return view;
    }
}
