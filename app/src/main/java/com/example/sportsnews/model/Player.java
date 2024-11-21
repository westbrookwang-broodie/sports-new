package com.example.sportsnews.model;

import org.litepal.crud.LitePalSupport;

public class Player extends LitePalSupport {

    private String userId;
    private int playerId;
    private float x;
    private float y;

    // Constructor
    public Player(int playerId, float x, float y, String userId) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.userId = userId;
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

