package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ApiData<Item> {
    @SerializedName("modhash")
    private final String hash;
    @SerializedName("children")
    private final List<Item> items;
    @SerializedName("after")
    private final String after;
    @SerializedName("before")
    private final String before;

    ApiData(String hash, List<Item> items, String after, String before) {
        this.hash = hash;
        this.items = items;
        this.after = after;
        this.before = before;
    }

    String getHash() {
        return hash;
    }

    List<Item> getItems() {
        return items;
    }

    String getAfter() {
        return after;
    }

    String getBefore() {
        return before;
    }
}