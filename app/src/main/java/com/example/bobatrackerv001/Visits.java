package com.example.bobatrackerv001;

import java.util.ArrayList;

public class Visits {

    private ArrayList<Order> orders;

    public Visits() {
        this.orders = new ArrayList<>(orders);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

    public void removeOrder(Order o) {
        orders.remove(o);
    }

}
