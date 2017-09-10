package com.example.gplotnikov.reddittestapp.domain.model;

public class Order {
    public static final String AFTER = "after";
    public static final String BEFORE = "before";

    private final String value;
    private final String hash;

    private Order(String value, String hash) {
        this.value = value;
        this.hash = hash;
    }

    public static Order after(String hash) {
        return new Order(AFTER, hash);
    }

    public static Order before(String hash) {
        return new Order(BEFORE, hash);
    }

    public String getValue() {
        return value;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!value.equals(order.value)) return false;
        return hash.equals(order.hash);

    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + hash.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "value='" + value + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}