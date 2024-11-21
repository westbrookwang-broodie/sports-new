package com.example.sportsnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.sportsnews.R;
import com.example.sportsnews.util.JerseyDrawable;

import java.util.List;

public class ColorAdapter extends BaseAdapter {

    private Context context;
    private List<String> colorList;
    private int layoutResource;

    public ColorAdapter(Context context, int layoutResource, List<String> colorList) {
        this.context = context;
        this.layoutResource = layoutResource;
        this.colorList = colorList;
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public Object getItem(int position) {
        return colorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.jerseyImageView = convertView.findViewById(R.id.jerseyImageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取颜色并设置到球衣形状中
        String color = colorList.get(position);
        int colorInt = android.graphics.Color.parseColor(color);

        // 使用 JerseyDrawable 来显示球衣形状并填充颜色
        JerseyDrawable jerseyDrawable = new JerseyDrawable(colorInt);
        viewHolder.jerseyImageView.setImageDrawable(jerseyDrawable);

        return convertView;
    }

    private static class ViewHolder {
        ImageView jerseyImageView;
    }
}
