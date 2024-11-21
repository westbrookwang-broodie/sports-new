package com.example.sportsnews.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sportsnews.R;
import com.example.sportsnews.adapter.NewsAdapter;
import com.example.sportsnews.model.Collection;
import com.example.sportsnews.model.News;
import com.example.sportsnews.model.NewsData;
import com.example.sportsnews.util.GlobalData;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsListActivity extends AppCompatActivity {

    private List<NewsData> newsDataList;            // 新闻数据集合
    private ListView newsListView;
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipeRefresh;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Log.d("tips:", "进入信息页面");

        // 顶部工具栏布局
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.manches_resized);
        }

        newsDataList = new ArrayList<NewsData>();
        /* 创建适配器(将解析出的新闻数据集合传入)
        在adapter中的数据更新之后，只要之后有对listview进行操作。listview中的数据会自动更新 */
        newsAdapter = new NewsAdapter(newsDataList, this);
        newsListView = findViewById(R.id.news_list);
        // 为页面中的ListView设置适配器
        newsListView.setAdapter(newsAdapter);
        //点击列表项跳转页面
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                boolean flag = false;
                NewsData data = newsDataList.get(position);
                Intent intent = new Intent(NewsListActivity.this, BrowseNewsActivity.class);
                intent.putExtra("title", data.getTitle());
                intent.putExtra("time", data.getTime());
                intent.putExtra("source", data.getSource());
                intent.putExtra("url", data.getUrl());
                intent.putExtra("imgUrl", data.getImgUrl());
                // 查询数据库收藏表 判断该文章是否已被当前用户收藏
                String username = GlobalData.getUsername();
                List<Collection> collections = LitePal.select("title", "author_name", "date", "url").where("username = ?", username).find(Collection.class);
                for (Collection collection : collections) {
                    if(data.getTitle().equals(collection.getTitle())
                            && data.getSource().equals(collection.getAuthor_name())) {
                        flag = true;
                    }
                }
                if(flag) {
                    intent.putExtra("starFlag", 2);     // 表示已收藏
                } else {
                    intent.putExtra("starFlag", 1);     // 表示未收藏
                }
                startActivity(intent);
            }
        });

        // 下拉刷新操作
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(R.color.red);
        swipeRefresh.setRefreshing(true);               // 第一次进入页面时刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsDataList.clear();       // 先清空集合数据
                sendRequestByUrl();         // 重新获取数据
            }
        });

        sendRequestByUrl();                 // 向服务器发送请求(加载数据到页面中)
    }

    // 从网络中抓取信息并解析
    private void sendRequestByUrl() {
        // 网络连接要在线程中进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // 通过builder创建出要访问的http请求
                    Request request = new Request.Builder()
                            .url("https://c.3g.163.com/nc/article/list/T1348649079062/0-20.html")
                            .build();
                    // 发送请求并获取响应
                    Response response = client.newCall(request).execute();
                    /* (BUG) 获取服务器返回的json字符串要调用.string()方法而不是toString() */
                    String responseData = response.body().string();
//                    Log.d("tips:", responseData);
                    // 解析响应的json字符串信息并添加到数据集合newsDataList中
                    parseJsonByGson(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 更新 adapter即新闻页中数据(必须在主线程中更新UI)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.notifyDataSetChanged();     //主动通知adapter中数据更新从而更新视图
                        swipeRefresh.setRefreshing(false);      // 停止刷新动画
                    }
                });
            }
        }).start();                 // [BUG] 开启线程
    }

    private void parseJsonByGson(String jsonStr)
    {
        // 使用gson进行解析
        Gson gson = new Gson();
        News news = gson.fromJson(jsonStr, News.class);
        List<NewsData> dataList = news.getData();
        for (int i = 0; i < dataList.size(); i++) {
            String sourceId = dataList.get(i).getSourceId();
            String title = dataList.get(i).getTitle();
            String time = dataList.get(i).getTime();
            String source = dataList.get(i).getSource();
            String url = dataList.get(i).getUrl();
            String imgUrl = dataList.get(i).getImgUrl();

//            System.out.println("日期："+ time);
//            System.out.println("作者："+ source);
//            System.out.println("标题："+ title);
//            System.out.println("网址："+ url);
//            System.out.println("图片："+ imgUrl);
//            System.out.println("=====================================");
            // 过滤掉文章URL为空的数据
            if(url != null && !("".equals(url))) {
                // 将解析的一条新闻的每一个属性封装成NewsData类添加到集合中(更新adapter中newsDataList的数据)
                newsDataList.add(new NewsData(sourceId, title, time, source, url, imgUrl));
            }
        }
    }

}