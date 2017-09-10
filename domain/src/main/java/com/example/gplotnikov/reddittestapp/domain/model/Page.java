package com.example.gplotnikov.reddittestapp.domain.model;

import java.util.Collections;
import java.util.List;

public class Page<Type> {
    private final List<Type> items;
    private final Order order;

    public Page(List<Type> items, Order order) {
        this.items = Collections.unmodifiableList(items);
        this.order = order;
    }

    public List<Type> getItems() {
        return items;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page<?> page = (Page<?>) o;

        if (!items.equals(page.items)) return false;
        return order.equals(page.order);

    }

    @Override
    public int hashCode() {
        int result = items.hashCode();
        result = 31 * result + order.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "items=" + items +
                ", order=" + order +
                '}';
    }
}