package com.example.sportsnews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportsnews.R;
import com.example.sportsnews.model.NewsData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<NewsData> newsDataList;
    // 声明一个上下文的对象（后续的getView()方法当中，会用到LayoutInflater加载XML布局）
    private Context mContext;
    private LruCache<String, Bitmap> mImageCache;

    public NewsAdapter(List<NewsData> newsDataList, Context mContext) {
        this.newsDataList = newsDataList;
        this.mContext = mContext;

        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    //控制该Adapter包含列表项的个数
    @Override
    public int getCount() {
        return newsDataList.size();
    }

    //决定第position处的列表项内容
    @Override
    public Object getItem(int position) {
        return newsDataList.get(position);
    }

    //决定第position处的列表项ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    //决定第position处的列表项组件
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);
            vh = new ViewHolder();
            vh.imageView = convertView.findViewById(R.id.news_pic);
            vh.newsAuthor = convertView.findViewById(R.id.news_author);
            vh.newsTime = convertView.findViewById(R.id.news_time);
            vh.newsTitle = convertView.findViewById(R.id.news_title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

//        TextView newsTitle = convertView.findViewById(R.id.news_title);
//        TextView newsTime = convertView.findViewById(R.id.news_time);
//        TextView newsAuthor = convertView.findViewById(R.id.news_author);
//        ImageView imageView = convertView.findViewById(R.id.news_pic);


        NewsData data = newsDataList.get(position);
        vh.newsTitle.setText(data.getTitle());
        vh.newsTime.setText(data.getTime());
        vh.newsAuthor.setText(data.getSource());

        // 异步加载图片(多线程)
        String imgUrl = data.getImgUrl();
        vh.imageView.setTag(imgUrl);           // 设置标签(URL)防止图片错位

        if ((mImageCache.get(imgUrl)) != null) {
            vh.imageView.setImageBitmap(mImageCache.get(imgUrl));
        } else {
            new ImageLoader().setPicBitmapByThread(imgUrl, vh.imageView);          // 设置图片资源(异步多线程获取)
        }
        return convertView;
    }


    class ViewHolder {
        TextView newsTitle;
        TextView newsTime;
        TextView newsAuthor;
        ImageView imageView;
    }

    class ImageLoader {

        private ImageView imageView;
        private String picUrl;
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (imageView.getTag().equals(picUrl)) {
                    imageView.setImageBitmap((Bitmap) msg.obj);
                }
            }
        };


        // 通过网络访问指定的URL加载图片资源
        public void setPicBitmapByThread(String picUrl, ImageView imageView) {
            this.imageView = imageView;
            this.picUrl = picUrl;
            // 网络连接要在子线程中进行
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获取网络连接(HttpURLConnection)
                        HttpURLConnection conn = (HttpURLConnection) new URL(picUrl).openConnection();
                        conn.connect();
                        // 获取连接中响应的数据(输入流)
                        InputStream inputStream = conn.getInputStream();
                        // 解析成图片资源
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        // 将图片存入缓存
                        if(mImageCache.get(picUrl) == null) {
                            mImageCache.put(picUrl, bitmap);
                        }

                        Message msg = Message.obtain();         // 获取Message对象
                        msg.obj = bitmap;
                        handler.sendMessage(msg);       // 在主线程中更新UI

                        // 关闭流
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
