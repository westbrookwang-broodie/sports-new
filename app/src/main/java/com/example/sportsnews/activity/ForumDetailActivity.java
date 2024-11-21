package com.example.sportsnews.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sportsnews.R;
import com.example.sportsnews.model.Forum;
import com.example.sportsnews.model.Reply;
import com.example.sportsnews.util.GlobalData;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForumDetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView detailTextView;
    private TextView authorTextView;
    private TextView timestampTextView;

    private ListView replyListView;
    private EditText replyEditText;
    private Button submitReplyButton;

    private int forumId;
    private List<Reply> replyList;
    private ReplyAdapter replyAdapter;
    private ArrayList<String> replies;
//    private ArrayAdapter<String> replyAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);

        //初始化布局
        Toolbar infoToolbar = findViewById(R.id.coll_toolbar);
        setSupportActionBar(infoToolbar);
        ActionBar infoActionBar = getSupportActionBar();
        if (infoActionBar!=null){
            infoActionBar.setDisplayHomeAsUpEnabled(true);
            infoActionBar.setHomeAsUpIndicator(R.drawable.manches_resized);
            infoActionBar.setTitle("论坛");
        }

        // 绑定视图
        titleTextView = findViewById(R.id.titleTextView);
        detailTextView = findViewById(R.id.detailTextView);
        authorTextView = findViewById(R.id.authorTextView);
        timestampTextView = findViewById(R.id.timestampTextView);

        replyListView = findViewById(R.id.replyListView);
        replyEditText = findViewById(R.id.replyEditText);
        submitReplyButton = findViewById(R.id.submitReplyButton);

        // 获取传递的帖子 ID
        forumId = getIntent().getIntExtra("forumId", -1);
        if (forumId == -1) {
            Toast.makeText(this, "无法加载帖子", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 加载帖子详情
        loadForumDetails();

        // 加载回复
//        replies = new ArrayList<>();
//        replyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, replies);
//        replyListView.setAdapter(replyAdapter);
        loadReplies();

        // 发布回复
        submitReplyButton.setOnClickListener(v -> postReply());
    }

    /**
     * 加载帖子详情
     */
    private void loadForumDetails() {
        Forum forum = LitePal.find(Forum.class, forumId);
        if (forum == null) {
            Toast.makeText(this, "帖子不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        titleTextView.setText(forum.getTitle());
        detailTextView.setText(forum.getContent());
        authorTextView.setText("作者：" + forum.getAuthor());
        timestampTextView.setText("发布时间：" + formatTimestamp(forum.getTimestamp()));
    }

    /**
     * 加载回复列表
     */
    private void loadReplies() {
        replyList = LitePal.where("forumId = ?", String.valueOf(forumId))
                .order("timestamp asc")  // 根据时间升序排序
                .find(Reply.class);
        replyAdapter = new ReplyAdapter(replyList);
        replyListView.setAdapter(replyAdapter);
    }

    /**
     * 发布回复
     */
    private void postReply() {
        String content = replyEditText.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Reply reply = new Reply();
        reply.setContent(content);
        reply.setForumId((forumId));
        reply.setUserId(GlobalData.getUsername()); // TODO: 替换为真实用户 ID
        reply.setTimestamp(System.currentTimeMillis());

        if (reply.save()) {
            Toast.makeText(this, "回复成功", Toast.LENGTH_SHORT).show();
            replyEditText.setText("");
            loadReplies();
        } else {
            Toast.makeText(this, "回复失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 格式化时间戳
     */
    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    /**
     * 自定义适配器显示回复
     */
    private class ReplyAdapter extends BaseAdapter {
        private final List<Reply> replies;

        public ReplyAdapter(List<Reply> replies) {
            this.replies = replies;
        }

        @Override
        public int getCount() {
            return replies.size();
        }

        @Override
        public Object getItem(int position) {
            return replies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.reply_detail, parent, false);
            }

            TextView replyContentTextView = convertView.findViewById(R.id.replyContentTextView);
            TextView replyMetaTextView = convertView.findViewById(R.id.replyMetaTextView);

            Reply reply = replies.get(position);
            replyContentTextView.setText(reply.getContent());
            replyMetaTextView.setText(
                    "由 " + reply.getUserId() + " 于 " + formatTimestamp(reply.getTimestamp()) + " 发布"
            );

            return convertView;
        }
    }
}
