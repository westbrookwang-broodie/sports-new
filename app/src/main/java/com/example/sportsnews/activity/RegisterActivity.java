package com.example.sportsnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportsnews.R;
import com.example.sportsnews.model.User;

import org.litepal.LitePal;

import java.util.List;

/**
 * 注册页面后台逻辑
 * 功能：向数据库user表中插入用户注册的信息
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName;
    private EditText registerMail;
    private EditText registerPwd;
    private EditText registerPwd2;
    private Button registerBtn;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerName = findViewById(R.id.register_name);
        registerMail = findViewById(R.id.register_mail);
        registerPwd = findViewById(R.id.register_password);
        registerPwd2 = findViewById(R.id.register_password2);
        registerBtn = findViewById(R.id.register_button);

        // 监听注册按钮点击事件
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(registerName.getText().toString()) || TextUtils.isEmpty(registerMail.getText().toString())
                        || TextUtils.isEmpty(registerPwd.getText().toString()) || TextUtils.isEmpty(registerPwd2.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "输入内容不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 查询数据库用户表全部数据获取每一条的name和mail数据
                List<User> users = LitePal.select("name", "mail").find(User.class);
                // 遍历查询到的数据集合，判断用户名或邮箱是否已经被注册过,如果注册过则flag=false
                for (User user : users) {
                    if(registerName.getText().toString().equals(user.getName()) ||
                            registerMail.getText().toString().equals(user.getMail())) {
                        flag = false;
                        break;
                    }
                }

                if(flag)
                {
                    if(!(registerPwd.getText().toString().equals(registerPwd2.getText().toString()))) {
                        Toast.makeText(RegisterActivity.this, "输入的两次密码不一致!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 创建实体类组装数据
                    User user = new User();
                    user.setName(registerName.getText().toString());
                    user.setMail(registerMail.getText().toString());
                    user.setPassword(registerPwd.getText().toString());
                    user.save();        // 向数据库user表中插入该条数据
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "用户名或邮箱已被注册", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}