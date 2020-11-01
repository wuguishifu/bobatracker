package com.example.bobatrackerv001.data.order_list;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {

    public List<Order> orders;

    public OrderHistory() {
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return this.orders;
    }
}
