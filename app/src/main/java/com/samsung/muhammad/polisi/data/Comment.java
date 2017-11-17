package com.samsung.muhammad.polisi.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dody on 11/17/17.
 */

public class Comment {

    String id;
    @SerializedName("id_berita")
    String newsId;
    @SerializedName("tanggal")
    String date;
    @SerializedName("nama")
    String name;
    @SerializedName("komentar")
    String comment;
    @SerializedName("foto")
    String imageUrl;

    public Comment(String id, String newsId, String date, String name, String comment, String imageUrl) {
        this.id = id;
        this.newsId = newsId;
        this.date = date;
        this.name = name;
        this.comment = comment;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
