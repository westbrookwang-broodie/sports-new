package com.example.sportsnews.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.sportsnews.R;
import com.example.sportsnews.model.Collection;
import com.example.sportsnews.util.GlobalData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class BrowseNewsActivity extends AppCompatActivity {

    private WebView webView;
    private FloatingActionButton fab;
    private String title;
    private String time;
    private String source;
    private String url;
    private int starFlag;            // 记录当前文章是否被收藏 num%2==0表示收藏
    private boolean flag = true;        // 标志是否在数据库收藏表中查找当前文章

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_news);

        // 接收新闻列表页面传来的数据
        title = getIntent().getStringExtra("title");
        source = getIntent().getStringExtra("source");
        time = getIntent().getStringExtra("time");
        url = getIntent().getStringExtra("url");
        starFlag = getIntent().getIntExtra("starFlag",1);   // 默认为1(即未收藏)
        Log.d("webUrl", url);

        //初始化布局
        Toolbar toolbar = findViewById(R.id.browse_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fab = findViewById(R.id.float_collection);
        // 如果之前已经收藏则将按钮图片改为收藏样式
        if(starFlag % 2 == 0) {
            Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.star);
            fab.setImageDrawable(drawable);
        }
        // 设置收藏按钮点击监听事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(starFlag % 2 == 1) {          // 表示点击前未收藏(点击后收藏)
                    Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.star);
                    fab.setImageDrawable(drawable);
                    starFlag++;          // 切换收藏状态
                    Collection collection = new Collection(title, source, time, url, GlobalData.getUsername());
                    collection.save();
                    Toast.makeText(BrowseNewsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {                    // 表示点击前已收藏(点击后取消收藏)
                    Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.nostar);
                    fab.setImageDrawable(drawable);
                    starFlag++;
                    // 删除当前用户收藏的当前文章
                    String username = GlobalData.getUsername();
                    LitePal.deleteAll(Collection.class, "title = ? and author_name = ? and username = ?", title, source, username);
                    Toast.makeText(BrowseNewsActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });

        webView = findViewById(R.id.webView);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}