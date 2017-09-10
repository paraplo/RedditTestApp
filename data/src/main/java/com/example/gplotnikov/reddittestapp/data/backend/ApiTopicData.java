package com.example.gplotnikov.reddittestapp.data.backend;

import com.google.gson.annotations.SerializedName;

class ApiTopicData {
    @SerializedName("title")
    private final String title;
    @SerializedName("author")
    private final String author;
    @SerializedName("created_utc")
    private final long createdAt;
    @SerializedName("thumbnail")
    private final String thumbnailUrl;
    @SerializedName("preview")
    private final ApiPreview preview;
    @SerializedName("num_comments")
    private final int commentsCount;

    public ApiTopicData(String title, String author, long createdAt, String thumbnailUrl, ApiPreview preview, int commentsCount) {
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.thumbnailUrl = thumbnailUrl;
        this.preview = preview;
        this.commentsCount = commentsCount;
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    long getCreatedAt() {
        return createdAt;
    }

    String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ApiPreview getPreview() {
        return preview;
    }

    int getCommentsCount() {
        return commentsCount;
    }
}