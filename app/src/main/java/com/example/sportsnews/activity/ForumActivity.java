package com.example.sportsnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sportsnews.R;
import com.example.sportsnews.model.Forum;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import com.example.sportsnews.adapter.ForumAdapter;
import com.example.sportsnews.model.Forum;
import com.example.sportsnews.util.GlobalData;

public class ForumActivity extends AppCompatActivity {

    private ListView postListView;
    private List<Forum> postList; // 帖子列表
    private Button addPostButton;
    private ForumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        //初始化布局
        Toolbar infoToolbar = findViewById(R.id.coll_toolbar);
        setSupportActionBar(infoToolbar);
        ActionBar infoActionBar = getSupportActionBar();
        if (infoActionBar!=null){
            infoActionBar.setDisplayHomeAsUpEnabled(true);
            infoActionBar.setHomeAsUpIndicator(R.drawable.manches_resized);
            infoActionBar.setTitle("论坛");
        }

        // 初始化视图
        addPostButton = findViewById(R.id.addPostButton);
        postListView = findViewById(R.id.postListView);

        // 初始化帖子列表
        postList = new ArrayList<>();

        // 设置自定义适配器
        adapter = new ForumAdapter(this, R.layout.item_forum, postList);
        postListView.setAdapter(adapter);

        // 加载帖子数据
        loadPosts();

        // 帖子点击事件
        postListView.setOnItemClickListener((parent, view, position, id) -> {
            Forum forum = postList.get(position);
            Intent intent = new Intent(ForumActivity.this, ForumDetailActivity.class);// 用户名保存到全局变量中
//            startActivity(intent);      // 跳转
//            Intent intent = new Intent(IndexActivity.class.newInstance(), ForumDetailActivity.class);
            intent.putExtra("forumId", forum.getId()); // 传递帖子 ID
            intent.putExtra("title", forum.getTitle()); // 传递帖子标题
            intent.putExtra("detail", forum.getContent()); // 传递帖子内容
            startActivity(intent);
        });

        // 跳转到发布帖子页面
        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(ForumActivity.this, AddForumActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 加载数据库中的帖子数据
     */
    private void loadPosts() {
        List<Forum> forums = LitePal.findAll(Forum.class); // 查询所有帖子
        if (forums.isEmpty()) {
            Toast.makeText(this, "暂无帖子", Toast.LENGTH_SHORT).show();
            return;
        }

        postList.clear();
        postList.addAll(forums);

        adapter.notifyDataSetChanged(); // 刷新列表视图
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts(); // 刷新帖子数据
    }
}

