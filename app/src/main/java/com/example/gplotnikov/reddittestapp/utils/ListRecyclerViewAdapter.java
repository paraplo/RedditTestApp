package com.example.gplotnikov.reddittestapp.utils;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ListRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (items.add(item)) {
            notifyItemInserted(items.size() - 1);
        }
    }

    public void remove(T item) {
        int index = items.indexOf(item);
        if (items.remove(item)) {
            notifyItemRemoved(index);
        }
    }

    public void addAll(Collection<T> items) {
        if (this.items.addAll(items)) {
            notifyDataSetChanged();
        }
    }

    public T replace(int position, T item) {
        try {
            T t = items.get(position);
            items.remove(position);
            items.add(position, item);
            notifyItemChanged(position);
            return t;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(Collection<T> items) {
        this.items.clear();
        addAll(items);
    }

    public T get(int position) {
        return items.get(position);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    @Override
    public int getItemCount() {
        return size();
    }
}