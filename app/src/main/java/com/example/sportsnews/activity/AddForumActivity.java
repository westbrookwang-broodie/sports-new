package com.example.sportsnews.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsnews.R;
import com.example.sportsnews.model.Forum;
import com.example.sportsnews.util.GlobalData;


public class AddForumActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button submitButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddForumActivity.this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 保存帖子到数据库
                Forum post = new Forum();
                post.setTitle(title);
                post.setContent(content);
                post.setAuthor(GlobalData.getUsername());
                post.setTimestamp(System.currentTimeMillis());
                post.save();

                Toast.makeText(AddForumActivity.this, "帖子发布成功！", Toast.LENGTH_SHORT).show();
                finish(); // 关闭当前活动，返回主界面
            }
        });
    }
}
