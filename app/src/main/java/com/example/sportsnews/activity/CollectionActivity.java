package com.example.sportsnews.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sportsnews.R;
import com.example.sportsnews.adapter.CollAdapter;
import com.example.sportsnews.model.Collection;
import com.example.sportsnews.util.GlobalData;

import org.litepal.LitePal;

import java.util.List;

public class CollectionActivity extends AppCompatActivity {
    private List<Collection> collectionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        //初始化布局
        Toolbar infoToolbar = findViewById(R.id.coll_toolbar);
        setSupportActionBar(infoToolbar);
        ActionBar infoActionBar = getSupportActionBar();
        if (infoActionBar!=null){
            infoActionBar.setDisplayHomeAsUpEnabled(true);
            infoActionBar.setHomeAsUpIndicator(R.mipmap.logo_small);
            infoActionBar.setTitle("我的收藏");
        }

        // 查询当前用户(*)的收藏数据
        String username = GlobalData.getUsername();
        collectionList = LitePal.select("title", "author_name", "date", "url").where("username = ?", username).find(Collection.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CollAdapter collAdapter = new CollAdapter(collectionList, CollectionActivity.this);
        recyclerView.setAdapter(collAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 查询当前用户(*)的收藏数据
        String username = GlobalData.getUsername();
        collectionList = LitePal.select("title", "author_name", "date", "url").where("username = ?", username).find(Collection.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CollAdapter collAdapter = new CollAdapter(collectionList, CollectionActivity.this);
        recyclerView.setAdapter(collAdapter);
    }
}