package com.example.sportsnews.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    /* 反序列化名称(对应服务器传来的json字符串中的值) */
    @SerializedName("T1348649079062")
    private List<NewsData> data;

    public List<NewsData> getData() {
        return data;
    }

    public void setData(List<NewsData> data) {
        this.data = data;
    }
}
