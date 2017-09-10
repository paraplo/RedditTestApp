package com.example.gplotnikov.reddittestapp.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    private int count;
    private boolean isEnabled;

    public LoadMoreScrollListener(RecyclerView.LayoutManager layoutManager, int count) {
        this.mLayoutManager = layoutManager;
        this.count = count;
        if (layoutManager instanceof GridLayoutManager) {
            this.count = getVisibleThreshold(((GridLayoutManager) layoutManager).getSpanCount());
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            this.count = getVisibleThreshold(((StaggeredGridLayoutManager) layoutManager).getSpanCount());
        }
    }

    private int getVisibleThreshold(int spanCount) {
        return count * spanCount;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }
        if (isEnabled && (lastVisibleItemPosition + count) > totalItemCount) {
            onLoadMore();
        }
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public abstract void onLoadMore();
}