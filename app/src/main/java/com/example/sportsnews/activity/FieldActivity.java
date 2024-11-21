package com.example.sportsnews.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.List;
import org.litepal.LitePal;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsnews.R;
import com.example.sportsnews.model.Player;
import com.example.sportsnews.util.GlobalData;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class FieldActivity extends AppCompatActivity {

    private RelativeLayout playerContainer;
    private Map<Integer, float[]> playerPositions = new HashMap<>();
    private static final int PLAYER_COUNT = 11; // 11 名球员
    private List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        playerContainer = findViewById(R.id.player_container);

        loadPlayers();

        if (players.isEmpty()){
            // 动态添加球员图标
            for (int i = 0; i < PLAYER_COUNT; i++) {
                addPlayer(i);
            }
        }


        // 保存按钮功能
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFormation();
            }
        });
    }

    private void loadPlayers() {
        players = LitePal.where("userId = ?", GlobalData.getUsername()).find(Player.class);

        if(players.isEmpty()){
            return;
        }

        // Loop through the players and set their positions based on the saved data
        for (Player player : players) {
            // Get the player ID, x, and y coordinates
            int playerId = player.getPlayerId();
            float x = player.getX();
            float y = player.getY();

            // Add the player at the saved position
            addPlayerAtPosition(playerId, x, y);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void addPlayerAtPosition(final int playerId, float x, float y) {
        // Create player icon
        ImageView player = new ImageView(this);
        player.setId(View.generateViewId());
        player.setImageResource(R.drawable.player);

        // Set the player's position based on the x and y coordinates
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.leftMargin = (int) x;  // Set the horizontal position
        params.topMargin = (int) y;   // Set the vertical position
        player.setLayoutParams(params);

        // Add player to the container
        playerContainer.addView(player);

        // Set touch listener for dragging the player
        player.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        v.setX(event.getRawX() + dX);
                        v.setY(event.getRawY() + dY);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Save the new position
                        playerPositions.put(playerId, new float[]{v.getX(), v.getY()});
                        break;
                }
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addPlayer(final int index) {
        // 创建球员图标
        ImageView player = new ImageView(this);
        player.setId(View.generateViewId());
        player.setImageResource(R.drawable.player);

        // 设置初始位置和大小
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.leftMargin = 200 + index * 50; // 初始水平位置
        params.topMargin = 400; // 初始垂直位置
        player.setLayoutParams(params);

        // 添加到容器
        playerContainer.addView(player);

        // 设置拖拽监听器
        player.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        v.setX(event.getRawX() + dX);
                        v.setY(event.getRawY() + dY);
                        break;

                    case MotionEvent.ACTION_UP:
                        // 保存当前位置
                        playerPositions.put(index, new float[]{v.getX(), v.getY()});
                        break;
                }
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setPlayerTouchListener(final ImageView player) {
        player.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        v.setX(event.getRawX() + dX);
                        v.setY(event.getRawY() + dY);
                        break;

                    case MotionEvent.ACTION_UP:
                        // 保存当前位置
                        playerPositions.put(v.getId(), new float[]{v.getX(), v.getY()});
                        break;
                }
                return true;
            }
        });
    }

    private void saveFormation() {
        try {
            // Loop through player positions and save them to the database
            for (Map.Entry<Integer, float[]> entry : playerPositions.entrySet()) {
                int playerId = entry.getKey();
                float x = entry.getValue()[0];
                float y = entry.getValue()[1];

                // Create a new Player object
                Player player = new Player(playerId, x, y, GlobalData.getUsername());

                // Save the player object to the database
                player.save();
            }

            // Optionally, you can display a success message or a toast
            Toast.makeText(this, "Formation saved successfully!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, show an error message or handle the exception
            Toast.makeText(this, "Error saving formation!", Toast.LENGTH_SHORT).show();
        }
    }
}
