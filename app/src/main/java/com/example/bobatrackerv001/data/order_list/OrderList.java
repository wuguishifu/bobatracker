package com.example.bobatrackerv001.data.order_list;

import java.util.ArrayList;

public class OrderList {

    public static ArrayList<Order> orders = new ArrayList<>();
    public static boolean hasAlreadyLoaded = false;


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
