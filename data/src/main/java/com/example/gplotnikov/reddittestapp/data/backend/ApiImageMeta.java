package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

class ApiImageMeta {
    @SerializedName("url")
    private final String url;
    @SerializedName("width")
    private final int width;
    @SerializedName("height")
    private final int height;

    public ApiImageMeta(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}