package com.example.gplotnikov.reddittestapp.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gplotnikov.reddittestapp.R;

import java.util.ArrayList;
import java.util.List;

public abstract class RichListRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends ListRecyclerViewAdapter<T, RecyclerView.ViewHolder> {
    private static final CustomItem PROGRESS_FOOTER = new ProgressItem();
    private final List<CustomItem> headers = new ArrayList<>();
    private final List<CustomItem> footers = new ArrayList<>();

    protected static int getCustomItemViewType(CustomItem item) {
        return item.getClass().hashCode();
    }

    public void setShowLoading(boolean isShowLoading) {
        if (isShowLoading && !footers.contains(PROGRESS_FOOTER)) {
            addFooter(PROGRESS_FOOTER);
        }
        if (!isShowLoading) {
            removeFooter(PROGRESS_FOOTER);
        }
    }

    public void addHeader(CustomItem header) {
        headers.add(header);
        notifyDataSetChanged();
    }

    public void removeHeader(CustomItem header) {
        if (headers.remove(header)) {
            notifyDataSetChanged();
        }
    }

    public void addFooter(CustomItem footer) {
        footers.add(footers.size(), footer);
        notifyDataSetChanged();
    }

    public void removeFooter(CustomItem footer) {
        if (footers.remove(footer)) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return headers.size() + size() + footers.size();
    }

    @Override
    public final int getItemViewType(int position) {
        if (!headers.isEmpty() && position < headers.size()) {
            return getCustomItemViewType(headers.get(position));
        }
        position = position - headers.size();
        if (position < size()) {
            return getAdapterItemViewType(position);
        }
        position = position - size();
        return getCustomItemViewType(footers.get(position));
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (CustomItem header : headers) {
            if (viewType == getCustomItemViewType(header)) {
                return onCreateHeaderViewHolder(parent, header);
            }
        }
        for (CustomItem footer : footers) {
            if (viewType == getCustomItemViewType(PROGRESS_FOOTER)) {
                return new ProgressViewHolder(parent);
            }
            if (viewType == getCustomItemViewType(footer)) {
                return onCreateFooterViewHolder(parent, footer);
            }
        }
        return onCreateItemViewHolder(parent, viewType);
    }

    protected int getAdapterItemViewType(int position) {
        return 0;
    }

    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, CustomItem item) {
        return new DummyViewHolder(parent);
    }

    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, CustomItem item) {
        return new DummyViewHolder(parent);
    }

    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindItemViewHolder(VH holder, int position);


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!headers.isEmpty() && position < headers.size()) {
            onBindHeaderViewHolder(holder, position);
            return;
        }
        position = position - headers.size();
        if (position < size()) {
            onBindItemViewHolder((VH) holder, position);
            return;
        }
        position = position - size();
        if (!footers.isEmpty()) {
            onBindFooterViewHolder(holder, position);
        }
    }

    public static class ProgressItem extends CustomItem {

    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_progress, parent, false));
        }
    }

    public static class DummyViewHolder extends RecyclerView.ViewHolder {
        public DummyViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
            TextView textView = itemView.findViewById(android.R.id.text1);
            textView.setText("Implement me!");
        }
    }

    public static class CustomItem {
        @Override
        public int hashCode() {
            return getClass().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return getClass().equals(obj.getClass());
        }
    }
}
