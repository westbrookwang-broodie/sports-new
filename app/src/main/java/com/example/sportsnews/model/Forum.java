package com.example.sportsnews.model;

import org.litepal.crud.LitePalSupport;

public class Forum extends LitePalSupport {
    private int id;    // id

    private String title;       // 标题

    private String userId;     //作者

    private String content;   //内容

    private long timestamp;

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return userId;
    }

    public void setAuthor(String author) {
        this.userId = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


}
