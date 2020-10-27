package com.example.bobatrackerv001.order_list;

import java.util.ArrayList;

public class OrderList {

    public static ArrayList<Order> orders = new ArrayList<>();

//    public static void init() {
//        orders = new ArrayList<Order>;
//    }

    /**
     * gets a specific order
     * @param index - the index of the order
     * @return the specific order at that index
     */
    public static Order getOrder(int index) {
        return orders.get(index);
    }

    public static void addOrder(Order o) {
        orders.add(o);
    }
}
