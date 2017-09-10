package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

class ApiListingResponse<Item> {
    @SerializedName("kind")
    private final String kind;
    @SerializedName("data")
    private final ApiData<Item> data;

    ApiListingResponse(String kind, ApiData<Item> data) {
        this.kind = kind;
        this.data = data;
    }

    String getKind() {
        return kind;
    }

    ApiData<Item> getData() {
        return data;
    }
}