package com.example.gplotnikov.reddittestapp.present.top;

import com.example.gplotnikov.reddittestapp.domain.model.Topic;

import java.util.List;

public interface TopEntriesView {
    void onLoaded(List<Topic> topics, boolean isRefreshed);

    void onRefreshing();

    void onLoading();

    void onException(Throwable throwable);
}