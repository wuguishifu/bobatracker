package com.example.bobatrackerv001;

import java.util.Objects;

public class Order {

    private String order;
    private int date;
    private double price;

    public Order() {

    }

    public Order(String order, int date, double price) {
        this.order = order;
        this.date = date;
        this.price = price;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order1 = (Order) o;
        return date == order1.date &&
                Double.compare(order1.price, price) == 0 &&
                Objects.equals(order, order1.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, date, price);
    }
}
