package com.example.sportsnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportsnews.R;
import com.example.sportsnews.model.User;
import com.example.sportsnews.util.GlobalData;

import org.litepal.LitePal;

import java.util.List;

/**
 * 登录页面后台逻辑
 * 功能:
 * 1. 查询数据库user表判断输入的用户名和密码是否正确
 * 2. 跳转注册页面
 */
public class LoginActivity extends AppCompatActivity {

    private EditText loginName;
    private EditText loginPwd;
    private Button loginBtn;
    private Button registerBtn;
    private boolean flag = false;       // 标志是否登录成功

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginName = findViewById(R.id.login_name);
        loginPwd = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_button);
        registerBtn = findViewById(R.id.main_register_button);


        // 登录按钮点击事件
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SQLiteDatabase database = LitePal.getDatabase();
//                System.out.println(database);
                // 查询数据库user表中name和password字段数据
                List<User> users = LitePal.select("name", "password").find(User.class);
                // 如果用户表中还没有数据则提醒用户先进行注册
                if(users.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请先注册", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(TextUtils.isEmpty(loginName.getText().toString())) && !(TextUtils.isEmpty(loginPwd.getText().toString()))) {
                    for (User user : users) {
                        // 查询数据库用户表判断是否有该用户
                        if(loginName.getText().toString().equals(user.getName()) &&
                            loginPwd.getText().toString().equals(user.getPassword())) {
                            flag = true;
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            // 跳转页面并保存该用户的用户名
                            Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                            GlobalData.setUsername(loginName.getText().toString());     // 用户名保存到全局变量中
                            intent.putExtra("username", loginName.getText().toString());
                            startActivity(intent);      // 跳转
                            finish();
                            break;
                        }
                    }
                    if(!flag) {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 注册按钮点击事件
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}