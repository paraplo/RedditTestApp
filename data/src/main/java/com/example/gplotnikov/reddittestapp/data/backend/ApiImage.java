package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ApiImage {
    @SerializedName("id")
    private final String id;
    @SerializedName("source")
    private final ApiImageMeta source;
    @SerializedName("resolutions")
    private final List<ApiImageMeta> resolutions;

    public ApiImage(String id, ApiImageMeta source, List<ApiImageMeta> resolutions) {
        this.id = id;
        this.source = source;
        this.resolutions = resolutions;
    }

    public String getId() {
        return id;
    }

    public ApiImageMeta getSource() {
        return source;
    }

    public List<ApiImageMeta> getResolutions() {
        return resolutions;
    }
}