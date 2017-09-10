package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ApiPreview {
    @SerializedName("images")
    private final List<ApiImage> images;
    @SerializedName("enabled")
    private final boolean isEnabled;

    public ApiPreview(List<ApiImage> images, boolean isEnabled) {
        this.images = images;
        this.isEnabled = isEnabled;
    }

    public List<ApiImage> getImages() {
        return images;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}