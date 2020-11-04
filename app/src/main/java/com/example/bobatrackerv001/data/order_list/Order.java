package com.example.bobatrackerv001.data.order_list;

public class Order {

    private String order;
    private String location;
    private double price;
    private int date;
    private boolean favorite;

    /**
     * default constructor
     * @param order - string of the order
     * @param price - the price in form $0000.00
     * @param date  - the date in form mmddyy -> august 30 2000: 83000 (leading zeros removed)
     */
    public Order(String order, double price, int date, boolean isFavorite, String location) {
        this.order = order;
        this.price = price;
        this.date = date;
        this.favorite = isFavorite;
        this.location  = location;
    }

    public Order() {}

    public void setOrder(String order) {
        this.order = order;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLocation() {
        return this.location;
    }

    public String getOrder() {
        return this.order;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public double getPrice() {
        return this.price;
    }

    public int getDate() {
        return this.date;
    }
}