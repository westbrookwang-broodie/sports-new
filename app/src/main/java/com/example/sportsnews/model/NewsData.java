package com.example.sportsnews.model;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

/**
 * 新闻信息类(对应网络中获取的json对象)
 * json对象反序列化为该类
 */
public class NewsData extends LitePalSupport {

    private String sourceId;    // 资源id

    private String title;       // 标题

    @SerializedName("mtime")
    private String time;       // 时间

    private String source;      // 作者(来源)

    private String url;         // 文章地址

    @SerializedName("imgsrc")
    private String imgUrl;      // 图片资源地址

    public NewsData() {
    }

    public NewsData(String sourceId, String title, String time, String source, String url, String imgUrl) {
        this.sourceId = sourceId;
        this.title = title;
        this.time = time;
        this.source = source;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
