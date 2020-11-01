package com.example.bobatrackerv001.data.user;

import com.example.bobatrackerv001.data.order_list.OrderHistory;

public class User {

    public String password;

    public OrderHistory orderHistory;

    public User(String password, OrderHistory orderHistory) {
        this.password = password;
        this.orderHistory = orderHistory;
    }

}
