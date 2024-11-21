package com.example.sportsnews.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsnews.R;
import com.example.sportsnews.adapter.ColorAdapter;
import com.example.sportsnews.model.Forum;
import com.example.sportsnews.model.Jersey;
import com.example.sportsnews.model.Reply;
import com.example.sportsnews.util.GlobalData;
import com.example.sportsnews.util.JerseyView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomizeJerseyActivity extends AppCompatActivity {

    private JerseyView jerseyPreviewImage;
    private Button saveJerseyButton;
    private GridView colorGridView;
    private EditText nameEditText;
    private EditText numberEditText;

    private Jersey jersey;

    private String selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_jersey);

        // 初始化视图
        jerseyPreviewImage = findViewById(R.id.jerseyPreviewText);
        saveJerseyButton = findViewById(R.id.saveJerseyButton);
        colorGridView = findViewById(R.id.colorGridView);
        nameEditText = findViewById(R.id.nameEditText);
        numberEditText = findViewById(R.id.numberEditText);

        loadJersey();

        if(jersey!=null){
            jerseyPreviewImage.setName(jersey.getName());
            jerseyPreviewImage.setNumber(jersey.getNumber());
            jerseyPreviewImage.setJerseyColor(android.graphics.Color.parseColor(jersey.getColor()));
        }


        // 创建颜色列表
        List<String> colorList = Arrays.asList("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF");

        selectedColor = colorList.get(0);

        // 创建并设置适配器
        ColorAdapter colorAdapter = new ColorAdapter(this, R.layout.item_color, colorList);
        colorGridView.setAdapter(colorAdapter);

        colorGridView.setOnItemClickListener((parent, view, position, id) -> {
            // 获取选中的颜色
            String selectedColor = colorList.get(position);
            // 更新球衣预览
            updateJerseyPreview(selectedColor);
        });

        saveJerseyButton.setOnClickListener(v -> {
            // 保存操作
            String name = nameEditText.getText().toString();
            String number = numberEditText.getText().toString();

            // 设置姓名和号码到球衣视图
            jerseyPreviewImage.setName(name);
            jerseyPreviewImage.setNumber(number);
            // 保存队服到数据库
            if (jersey == null){
            Jersey jersey = new Jersey();
            jersey.setNumber(number);
            jersey.setName(name);
            jersey.setUserId(GlobalData.getUsername());
            jersey.setColor(selectedColor);
            jersey.save();
            }
            else {
                jersey.setNumber(number);
                jersey.setName(name);
                jersey.setUserId(GlobalData.getUsername());
                jersey.setColor(selectedColor);
                jersey.save();
            }
            Toast.makeText(this, "队服已保存", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateJerseyPreview(String color) {
        // 将选中的颜色设置给球衣
        int colorInt = android.graphics.Color.parseColor(color);
        jerseyPreviewImage.setJerseyColor(colorInt);
    }

    private void updateJerseyPreview_name(String name) {
        // 将选中的颜色设置给球衣
        jerseyPreviewImage.setName(name);
    }

    private void updateJerseyPreview_number(String number) {
        // 将选中的颜色设置给球衣
        jerseyPreviewImage.setName(number);
    }

    private void loadJersey() {
        List<Jersey> jerseys = LitePal.where("userId = ?", GlobalData.getUsername())
                .find(Jersey.class);
        if(jerseys.isEmpty()){
            return;
        }
        else{
            jersey = jerseys.get(0);
        }
    }
}
