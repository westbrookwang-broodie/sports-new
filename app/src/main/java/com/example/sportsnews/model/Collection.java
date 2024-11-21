package com.example.sportsnews.model;

import org.litepal.crud.LitePalSupport;

/**
 *  收藏夹实体类
 */

public class Collection extends LitePalSupport {
    private String title;           // 文章标题
    private String author_name;     // 作者名称
    private String date;            // 日期
    private String url;             // 文章链接地址
    private String username;        // 所属用户

    public Collection() {
    }

    public Collection(String title, String author_name, String date, String url, String username) {
        this.title = title;
        this.author_name = author_name;
        this.date = date;
        this.url = url;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
