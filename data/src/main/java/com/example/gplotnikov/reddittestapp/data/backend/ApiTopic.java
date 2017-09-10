package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

class ApiTopic {
    @SerializedName("kind")
    private final String kind;
    @SerializedName("data")
    private final ApiTopicData data;

    ApiTopic(String kind, ApiTopicData data) {
        this.kind = kind;
        this.data = data;
    }

    String getKind() {
        return kind;
    }

    ApiTopicData getData() {
        return data;
    }
}