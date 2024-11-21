package com.example.sportsnews.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sportsnews.R;
import com.example.sportsnews.model.User;
import com.example.sportsnews.util.GlobalData;

import org.litepal.LitePal;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    final String[] gender = new String[]{"男","女"};
    final String[] city = new String[]{
            "南京市","无锡市","徐州市","常州市","苏州市","南通市","连云港市","淮安市",
            "盐城市","扬州市","镇江市","泰州市","宿迁市"
    };

    private String userName;
    private TextView info_name;
    private TextView info_gender;
    private TextView info_mail;
    private TextView info_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);                   // 设置成actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.logo_small);
            actionBar.setTitle("我的信息");
        }

//        userName = getIntent().getStringExtra("username");      // 获取登录页面传来的用户名
        userName = GlobalData.getUsername();
        info_name = findViewById(R.id.info_name);
        info_gender = findViewById(R.id.info_gender);
        info_mail = findViewById(R.id.info_mail);
        info_location = findViewById(R.id.info_location);

        List<User> users = LitePal.findAll(User.class);
        for (User user : users) {
            if(userName.equals(user.getName())) {
                info_name.setText(user.getName());
                info_gender.setText(user.getGender());
                info_mail.setText(user.getMail());
                info_location.setText(user.getLocation());
            }
        }

        //设置性别
        info_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this).setTitle("请选择你的性别")
                        .setItems(gender, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                info_gender.setText(gender[which].toString());
                                User user_gender = new User();
                                user_gender.setGender(gender[which].toString());
                                user_gender.updateAll("name = ?", userName);
                            }
                        }).show();
            }
        });

        //设置城市
        info_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this).setTitle("请选择你的城市")
                        .setItems(city, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                info_location.setText(city[which].toString());
                                User user_location = new User();
                                user_location.setLocation(city[which].toString());
                                user_location.updateAll("name = ?", userName);
                            }
                        }).show();
            }
        });
    }
}